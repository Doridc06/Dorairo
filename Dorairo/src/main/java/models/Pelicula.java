package models;

/**
 * Clase que representa una película
 * 
 * @author JairoAB
 *
 */
public class Pelicula {

// Atributos de la película

	private boolean adult;
	private String backdrop_path;
	private int[] genre_ids;
	private int id;
	private String original_language;
	private String original_title;
	private String overview;
	private double popularity;
	private String poster_path;
	private String release_date;
	private String title;
	private boolean video;
	private double vote_average;
	private int vote_count;

	public Pelicula(String title, String compañia, String localizacion, String fechaEstreno, String valoracionPersonal,
			String valoracionGlobal, String genero, String actores, String directores, String descripcion,
			String comentarios) {

	}

	/**
	 * @return the adult
	 */
	public boolean isAdult() {
		return adult;
	}

	/**
	 * @param adult the adult to set
	 */
	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	/**
	 * @return the backdrop_path
	 */
	public String getBackdrop_path() {
		return backdrop_path;
	}

	/**
	 * @param backdrop_path the backdrop_path to set
	 */
	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	/**
	 * @return the genre_ids
	 */
	public int[] getGenre_ids() {
		return genre_ids;
	}

	/**
	 * @param genre_ids the genre_ids to set
	 */
	public void setGenre_ids(int[] genre_ids) {
		this.genre_ids = genre_ids;
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
	 * @return the original_language
	 */
	public String getOriginal_language() {
		return original_language;
	}

	/**
	 * @param original_language the original_language to set
	 */
	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}

	/**
	 * @return the original_title
	 */
	public String getOriginal_title() {
		return original_title;
	}

	/**
	 * @param original_title the original_title to set
	 */
	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
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
	 * @return the popularity
	 */
	public double getPopularity() {
		return popularity;
	}

	/**
	 * @param popularity the popularity to set
	 */
	public void setPopularity(double popularity) {
		this.popularity = popularity;
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
	 * @return the release_date
	 */
	public String getRelease_date() {
		return release_date;
	}

	/**
	 * @param release_date the release_date to set
	 */
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
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
	 * @return the video
	 */
	public boolean isVideo() {
		return video;
	}

	/**
	 * @param video the video to set
	 */
	public void setVideo(boolean video) {
		this.video = video;
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
	 * @return the vote_count
	 */
	public int getVote_count() {
		return vote_count;
	}

	/**
	 * @param vote_count the vote_count to set
	 */
	public void setVote_count(int vote_count) {
		this.vote_count = vote_count;
	}

}
