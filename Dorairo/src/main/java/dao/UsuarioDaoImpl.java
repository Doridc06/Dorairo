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
		checkActiveTransaction();

		// Searches for the user profile and returns it
		return (Usuario) session.createQuery("FROM Usuario WHERE usuario = '" + usuario + "'").uniqueResult();
	}

	@Override
	public Usuario searchByCorreo(String correo) {
		checkActiveTransaction();

		// Searches for the email profile and returns it
		return (Usuario) session.createQuery("FROM Usuario WHERE correo = '" + correo + "'").uniqueResult();
	}

	@Override
	public Usuario searchByUsuarioAndPassword(String usuario, String password) {
		checkActiveTransaction();
		return (Usuario) session
				.createQuery("FROM Usuario WHERE usuario = '" + usuario + "' AND clave = '" + password + "'").uniqueResult();
	}

}
