package dao;

import java.util.List;

import org.hibernate.Session;

import models.Actores;

/**
 * Actores Dao Interface Implementation
 * 
 * @author JairoAB
 */
public class ActoresDaoImpl extends CommonDaoImpl<Actores> implements ActoresDaoI {

	/** Database connection */
	private Session session;

	/**
	 * ActoresDao Constructor
	 * 
	 * @param session Sesion para la base de datos
	 */
	public ActoresDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Actores searchById(int id) {
		activeTransaction();

		// Busca el actor con el id y lo devuelve
		return (Actores) session.createQuery("FROM Actores WHERE id = " + id).uniqueResult();
	}

	@Override
	public List<Actores> searchByName(String name) {
		activeTransaction();

		// Busca los actores con dicho nombre
		return session.createQuery("FROM Actores WHERE nombre = '" + name + "'").list();
	}

	@Override
	public String searchMaxId() {
		activeTransaction();

		// Searches for the max id
		Integer max = (Integer) session.createQuery("SELECT MAX(id) FROM Actores").uniqueResult();
		String maxId = "" + max;
		return maxId;
	}

}
