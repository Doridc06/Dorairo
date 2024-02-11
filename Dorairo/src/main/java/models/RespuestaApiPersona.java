package models;

import java.util.List;

/**
 * Clase que representa la respuesta de la API de persona
 * 
 * @author JairoAB
 *
 */
public class RespuestaApiPersona {

	private int page;

	private List<PersonaApi> results;

	private int totalPages;

	private int totalResults;

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the results
	 */
	public List<PersonaApi> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<PersonaApi> results) {
		this.results = results;
	}

	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the totalResults
	 */
	public int getTotalResults() {
		return totalResults;
	}

	/**
	 * @param totalResults the totalResults to set
	 */
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

}
