package dao;

import java.util.List;

import models.Genero;

/**
 * Genero Dao Interface
 * 
 * @author JairoAB
 *
 */
public interface GeneroDaoI extends CommonDaoI<Genero> {

	/**
	 * Searches for the genre that has the same id and returns it
	 * 
	 * @param id Id genre to find
	 * @return Genre that has this id
	 */
	public Genero searchById(final int id);

	/**
	 * Searches for all genres with this name and returns them
	 * 
	 * @param nombre Genre name to find
	 * @return List of all genres with this title
	 */
	public List<Genero> searchByName(final String nombre);

	/**
	 * Searches for the greatest id of the genres
	 * 
	 * @return Max id
	 */
	public String searchMaxId();

}
