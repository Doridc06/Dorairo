package models;

/**
 * Respuesta de la Api a las peliculas/series trending
 * 
 * @author JairoAB
 *
 */
public class RespuestaApiTrending {

	/** Lista de series/pelis trending */
	private MediaItem[] results;

	/**
	 * @return the results
	 */
	public MediaItem[] getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(MediaItem[] results) {
		this.results = results;
	}

	/**
	 * Item de trending, al no saber si es pelicula o serie, este objeto recoge el
	 * tipo para poder diferenciarlo posteriormente
	 * 
	 * @author JairoAB
	 *
	 */
	public class MediaItem {

		/** Id de la obra */
		private int id;

		/** Tipo de obra */
		private String media_type;

		/** Ruta al poster de la obra */
		private String poster_path;

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
		 * @return the mediaType
		 */
		public String getMediaType() {
			return media_type;
		}

		/**
		 * @param mediaType the mediaType to set
		 */
		public void setMediaType(String mediaType) {
			this.media_type = mediaType;
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

	}
}
