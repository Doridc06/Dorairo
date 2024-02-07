package dao;

import org.hibernate.Session;

import models.Usuario;

/**
 * Profile Dao Interface Implementation
 * 
 * @author JairoAB
 *
 */
public class PerfilDaoImpl extends CommonDaoImpl<Usuario> implements PerfilDaoI {

	/** Database connection */
	private Session session;

	/**
	 * ProfileDao constructor
	 * 
	 * @param session Database session
	 */
	public PerfilDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public Usuario searchByUsuario(String usuario) {
		checkActiveTransaction();

		// Searches for the user profile and returns it
		return (Usuario) session.createQuery("FROM Perfil WHERE usuario = '" + usuario + "'").uniqueResult();
	}

	@Override
	public Usuario searchByCorreo(String correo) {
		checkActiveTransaction();

		// Searches for the email profile and returns it
		return (Usuario) session.createQuery("FROM Perfil WHERE correo = '" + correo + "'").uniqueResult();
	}
}
