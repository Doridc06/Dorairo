package models;

import java.util.List;

/**
 * Clase que representa la respuesta de la API de generos
 * 
 * @author JairoAB
 *
 */
public class RespuestaApiGenero {

	private List<Genero> generos;

	/**
	 * @return the generos
	 */
	public List<Genero> getGeneros() {
		return generos;
	}

	/**
	 * @param generos the generos to set
	 */
	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

}
