package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class HibernateUtil {
    private static Session session;
    private static EntityManager entityManager;
    
    private static EntityManager createEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager");
        
        return entityManagerFactory.createEntityManager();
    }
    
    public static EntityManager getEntityManager() {
        if (entityManager == null) {
        	entityManager = createEntityManager();
        }
        return entityManager;
    }
    
    public static void closeEntityManager() {
        if (entityManager != null) {
        	entityManager.close();
        }
    }

    public static Session getSession() {
        if (session == null) {
        	entityManager = getEntityManager();
        	session = entityManager.unwrap(Session.class);
        }
        return session;
    }
    
    public static void closeSession() {
        if (session != null) {
        	if (session.getTransaction() != null) {
        		session.getTransaction().rollback();
        	}
        	session.close();
        }
    }
}