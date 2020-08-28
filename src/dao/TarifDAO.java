package dao;

import java.util.ArrayList;

import javax.persistence.EntityExistsException;
import javax.persistence.Query;

import model.Tarif;
import util.HibernateUtil;

public class TarifDAO {
	
	public void persistTarif(Tarif tarif) throws Exception {
		try {
			HibernateUtil.getEntityManager().persist(tarif);
		}
		catch (EntityExistsException exception) {
			throw new Exception("Code " + tarif.getCode() + " déjà existant");
		}
	}
	
	public Tarif queryTarifByCode(String code) throws Exception {
		Tarif tarif = null;
		
		try {
			tarif = HibernateUtil.getEntityManager().find(Tarif.class, code);
		}
		catch (Exception exception) {
			throw new Exception("Le code renseigné ne correspond à aucun tarif");
		}
		
		return tarif;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Tarif> queryListeTarifs() throws Exception {
		ArrayList<Tarif> listeTarifs = null;
		
		String queryString = "SELECT tarif FROM Tarif tarif";
			
		Query query = HibernateUtil.getEntityManager().createQuery(queryString);

		listeTarifs = (ArrayList<Tarif>)query.getResultList();
		
		return listeTarifs;
	}
}
