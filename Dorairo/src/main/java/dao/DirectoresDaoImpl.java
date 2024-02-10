package dao;

import java.util.List;

import org.hibernate.Session;

import models.Directores;

/**
 * Directores dao interface implementation
 * 
 * @author JairoAB
 *
 */
public class DirectoresDaoImpl extends CommonDaoImpl<Directores> implements DirectoresDaoI {

	/** Database connection */
	private Session session;

	/**
	 * DirectoresDao Constructor
	 * 
	 * @param session Sesion para la base de datos
	 */
	public DirectoresDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Directores searchById(int id) {
		activeTransaction();

		// Busca el director con el id y lo devuelve
		return (Directores) session.createQuery("FROM Directores WHERE id = " + id).uniqueResult();
	}

	@Override
	public List<Directores> searchByName(String name) {
		activeTransaction();

		// Busca los directores con dicho nombre
		return session.createQuery("FROM Directores WHERE nombre = '" + name + "'").list();
	}

}
