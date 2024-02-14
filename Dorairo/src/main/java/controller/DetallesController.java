package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.hibernate.Session;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import conexion.HibernateUtil;
import constants.Constants;
import dao.LocalizacionDaoImpl;
import dao.PeliculaDaoImpl;
import dao.SeriesDaoImpl;
import dao.UsuarioPeliculaDaoImpl;
import dao.UsuarioSerieDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Actores;
import models.Directores;
import models.Genero;
import models.Localizacion;
import models.Pelicula;
import models.Series;
import models.Usuario;
import models.UsuarioPelicula;
import models.UsuarioPeliculaID;
import models.UsuarioSerie;
import models.UsuarioSerieID;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;
import utilities.Utils;

public class DetallesController {

  @FXML
  private ImageView imagenLogoCabecera;

  @FXML
  private Text titulo;

  @FXML
  private ImageView cartel;

  @FXML
  private Text detalles;

  private Scene scene;
  private Stage stage;
  private GestorVentanas gestorVentanas;

  @FXML
  private ImageView fondoIm;

  @FXML
  private ImageView lupa;

  @FXML
  private MenuButton Agregar;

  @FXML
  private MenuButton Exportar;

  @FXML
  private MenuItem csv;

  @FXML
  private MenuItem json;

  @FXML
  private MenuItem miLista;

  @FXML
  private MenuItem yaVisto;

  private Pelicula pelicula;

  private Series series;

  @FXML
  private Button guardar;

  private Session session;

  private String tipo;

  private Usuario usuario;

  private String id;

  @FXML
  private Text actoresYdirectores;

  @FXML
  private Text compania;
  @FXML
  private TextField guardadoEn;


  private static final String API_KEY =
      "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

  @FXML
  void initialize() throws IOException {
    // Recogemos la sesion
    session = HibernateUtil.openSession();
    usuario = UsuarioController.getUsuarioRegistrado();

    gestorVentanas = new GestorVentanas();
    Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
    imagenLogoCabecera.setImage(imagenLogo);

    Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
    lupa.setImage(imagenLupa);

    csv.setOnAction(event -> exportarPeliculaYSerie("csv"));
    json.setOnAction(event -> exportarPeliculaYSerie("json"));

    // Agregar evento de clic a miLista
    miLista.setOnAction(event -> addMiLista());
    // Agregar evento de clic a peliculasVistas
    yaVisto.setOnAction(event -> addYaVistas());
    
    guardar.setOnAction(event -> searchLocalizacion());

    searchLocalizacion();
  }

