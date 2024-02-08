package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la tabla de Localizacion
 * 
 * @author JairoAB
 *
 */
@Entity
@Table(name = "Localizaciones")
public class Localizacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "lugar", length = 200)
	private String lugar;

	/**
	 * Constructor para la localizacion
	 * 
	 * @param id
	 * @param lugar
	 */
	public Localizacion(int id, String lugar) {
		this.id = id;
		this.lugar = lugar;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}

	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

}
