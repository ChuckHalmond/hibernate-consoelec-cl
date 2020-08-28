package dao;

import javax.persistence.EntityExistsException;

import model.Personne;
import util.HibernateUtil;

public class PersonneDAO {
	
	private AdresseDAO adresseDAO;
	
	public PersonneDAO(AdresseDAO adresseDAO) {
		this.adresseDAO = adresseDAO;
	}

	public void persistPersonne(Personne personne) throws Exception {
		try {
			adresseDAO.persistAdresse(personne.getAdresse());
			HibernateUtil.getEntityManager().persist(personne);
		}
		catch (EntityExistsException exception) {
			throw new Exception("NSS " + personne.getNSS() + " déjà existant");
		}
	}
}