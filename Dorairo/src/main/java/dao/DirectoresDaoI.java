package dao;

import java.util.List;

import models.Directores;

/**
 * Directores Dao Interfaz
 * 
 * @author JairoAB
 *
 */
public interface DirectoresDaoI extends CommonDaoI<Directores> {

	/**
	 * Busca el director que tiene dicho id
	 * 
	 * @param id ID del director a encontrar
	 * @return Director que tiene el id
	 */
	public Directores searchById(final int id);

	/**
	 * Busca los directores que tengan dicho nombre
	 * 
	 * @param name Nombre por el que se va a buscar
	 * @return Lista de Directores con el nombre
	 */
	public List<Directores> searchByName(final String name);
}
