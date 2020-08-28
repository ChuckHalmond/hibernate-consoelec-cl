package dao;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.Query;

import model.CompteurElectrique;
import model.PlageConsommation;
import model.Tarif;
import util.HibernateUtil;

public class CompteurElectriqueDAO {

	private AdresseDAO adresseDAO;
	private PersonneDAO personneDAO;
	private PlageConsommationDAO plageConsommationDAO;
	
	public CompteurElectriqueDAO(AdresseDAO adresseDAO, PersonneDAO personneDAO, PlageConsommationDAO plageConsommationDAO) {
		this.adresseDAO = adresseDAO;
		this.personneDAO = personneDAO;
		this.plageConsommationDAO = plageConsommationDAO;
	}
	
	public void persistCompteurElectrique(CompteurElectrique compteurElectrique) throws Exception {
		try {
			adresseDAO.persistAdresse(compteurElectrique.getAdresse());
			personneDAO.persistPersonne(compteurElectrique.getProprietaire());
			
			for (PlageConsommation plageConsommation : compteurElectrique.getPlagesConsommations()) {
				plageConsommationDAO.persistPlageConsommation(plageConsommation);
			}
			
			HibernateUtil.getEntityManager().persist(compteurElectrique);
		}
		catch (EntityExistsException exception) {
			throw new Exception("Numéro " + compteurElectrique.getNumero() + " déjà existant");
		}
	}

	public CompteurElectrique queryCompteurElectriqueByNumero(int numero) throws Exception {
		CompteurElectrique compteurElectrique = null;
		
		try {
			compteurElectrique = HibernateUtil.getEntityManager().find(CompteurElectrique.class, numero);
		}
		catch (Exception exception) {
			throw new Exception("Le numéro renseigné ne correspond à aucun compteur électrique");
		}
		
		return compteurElectrique;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<CompteurElectrique> queryListeCompteursElectriques() {
		ArrayList<CompteurElectrique> listeCompteursElectriques = null;
		
		String queryString = "SELECT compteurElectrique FROM CompteurElectrique compteurElectrique";
		
		Query query = HibernateUtil.getEntityManager().createQuery(queryString);
		
		listeCompteursElectriques = (ArrayList<CompteurElectrique>)query.getResultList();

		return listeCompteursElectriques;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<PlageConsommation> queryListePlagesConsommationsByTarif(Tarif tarif) {
		ArrayList<PlageConsommation> listePlagesConsommations = null;
		
		String queryString = 
			"SELECT pc FROM CompteurElectrique ce "
			+ "JOIN ce.plagesConsommations pc "
			+ "JOIN pc.plagesHoraires ph "
			+ "WHERE ph.tarif = :tarif";
		
		Query query = HibernateUtil.getEntityManager().createQuery(queryString).setParameter("tarif", tarif);

		listePlagesConsommations = (ArrayList<PlageConsommation>)query.getResultList();

		return listePlagesConsommations;
	}
	
	public float queryCoutTotalConsommationByCompteurElectriqueEtDate(CompteurElectrique compteurElectrique, Date date) {
		float totalConsommation = 0;
		
		String queryString = 
			"SELECT SUM(ph.KWh) FROM PlageHoraire ph "
			+ "JOIN ph.plageConsommation pc "
				+ "WHERE pc.compteurElectrique = :compteurElectrique AND "
				+ "(DAY(ph.dateDebut) = DAY(:date) AND "
				+ "DAY(ph.dateFin) = DAY(:date))";
		
		Query query = HibernateUtil.getEntityManager().createQuery(queryString).setParameter("compteurElectrique", compteurElectrique).setParameter("date", date);

		totalConsommation = Float.parseFloat(String.valueOf(query.getSingleResult()));

		return totalConsommation;
	}
}
