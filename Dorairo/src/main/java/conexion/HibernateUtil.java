package conexion;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.Session;

/**
 * Hibernate Util Class
 * 
 * @author JairoAB
 */
public class HibernateUtil {

	/** Session Factory instance */
	private static SessionFactory sf;

	/** Session instance */
	private static Session session;

	/**
	 * Construye la SessionFactory
	 * 
	 */
	public static synchronized void buildSessionFactory() {
		if (sf == null) {
			// Generador de sesiones
			StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build();
			sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
		}
	}

	/**
	 * Abre la session y la devuelve
	 * 
	 * @return Session
	 */
	public static synchronized Session openSession() {
		buildSessionFactory();
		if (session == null) {
			session = sf.openSession();
		}
		return session;
	}

	/**
	 * Devuelve la SessionFactory
	 * 
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		buildSessionFactory();
		return sf;
	}

	/**
	 * Guarda el objeto pasado en la base de datos
	 * 
	 * @param objeto Objeto a guardar
	 */
	public static void saveData(Object objeto) {
		session.save(objeto);
	}

	/**
	 * Realiza un batch sobre los cambios guardados
	 * 
	 */
	public static void executeBatch() {
		session.flush();
		session.clear();
	}

	/**
	 * Inicia la transaction
	 * 
	 */
	public static void startTransaction() {
		session.beginTransaction();
	}

	/**
	 * Realiza un commit sobre los cambios producidos
	 * 
	 */
	public static void commitCambios() {
		session.getTransaction().commit();
	}

	/**
	 * Realiza un rollback sobre los cambios producidos
	 * 
	 */
	public static void rollbackCambios() {
		// Comprueba que se pueda realizar un rollback
		if (session != null && session.getTransaction().getStatus().canRollback()) {
			session.getTransaction().rollback();
		}
	}

	/**
	 * Cierra todas las conexiones
	 * 
	 */
	public static void closeAll() {
		closeSession();
		closeSessionFactory();
	}

	/**
	 * Cierra la session
	 * 
	 */
	public static void closeSession() {
		if (session != null) {
			session.close();
		}
	}

	/**
	 * Cierra la factoria de sessiones
	 * 
	 */
	public static void closeSessionFactory() {
		if ((sf != null) && (!sf.isClosed())) {
			sf.close();
		}
	}
}
