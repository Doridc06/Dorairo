package models;

import java.util.List;

/**
 * Clase que representa la respuesta de la API de generos
 * 
 * @author JairoAB
 *
 */
public class RespuestaApiGenero {

	private Genero[] genres;

	public RespuestaApiGenero() {
	}

	/**
	 * @return the generos
	 */
	public Genero[] getGeneros() {
		return genres;
	}

	/**
	 * @param generos the generos to set
	 */
	public void setGeneros(Genero[] generos) {
		this.genres = generos;
	}

}
