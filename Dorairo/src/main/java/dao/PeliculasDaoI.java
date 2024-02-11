package dao;

import java.util.List;

import models.Pelicula;

/**
 * Pelicula Dao Interface
 * 
 * @author JairoAB
 *
 */
public interface PeliculasDaoI extends CommonDaoI<Pelicula> {

	/**
	 * Searches for the movie that has the same id and returns it
	 * 
	 * @param id Id movie to find
	 * @return Movie that has this id
	 */
	public Pelicula searchById(final String id);

	/**
	 * Searches for all movies with this title and returns them
	 * 
	 * @param title Movies title to find
	 * @return List of all movies with this title
	 */
	public List<Pelicula> searchByTitle(final String title);

}
