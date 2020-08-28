package controllers;

public class MainController {

	private CompteurElectriqueController compteurElectriqueController;
	private TarifController tarifController;

	public MainController() {

		this.tarifController = new TarifController("tarif");
		this.compteurElectriqueController = new CompteurElectriqueController("compteur");
	}
	
	public boolean executeCommand(String command) {
		if (command.startsWith(tarifController.getCommandPrefixe())) {
			tarifController.executeCommand(command.replace(tarifController.getCommandPrefixe() + " ",""));
		}
		else if (command.startsWith(compteurElectriqueController.getCommandPrefixe())) {
			compteurElectriqueController.executeCommand(command.replace(compteurElectriqueController.getCommandPrefixe() + " ",""));
		}
		else {
			try {
				switch (command) {
					case "-m": {
						
						tarifController.executeCommand("-i");
						compteurElectriqueController.executeCommand("-i");
						
						System.out.println(
							"=================      Commandes générales      ================\n\n"
							+ "'-m'\t\t:\t\taffiche ce (m)enu d'aide\n"
							+ "'-q'\t\t:\t\t(q)uitte l'application\n"
						);
						
						break;
					}
					case "-q": {
						System.out.println("Fermeture de l'application");
						
						return false;
					}
					default: {
						System.out.println("Commande inconnue, tapez '-m' pour obtenir la liste des commandes disponibles");
						
						break;
					}
				}
			}
			catch (Exception exception) {
				System.out.println(exception.getMessage());
			}
		}
		
		return true;
	}
}
