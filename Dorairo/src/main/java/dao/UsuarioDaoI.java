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

	/**
	 * Searches for the profile that has the same user and password and returns it
	 * 
	 * @param usuario  User profile to find
	 * @param password Password of the profile
	 * @return Profile that has this user
	 */
	public Usuario searchByUsuarioAndPassword(final String usuario, final String password);

	/**
	 * Searches for the number of movies that this id user has seen
	 * 
	 * @param usuario Id of the user
	 * @return Number of user movies
	 */
	public String searchNumeroPeliculas(final String usuario);

	/**
	 * Searches for the number of series that this id user has seen
	 * 
	 * @param usuario Id of the user
	 * @return Number of user series
	 */
	public String searchNumeroSeries(final String usuario);

	/**
	 * Deletes user data about his watched or stored movie or series
	 * 
	 * @param usuario Id of the user
	 */
	public void deleteDataUser(String usuario);

}
