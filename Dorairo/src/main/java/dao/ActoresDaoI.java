package dao;

import java.util.List;

import models.Actores;

/**
 * Actor Dao Interfaz
 * 
 * @author JairoAB
 *
 */
public interface ActoresDaoI extends CommonDaoI<Actores> {

	/**
	 * Busca al actor que tiene dicho id
	 * 
	 * @param id ID del actor a encontrar
	 * @return Actores que tiene el id
	 */
	public Actores searchById(final int id);

	/**
	 * Busca los actores que tengan dicho nombre
	 * 
	 * @param name Nombre por el que se va a buscar
	 * 
	 * @return Lista de Actores con el nombre
	 */
	public List<Actores> searchByName(final String name);

}
