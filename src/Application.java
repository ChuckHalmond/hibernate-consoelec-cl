
import controllers.MainController;
import dao.DatabasePopulator;
import util.HibernateUtil;
import util.InputManager;

public class Application {
	
	public static void main(String[] args) {
		MainController mainController = new MainController();

		System.out.println("-- G�n�ration des donn�es --");
		DatabasePopulator.populateDatabase();
		System.out.println("--   G�n�ration termin�e  --");
		
		System.out.println("\nPour ne pas perdre les donn�es ajout�es pendant cette session, commentez la propri�t� 'hibernate.hbm2ddl.auto' au prochain lancement\n");
		
		System.out.println("Bienvenue, tapez '-m' pour obtenir la liste des commandes disponibles");
		System.out.print("> ");
		while (mainController.executeCommand(InputManager.getScannerNextLine())) {
			System.out.print("> ");
		}
		
		InputManager.closeScanner();
		HibernateUtil.closeSession();
		HibernateUtil.closeEntityManager();
		System.exit(0);
	}
}
