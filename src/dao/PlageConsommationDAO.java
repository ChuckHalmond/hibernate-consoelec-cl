package dao;

import model.PlageConsommation;
import model.PlageHoraire;
import util.HibernateUtil;

public class PlageConsommationDAO {

	private PlageHoraireDAO plageHoraireDAO;
	
	public PlageConsommationDAO(PlageHoraireDAO plageHoraireDAO) {
		this.plageHoraireDAO = plageHoraireDAO;
	}
	
	public void persistPlageConsommation(PlageConsommation plageConsommation) throws Exception {
		for (PlageHoraire plageHoraire : plageConsommation.getPlagesHoraires()) {
			plageHoraireDAO.persistPlageHoraire(plageHoraire);
		}
		
		HibernateUtil.getEntityManager().persist(plageConsommation);
	}
}
