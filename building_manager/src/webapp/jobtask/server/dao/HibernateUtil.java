package webapp.jobtask.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * Initializes EntityManagerFactory by given persistance.xml.
 * @author user
 *
 */
public class HibernateUtil {

	private static final String PERSISTENT_UNIT_NAME = "item-manager-pu";

	private static final EntityManagerFactory emf;

	static {
		try {
			emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}
	/**
	 * Getter for an EntityManager.
	 * @return current entity manager.
	 */
	public static EntityManager getManager() {
		return emf.createEntityManager();
	}
}