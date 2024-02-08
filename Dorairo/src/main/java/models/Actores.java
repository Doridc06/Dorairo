package models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Clase que representa la tabla de Actores
 * 
 * @author JairoAB
 *
 */
@Entity
@Table(name = "Actores")
public class Actores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "nombre", length = 200)
	private String name;

	/** Relacion de Actores con Series */
	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "actores")
	private Set<Series> series = new HashSet<>();

	/** Relacion de Actores con Peliculas */
	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "actores")
	private Set<Pelicula> peliculas = new HashSet<>();

	/**
	 * Constructor para la tabla Actores
	 * 
	 * @param id
	 * @param name
	 */
	public Actores(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
