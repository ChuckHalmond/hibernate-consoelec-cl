package view;

import java.util.ArrayList;
import java.util.Date;

import model.Adresse;
import model.CompteurElectrique;
import model.Personne;
import model.PlageConsommation;
import util.InputManager;

public class CompteurElectriqueView {
	
	private AdresseView adresseView;
	private PersonneView personneView;
	
	public CompteurElectriqueView(AdresseView adresseView, PersonneView personneView) {
		this.adresseView = adresseView;
		this.personneView = personneView;
	}
	
	public CompteurElectrique inputCompteurElectrique() {
		int numero = 0;
		Date dateActivation = null;
		float consommationMoyenneKWh = 0;
		Adresse adresse = null;
		Personne proprietaire = null; 

		System.out.println("\n============ Saisie Compteur �lectrique ============\n");
		
		numero = InputManager.inputUnsignedInt("Num�ro");
		dateActivation = InputManager.inputDateTime("Date d'activation");
		consommationMoyenneKWh = InputManager.inputUnsignedFloat("Consommation moyenne (en KWh)");
		adresse = adresseView.inputAdresse();
		proprietaire = personneView.inputPersonne();
		
		CompteurElectrique compteurElectrique = new CompteurElectrique(
			numero, adresse, proprietaire, dateActivation, consommationMoyenneKWh
		);
		
		System.out.println("\n============ Fin Saisie Compteur �lectrique ============\n");

		return compteurElectrique;
	}
	
	public int inputNumeroCompteurElectrique() {
		int numeroCompteurElectrique = 0;

		numeroCompteurElectrique = InputManager.inputUnsignedInt("Num�ro du compteur �lectrique");
		
		return numeroCompteurElectrique;
	}
	
	public Date inputDateDebutFonctionnement() {
		Date dateDebut = null;

		dateDebut = InputManager.inputDateTime("Date de d�but de fonctionnement");
		
		return dateDebut;
	}
	
	public Date inputDateFinFonctionnement() {
		Date dateFin = null;

		dateFin = InputManager.inputDateTime("Date de fin de fonctionnement");
		
		return dateFin;
	}
	
	public Date inputDateAConsiderer() {
		Date date = null;

		date = InputManager.inputDate("Date � consid�rer");
		
		return date;
	}
	
	public void outputCoutTotal(float coutTotal) {
		System.out.println("\n============ Co�t total de consommation ============\n");
		
		System.out.println("Co�t total = " + coutTotal);
	}
	
	public void outputCompteurElectrique(CompteurElectrique compteurElectrique) {
		System.out.println("\n============ Compteur �lectrique ============\n");
		
		System.out.println(compteurElectrique);
	}

	public void outputListeCompteursElectriques(ArrayList<CompteurElectrique> listeCompteursElectriques) {
		System.out.println("\n============ Liste compteurs �lectriques ============\n");
		
		if (listeCompteursElectriques.size() > 0) {
			for (CompteurElectrique compteurElectrique : listeCompteursElectriques) {
				System.out.println(compteurElectrique);
			}
		}
		else {
			System.out.println("Aucun compteur �l�ctrique trouv�e");
		}
	}

	public void outputListePlagesConsommationsWithCodeTarif(ArrayList<PlageConsommation> listePlagesConsommations, String codeTarif) {
		System.out.println("\n============ Liste plages consommations contenant tarif " + codeTarif + " ============\n");
		
		if (listePlagesConsommations.size() > 0) {
			for (PlageConsommation plageConsommation : listePlagesConsommations) {
				System.out.println(plageConsommation);
			}
		}
		else {
			System.out.println("Aucun plage de consommation trouv�e");
		}
	}
}
