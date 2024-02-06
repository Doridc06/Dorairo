package dao;

import models.Perfil;

/**
 * Profile Dao Interface
 * 
 * @author JairoAB
 *
 */
public interface PerfilDaoI extends CommonDaoI<Perfil> {

	/**
	 * Searches for the profile that has the same user and returns it
	 * 
	 * @param usuario User profile to find
	 * @return Profile that has this user
	 */
	public Perfil searchByUsuario(final String usuario);

	/**
	 * Searches for the profile that has the same email and returns it
	 * 
	 * @param correo Email profile to find
	 * @return Profile that has this email
	 */
	public Perfil searchByCorreo(final String correo);

}
