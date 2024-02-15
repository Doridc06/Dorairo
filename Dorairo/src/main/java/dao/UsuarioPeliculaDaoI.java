package dao;

import java.util.List;

import models.UsuarioPelicula;

/**
 * UsuarioPelicula Dao Interfaz
 * 
 * @author JairoAB
 *
 */
public interface UsuarioPeliculaDaoI extends CommonDaoI<UsuarioPelicula> {

	/**
	 * Searches for the usuarioPelicula that has the same user and returns it
	 * 
	 * @param usuario User profile to find
	 * @return List of UsuarioPelicula that has this user
	 */
	public List<UsuarioPelicula> searchByUsuario(final String usuario);

	/**
	 * Search for the UserPelicula that has the user and id of the movie passed.
	 * 
	 * @param usuario User profile to find
	 * @param movieId Movie Id
	 * @return UsuarioPelicula with the user and movieId
	 */
	public UsuarioPelicula searchByUsuarioAndMovieId(final String usuario, final int movieId);

	/**
	 * Searches for the number of movies that this id user has seen
	 * 
	 * @param usuario Id of the user
	 * @return Number of user movies
	 */
	public String searchNumeroPeliculas(final String usuario);

	/**
	 * Searches for the list of movies with miLista true of the user
	 * 
	 * @param usuario User to find
	 * @return List of UsuarioPelicula
	 */
	public List<UsuarioPelicula> searchPeliculasMiLista(String usuario);

	/**
	 * Searches for the list of movies with vistas true of the user
	 * 
	 * @param usuario User to find
	 * @return List of UsuarioPelicula
	 */
	public List<UsuarioPelicula> searchPeliculasYaVistas(String usuario);

}
