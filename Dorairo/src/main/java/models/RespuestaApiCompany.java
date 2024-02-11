package models;

import java.util.List;

/**
 * Clase que representa la respuesta de la API de compañias
 * 
 * @author JairoAB
 *
 */
public class RespuestaApiCompany {

	private int page;

	private List<Compañia> results;

	private int total_pages;

	private int total_results;

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
	public List<Compañia> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<Compañia> results) {
		this.results = results;
	}

	/**
	 * @return the total_pages
	 */
	public int getTotal_pages() {
		return total_pages;
	}

	/**
	 * @param total_pages the total_pages to set
	 */
	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}

	/**
	 * @return the total_results
	 */
	public int getTotal_results() {
		return total_results;
	}

	/**
	 * @param total_results the total_results to set
	 */
	public void setTotal_results(int total_results) {
		this.total_results = total_results;
	}

}