  private void addYaVistas() {
    if (tipo.equals("movie")) {
      PeliculaDaoImpl pDao = new PeliculaDaoImpl(HibernateUtil.openSession());

      if (pDao.searchById(pelicula.getId()) == null) {
        pDao.update(pelicula);

      }
      UsuarioPelicula up = new UsuarioPelicula(new UsuarioPeliculaID(usuario, pelicula), 0, null,
          API_KEY, null, true, false);

      UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
      if (upDao.searchByUsuarioAndMovieId(usuario.getUser(), pelicula.getId()).isVista()) {
        Utils.mostrarAlerta("Película ya agregada a ya vistas", Constants.INFORMATION_TYPE);
      } else {
        upDao.update(up);
        Utils.mostrarAlerta("¡Película agregada a ya vistas correctamente!",
            Constants.INFORMATION_TYPE);
      }
    } else {
      // serie
      SeriesDaoImpl sDao = new SeriesDaoImpl(HibernateUtil.openSession());

      if (sDao.searchById(series.getId()) == null) {
        sDao.update(series);

      }

      UsuarioSerie us = new UsuarioSerie(new UsuarioSerieID(usuario, series), 0, null, API_KEY,
          null, true, false);
      UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
      if (usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId()) != null) {
        Utils.mostrarAlerta("Serie ya agregada a ya vistas", Constants.INFORMATION_TYPE);

      } else {
        usDao.update(us);
        Utils.mostrarAlerta("¡Serie agregada a ya vistas correctamente!",
            Constants.INFORMATION_TYPE);
      }
    }

  }

  private void addMiLista() {
    if (tipo.equals("movie")) {
      PeliculaDaoImpl pDao = new PeliculaDaoImpl(HibernateUtil.openSession());

      if (pDao.searchById(pelicula.getId()) == null) {
        pDao.update(pelicula);

      }
      UsuarioPelicula up = new UsuarioPelicula(new UsuarioPeliculaID(usuario, pelicula), 0, null,
          API_KEY, null, false, true);

      UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
      if (upDao.searchByUsuarioAndMovieId(usuario.getUser(), pelicula.getId()) != null) {
        Utils.mostrarAlerta("Película ya agregada a mi lista", Constants.INFORMATION_TYPE);
      } else {
        upDao.update(up);
        Utils.mostrarAlerta("¡Película agregada a mi lista correctamente!",
            Constants.INFORMATION_TYPE);
      }
    } else {
      // serie
      SeriesDaoImpl sDao = new SeriesDaoImpl(HibernateUtil.openSession());

      if (sDao.searchById(series.getId()) == null) {
        sDao.update(series);

      }

      UsuarioSerie us = new UsuarioSerie(new UsuarioSerieID(usuario, series), 0, null, API_KEY,
          null, false, true);
      UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
      if (usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId()) != null) {
        Utils.mostrarAlerta("Serie ya agregada a mi lista", Constants.INFORMATION_TYPE);

      } else {
        usDao.update(us);
        Utils.mostrarAlerta("¡Serie agregada a mi lista correctamente!",
            Constants.INFORMATION_TYPE);
      }
    }

  }

  @FXML
  void inicioClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
  }

  @FXML
  void peliculasClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_PELICULA_FXML, "Pelicula");
  }

  @FXML
  void seriesClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_SERIES_FXML, "Series");
  }

  @FXML
  void buscadorClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_BUSCADOR_FXML, "Buscador");
  }

  @FXML
  void perfilClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_USUARIO_FXML, "Perfil");
  }

  public void setSceneAndStage() {
    scene = imagenLogoCabecera.getScene();
    stage = (Stage) scene.getWindow();
  }

  public void initData(String id, String type) {
    this.tipo = type;
    this.id = id;

    if (id.startsWith(Constants.PREFIJO_ID_PELIS_MANUALES)) {
      PeliculaDaoImpl peliculaDao = new PeliculaDaoImpl(session);
      pelicula = peliculaDao.searchById(Integer.parseInt(id));

      if (pelicula != null) {
        titulo.setText(pelicula.getTitle());
        detalles.setText("Descripción: " + pelicula.getOverview() + "\n" + "Fecha de estreno: "
            + pelicula.getRelease_date() + "\n" + "Valoración: " + pelicula.getVote_average() + "\n"
            + "Actores: " + formatActores(pelicula.getActores()) + "\n" + "Directores: "
            + formatDirectores(pelicula.getDirectores()) + "\n" + "Géneros: "
            + formatGeneros(pelicula.getGenres()) + "\n" + "Compañía: "
            + pelicula.getCompany().getName() + "\n");
        
        // Cargar la imagen de la película
        String posterPath = pelicula.getPoster_path();
        if (posterPath != null && !posterPath.isEmpty()) {
          try {
            File file = new File(posterPath);
            Image image = new Image(file.toURI().toString());
            cartel.setImage(image);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }

    } else if (id.startsWith(Constants.PREFIJO_ID_SERIES_MANUALES)) {
      SeriesDaoImpl seriesDao = new SeriesDaoImpl(session);
      series = seriesDao.searchById(Integer.parseInt(id));

      if (series != null) {
        titulo.setText(series.getName());
        detalles.setText("Descripción: " + series.getOverview() + "\n" + "Fecha de estreno: "
            + series.getFirst_air_date() + "\n" + "Valoración: " + series.getVote_average() + "\n"
            + "Actores: " + formatActores(series.getActores()) + "\n" + "Directores: "
            + formatDirectores(series.getDirectores()) + "\n" + "Géneros: "
            + formatGeneros(series.getGenres()) + "\n" + "Compañía: "
            + series.getCompany().getName() + "\n");

        // Cargar la imagen de la serie
        String posterPath = series.getPoster_path();
        if (posterPath != null && !posterPath.isEmpty()) {
          try {
            File file = new File(posterPath);
            Image image = new Image(file.toURI().toString());
            cartel.setImage(image);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    } else {
      mostrarDatosApi();
    }
  }

  // Método auxiliar para formatear la lista de actores
  private String formatActores(List<Actores> actores) {
    StringBuilder sb = new StringBuilder();
    for (Actores actor : actores) {
      sb.append(actor.getName()).append(", ");
    }
    if (sb.length() > 2) {
      sb.setLength(sb.length() - 2); // Eliminar la última coma y el espacio
    }
    return sb.toString();
  }

  // Método auxiliar para formatear la lista de directores
  private String formatDirectores(List<Directores> directores) {
    StringBuilder sb = new StringBuilder();
    for (Directores director : directores) {
      sb.append(director.getName()).append(", ");
    }
    if (sb.length() > 2) {
      sb.setLength(sb.length() - 2); // Eliminar la última coma y el espacio
    }
    return sb.toString();
  }

  // Método auxiliar para formatear la lista de géneros
  private String formatGeneros(List<Genero> generos) {
    StringBuilder sb = new StringBuilder();
    for (Genero genero : generos) {
      sb.append(genero.getName()).append(", ");
    }
    if (sb.length() > 2) {
      sb.setLength(sb.length() - 2); // Eliminar la última coma y el espacio
    }
    return sb.toString();
  }


  private void mostrarDatosApi() {
    OkHttpClient client = new OkHttpClient();
    String apiUrl = "https://api.themoviedb.org/3/" + tipo + "/" + id;
    String queryParams = "?language=es-ES";
    String fullUrl = apiUrl + queryParams;
    Request request =
        new Request.Builder().url(fullUrl).get().addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + API_KEY).build();

    try (Response response = client.newCall(request).execute()) {
      String responseBody = response.body().string();
      Gson gson = new Gson();

      if (tipo.equals("movie")) {
        Pelicula datos = gson.fromJson(responseBody, Pelicula.class);
        titulo.setText(datos.getTitle());
        cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        fondoIm.setPreserveRatio(true);
        fondoIm.setLayoutX(screenWidth / 2);
        fondoIm.setLayoutY(0);
        fondoIm.setFitWidth(screenWidth);
        fondoIm.setFitHeight(screenHeight);
        fondoIm.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        fondoIm.setOpacity(0.15);
        fondoIm.toBack();

        StringBuilder generosString = new StringBuilder();
        for (Genero genero : datos.getGenres()) {
          generosString.append(genero.getName()).append(", ");
        }
        if (generosString.length() > 0) {
          generosString.setLength(generosString.length() - 2);
        }
        detalles.setText("Descripción: " + datos.getOverview() + "\n" + "Fecha de estreno: "
            + datos.getRelease_date() + "\n" + "Géneros: " + generosString.toString() + "\n"
            + "Valoración: " + datos.getVote_average() + "\n");

        // Obtener la compañía de producción
        JsonArray productionCompanies = JsonParser.parseString(responseBody).getAsJsonObject()
            .getAsJsonArray("production_companies");
        if (productionCompanies != null && productionCompanies.size() > 0) {
          JsonObject companyObject = productionCompanies.get(0).getAsJsonObject();
          String nombreCompania = companyObject.get("name").getAsString();
          // Establecer el texto en el componente de la interfaz de usuario
          compania.setText("Compañía: " + nombreCompania);
        } else {
          // Manejo si no se encontraron compañías de producción
          compania.setText("No se encontró información de compañía para esta película.");
        }

        setPelicula(datos);
        obtenerActoresYDirectores(id);

      } else if (tipo.equals("tv")) {
        Series datos = gson.fromJson(responseBody, Series.class);
        titulo.setText(datos.getName());
        cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        fondoIm.setPreserveRatio(true);
        fondoIm.setLayoutX(screenWidth / 2);
        fondoIm.setLayoutY(0);
        fondoIm.setFitWidth(screenWidth);
        fondoIm.setFitHeight(screenHeight);
        fondoIm.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        fondoIm.setOpacity(0.15);
        fondoIm.toBack();

        StringBuilder generosString = new StringBuilder();
        for (Genero genero : datos.getGenres()) {
          generosString.append(genero.getName()).append(", ");
        }
        if (generosString.length() > 0) {
          generosString.setLength(generosString.length() - 2);
        }
        detalles.setText("Descripción: " + datos.getOverview() + "\n" + "Fecha de estreno: "
            + datos.getFirst_air_date() + "\n" + "Géneros: " + generosString.toString() + "\n"
            + "Valoración: " + datos.getVote_average() + "\n" + "Episodios: "
            + datos.getNumber_of_episodes() + "\n" + "Temporadas: " + datos.getNumber_of_seasons());

        // Obtener la compañía de producción
        JsonArray productionCompanies = JsonParser.parseString(responseBody).getAsJsonObject()
            .getAsJsonArray("production_companies");
        if (productionCompanies != null && productionCompanies.size() > 0) {
          JsonObject companyObject = productionCompanies.get(0).getAsJsonObject();
          String nombreCompania = companyObject.get("name").getAsString();
          // Establecer el texto en el componente de la interfaz de usuario
          compania.setText("Compañía: " + nombreCompania);
        } else {
          // Manejo si no se encontraron compañías de producción
          compania.setText("No se encontró información de compañía para esta serie de TV.");
        }

        setSeries(datos);
        obtenerActoresYDirectores(id);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void setPelicula(Pelicula pelicula) {
    this.pelicula = pelicula;
  }

  public void setSeries(Series series) {
    this.series = series;

  }

  private void exportarPeliculaYSerie(String formato) {
    if (pelicula == null && series == null) {
      return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Guardar película o serie");

    FileChooser.ExtensionFilter extFilter = null;
    if (formato.equals("csv")) {
      extFilter = new FileChooser.ExtensionFilter("Archivos CSV (*.csv)", "*.csv");
    } else if (formato.equals("json")) {
      extFilter = new FileChooser.ExtensionFilter("Archivos JSON (*.json)", "*.json");
    }
    if (extFilter != null) {
      fileChooser.getExtensionFilters().add(extFilter);
    }

    File archivo = fileChooser.showSaveDialog(stage);

    if (archivo != null) {
      try {
        FileWriter escritor = new FileWriter(archivo);
        if (pelicula != null && formato.equals("csv")) {
          escritor.write(pelicula.toStringCsv());
        } else if (pelicula != null && formato.equals("json")) {
          escritor.write(pelicula.toStringJson());
        } else if (series != null && formato.equals("csv")) {
          escritor.write(series.toStringCsv());
        } else if (series != null && formato.equals("json")) {
          escritor.write(series.toStringJson());
        }
        escritor.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }



  private void obtenerActoresYDirectores(String id) {
    OkHttpClient client = new OkHttpClient();
    String creditsUrl = "https://api.themoviedb.org/3/" + tipo + "/" + id + "/credits";
    Request creditsRequest =
        new Request.Builder().url(creditsUrl).get().addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + API_KEY).build();

    try (Response creditsResponse = client.newCall(creditsRequest).execute()) {
      String creditsResponseBody = creditsResponse.body().string();
      JsonObject creditsJson = JsonParser.parseString(creditsResponseBody).getAsJsonObject();

      // Obtener los actores y directores del JSON de respuesta
      JsonArray cast =
          creditsJson.has("cast") ? creditsJson.getAsJsonArray("cast") : new JsonArray();
      JsonArray crew =
          creditsJson.has("crew") ? creditsJson.getAsJsonArray("crew") : new JsonArray();

      // Procesar actores
      StringBuilder actoresString = new StringBuilder();
      for (JsonElement actorElement : cast) {
        JsonObject actorObject = actorElement.getAsJsonObject();
        String knownForDepartment = actorObject.get("known_for_department").getAsString();
        if ("Acting".equals(knownForDepartment)) {
          String actorName = actorObject.get("name").getAsString();
          actoresString.append(actorName).append(", ");
        }
      }
      if (actoresString.length() > 0) {
        actoresString.setLength(actoresString.length() - 2);
      }

      // Procesar directores
      StringBuilder directoresString = new StringBuilder();
      for (JsonElement directorElement : crew) {
        JsonObject directorObject = directorElement.getAsJsonObject();
        String knownForDepartment = directorObject.get("known_for_department").getAsString();
        if ("Directing".equals(knownForDepartment)) {
          String directorName = directorObject.get("name").getAsString();
          directoresString.append(directorName).append(", ");
        }
      }
      if (directoresString.length() > 0) {
        directoresString.setLength(directoresString.length() - 2);
      }

      // Mostrar información en la interfaz de usuario o realizar cualquier otra operación necesaria
      mostrarActoresYDirectores(actoresString.toString(), directoresString.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void mostrarActoresYDirectores(String actores, String directores) {
    // Actualizar el texto del Text con la información de los actores y directores
    actoresYdirectores.setText("Actores: " + actores + "\n" + "\nDirectores: " + directores);
  }


  /**
   * Busca la localizacion en la bbdd, si no existe, se crea una nueva
   * 
   * @return Localizacion
   */
  private Localizacion searchLocalizacion() {
    LocalizacionDaoImpl lDao = new LocalizacionDaoImpl(session);
    Localizacion lugar = lDao.searchByName(guardadoEn.getText());
    if (lugar == null && !guardadoEn.getText().isBlank()) {
      lugar = new Localizacion(guardadoEn.getText());
      lDao.update(lugar);
    } else if (guardadoEn.getText().isBlank()) {
      return null;
    }
    return lugar;
  }

}
