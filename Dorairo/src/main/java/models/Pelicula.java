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

/**
 * Clase que representa la tabla de Pelicula
 * 
 * @author JairoAB
 *
 */
@Entity
@Table(name = "Peliculas")
public class Pelicula implements Serializable {

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

	/**
	 * Relación de pelicula con la tabla de los id de usuarioPelicula
	 */
	@OneToMany(mappedBy = "id.pelicula", cascade = CascadeType.ALL)
	private List<UsuarioPelicula> usuarioPelicula = new ArrayList<>();

	/**
	 * Tabla intermedia entre peliculas y actores
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Peliculas_Actores", joinColumns = { @JoinColumn(name = "id_pelicula") }, inverseJoinColumns = {
			@JoinColumn(name = "id_actor") })
	private List<Actores> actores = new ArrayList<>();

	/**
	 * Tabla intermedia entre peliculas y directores
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Peliculas_Directores", joinColumns = { @JoinColumn(name = "id_pelicula") }, inverseJoinColumns = {
			@JoinColumn(name = "id_director") })
	private List<Directores> directores = new ArrayList<>();

	/**
	 * Tabla intermedia entre peliculas y generos
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Peliculas_Generos", joinColumns = { @JoinColumn(name = "id_pelicula") }, inverseJoinColumns = {
			@JoinColumn(name = "id_genero") })
	private List<Genero> genres = new ArrayList<>();

	/**
	 * Constructor para Pelicula
	 * 
	 * @param titulo
	 * @param fechaEstreno
	 * @param compañia
	 * @param descripcion
	 * @param poster
	 * @param valoracionGlobal
	 */
	public Pelicula(String titulo, Date fechaEstreno, Compañia compañia, String descripcion, String poster,
			String valoracionGlobal) {
		this.title = titulo;
		this.release_date = fechaEstreno;
		this.company = compañia;
		this.overview = descripcion;
		this.poster_path = poster;
		this.vote_average = Double.parseDouble(valoracionGlobal);
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
	 * @return the usuarioPelicula
	 */
	public List<UsuarioPelicula> getUsuarioPelicula() {
		return usuarioPelicula;
	}

	/**
	 * @param usuarioPelicula the usuarioPelicula to set
	 */
	public void setUsuarioPelicula(List<UsuarioPelicula> usuarioPelicula) {
		this.usuarioPelicula = usuarioPelicula;
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
	 * @return the genres
	 */
	public List<Genero> getGenres() {
		return genres;
	}

	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<Genero> genres) {
		this.genres = genres;
	}

	public boolean contieneGenero(String nombreGenero) {
		if (genres != null && nombreGenero != null) {
			for (Genero genero : genres) {
				if (nombreGenero.equalsIgnoreCase(genero.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Pelicula [id=" + id + ", title=" + title + ", release_date=" + release_date + ", company=" + company
				+ ", overview=" + overview + ", poster_path=" + poster_path + ", vote_average=" + vote_average
				+ ", usuarioPelicula=" + usuarioPelicula + ", actores=" + actores + ", directores=" + directores + ", genres="
				+ genres + "]";
	}

}
