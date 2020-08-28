package model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import util.DateFormatters;

@Entity
@Table(name = "Tarif")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Tarif {
	@Id
	@Column(name = "Code")
	private String code;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "HeureDebut")
	private Date heureDebut;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "HeureFin")
	private Date heureFin;

	@Column(name = "PrixKWH")
	private float prixKWh;
	
	@Column(name = "reduction")
	private float reduction;

	public Tarif(String code, Date heureDebut, Date heureFin, float prixKWh, float reduction) {
		this.code = code;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.prixKWh = prixKWh;
		this.reduction = reduction;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat timeFormatter = DateFormatters.getTimeFormatter();
		
		return "\nTarif :" +
			"\n\t- Code : " + getCode() +
			"\n\t- Heure de début : " + timeFormatter.format(getHeureDebut()) +
			"\n\t- Heure de fin : " + timeFormatter.format(getHeureFin()) +
			"\n\t- Prix (KWh) : " + getPrixKWh() +
			"\n\t- Réduction (%) : " + getReduction() * 100;
	}
	
	public static ArrayList<Tarif> trierTarifs(ArrayList<Tarif> tarifs) throws Exception {
		verifierCompatibilite(tarifs);
		
		Collections.sort(tarifs, 
			(tarifA, tarifB) -> tarifA.getHeureDebut().compareTo(tarifB.getHeureDebut())
		);

		return tarifs;
	}
	
	public float appliquerSurDuree(long duree) {
		return duree * prixKWh * (1 - reduction);
	}
	
	public static void verifierCompatibilite(ArrayList<Tarif> tarifs) throws Exception {
		for (Tarif tarifA : tarifs) {
			long timeDebutA = tarifA.getHeureDebut().getTime();
			long timeFinA = tarifA.getHeureFin().getTime();
			
			for (Tarif tarifB : tarifs) {
				if (tarifB != tarifA) {
					long timeDebutB = tarifB.getHeureDebut().getTime();
					long timeFinB = tarifB.getHeureFin().getTime();
					
					boolean compatiblesLocal = (
						(timeDebutB < timeDebutA && timeFinB < timeDebutA) ||
						(timeDebutB > timeFinA && timeFinB > timeFinA)
					);
					
					if (!compatiblesLocal) {
						throw new Exception(
							"Les tarifs "
							+ tarifA.getCode()
							+ " et "
							+ tarifB.getCode()
							+ " ont des créneaux horaires incompatibles"
						);
					}
				}
			}
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(Date heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Date getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(Date heureFin) {
		this.heureFin = heureFin;
	}

	public float getPrixKWh() {
		return prixKWh;
	}

	public void setPrixKWh(float prixKWh) {
		this.prixKWh = prixKWh;
	}
	
	public float getReduction() {
		return reduction;
	}

	public void setReduction(float reduction) {
		this.reduction = reduction;
	}
}
