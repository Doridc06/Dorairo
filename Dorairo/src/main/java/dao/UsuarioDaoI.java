package dao;

import models.Usuario;

/**
 * Usuario Dao Interfaz
 * 
 * @author JairoAB
 *
 */
public interface UsuarioDaoI extends CommonDaoI<Usuario> {

	/**
	 * Searches for the profile that has the same user and returns it
	 * 
	 * @param usuario User profile to find
	 * @return Profile that has this user
	 */
	public Usuario searchByUsuario(final String usuario);

	/**
	 * Searches for the profile that has the same email and returns it
	 * 
	 * @param correo Email profile to find
	 * @return Profile that has this email
	 */
	public Usuario searchByCorreo(final String correo);

}
