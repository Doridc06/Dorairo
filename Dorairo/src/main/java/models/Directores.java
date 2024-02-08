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
 * Clase que representa la tabla de Directores
 * 
 * @author JairoAB
 *
 */
@Entity
@Table(name = "Directores")
public class Directores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "nombre", length = 200)
	private String name;

	/** Relacion de Directores con Series */
	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "directores")
	private Set<Series> series = new HashSet<>();

	/** Relacion de Directores con Peliculas */
	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "directores")
	private Set<Pelicula> peliculas = new HashSet<>();

	public Directores(int id, String name) {
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
