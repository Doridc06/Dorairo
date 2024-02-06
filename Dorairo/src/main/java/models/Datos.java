package models;

/**
 * Clase que representa una película
 * 
 * 
 *
 */
public class Datos {

// Atributos de la película
	private String title;
	private String overview;
	private String release_date;
	private String poster_path;
	private String id;
	private String compañia;
	private String generos;
	private String actores;
	private String directores;
	private String valoracion;
	private String valoracionUser;
	private String plataformas;
	private String fechaVisualizacion;
	private String comentariosUser;
	private String guardadoEn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtener el resumen de la película
	 * 
	 * @return
	 */
	public String getOverview() {
		return overview;
	}

	/**
	 * Obtener la ruta del póster de la película
	 * 
	 * @return
	 */
	public String getPoster_path() {
		return poster_path;
	}

	/**
	 * Obtener la fecha de estreno de la película
	 * 
	 * @return
	 */
	public String getRelease_date() {
		return release_date;
	}

	/**
	 * Obtener el título de la película
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	public String getCompañia() {
		return compañia;
	}

	public void setCompañia(String compañia) {
		this.compañia = compañia;
	}

	public String getGeneros() {
		return generos;
	}

	public void setGeneros(String generos) {
		this.generos = generos;
	}

	public String getActores() {
		return actores;
	}

	public void setActores(String actores) {
		this.actores = actores;
	}

	public String getDirectores() {
		return directores;
	}

	public void setDirectores(String directores) {
		this.directores = directores;
	}

	public String getValoracion() {
		return valoracion;
	}

	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

	public String getValoracionUser() {
		return valoracionUser;
	}

	public void setValoracionUser(String valoracionUser) {
		this.valoracionUser = valoracionUser;
	}

	public String getPlataformas() {
		return plataformas;
	}

	public void setPlataformas(String plataformas) {
		this.plataformas = plataformas;
	}

	public String getFechaVisualizacion() {
		return fechaVisualizacion;
	}

	public void setFechaVisualizacion(String fechaVisualizacion) {
		this.fechaVisualizacion = fechaVisualizacion;
	}

	public String getComentariosUser() {
		return comentariosUser;
	}

	public void setComentariosUser(String comentariosUser) {
		this.comentariosUser = comentariosUser;
	}

	public String getGuardadoEn() {
		return guardadoEn;
	}

	public void setGuardadoEn(String guardadoEn) {
		this.guardadoEn = guardadoEn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}
	
}
