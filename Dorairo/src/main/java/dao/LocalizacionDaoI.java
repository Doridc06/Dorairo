package dao;

import models.Localizacion;

/**
 * Localizacion Dao Interfaz
 * 
 * @author JairoAB
 *
 */
public interface LocalizacionDaoI extends CommonDaoI<Localizacion> {

	/**
	 * Busca la localizacion que tiene dicho nombre
	 * 
	 * @param nombre Nombre para buscar
	 * @return Localizacion con dicho nombre
	 */
	public Localizacion searchByName(final String nombre);

	/**
	 * Busca la localizacion que tiene dicho id
	 * 
	 * @param id Id para buscar
	 * @return Localizacion con dicho id
	 */
	public Localizacion searchById(final int id);

}