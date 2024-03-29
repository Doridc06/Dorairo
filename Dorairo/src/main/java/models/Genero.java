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

@Entity
@Table(name = "Generos")
public class Genero implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "nombre", length = 200)
	private String name;

	/** Relacion de Generos con Series */
	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "genres")
	private List<Series> series = new ArrayList<>();

	/** Relacion de Generos con Peliculas */
	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "genres")
	private List<Pelicula> peliculas = new ArrayList<>();

	/**
	 * Constructor Genero
	 * 
	 * @param id
	 * @param name
	 */
	public Genero(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Genero() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object toLowerCase() {
		// TODO Auto-generated method stub
		return null;
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