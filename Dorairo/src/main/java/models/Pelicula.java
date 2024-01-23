package models;

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
}
