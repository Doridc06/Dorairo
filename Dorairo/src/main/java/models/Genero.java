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
	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "generos")
	private Set<Series> series = new HashSet<>();

	/** Relacion de Generos con Peliculas */
	@ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "generos")
	private Set<Pelicula> peliculas = new HashSet<>();

}
