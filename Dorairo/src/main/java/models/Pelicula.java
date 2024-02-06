package models;

import java.util.List;
import com.google.gson.JsonObject;

/**
 * Clase que representa una película
 * 
 * 
 *
 */
public class Pelicula {

// Atributos de la película
	private String title;
	private String overview;
	private String release_date;
	private String poster_path;
	private String id;
	 private List<Genero> genres; 
	// private List<JsonObject> production_companies; 
	private String popularity;
	private String vote_average;
	private String runtime;
	
	
	
	
/*
	private String actores;
	private String directores;
	private String valoracion;
	private String valoracionUser;
	private String plataformas;
	private String fechaVisualizacion;
	private String comentariosUser;
	private String guardadoEn;*/

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

    public List<Genero> getGenres() {
      return genres;
  }

  public void setGenres(List<Genero> genres) {
      this.genres = genres;
  }


  public String getPopularity() {
    return popularity;
  }

  public void setPopularity(String popularity) {
    this.popularity = popularity;
  }

  public String getVote_average() {
    return vote_average;
  }

  public void setVote_average(String vote_average) {
    this.vote_average = vote_average;
  }

  public String getRuntime() {
    return runtime;
  }

  public void setRuntime(String runtime) {
    this.runtime = runtime;
  }


	
	
	
}