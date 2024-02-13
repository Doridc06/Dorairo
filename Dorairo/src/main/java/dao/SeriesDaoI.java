package dao;

import java.util.List;

import models.Series;

/**
 * Series Dao Interface
 * 
 * @author JairoAB
 *
 */
public interface SeriesDaoI extends CommonDaoI<Series> {

	/**
	 * Searches for the series that has the same id and returns it
	 * 
	 * @param id Id series to find
	 * @return Series that has this id
	 */
	public Series searchById(final int id);

	/**
	 * Searches for all series with this title and returns them
	 * 
	 * @param title Series title to find
	 * @return List of all series with this title
	 */
	public List<Series> searchByTitle(final String title);
	
	/**
	 * Searches for the greatest id of the serie
	 * 
	 * @return Max id
	 */
	public String searchMaxId();

}
