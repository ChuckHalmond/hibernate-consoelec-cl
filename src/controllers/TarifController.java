package controllers;
import java.util.ArrayList;

import org.hibernate.Transaction;

import dao.TarifDAO;
import model.Tarif;
import util.HibernateUtil;
import view.TarifView;

public class TarifController {

	private String commandPrefixe;

	private TarifDAO tarifDAO;
	private TarifView tarifView;
	
	public TarifController(String commandPrefixe) {
		this.commandPrefixe = commandPrefixe;
		
		this.tarifDAO = new TarifDAO();
		this.tarifView = new TarifView();
	}
	
	public String getCommandPrefixe() {
		return commandPrefixe;
	}
	
	public boolean executeCommand(String command) {
		
		Transaction transaction = null;
		
		try {
			transaction = HibernateUtil.getSession().beginTransaction();
			
			switch (command) {
				case "-n": {
					ArrayList<Tarif> tarifs = null;
					Tarif tarif = null;
					
					tarifs = tarifDAO.queryListeTarifs();
					tarif = tarifView.inputTarif();
					
					tarifs.add(tarif);
					
					Tarif.verifierCompatibilite(tarifs);
					
					tarifDAO.persistTarif(tarif);
					
					tarifView.outputTarif(tarif);
	
					break;
				}
				case "-d": {
					String codeTarif = null;
					Tarif tarif = null;
					
					codeTarif = tarifView.inputCodeTarif();
					tarif = tarifDAO.queryTarifByCode(codeTarif);
					
					tarifView.outputTarif(tarif);
					
					break;
				}
				case "-l": {
					ArrayList<Tarif> listeTarifs = null;

					listeTarifs = tarifDAO.queryListeTarifs();

					tarifView.outputListeTarifs(listeTarifs);
					
					break;
				}
				case "-i": {
					System.out.println(
						"=======================      Tarif      ========================\n\n"
						+ "'" + getCommandPrefixe() + " -n'\t:\tlance la procédure de saisie d'un (n)ouveau tarif\n"
						+ "'" + getCommandPrefixe() + " -s'\t:\tlance la procédure d'affichage d'un tarif (s)pécifique\n"
						+ "'" + getCommandPrefixe() + " -l'\t:\taffiche la (l)iste détaillée des tarifs\n"
					);
					break;
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
		finally {
			if (transaction != null) {
				transaction.commit();
			}
		}
		
		return true;
	}
	
	public ArrayList<Tarif> getListeTarifs() throws Exception {
		return tarifDAO.queryListeTarifs();
	}
}
