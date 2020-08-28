package dao;

import model.PlageHoraire;
import util.HibernateUtil;

public class PlageHoraireDAO {
	
	public void persistPlageHoraire(PlageHoraire plageHoraire) throws Exception {
		HibernateUtil.getEntityManager().persist(plageHoraire);
	}
}
