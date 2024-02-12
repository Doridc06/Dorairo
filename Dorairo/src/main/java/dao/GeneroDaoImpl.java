package dao;

import java.util.List;

import org.hibernate.Session;

import models.Genero;

/**
 * Genero dao interface implementation
 * 
 * @author JairoAB
 *
 */
public class GeneroDaoImpl extends CommonDaoImpl<Genero> implements GeneroDaoI {

	/** Database connection */
	private Session session;

	/**
	 * GeneroDao constructor
	 * 
	 * @param session Database session
	 */
	public GeneroDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Genero searchById(int i) {
		activeTransaction();

		// Searches for the genre with the id
		return (Genero) session.createQuery("FROM Genero WHERE id = " + i).uniqueResult();
	}

	@Override
	public List<Genero> searchByName(String nombre) {
		activeTransaction();

		// Searches for all genres with this title
		return session.createQuery("FROM Genero WHERE nombre = '" + nombre + "'").list();
	}

	@Override
	public String searchMaxId() {
		activeTransaction();

		// Searches for the max id
		Integer max = (Integer) session.createQuery("SELECT MAX(id) FROM Genero").uniqueResult();
		String maxId = "" + max;
		return maxId;
	}

}
