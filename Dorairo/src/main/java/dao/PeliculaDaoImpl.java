package dao;

import java.util.List;

import org.hibernate.Session;

import models.Pelicula;

/**
 * Pelicula dao interface implementation
 * 
 * @author JairoAB
 *
 */
public class PeliculaDaoImpl extends CommonDaoImpl<Pelicula> implements PeliculasDaoI {

	/** Database connection */
	private Session session;

	/**
	 * PeliculaDao constructor
	 * 
	 * @param session Databas session
	 */
	public PeliculaDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Pelicula searchById(String id) {
		activeTransaction();

		// Searches for the movie with the id
		return (Pelicula) session.createQuery("FROM Pelicula WHERE id = " + id).uniqueResult();
	}

	@Override
	public List<Pelicula> searchByTitle(String title) {
		activeTransaction();

		// Searches for all movies with this title
		return session.createQuery("FROM Pelicula WHERE titulo = '" + title + "'").list();
	}

}
