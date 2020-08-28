package dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.persistence.EntityManager;

import org.hibernate.Transaction;

import model.Adresse;
import model.CompteurElectrique;
import model.Personne;
import model.PlageConsommation;
import model.PlageHoraire;
import model.Tarif;
import model.TarifCreux;
import model.TarifPlein;
import util.DateFormatters;
import util.HibernateUtil;

public class DatabasePopulator {
	
	public static void populateDatabase() {
		
		try {
			EntityManager entityManager = HibernateUtil.getEntityManager();
			Transaction transaction = HibernateUtil.getSession().beginTransaction();

			SimpleDateFormat formatDateHeure = DateFormatters.getDateTimeFormatter();
			SimpleDateFormat formatHeure = DateFormatters.getTimeFormatter();
			
			Date dateActivation_1 = formatDateHeure.parse("11/01/2019 10:00");
			
			Date datePlage_1_debut = formatDateHeure.parse("11/01/2019 10:30");
			Date datePlage_1_fin = formatDateHeure.parse("11/01/2019 20:00");
			Date datePlage_2_debut = formatDateHeure.parse("31/01/2019 08:30");
			Date datePlage_2_fin = formatDateHeure.parse("01/02/2019 20:00");
			
			Date dateTarifPlein_1_debut = formatHeure.parse("10:00");
			Date dateTarifPlein_1_fin = formatHeure.parse("18:00");
			Date dateTarifCreux_1_debut = formatHeure.parse("00:00");
			Date dateTarifCreux_1_fin = formatHeure.parse("7:59");
			
			Adresse adresse_1 = new Adresse("Adresse_1", "Ville_1", 123456, "France");
			Personne personne_1 = new Personne(1, adresse_1, "0123456789");	
			
			ArrayList<Tarif> tarifs = new ArrayList<Tarif>();
			
			Collections.addAll(tarifs,
				new TarifPlein("TarifPleinJournee", dateTarifPlein_1_debut, dateTarifPlein_1_fin, 4f),
				new TarifCreux("TarifCreuxMatin", dateTarifCreux_1_debut, dateTarifCreux_1_fin, 4f, 0.3f)
			);

			CompteurElectrique compteur_1 = new CompteurElectrique(1, adresse_1, personne_1, dateActivation_1, 0.6f);
			compteur_1.fonctionner(datePlage_1_debut, datePlage_1_fin, tarifs);
			compteur_1.fonctionner(datePlage_2_debut, datePlage_2_fin, tarifs);
			
			entityManager.persist(adresse_1);
			entityManager.persist(personne_1);
			for (Tarif tarif : tarifs) {
				entityManager.persist(tarif);
			}
			for (PlageConsommation plageConsommation : compteur_1.getPlagesConsommations()) {
				for (PlageHoraire plageHoraire : plageConsommation.getPlagesHoraires()) {
					entityManager.persist(plageHoraire);
				}
				entityManager.persist(plageConsommation);
			}
			entityManager.persist(compteur_1);
			
			transaction.commit();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
