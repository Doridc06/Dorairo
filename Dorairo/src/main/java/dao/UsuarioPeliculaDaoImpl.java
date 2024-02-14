package dao;

import java.util.List;

import org.hibernate.Session;

import models.UsuarioPelicula;
import models.UsuarioSerie;

public class UsuarioPeliculaDaoImpl extends CommonDaoImpl<UsuarioPelicula> implements UsuarioPeliculaDaoI {

	/** Database connection */
	private Session session;

	/**
	 * UsuarioPeliculaDao constructor
	 * 
	 * @param session Database session
	 */
	public UsuarioPeliculaDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public List<UsuarioPelicula> searchByUsuario(String usuario) {
		activeTransaction();

		// Searches for all usuarioPelicula with this user
		return session.createQuery("FROM UsuarioPelicula WHERE id.usuario = '" + usuario + "'").list();
	}

	@Override
	public UsuarioPelicula searchByUsuarioAndMovieId(String usuario, int movieId) {
		activeTransaction();

		// Searches for the UsuarioPelicula and returns it
		return (UsuarioPelicula) session
				.createQuery("FROM UsuarioPelicula WHERE id.usuario = '" + usuario + "' and id.pelicula = " + movieId)
				.uniqueResult();
	}

	@Override
	public String searchNumeroPeliculas(String usuario) {
		activeTransaction();
		Long numero = (Long) session.createQuery("SELECT count(*) FROM UsuarioPelicula WHERE id.usuario = '" + usuario + "'  and vista = true")
				.uniqueResult();
		return numero.toString();

	}
	
	@Override
	public List<UsuarioPelicula> searchPeliculasMiLista(String usuario) {
		activeTransaction();

		// Searches for all UsuarioPelicula with this user and miLista true
		return session.createQuery("FROM UsuarioPelicula WHERE id.usuario = '" + usuario + "' and miLista = true").list();
	}

  public List<UsuarioPelicula> searchPeliculasYaVistas(String usuario) {
    activeTransaction();

    // Searches for all UsuarioPelicula with this user and miLista true
    return session.createQuery("FROM UsuarioPelicula WHERE id.usuario = '" + usuario + "' and vista = true").list();
  }
	
}
