package models;

/**
 * Clase que representa la respuesta de la API
 * 
 * @author Doriana Dc
 *
 */
public class RespuestaApi {

  // Atributos de la respuesta de la API
  private int page;
  private Pelicula[] results;
  private int total_pages;
  private int total_results;

  // Métodos getter y setter para cada atributo

  /**
   * Obtener el número de página actual
   * 
   * @return
   */
  public int getPage() {
    return page;
  }

  /**
   * Modifica el número de página actual
   * 
   * @param page
   */
  public void setPage(int page) {
    this.page = page;
  }

  /**
   * Obtener el array de películas en la respuesta
   * 
   * @return
   */
  public Pelicula[] getResults() {
    return results;
  }

  /**
   * Modificar el array de películas en la respuesta
   * 
   * @param results
   */
  public void setResults(Pelicula[] results) {
    this.results = results;
  }

  /**
   * Obtener el total de páginas disponibles
   * 
   * @return
   */
  public int getTotal_pages() {
    return total_pages;
  }

  /**
   * Modificar el total de páginas disponibles
   * 
   * @param total_pages
   */
  public void setTotal_pages(int total_pages) {
    this.total_pages = total_pages;
  }

  /**
   * Obtener el total de resultados disponibles
   * 
   * @return
   */
  public int getTotal_results() {
    return total_results;
  }

  /**
   * Modificar el total de resultados disponibles
   * 
   * @param total_results
   */
  public void setTotal_results(int total_results) {
    this.total_results = total_results;
  }

}
