package dao;

import java.util.List;

import org.hibernate.Session;

import models.Series;

/**
 * Series dao interface implementation
 * 
 * @author JairoAB
 *
 */
public class SeriesDaoImpl extends CommonDaoImpl<Series> implements SeriesDaoI {

	/** Database connection */
	private Session session;

	/**
	 * SeriesDao constructor
	 * 
	 * @param session Databas session
	 */
	public SeriesDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Series searchById(String id) {
		activeTransaction();

		// Searches for the series with the id
		return (Series) session.createQuery("FROM Series WHERE id = " + id).uniqueResult();
	}

	@Override
	public List<Series> searchByTitle(String title) {
		activeTransaction();

		// Searches for all series with this title
		return session.createQuery("FROM Series WHERE titulo = '" + title + "'").list();
	}

	@Override
	public String searchMaxId() {
		activeTransaction();

		// Searches for the max id
		Integer max = (Integer) session.createQuery("SELECT MAX(id) FROM Series").uniqueResult();
		String maxId = "" + max;
		return maxId;
	}

}
