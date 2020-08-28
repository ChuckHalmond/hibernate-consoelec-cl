package view;

import java.util.ArrayList;
import java.util.Date;

import model.Tarif;
import model.TarifCreux;
import model.TarifPlein;
import util.InputManager;

public class TarifView {
	
	public Tarif inputTarif() {
		String code = null;
		Date heureDebut = null;
		Date heureFin = null;
		float prixKWh = 0;
		float reduction = 0;
		
		System.out.println("\n============ Saisie Tarif ============\n");

		code = InputManager.inputString("Code");
		heureDebut = InputManager.inputTime("Heure de début");
		heureFin = InputManager.inputTime("Heure de fin");
		
		boolean valid = heureFin.compareTo(heureDebut) >= 0;
		while (!valid) {
			System.out.println("La date de fin doit être postérieure à la date de début");
			
			heureFin = InputManager.inputTime("Heure de fin");
			valid = heureFin.compareTo(heureDebut) >= 0;
		}
		
		prixKWh = InputManager.inputUnsignedFloat("Prix (KWh)");
		
		Tarif tarif = null;
		
		if (InputManager.inputChoices("Tarif plein ?", "O", "N").equals("N")) {
			reduction = InputManager.inputFloatInInterval("Réduction (%)", 0f, 1f);
			
			tarif = new TarifCreux(code, heureDebut, heureFin, prixKWh, reduction);
		}
		else {
			tarif = new TarifPlein(code, heureDebut, heureFin, prixKWh);
		}
		
		System.out.println("\n============ Fin Saisie Tarif ============\n");
		
		return tarif;
	}
	
	public String inputCodeTarif() {
		String codeTarif = null;

		codeTarif = InputManager.inputString("Code du tarif");

		return codeTarif;
	}
	
	public void outputTarif(Tarif tarif) {
		System.out.println("\n============ Tarif ============\n");
		
		System.out.println(tarif);
	}
	

	public void outputListeTarifs(ArrayList<Tarif> listeTarifs) {
		System.out.println("\n============ Liste tarifs ============\n");
		if (listeTarifs.size() > 0) {
			for (Tarif tarif : listeTarifs) {
				System.out.println(tarif);
			}
		}
		else {
			System.out.println("Aucun tarif");
		}
	}
}
