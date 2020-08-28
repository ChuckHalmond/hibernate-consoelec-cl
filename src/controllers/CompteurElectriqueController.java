package controllers;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Transaction;

import dao.AdresseDAO;
import dao.CompteurElectriqueDAO;
import dao.PersonneDAO;
import dao.PlageConsommationDAO;
import dao.PlageHoraireDAO;
import dao.TarifDAO;
import model.CompteurElectrique;
import model.PlageConsommation;
import model.Tarif;
import util.HibernateUtil;
import view.AdresseView;
import view.CompteurElectriqueView;
import view.PersonneView;
import view.TarifView;

public class CompteurElectriqueController {

	private String commandPrefixe;
	
	private CompteurElectriqueDAO compteurElectriqueDAO;
	private AdresseDAO adresseDAO;
	private PersonneDAO personneDAO;
	private PlageConsommationDAO plageConsommationDAO;
	private PlageHoraireDAO plageHoraireDAO;
	private TarifDAO tarifDAO;
	
	private AdresseView adresseView;
	private PersonneView personneView;
	private CompteurElectriqueView compteurElectriqueView;
	private TarifView tarifView;
	
	public CompteurElectriqueController(String commandPrefixe) {
		this.commandPrefixe = commandPrefixe;
		
		this.adresseDAO = new AdresseDAO();
		this.personneDAO = new PersonneDAO(adresseDAO);
		this.plageHoraireDAO = new PlageHoraireDAO();
		this.plageConsommationDAO = new PlageConsommationDAO(plageHoraireDAO);
		this.compteurElectriqueDAO = new CompteurElectriqueDAO(adresseDAO, personneDAO, plageConsommationDAO);
		this.tarifDAO = new TarifDAO();
		
		this.adresseView = new AdresseView();
		this.personneView = new PersonneView(adresseView);
		this.compteurElectriqueView = new CompteurElectriqueView(adresseView, personneView);
		this.tarifView = new TarifView();
	}
	
	public String getCommandPrefixe() {
		return commandPrefixe;
	}
	
	public boolean executeCommand(String command) {
		
		Transaction transaction = null;
		
		try  {
			transaction = HibernateUtil.getSession().beginTransaction();
			
			switch (command) {
				case "-n": {
					CompteurElectrique compteurElectrique = null;
	
					compteurElectrique = compteurElectriqueView.inputCompteurElectrique();
					compteurElectriqueDAO.persistCompteurElectrique(compteurElectrique);

					compteurElectriqueView.outputCompteurElectrique(compteurElectrique);
	
					break;
				}
				case "-d": {
					CompteurElectrique compteurElectrique = null;
					int numeroCompteurElectrique = 0;
	
					numeroCompteurElectrique = compteurElectriqueView.inputNumeroCompteurElectrique();
					compteurElectrique = compteurElectriqueDAO.queryCompteurElectriqueByNumero(numeroCompteurElectrique);
					
					compteurElectriqueView.outputCompteurElectrique(compteurElectrique);
	
					break;
				}
				case "-l": {
					ArrayList<CompteurElectrique> listeCompteursElectriques = null;
	
					listeCompteursElectriques = compteurElectriqueDAO.queryListeCompteursElectriques();
					
					compteurElectriqueView.outputListeCompteursElectriques(listeCompteursElectriques);
					
					break;
				}
				case "-f": {
					int numeroCompteurElectrique = 0;
					ArrayList<Tarif> tarifs = null;
					Date dateDebut = null;
					Date dateFin = null;
					
					CompteurElectrique compteurElectrique = null;
	
					numeroCompteurElectrique = compteurElectriqueView.inputNumeroCompteurElectrique();
					compteurElectrique = compteurElectriqueDAO.queryCompteurElectriqueByNumero(numeroCompteurElectrique);
					dateDebut = compteurElectriqueView.inputDateDebutFonctionnement();
					dateFin = compteurElectriqueView.inputDateFinFonctionnement();
					tarifs = tarifDAO.queryListeTarifs();
					
					compteurElectrique.fonctionner(dateDebut, dateFin, tarifs);
					
					compteurElectriqueDAO.persistCompteurElectrique(compteurElectrique);
					
					compteurElectriqueView.outputCompteurElectrique(compteurElectrique);

					break;
				}
				case "-p": {
					ArrayList<PlageConsommation> listePlagesConsommations = null;
					String codeTarif = null;
					Tarif tarif = null;

					codeTarif = tarifView.inputCodeTarif();
					tarif = tarifDAO.queryTarifByCode(codeTarif);
					listePlagesConsommations = compteurElectriqueDAO.queryListePlagesConsommationsByTarif(tarif);
					
					compteurElectriqueView.outputListePlagesConsommationsWithCodeTarif(listePlagesConsommations, codeTarif);

					break;
				}
				case "-c": {
					CompteurElectrique compteurElectrique = null;
					int numeroCompteurElectrique = 0;
					Date date = null;
					float coutTotal = 0;

					numeroCompteurElectrique = compteurElectriqueView.inputNumeroCompteurElectrique();
					date = compteurElectriqueView.inputDateAConsiderer();
					compteurElectrique = compteurElectriqueDAO.queryCompteurElectriqueByNumero(numeroCompteurElectrique);

					coutTotal = compteurElectriqueDAO.queryCoutTotalConsommationByCompteurElectriqueEtDate(compteurElectrique, date);
					compteurElectriqueView.outputCoutTotal(coutTotal);
					
					break;
				}
				case "-i": {
					
					System.out.println(
						"===============      Compteurs électriques      ================\n\n"
						+ "'" + getCommandPrefixe() + " -n'\t:\tlance la procédure de saisie d'un (n)ouveau compteur électrique\n"
						+ "'" + getCommandPrefixe() + " -s'\t:\tlance la procédure d'affichage d'un compteur électrique (s)pécifique\n"
						+ "'" + getCommandPrefixe() + " -l'\t:\taffiche la (l)iste détaillée des compteurs électriques\n"
						+ "'" + getCommandPrefixe() + " -f'\t:\tlance la procédure de (f)onctionnement d'un compteur électrique\n"
						+ "'" + getCommandPrefixe() + " -p'\t:\tlance la procédure de mise en évidence des (p)lages de consommation comportant un tarif spécifique\n"
						+ "'" + getCommandPrefixe() + " -c'\t:\tlance la procédure de calcul de (c)oût total de consommation\n"
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
		finally {
			if (transaction != null) {
				transaction.commit();
			}
		}
		
		return true;
	}
}
