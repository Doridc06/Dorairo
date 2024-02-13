package dao;

import java.util.List;

import org.hibernate.Session;

import models.Compañia;

/**
 * Compañia dao interface implementation
 * 
 * @author JairoAB
 *
 */
public class CompañiaDaoImpl extends CommonDaoImpl<Compañia> implements CompañiaDaoI {

	/** Database connection */
	private Session session;

	/**
	 * CompañiaDao Constructor
	 * 
	 * @param session Sesion para la base de datos
	 */
	public CompañiaDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Compañia searchById(int id) {
		activeTransaction();

		// Busca la compañia con el id y la devuelve
		return (Compañia) session.createQuery("FROM Compañia WHERE id = " + id).uniqueResult();
	}

	@Override
	public List<Compañia> searchByName(String name) {
		activeTransaction();

		// Busca las compañias con dicho nombre
		return session.createQuery("FROM Compañia WHERE nombre = '" + name + "'").list();
	}

	@Override
	public String searchMaxId() {
		activeTransaction();

		// Searches for the max id
		Integer max = (Integer) session.createQuery("SELECT MAX(id) FROM Compañia").uniqueResult();
		String maxId = "" + max;
		return maxId;
	}

}
