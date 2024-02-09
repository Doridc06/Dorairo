package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	private List<Series> series = new ArrayList<>();

	/** Relacion de Actores con Peliculas */
	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "actores")
	private List<Pelicula> peliculas = new ArrayList<>();

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the series
	 */
	public List<Series> getSeries() {
		return series;
	}

	/**
	 * @param series the series to set
	 */
	public void setSeries(List<Series> series) {
		this.series = series;
	}

	/**
	 * @return the peliculas
	 */
	public List<Pelicula> getPeliculas() {
		return peliculas;
	}

	/**
	 * @param peliculas the peliculas to set
	 */
	public void setPeliculas(List<Pelicula> peliculas) {
		this.peliculas = peliculas;
	}

}
