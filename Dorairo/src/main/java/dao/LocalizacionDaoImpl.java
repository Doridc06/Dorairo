package dao;

import org.hibernate.Session;

import models.Localizacion;

public class LocalizacionDaoImpl extends CommonDaoImpl<Localizacion> implements LocalizacionDaoI {

	/** Database connection */
	private Session session;

	/**
	 * LocalizacionDao constructor
	 * 
	 * @param session Database session
	 */
	public LocalizacionDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Localizacion searchByName(String nombre) {
		activeTransaction();

		// Searches for the Localizacion with the name
		return (Localizacion) session.createQuery("FROM Localizacion WHERE lugar = '" + nombre + "'").uniqueResult();
	}

	@Override
	public Localizacion searchById(int id) {
		activeTransaction();

		// Searches for the Localizacion with the id
		return (Localizacion) session.createQuery("FROM Localizacion WHERE id = " + id).uniqueResult();
	}

}
