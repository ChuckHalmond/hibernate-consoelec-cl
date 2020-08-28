package model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import util.DateFormatters;

@Entity
@Table(name = "CompteurElectrique")
public class CompteurElectrique {
	@Id
	@Column(name = "Numero")
	private int numero;
	
	@OneToOne
	@JoinColumn(name = "Adresse")
	private Adresse adresse;
	
	@OneToOne
	@JoinColumn(name = "Proprietaire")
	private Personne proprietaire;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateActivation")
	private Date dateActivation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateDerniereConsommation")
	private Date dateDerniereConsommation;
	
	@Column(name = "ConsommationMoyenneKWh")
	private float consommationMoyenneKWh;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="compteurElectrique")
	@Column(name = "PlagesConsommations")
	private List<PlageConsommation> plagesConsommations;

	public CompteurElectrique(int numero, Adresse adresse, Personne proprietaire, 
			Date dateActivation, float consommationMoyenneKWh, ArrayList<PlageConsommation> plagesConsommations) {
		this.numero = numero;
		this.adresse = adresse;
		this.proprietaire = proprietaire;
		this.dateActivation = dateActivation;
		this.dateDerniereConsommation = dateActivation;
		this.consommationMoyenneKWh = consommationMoyenneKWh;
		this.plagesConsommations = plagesConsommations;
	}
	
	public CompteurElectrique(int numero, Adresse adresse, Personne proprietaire, 
			Date dateActivation, float consommationMoyenneKWh) {
		this.numero = numero;
		this.adresse = adresse;
		this.proprietaire = proprietaire;
		this.dateActivation = dateActivation;
		this.dateDerniereConsommation = dateActivation;
		this.consommationMoyenneKWh = consommationMoyenneKWh;
		this.plagesConsommations = new ArrayList<PlageConsommation>();
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateTimeFormatter = DateFormatters.getDateTimeFormatter();
		
		String string = "Compteur électrique :" +
			"\n\t- Numéro : " + getNumero() +
			"\n\t- Date d'activation : " + dateTimeFormatter.format(getDateActivation()) +
			"\n\t- Date de dernière consommation : " + dateTimeFormatter.format(getDateDerniereConsommation()) +
			getAdresse() +
			getProprietaire();

		
		for (PlageConsommation plageConsommation : getPlagesConsommations()) {
			string += plageConsommation;
		}
		
		return string;
	}

	public void fonctionner(Date dateDebut, Date dateFin, ArrayList<Tarif> tarifs) throws Exception {
		SimpleDateFormat formatDateHeure = DateFormatters.getDateTimeFormatter();
		Tarif.trierTarifs(tarifs);
		
		if (dateDebut.getTime() >= dateDerniereConsommation.getTime()) {
			if (dateFin.getTime() >= dateDebut.getTime()) {
				PlageConsommation plageConsommation = new PlageConsommation(this, dateDebut, dateFin);
				plageConsommation.genererPlagesHorairesSelonTarif(tarifs);
				plagesConsommations.add(plageConsommation);
				dateDerniereConsommation = dateFin;
			}
			else {
				throw new Exception(
					"Impossible de lancer le fonctionnement du compteur : "
					+ formatDateHeure.format(dateDebut)
					+ " (date de début) est postérieure à "
					+ formatDateHeure.format(dateFin)
					+ " (date de fin)"
				);
			}
		}
		else {
			throw new Exception(
				"Impossible de lancer le fonctionnement du compteur : "
				+ formatDateHeure.format(dateDebut)
				+ " (date de début) doit être postérieure à "
				+ formatDateHeure.format(dateDerniereConsommation)
				+ " (date de dernière consommation)"
			);
		}
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Personne getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Personne proprietaire) {
		this.proprietaire = proprietaire;
	}

	public Date getDateActivation() {
		return dateActivation;
	}

	public void setDateActivation(Date dateActivation) {
		this.dateActivation = dateActivation;
	}
	
	public List<PlageConsommation> getPlagesConsommations() {
		return plagesConsommations;
	}

	public void setPlagesConsommations(List<PlageConsommation> plagesConsommations) {
		this.plagesConsommations = plagesConsommations;
	}
	
	public Date getDateDerniereConsommation() {
		return dateDerniereConsommation;
	}

	public void setDateDerniereConsommation(Date dateDerniereConsommation) {
		this.dateDerniereConsommation = dateDerniereConsommation;
	}

	public float getConsommationMoyenneKWh() {
		return consommationMoyenneKWh;
	}

	public void setConsommationMoyenneKWh(float consommationMoyenneKWh) {
		this.consommationMoyenneKWh = consommationMoyenneKWh;
	}
}
