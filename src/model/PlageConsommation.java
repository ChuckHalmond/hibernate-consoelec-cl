package model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import util.DateFormatters;

@Entity
@Table(name = "PlageConsommation")
public class PlageConsommation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PlageConsommationID")
	private int plageConsommationID;
	
	@ManyToOne
	@JoinColumn(name = "CompteurElectrique")
	private CompteurElectrique compteurElectrique;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="plageConsommation")
	@Column(name = "PlagesHoraires")
	private List<PlageHoraire> plagesHoraires;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateDebut")
	private Date dateDebut;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateFin")
	private Date dateFin;
	
	@Column(name = "TotalConsommation")
	private float totalConsommation;

	public PlageConsommation(CompteurElectrique compteurElectrique, Date dateDebut, Date dateFin) {
		this.compteurElectrique = compteurElectrique;
		this.plagesHoraires = new ArrayList<PlageHoraire>();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.totalConsommation = 0;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateTimeFormatter = DateFormatters.getDateTimeFormatter();
		
		String string = "\nPlageConsommation :" +
			"\n\t- ID : " + getPlageConsommationID() +
			"\n\t- Numéro compteur : " + getCompteurElectrique().getNumero() +
			"\n\t- Date de début : " + dateTimeFormatter.format(getDateDebut()) +
			"\n\t- Date de fin : " + dateTimeFormatter.format(getDateFin()) +
			"\n\t- Consommation totale (KWh) : " + getTotalConsommation();
		
		for (PlageHoraire plageHoraire : getPlagesHoraires()) {
			string += plageHoraire;
		}
		
		return string;
	}

	public List<PlageHoraire> genererPlagesHorairesSelonTarif(ArrayList<Tarif> tarifs) {
		long msPerDay = (long)(8.64 * Math.pow(10d, 7d));

		long heureDebutConso = dateDebut.getTime() % msPerDay - (dateDebut.getTime() % 1000);
		long jourDebutConso = dateDebut.getTime() - heureDebutConso;
		long heureFinConso = dateFin.getTime() % msPerDay - (dateFin.getTime() % 1000);
		long jourFinConso = dateFin.getTime() - heureFinConso;

		long dureePlageHoraire = 0;
		float prixPlageHoraire = 0;
		
		// Le premier jour est un cas particulier
		boolean premierTarifComptaibleTrouve = false;
		for (Tarif tarif : tarifs) {
			long heureDebutTarif = tarif.getHeureDebut().getTime();
			long heureFinTarif = tarif.getHeureFin().getTime();
			
			// On commence au premier tarif du premier jour
			if (heureFinTarif >= heureDebutConso && heureDebutTarif <= heureDebutConso || premierTarifComptaibleTrouve) {
				premierTarifComptaibleTrouve = true;
				Date dateDebutPlage = new Date(jourDebutConso + heureDebutConso);
				Date dateFinPlage = new Date(jourDebutConso + heureFinTarif);
				
				dureePlageHoraire = heureFinTarif - heureDebutConso;
				prixPlageHoraire = tarif.appliquerSurDuree(dureePlageHoraire);
				
				totalConsommation += prixPlageHoraire;
				plagesHoraires.add(
					new PlageHoraire(
						this,
						dateDebutPlage,
						dateFinPlage,
						tarif,
						prixPlageHoraire
					)
				);
				
				if (tarif == tarifs.get(tarifs.size() - 1)) {
					jourDebutConso = jourDebutConso + msPerDay;
					heureDebutConso = 0;
				}
				else {
					heureDebutConso = heureFinTarif;
				}
			}
		}
		
		mainLoop:
		while (jourDebutConso <= jourFinConso) {
			// Jours intermédiaires
			if (jourDebutConso < jourFinConso) {
				for (Tarif tarif : tarifs) {
					long heureDebutTarif = tarif.getHeureDebut().getTime();
					long heureFinTarif = tarif.getHeureFin().getTime();
					
					Date dateDebutPlage = new Date(jourDebutConso + heureDebutTarif);
					Date dateFinPlage = new Date(jourDebutConso + heureFinTarif);
					
					dureePlageHoraire = heureFinTarif - heureDebutTarif;
					prixPlageHoraire = tarif.appliquerSurDuree(dureePlageHoraire);
					
					totalConsommation += prixPlageHoraire;
					plagesHoraires.add(
						new PlageHoraire(
							this,
							dateDebutPlage,
							dateFinPlage,
							tarif,
							prixPlageHoraire
						)
					);
					
					heureDebutConso = heureFinTarif;
					if (tarif == tarifs.get(tarifs.size() - 1)) {
						jourDebutConso = jourDebutConso + msPerDay;
					}
				}
			}
			else { 	// Le dernier jour est un cas particulier
				for (Tarif tarif : tarifs) {
					long heureDebutTarif = tarif.getHeureDebut().getTime();
					long heureFinTarif = tarif.getHeureFin().getTime();
					
					Date dateDebutPlage = new Date(jourDebutConso + heureDebutTarif);
					Date dateFinPlage = new Date(jourDebutConso + heureFinTarif);
				
					// Le dernier tarif du dernier jour
					if (heureDebutTarif < heureFinConso && heureFinTarif > heureFinConso) {
						dureePlageHoraire = heureFinConso - heureDebutTarif;
						prixPlageHoraire = tarif.appliquerSurDuree(dureePlageHoraire);
						
						totalConsommation += prixPlageHoraire;
						plagesHoraires.add(
							new PlageHoraire(
								this,
								dateDebutPlage,
								dateFin,
								tarif,
								prixPlageHoraire
							)
						);

						break mainLoop;
					}
					else {
						dureePlageHoraire = heureFinTarif - heureDebutTarif;
						prixPlageHoraire = tarif.appliquerSurDuree(dureePlageHoraire);
						
						totalConsommation += prixPlageHoraire;
						plagesHoraires.add(
							new PlageHoraire(
								this,
								dateDebutPlage,
								dateFinPlage,
								tarif,
								prixPlageHoraire
							)
						);
						
						if (tarif == tarifs.get(tarifs.size() - 1)) {
							jourDebutConso = jourDebutConso + msPerDay;
							heureDebutConso = 0;
						}
						else {
							heureDebutConso = heureFinTarif;
						}
					}
				}
			}
		}
		
		return plagesHoraires;
	}

	public int getPlageConsommationID() {
		return plageConsommationID;
	}

	public void setPlageConsommationID(int plageConsommationID) {
		this.plageConsommationID = plageConsommationID;
	}

	public CompteurElectrique getCompteurElectrique() {
		return compteurElectrique;
	}

	public void setCompteurElectrique(CompteurElectrique compteurElectrique) {
		this.compteurElectrique = compteurElectrique;
	}

	public List<PlageHoraire> getPlagesHoraires() {
		return plagesHoraires;
	}
	
	public void addPlageHoraire(PlageHoraire plageHoraire) {
		plagesHoraires.add(plageHoraire);
	}

	public void setPlagesHoraires(List<PlageHoraire> plagesHoraires) {
		this.plagesHoraires = plagesHoraires;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public float getTotalConsommation() {
		return totalConsommation;
	}

	public void setTotalConsommation(float totalConsommation) {
		this.totalConsommation = totalConsommation;
	}
}
