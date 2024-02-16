package dao;

import java.util.List;

import models.UsuarioSerie;

/**
 * UsuarioSerie Dao Interfaz
 * 
 * @author JairoAB
 *
 */
public interface UsuarioSerieDaoI extends CommonDaoI<UsuarioSerie> {

	/**
	 * Searches for the UsuarioSerie that has the same user and returns it
	 * 
	 * @param usuario User profile to find
	 * @return List of UsuarioSerie that has this user
	 */
	public List<UsuarioSerie> searchByUsuario(final String usuario);

	/**
	 * Search for the UsuarioSerie that has the user and id of the serie passed.
	 * 
	 * @param usuario User profile to find
	 * @param serieId Serie Id
	 * @return UsuarioSerie with the user and serieId
	 */
	public UsuarioSerie searchByUsuarioAndSerieId(final String usuario, final int serieId);

	/**
	 * Searches for the number of series that this id user has seen
	 * 
	 * @param usuario Id of the user
	 * @return Number of user series
	 */
	public String searchNumeroSeries(final String usuario);

	/**
	 * Searches for the list of series with miLista true of the user
	 * 
	 * @param usuario User to find
	 * @return List of UsuarioSerie
	 */
	public List<UsuarioSerie> searchSeriesMiLista(String usuario);

	/**
	 * Searches for the list of series with vistas true of the user
	 * 
	 * @param usuario User to find
	 * @return List of UsuarioSerie
	 */
	public List<UsuarioSerie> searchSeriesYaVista(String usuario);

}
