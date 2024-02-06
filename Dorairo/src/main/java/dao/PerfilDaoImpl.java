package dao;

import org.hibernate.Session;

import models.Perfil;

/**
 * Profile Dao Interface Implementation
 * 
 * @author JairoAB
 *
 */
public class PerfilDaoImpl extends CommonDaoImpl<Perfil> implements PerfilDaoI {

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
	public Perfil searchByUsuario(String usuario) {
		checkActiveTransaction();

		// Searches for the user profile and returns it
		return (Perfil) session.createQuery("FROM Perfil WHERE usuario = '" + usuario + "'").uniqueResult();
	}

	@Override
	public Perfil searchByCorreo(String correo) {
		checkActiveTransaction();

		// Searches for the email profile and returns it
		return (Perfil) session.createQuery("FROM Perfil WHERE correo = '" + correo + "'").uniqueResult();
	}
}
