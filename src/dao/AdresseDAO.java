package dao;

import model.Adresse;
import util.HibernateUtil;

public class AdresseDAO {

	public void persistAdresse(Adresse adresse) throws Exception {
		HibernateUtil.getEntityManager().persist(adresse);
	}
}
