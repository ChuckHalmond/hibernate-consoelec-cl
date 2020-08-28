package view;

import model.Adresse;
import util.InputManager;

public class AdresseView {
	
	public Adresse inputAdresse() {
		Adresse adresse = null;
		String rue = null;
		String ville = null;
		int codePostal = 0;
		String pays = null;
		
		System.out.println("\n============ Saisie Adresse ============\n");

		rue = InputManager.inputString("Rue");
		ville = InputManager.inputString("Ville");
		codePostal = InputManager.inputUnsignedInt("Code postal");
		pays = InputManager.inputString("Pays");

		adresse = new Adresse(rue, ville, codePostal, pays);
		
		System.out.println("\n============ Fin Saisie Adresse ============\n");
		
		return adresse;
	}
	
	public void outputAdresse(Adresse adresse) {
		System.out.println("\n============ Adresse ============\n");
		
		System.out.println(adresse);
	}
}
