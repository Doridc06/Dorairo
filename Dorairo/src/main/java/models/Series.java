package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private String title;

	@Column(name = "fecha_estreno")
	private Date release_date;

	@ManyToOne
	@JoinColumn(name = "compañia")
	private Compañia company;

	@Column(name = "descripcion")
	private String overview;

	@Column(name = "cartel")
	private String poster_path;

	@Column(name = "valoracion", precision = 5, scale = 3)
	private double vote_average;

	@Column(name = "num_episodios")
	private int numeroEpisodios;

	@Column(name = "num_temporadas")
	private int numeroTemporadas;

	/**
	 * Relación de serie con la tabla de los id de usuarioSerie
	 */
	@OneToMany(mappedBy = "id.series", cascade = CascadeType.ALL)
	private List<UsuarioSerie> usuarioSerie = new ArrayList<>();

	/**
	 * Tabla intermedia entre series y actores
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Series_Actores", joinColumns = { @JoinColumn(name = "id_serie") }, inverseJoinColumns = {
			@JoinColumn(name = "id_actor") })
	private List<Actores> actores = new ArrayList<>();

	/**
	 * Tabla intermedia entre series y directores
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Series_Directores", joinColumns = { @JoinColumn(name = "id_serie") }, inverseJoinColumns = {
			@JoinColumn(name = "id_director") })
	private List<Directores> directores = new ArrayList<>();

	/**
	 * Tabla intermedia entre series y generos
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Series_Generos", joinColumns = { @JoinColumn(name = "id_serie") }, inverseJoinColumns = {
			@JoinColumn(name = "id_genero") })
	private List<Genero> genres = new ArrayList<>();

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
	public Series(int id, String titulo, Date año, Compañia compañia, String descripcion, String cartel, int valoracion,
			int numeroEpisodios, int numeroTemporadas) {
		super();
		this.id = id;
		this.title = titulo;
		this.release_date = año;
		this.company = compañia;
		this.overview = descripcion;
		this.poster_path = cartel;
		this.vote_average = valoracion;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the release_date
	 */
	public Date getRelease_date() {
		return release_date;
	}

	/**
	 * @param release_date the release_date to set
	 */
	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}

	/**
	 * @return the company
	 */
	public Compañia getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Compañia company) {
		this.company = company;
	}

	/**
	 * @return the overview
	 */
	public String getOverview() {
		return overview;
	}

	/**
	 * @param overview the overview to set
	 */
	public void setOverview(String overview) {
		this.overview = overview;
	}

	/**
	 * @return the poster_path
	 */
	public String getPoster_path() {
		return poster_path;
	}

	/**
	 * @param poster_path the poster_path to set
	 */
	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	/**
	 * @return the vote_average
	 */
	public double getVote_average() {
		return vote_average;
	}

	/**
	 * @param vote_average the vote_average to set
	 */
	public void setVote_average(double vote_average) {
		this.vote_average = vote_average;
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

	/**
	 * @return the usuarioSerie
	 */
	public List<UsuarioSerie> getUsuarioSerie() {
		return usuarioSerie;
	}

	/**
	 * @param usuarioSerie the usuarioSerie to set
	 */
	public void setUsuarioSerie(List<UsuarioSerie> usuarioSerie) {
		this.usuarioSerie = usuarioSerie;
	}

	/**
	 * @return the actores
	 */
	public List<Actores> getActores() {
		return actores;
	}

	/**
	 * @param actores the actores to set
	 */
	public void setActores(List<Actores> actores) {
		this.actores = actores;
	}

	/**
	 * @return the directores
	 */
	public List<Directores> getDirectores() {
		return directores;
	}

	/**
	 * @param directores the directores to set
	 */
	public void setDirectores(List<Directores> directores) {
		this.directores = directores;
	}

	/**
	 * @return the generos
	 */
	public List<Genero> getGeneros() {
		return genres;
	}

	/**
	 * @param generos the generos to set
	 */
	public void setGeneros(List<Genero> generos) {
		this.genres = generos;
	}

}
