package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Entity
@Table(name = "Series")
public class Series implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "titulo", length = 200)
  private String name;

  @Column(name = "fecha_estreno")
  private String first_air_date;

  @ManyToOne
  @JoinColumn(name = "compañia")
  private Compañia company;

  @Column(name = "descripcion")
  private String overview;

  @Column(name = "cartel")
  private String poster_path;

  @Column(name = "valoracion", precision = 5, scale = 3)
  private double vote_average;

  @Column(name = "num_episodios")
  private int number_of_episodes;

  @Column(name = "num_temporadas")
  private int number_of_seasons;

  /**
   * Relación de serie con la tabla de los id de usuarioSerie
   */
  @OneToMany(mappedBy = "id.series", cascade = CascadeType.ALL)
  private Set<UsuarioSerie> usuarioSerie = new HashSet<>();

  /**
   * Tabla intermedia entre series y actores
   */
  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(name = "Series_Actores", joinColumns = {@JoinColumn(name = "id_serie")},
      inverseJoinColumns = {@JoinColumn(name = "id_actor")})
  private Set<Actores> actores = new HashSet<>();

  /**
   * Tabla intermedia entre series y directores
   */
  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(name = "Series_Directores", joinColumns = {@JoinColumn(name = "id_serie")},
      inverseJoinColumns = {@JoinColumn(name = "id_director")})
  private Set<Directores> directores = new HashSet<>();

  /**
   * Tabla intermedia entre series y generos
   */
  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(name = "Series_Generos", joinColumns = {@JoinColumn(name = "id_serie")},
      inverseJoinColumns = {@JoinColumn(name = "id_genero")})
  private List<Genero> genres = new ArrayList<>();

  /**
   * Constructor para Serie
   * 
   * @param id
   * @param titulo
   * @param año
   * @param compañia
   * @param descripcion
   * @param cartel
   * @param valoracion
   * @param numeroEpisodios
   * @param numeroTemporadas
   */
  public Series(int id, String name, String first_air_date, Compañia compañia, String descripcion,
      String cartel, int valoracion, int number_of_episodes, int number_of_seasons) {
    super();
    this.id = id;
    this.name = name;
    this.first_air_date = first_air_date;
    this.company = compañia;
    this.overview = descripcion;
    this.poster_path = cartel;
    this.vote_average = valoracion;
    this.number_of_episodes = number_of_episodes;
    this.number_of_seasons = number_of_seasons;
    this.genres = new ArrayList<>();

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
  public String getName() {
    return name;
  }

  /**
   * @param title the title to set
   */
  public void setName(String title) {
    this.name = title;
  }

  /**
   * @return the release_date
   */
  public String getFirst_air_date() {
    return first_air_date;
  }

  /**
   * @param release_date the release_date to set
   */
  public void setFirst_air_date(String first_air_date) {
    this.first_air_date = first_air_date;
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
   * @return the numeroEpisodios
   */
  public int getNumber_of_episodes() {
    return number_of_episodes;
  }

  /**
   * @param numeroEpisodios the numeroEpisodios to set
   */
  public void setNumber_of_episodes(int number_of_episodes) {
    this.number_of_episodes = number_of_episodes;

  }

  /**
   * @return the numeroTemporadas
   */
  public int getNumber_of_seasons() {
    return number_of_seasons;
  }

  /**
   * @param numeroTemporadas the numeroTemporadas to set
   */
  public void setNumber_of_seasons(int number_of_seasons) {
    this.number_of_seasons = number_of_seasons;
  }

  /**
   * @return the usuarioSerie
   */
  public Set<UsuarioSerie> getUsuarioSerie() {
    return usuarioSerie;
  }

  /**
   * @param usuarioSerie the usuarioSerie to set
   */
  public void setUsuarioSerie(Set<UsuarioSerie> usuarioSerie) {
    this.usuarioSerie = usuarioSerie;
  }

  /**
   * @return the actores
   */
  public Set<Actores> getActores() {
    return actores;
  }

  /**
   * @param actores the actores to set
   */
  public void setActores(Set<Actores> actores) {
    this.actores = actores;
  }

  /**
   * @return the directores
   */
  public Set<Directores> getDirectores() {
    return directores;
  }

  /**
   * @param directores the directores to set
   */
  public void setDirectores(Set<Directores> directores) {
    this.directores = directores;
  }

  /**
   * @return the generos
   */
  public List<Genero> getGenres() {
    return genres;
  }

  public void setGenres(List<Genero> genres) {
    this.genres = genres;
  }

  public String getTipo() {
    return "tv";
  }

  
  
  public String toStringCsv() {
    return name + "\",\"" + first_air_date + "\",\"" + overview + "\",\"" + poster_path + "\",\"" + vote_average  +  "\",\"" + number_of_episodes + "\",\"" + number_of_seasons + "\"";

    }

  public String toStringJson() {
    JsonObject jsonObject = new JsonObject();
    
    jsonObject.addProperty("titulo", name);
    jsonObject.addProperty("detalles", overview);
    jsonObject.addProperty("fecha", first_air_date);
    
    // Construir la cadena de géneros
    StringBuilder generosString = new StringBuilder();
    for (Genero genero : genres) {
        generosString.append(genero.getName()).append(", ");
    }
    // Eliminar la coma y el espacio extra al final
    if (generosString.length() > 0) {
        generosString.setLength(generosString.length() - 2);
    }
    jsonObject.addProperty("Géneros", generosString.toString());
    
    jsonObject.addProperty("Valoracion", vote_average);
    jsonObject.addProperty("Número de episodios", number_of_episodes);
    jsonObject.addProperty("Número de temporadas", number_of_seasons);
    
    Gson gson = new Gson();
    return gson.toJson(jsonObject);
}

  
  
}
