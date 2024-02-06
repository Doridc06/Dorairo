package models;

/**
 * Clase que representa una película
 * 
 * @author JairoAB
 *
 */
public class Pelicula {

// Atributos de la película

	// Mirar problema de id api vs manual
	private int id;
	private String title;
	private String release_date;
	private Compañia company; // revisar api
	private String overview;
	private String poster_path;
	private double vote_average;

	public Pelicula(String titulo, String compañia, String localizacion, String fechaEstreno, String valoracionPersonal,
			String valoracionGlobal, Genero[] genero, String actores, String directores, String descripcion,
			String comentarios) {

		this.title = titulo;
		this.release_date = fechaEstreno;
		// compañia
		this.overview = descripcion;
		// poster
		this.vote_average = Double.parseDouble(valoracionGlobal);

	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

}
