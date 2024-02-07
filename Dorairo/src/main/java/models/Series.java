package models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Series")
public class Series implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "titulo", length = 200)
	private String titulo;

	@Column(name = "año")
	private int año;

	@ManyToOne
	@JoinColumn(name = "id")
	private Compañia compañia;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "cartel")
	private String cartel;

	@Column(name = "valoracion")
	private int valoracion;

	@Column(name = "num_episodios")
	private int numeroEpisodios;

	@Column(name = "num_temporadas")
	private int numeroTemporadas;

	/**
	 * Relación de serie con la tabla de los id de usuarioSerie
	 */
	@OneToMany(mappedBy = "id.series", cascade = CascadeType.ALL)
	private Set<UsuarioSerie> usuarioSerie = new HashSet<>();

	/**
	 * Tabla intermedia entre series y actores
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Series_Actores", joinColumns = { @JoinColumn(name = "id_serie") }, inverseJoinColumns = {
			@JoinColumn(name = "id_actor") })
	private Set<Actores> actores = new HashSet<>();

	/**
	 * Tabla intermedia entre series y directores
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Series_Directores", joinColumns = { @JoinColumn(name = "id_serie") }, inverseJoinColumns = {
			@JoinColumn(name = "id_director") })
	private Set<Directores> directores = new HashSet<>();

	/**
	 * Tabla intermedia entre series y generos
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Series_Generos", joinColumns = { @JoinColumn(name = "id_serie") }, inverseJoinColumns = {
			@JoinColumn(name = "id_genero") })
	private Set<Genero> generos = new HashSet<>();

	/**
	 * Constructor para Serie
	 * 
	 * @param id
	 * @param titulo
	 * @param año
	 * @param compañia
	 * @param descripcion
	 * @param cartel
	 * @param valoracion
	 * @param numeroEpisodios
	 * @param numeroTemporadas
	 */
	public Series(int id, String titulo, int año, Compañia compañia, String descripcion, String cartel, int valoracion,
			int numeroEpisodios, int numeroTemporadas) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.año = año;
		this.compañia = compañia;
		this.descripcion = descripcion;
		this.cartel = cartel;
		this.valoracion = valoracion;
		this.numeroEpisodios = numeroEpisodios;
		this.numeroTemporadas = numeroTemporadas;
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
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the año
	 */
	public int getAño() {
		return año;
	}

	/**
	 * @param año the año to set
	 */
	public void setAño(int año) {
		this.año = año;
	}

	/**
	 * @return the compañia
	 */
	public Compañia getCompañia() {
		return compañia;
	}

	/**
	 * @param compañia the compañia to set
	 */
	public void setCompañia(Compañia compañia) {
		this.compañia = compañia;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the cartel
	 */
	public String getCartel() {
		return cartel;
	}

	/**
	 * @param cartel the cartel to set
	 */
	public void setCartel(String cartel) {
		this.cartel = cartel;
	}

	/**
	 * @return the valoracion
	 */
	public int getValoracion() {
		return valoracion;
	}

	/**
	 * @param valoracion the valoracion to set
	 */
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	/**
	 * @return the numeroEpisodios
	 */
	public int getNumeroEpisodios() {
		return numeroEpisodios;
	}

	/**
	 * @param numeroEpisodios the numeroEpisodios to set
	 */
	public void setNumeroEpisodios(int numeroEpisodios) {
		this.numeroEpisodios = numeroEpisodios;
	}

	/**
	 * @return the numeroTemporadas
	 */
	public int getNumeroTemporadas() {
		return numeroTemporadas;
	}

	/**
	 * @param numeroTemporadas the numeroTemporadas to set
	 */
	public void setNumeroTemporadas(int numeroTemporadas) {
		this.numeroTemporadas = numeroTemporadas;
	}

}
