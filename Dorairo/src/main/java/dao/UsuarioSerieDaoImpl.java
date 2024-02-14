package dao;

import java.util.List;

import org.hibernate.Session;
import models.UsuarioPelicula;
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
				.createQuery("FROM UsuarioSerie WHERE id.usuario = '" + usuario + "' and id.series = " + serieId).uniqueResult();
	}

	@Override
	public String searchNumeroSeries(String usuario) {
		activeTransaction();
		Long numero = (Long) session
				.createQuery("SELECT count(*) FROM UsuarioSerie WHERE id.usuario = '" + usuario + "' and vista = true")
				.uniqueResult();
		return numero.toString();
	}
	
	@Override
    public List<UsuarioSerie> searchSeriesMiLista(String usuario) {
        activeTransaction();

        // Searches for all UsuarioSerie with this user and miLista true
        return session.createQuery("FROM UsuarioSerie WHERE id.usuario = '" + usuario + "' and miLista = true").list();
    }

  public List<UsuarioSerie> searchSeriesYaVista(String usuario) {
    activeTransaction();

    // Searches for all UsuarioSerie with this user and miLista true
    return session.createQuery("FROM UsuarioSerie WHERE id.usuario = '" + usuario + "' and vista = true").list();
}


	
}
