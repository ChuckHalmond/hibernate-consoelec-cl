package view;

import model.Adresse;
import model.Personne;
import util.InputManager;

public class PersonneView {
	
	private AdresseView adresseView;
	
	public PersonneView(AdresseView adresseView) {
		this.adresseView = adresseView;
	}

	public Personne inputPersonne() {
		Personne personne = null;
		int NSS = 0;
		String telephone = null;
		Adresse adresse = null;
		
		System.out.println("\n============ Saisie Personne ============\n");

		NSS = InputManager.inputUnsignedInt("NSS");
		telephone = InputManager.inputString("Téléphone");
		adresse = adresseView.inputAdresse();
		
		personne = new Personne(NSS, adresse, telephone);
		
		System.out.println("\n============ Fin Saisie Personne ============\n");
		
		return personne;

	}
	
	public void outputPersonne(Personne personne) {
		System.out.println("\n============ Personne ============\n");
		
		System.out.println(personne);
	}
}
