package dao;

import java.util.List;

import org.hibernate.Session;

import models.UsuarioSerie;

public class UsuarioSerieDaoImpl extends CommonDaoImpl<UsuarioSerie> implements UsuarioSerieDaoI {

	/** Database connection */
	private Session session;

	/**
	 * UsuarioSerieDao constructor
	 * 
	 * @param session Database session
	 */
	public UsuarioSerieDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public List<UsuarioSerie> searchByUsuario(String usuario) {
		activeTransaction();

		// Searches for all UsuarioSerie with this user
		return session.createQuery("FROM UsuarioSerie WHERE id.usuario = '" + usuario + "'").list();
	}

	@Override
	public UsuarioSerie searchByUsuarioAndSerieId(String usuario, int serieId) {
		activeTransaction();

		// Searches for the UsuarioSerie and returns it
		return (UsuarioSerie) session
				.createQuery("FROM UsuarioSerie WHERE id.usuario = '" + usuario + "' and id.serie = " + serieId).uniqueResult();
	}

	@Override
	public String searchNumeroSeries(String usuario) {
		activeTransaction();
		Long numero = (Long) session.createQuery("SELECT count(*) FROM UsuarioSerie WHERE id.usuario = '" + usuario + "'")
				.uniqueResult();
		return numero.toString();
	}
	
}
