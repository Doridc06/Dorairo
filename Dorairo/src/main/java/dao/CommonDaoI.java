package dao;

import java.util.List;

/**
 * Common DAO interface
 * 
 * @author JairoAB
 *
 * @param <T> Type of object
 */
public interface CommonDaoI<T> {

	/**
	 * Inserts a T object into the database
	 * 
	 * @param paramT Object to insert into the database
	 */
	public void insert(final T paramT);

	/**
	 * Updates a T Object in the database
	 * 
	 * @param paramT Object to update in the database
	 */
	public void update(final T paramT);

	/**
	 * Deletes a T Object from the database
	 * 
	 * @param paramT Object to delete in the database
	 */
	public void delete(final T paramT);

	/**
	 * Returns all the objects in the database
	 * 
	 * @return List<T> List of all database objects
	 */
	public List<T> searchAll();

	/**
	 * Checks if the transaction is not active to activate it
	 */
	public void checkActiveTransaction();

}
