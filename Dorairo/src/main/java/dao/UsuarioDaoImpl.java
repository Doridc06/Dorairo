package dao;

import org.hibernate.Session;

import models.Usuario;

/**
 * Usuario Dao Interface Implementation
 * 
 * @author JairoAB
 *
 */
public class UsuarioDaoImpl extends CommonDaoImpl<Usuario> implements UsuarioDaoI {

	/** Database connection */
	private Session session;

	/**
	 * UsuarioDao constructor
	 * 
	 * @param session Database session
	 */
	public UsuarioDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Usuario searchByUsuario(String usuario) {
		activeTransaction();

		// Searches for the user profile and returns it
		return (Usuario) session.createQuery("FROM Usuario WHERE usuario = '" + usuario + "'").uniqueResult();
	}

	@Override
	public Usuario searchByCorreo(String correo) {
		activeTransaction();

		// Searches for the email profile and returns it
		return (Usuario) session.createQuery("FROM Usuario WHERE correo = '" + correo + "'").uniqueResult();
	}

	@Override
	public Usuario searchByUsuarioAndPassword(String usuario, String password) {
		activeTransaction();
		return (Usuario) session
				.createQuery("FROM Usuario WHERE usuario = '" + usuario + "' AND clave = '" + password + "'").uniqueResult();
	}

	@Override
	public void deleteDataUser(String usuario) {
		activeTransaction();
		// Elimina los datos de la tabla usuario_serie y usuario_pelicula de los
		// registros del usuario con el id proporcionado
		session.createQuery("DELETE FROM UsuarioSerie WHERE id.usuario = '" + usuario + "'").executeUpdate();
		session.createQuery("DELETE FROM UsuarioPelicula WHERE id.usuario = '" + usuario + "'").executeUpdate();
		session.flush();
		session.getTransaction().commit();
	}

}
