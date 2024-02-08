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
		checkActiveTransaction();

		// Busca la compañia con el id y la devuelve
		return (Compañia) session.createQuery("FROM Compañias WHERE id = " + id).uniqueResult();
	}

	@Override
	public List<Compañia> searchByName(String name) {
		checkActiveTransaction();

		// Busca las compañias con dicho nombre
		return session.createQuery("FROM Compañias WHERE nombre = '" + name + "'").list();
	}

}
