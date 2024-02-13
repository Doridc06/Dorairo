package dao;

import java.util.List;

import models.Compañia;

/**
 * Compañias Dao Interfaz
 * 
 * @author JairoAB
 *
 */
public interface CompañiaDaoI extends CommonDaoI<Compañia> {

	/**
	 * Busca la compañia que tiene dicho id
	 * 
	 * @param id ID de la compañia a encontrar
	 * @return Compañia que tiene el id
	 */
	public Compañia searchById(final int id);

	/**
	 * Busca las compañias que tengan dicho nombre
	 * 
	 * @param name Nombre por el que se va a buscar
	 * @return Lista de Compañias con el nombre
	 */
	public List<Compañia> searchByName(final String name);

	/**
	 * Busca el mayor id de las compañias
	 * 
	 * @return Max id
	 */
	public String searchMaxId();

}
