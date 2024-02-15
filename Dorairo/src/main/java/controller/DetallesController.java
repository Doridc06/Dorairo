package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
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

/**
 * Clase Detalles que nos ayuda a ver toda la información de las peliculas y de las series
 * 
 * @author Doriana dc
 */
public class DetallesController {

  /** ImageView para el logo de la cabecera. */
  @FXML
  private ImageView imagenLogoCabecera;

  /** Texto para mostrar el título. */
  @FXML
  private Text titulo;

  /** ImageView para mostrar el cartel de la película o serie. */
  @FXML
  private ImageView cartel;

  /** Texto para mostrar detalles adicionales. */
  @FXML
  private Text detalles;

  /**
   * Escena de la ventana.
   */
  private Scene scene;

  /**
   * Escenario de la ventana.
   */
  private Stage stage;

  /**
   * Instancia del gestor de ventanas.
   */
  private GestorVentanas gestorVentanas;

  /**
   * ImageView para el fondo de la interfaz.
   * 
   */
  @FXML
  private ImageView fondoIm;

  /** ImageView para el icono de lupa.
   */
  @FXML
  private ImageView lupa;
  
  /** Menú desplegable para opciones de agregar.
   */
  @FXML
  private MenuButton agregar;
  
  /** Menú desplegable para opciones de exportar.
   */
  @FXML
  private MenuButton exportar;
  
  /** Elemento del menú para exportar a CSV.
   */

  @FXML
  private MenuItem csv;
  /** Elemento del menú para exportar a JSON.
   * 
   */

  @FXML
  private MenuItem json;
  /**
   * Elemento del menú para agregar a "Mi Lista".
   * 
   */

  @FXML
  private MenuItem miLista;
  /** Elemento del menú para marcar como "Ya Visto".
   * 
   */

  @FXML
  private MenuItem yaVisto;
  /** Botón para guardar información.
   * 
   */

  @FXML
  private Button guardar;
  /** Texto para mostrar actores y directores.
   * 
   */

  @FXML
  private Text actoresYdirectores;
  /** Texto para mostrar la compañía productora.
   * 
   */

  @FXML
  private Text compania;
  /** Campo de texto para ingresar la ubicación de guardado.
   * 
   */

  @FXML
  private TextField guardadoEn;
  /** Botón para agregar un comentario.
   */

  @FXML
  private Button agregarComentario;
  /** Campo de texto para ingresar un comentario.
   * 
   */

  @FXML
  private TextField comentarioUsuario;
  /** Texto para mostrar un comentario.
   * 
   */

  @FXML
  private Text comentario;
  /** Película actual.
   */

  private Pelicula pelicula;
  /** Serie actual.
   * 
   */

  private Series series;
  /** Sesión actual.
   * 
   */

  private Session session;
  /** Tipo de contenido (película o serie).
   * 
   */

  private String tipo;
  /** Usuario actual.
   */

  private Usuario usuario;
  /** ID del contenido.
   */

  private String id;
  /** clave de la api
   */
  private static final String API_KEY =
      "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";
  
  /**
   * Método que inicia el controlador
   * 
   * @throws IOException
   * 
   */
  @FXML
  void initialize() {
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
    agregarComentario.setOnAction(event -> addComentarioUser());
    
  }

  /**
   * Maneja el evento de clic en la opción de inicio. Configura la escena y el escenario y muestra
   * la ventana de perfil.
   * 
   * @param event El evento de clic del mouse.
   * 
   */
  @FXML
  void inicioClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
  }

  /**
   * Maneja el evento de clic en la opción de película. Configura la escena y el escenario y muestra
   * la ventana de perfil.
   * 
   * @param event El evento de clic del mouse.
   * 
   */
  @FXML
  void peliculasClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_PELICULA_FXML, "Pelicula");
  }

  /**
   * Maneja el evento de clic en la opción de serie. Configura la escena y el escenario y muestra la
   * ventana de perfil.
   * 
   * @param event El evento de clic del mouse.
   * 
   */
  @FXML
  void seriesClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_SERIES_FXML, "Series");
  }

  /**
   * 
   * Maneja el evento de clic en la opción de buscador. Configura la escena y el escenario y muestra
   * la ventana de perfil.
   * 
   * @param event El evento de clic del mouse.
   * 
   */
  @FXML
  void buscadorClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_BUSCADOR_FXML, "Buscador");
  }


  /**
   * Maneja el evento de clic en la opción de perfil. Configura la escena y el escenario y muestra
   * la ventana de perfil.
   * 
   * @param event El evento de clic del mouse.
   * 
   */
  @FXML
  void perfilClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_USUARIO_FXML, "Perfil");

  }

  /**
   * Establece la escena y el escenario actuales utilizando el ImageView
   * 
   * imagenLogoCabecera como referencia.
   */
  public void setSceneAndStage() {
    scene = imagenLogoCabecera.getScene();
    stage = (Stage) scene.getWindow();
  }


  /**
   * Método que agrega las peliculas y series a ya vistas
   */
  private void addYaVistas() {
    if (tipo.equals(Constants.PELICULA)) {
      PeliculaDaoImpl pDao = new PeliculaDaoImpl(HibernateUtil.openSession());
      UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
      // Verificar si la película ya está en la lista de "Ya vistas"
      UsuarioPelicula usuarioPelicula =
          upDao.searchByUsuarioAndMovieId(usuario.getUser(), pelicula.getId());
      if (usuarioPelicula != null && usuarioPelicula.isVista()) {
        Utils.mostrarAlerta("Película ya agregada a ya vistas", Constants.INFORMATION_TYPE);
      } else {
        // Si la película está en la lista personalizada, quitarla de ahí
        UsuarioPelicula listaPersonalizada =
            upDao.searchByUsuarioAndMovieId(usuario.getUser(), pelicula.getId());
        if (listaPersonalizada != null && listaPersonalizada.isMiLista()) {
          listaPersonalizada.setMiLista(false);
          upDao.update(listaPersonalizada);
        }
        // Agregar la película a "Ya vistas"
        if (pDao.searchById(pelicula.getId()) == null) {
          pDao.update(pelicula);
        }
        UsuarioPelicula up = new UsuarioPelicula(new UsuarioPeliculaID(usuario, pelicula), 0, null,
            null, null, true, false);
        upDao.update(up);
        Utils.mostrarAlerta("¡Película agregada a ya vistas correctamente!",
            Constants.INFORMATION_TYPE);
      }
    } else {
      // Serie
      SeriesDaoImpl sDao = new SeriesDaoImpl(HibernateUtil.openSession());
      UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
      // Verificar si la serie ya está en la lista de "Ya vistas"
      UsuarioSerie usuarioSerie =
          usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId());
      if (usuarioSerie != null && usuarioSerie.isVista()) {
        Utils.mostrarAlerta("Serie ya agregada a ya vistas", Constants.INFORMATION_TYPE);
      } else {
        // Si la serie está en la lista personalizada, quitarla de ahí
        UsuarioSerie listaPersonalizada =
            usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId());

        if (listaPersonalizada != null && listaPersonalizada.isMiLista()) {
          listaPersonalizada.setMiLista(false);
          usDao.update(listaPersonalizada);
        }
        // Agregar la serie a "Ya vistas"

        if (sDao.searchById(series.getId()) == null) {
          sDao.update(series);
        }
        UsuarioSerie us =
            new UsuarioSerie(new UsuarioSerieID(usuario, series), 0, null, null, null, true, false);
        usDao.update(us);
        Utils.mostrarAlerta("¡Serie agregada a ya vistas correctamente!",
            Constants.INFORMATION_TYPE);
      }
    }
  }

  /**
   *  Método que agrega las peliculas y series a mi lista
   */
  private void addMiLista() {
    if (tipo.equals(Constants.PELICULA)) {
      PeliculaDaoImpl pDao = new PeliculaDaoImpl(HibernateUtil.openSession());
      UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);

      // Verificar si la película ya está en la lista personalizada
      UsuarioPelicula usuarioPelicula =
          upDao.searchByUsuarioAndMovieId(usuario.getUser(), pelicula.getId());

      if (usuarioPelicula != null && usuarioPelicula.isMiLista()) {
        Utils.mostrarAlerta("Película ya agregada a mi lista", Constants.INFORMATION_TYPE);

      } else {
        // Si la película está en la lista de "Ya vistas", quitarla de ahí

        UsuarioPelicula yaVista =
            upDao.searchByUsuarioAndMovieId(usuario.getUser(), pelicula.getId());

        if (yaVista != null && yaVista.isVista()) {
          yaVista.setVista(false);
          upDao.update(yaVista);
        }

        // Agregar la película a la lista personalizada

        if (pDao.searchById(pelicula.getId()) == null) {
          pDao.update(pelicula);
        }

        UsuarioPelicula up = new UsuarioPelicula(new UsuarioPeliculaID(usuario, pelicula), 0, null,
            null, null, false, true);

        upDao.update(up);
        Utils.mostrarAlerta("¡Película agregada a mi lista correctamente!",
            Constants.INFORMATION_TYPE);
      }
    } else {
      // Serie
      SeriesDaoImpl sDao = new SeriesDaoImpl(HibernateUtil.openSession());
      UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);

      // Verificar si la serie ya está en la lista personalizada
      UsuarioSerie usuarioSerie =
          usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId());

      if (usuarioSerie != null && usuarioSerie.isMiLista()) {
        Utils.mostrarAlerta("Serie ya agregada a mi lista", Constants.INFORMATION_TYPE);

      } else {
        // Si la serie está en la lista de "Ya vistas", quitarla de ahí
        UsuarioSerie yaVista = usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId());

        if (yaVista != null && yaVista.isVista()) {
          yaVista.setVista(false);
          usDao.update(yaVista);
        }

        // Agregar la serie a la lista personalizada
        if (sDao.searchById(series.getId()) == null) {
          sDao.update(series);
        }
        UsuarioSerie us =
            new UsuarioSerie(new UsuarioSerieID(usuario, series), 0, null, null, null, false, true);
        usDao.update(us);
        Utils.mostrarAlerta("¡Serie agregada a mi lista correctamente!",
            Constants.INFORMATION_TYPE);
      }
    }
  }

  /**
   * Inicializa los datos de la ventana de detalles de una película o serie según su tipo y ID.
   * 
   * @param id El ID de la película o serie.
   * @param type El tipo de contenido (movie o tv).
   * 
   */
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
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            fondoIm.setPreserveRatio(true);
            fondoIm.setLayoutX(screenWidth / 2);
            fondoIm.setLayoutY(0);
            fondoIm.setFitWidth(screenWidth);
            fondoIm.setFitHeight(screenHeight);
            fondoIm.setImage(image);
            fondoIm.setOpacity(0.15);
            fondoIm.toBack();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      } else {
        Utils.mostrarAlerta("Error al mostrar los detalles. Película no encontrada.",
            Constants.ERROR_TYPE);
      }
    } else if (id.startsWith(Constants.PREFIJO_ID_SERIES_MANUALES)) {
      SeriesDaoImpl seriesDao = new SeriesDaoImpl(session);
      series = seriesDao.searchById(Integer.parseInt(id));
      if (series != null) {
        titulo.setText(series.getName());
        detalles.setText(

            "Descripción: " + series.getOverview() + "\n" + "Fecha de estreno: "
                + series.getFirst_air_date() + "\n"
                + "Valoración: " + series.getVote_average() + "\n" + "Actores: "
                + formatActores(series.getActores()) + "\n" + "Directores: "
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

            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            fondoIm.setPreserveRatio(true);
            fondoIm.setLayoutX(screenWidth / 2);
            fondoIm.setLayoutY(0);
            fondoIm.setFitWidth(screenWidth);
            fondoIm.setFitHeight(screenHeight);
            fondoIm.setImage(image);
            fondoIm.setOpacity(0.15);
            fondoIm.toBack();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      } else {
        Utils.mostrarAlerta("Error al mostrar los detalles. Serie no encontrada.",
            Constants.ERROR_TYPE);
      }
    } else {
      mostrarDatosApi();
    }
    muestraLocalizacion();
    cargarComentarioDesdeDB();
  }

  /**
   * Método auxiliar para formatear la lista de actores
   * 
   * @param actores
   * @return
   * 
   */
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

  /**
   * 
   * Método auxiliar para formatear la lista de directores
   * 
   * @param directores
   * @return
   * 
   */

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

  /**
   * 
   * Método auxiliar para formatear la lista de géneros
   * 
   * @param generos
   * @return
   * 
   */

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

  /**
   * Método que muestra los datos de la api
   */

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

      // Compriueba si es una pelicula

      if (tipo.equals(Constants.PELICULA)) {
        Pelicula datos = gson.fromJson(responseBody, Pelicula.class);
        titulo.setText(datos.getTitle());
        cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        cartel.getStyleClass().add("sombraDerecha");
        
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
      } else if (tipo.equals(Constants.SERIES)) {
        Series datos = gson.fromJson(responseBody, Series.class);
        titulo.setText(datos.getName());
        cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        cartel.getStyleClass().add("sombraDerecha");
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
      }
      obtenerActoresYDirectores(id);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * Establece la película actual para mostrar sus detalles. * @param pelicula La película a
   * establecer.
   */

  public void setPelicula(Pelicula pelicula) {
    this.pelicula = pelicula;
  }

  /**
   * Establece la serie actual para mostrar sus detalles.
   *
   * @param series La serie a establecer.
   * 
   */

  public void setSeries(Series series) {
    this.series = series;
  }

  /**
   * Método para exportar las peliculas y series en archivos JSON Y CSV
   * 
   * @param formato
   * 
   */

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
      try (FileWriter escritor = new FileWriter(archivo)) {
        if (pelicula != null && formato.equals("csv")) {
          escritor.write(pelicula.toStringCsv());
        } else if (pelicula != null && formato.equals("json")) {
          escritor.write(pelicula.toStringJson());
        } else if (series != null && formato.equals("csv")) {
          escritor.write(series.toStringCsv());
        } else if (series != null && formato.equals("json")) {
          escritor.write(series.toStringJson());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Método para obtener los actores y directos de la api
   * @param id
   * 
   */
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

      // Mostrar información en la interfaz de usuario o realizar cualquier otra
      mostrarActoresYDirectores(actoresString.toString(), directoresString.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * muestra los actores y directores
   @param actores
   * 
   * @param directores
   */
  private void mostrarActoresYDirectores(String actores, String directores) {
    // Actualizar el texto del Text con la información de los actores y directores
    actoresYdirectores.setText("Actores: " + actores + "\n" + "\nDirectores: " + directores);
  }

  /**
   * Método para agregar los comentarios de los usuarios
   */
  private void addComentarioUser() {
    // Obtener el comentario del TextField
    String comentarioTexto = comentarioUsuario.getText();
    // Verificar que el comentario no esté vacío
    if (!comentarioTexto.isBlank()) {
      // Comprueba si es una pelicula
      if (tipo.equals(Constants.PELICULA)) {
        // Crear el objeto UsuarioPelicula correspondiente
        UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
        UsuarioPelicula usuarioPelicula =
            upDao.searchByUsuarioAndMovieId(usuario.getUser(), pelicula.getId());
        // Comprueba si es null
        if (usuarioPelicula == null) {
          setPelicula(gePeliculaBBDD());
          // Crea un nuevo UsuarioPelicula con todos los datos a null
          usuarioPelicula = new UsuarioPelicula(new UsuarioPeliculaID(usuario, pelicula), 0,
              new Date(), null, null, false, false);
        }

        // Establece el comentario para esa Pelicula y la guarda en la bbdd
        usuarioPelicula.setComentariosUsuario(comentarioTexto);
        upDao.update(usuarioPelicula);
      } else if (tipo.equals(Constants.SERIES)) {
        UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
        UsuarioSerie usuarioSerie =
            usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId());
        // Comprueba si es null
        if (usuarioSerie == null) {
          setSeries(getSerieBBDD());
          // Crea un nuevo UsuarioSerie con todos los datos a null
          usuarioSerie = new UsuarioSerie(new UsuarioSerieID(usuario, series), 0, new Date(), null,
              null, false, false);
        }
        // Establece el comentario para esa serie y la guarda en la bbdd
        usuarioSerie.setComentariosUsuario(comentarioTexto);
        usDao.update(usuarioSerie);
      }
      // Informar al usuario que el comentario se ha agregado correctamente
      Utils.mostrarAlerta("¡Comentario agregado correctamente!", Constants.INFORMATION_TYPE);
    } else {
      // Informar al usuario que el comentario está vacío
      Utils.mostrarAlerta("El comentario no puede estar vacío", Constants.ERROR_TYPE);
    }
  }

  /**
   * Método para cargar el comentario desde la base de datos y mostrarlo en la ventana
   */
  private void cargarComentarioDesdeDB() {
    // Comprueba si es una pelicula
    if (tipo.equals(Constants.PELICULA)) {
      UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
      UsuarioPelicula usuarioPelicula =
          upDao.searchByUsuarioAndMovieId(usuario.getUser(), pelicula.getId());
      // Comprueba si existe un objeto usuarioPelicula en la bbdd y si tiene un
      if (usuarioPelicula != null && usuarioPelicula.getComentariosUsuario() != null
          && !usuarioPelicula.getComentariosUsuario().isBlank()) {
        // Establece el comentario
        comentarioUsuario.setText(usuarioPelicula.getComentariosUsuario());
      }

    } else if (tipo.equals(Constants.SERIES)) {
      UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
      UsuarioSerie usuarioSerie =
          usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId());

      // Comprueba si existe un objeto usuarioSerie en la bbdd y si tiene un
      if (usuarioSerie != null && usuarioSerie.getComentariosUsuario() != null
          && !usuarioSerie.getComentariosUsuario().isBlank()) {
        // Establece el comentario
        comentarioUsuario.setText(usuarioSerie.getComentariosUsuario());
      }
    }
  }

  /**
   * Busca la localizacion en la bbdd, si no existe, se crea una nueva.
   */
  private void searchLocalizacion() {
    // Comprueba si el campo de texto está vacío
    if (guardadoEn.getText().isBlank()) {
      Utils.mostrarAlerta("El campo de texto está vacío", Constants.ERROR_TYPE);
    } else {
      LocalizacionDaoImpl lDao = new LocalizacionDaoImpl(session);
      Localizacion lugar = lDao.searchByName(guardadoEn.getText());
      // Si no existe el lugar, lo crea
      if (lugar == null) {
        lugar = new Localizacion(guardadoEn.getText());
        lDao.update(lugar);
      }

      if (tipo.equals(Constants.PELICULA)) {
        // Recoge el objeto de UsuarioPelicula
        UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
        UsuarioPelicula up = upDao.searchByUsuarioAndMovieId(
            UsuarioController.getUsuarioRegistrado().getUser(), pelicula.getId());
        // Comprueba si es null
        if (up == null) {
          setPelicula(gePeliculaBBDD());
          // Crea un nuevo UsuarioPelicula con todos los datos a null
          up = new UsuarioPelicula(new UsuarioPeliculaID(usuario, pelicula), 0, new Date(), null,
              null, false, false);
        }
        // Establece la localizacion para esa Pelicula
        up.setLocalizacion(lugar);
        // Guarda el objeto
        upDao.update(up);
      } else {
        // Recoge el objeto de UsuarioSerie
        UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
        UsuarioSerie us = usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId());
        // Comprueba si es null
        if (us == null) {
          setSeries(getSerieBBDD());
          // Crea un nuevo UsuarioSerie con todos los datos a null
          us = new UsuarioSerie(new UsuarioSerieID(usuario, series), 0, new Date(), null, null,
              false, false);
        }
        // Establece la localizacion para esa Pelicula
        us.setLocalizacion(lugar);
        // Guarda el objeto
        usDao.update(us);
      }
      Utils.mostrarAlerta("Localización guardada correctamente", Constants.INFORMATION_TYPE);
    }
  }

  /** Comprueba si la pelicula ya existe en la bbdd, sino, la crea
   @return Pelicula de la bbdd o nueva peli
   * 
   */
  private Pelicula gePeliculaBBDD() {
    PeliculaDaoImpl peliDao = new PeliculaDaoImpl(session);
    Pelicula peli = peliDao.searchById(Integer.parseInt(id));
    // Si existe la peli, la devuelve
    if (peli != null) {
      return peli;
    }
    // Si no existe, guarda la pelicula actual y la devuelve
    peliDao.update(pelicula);
    return pelicula;
  }

  /** Comprueba si la serie ya existe en la bbdd, sino, la crea
   * @return Serie de la bbdd o nueva serie
   * 
   */
  private Series getSerieBBDD() {
    SeriesDaoImpl serieDao = new SeriesDaoImpl(session);
    Series serie = serieDao.searchById(Integer.parseInt(id));
    // Si existe la peserie, la devuelve
    if (serie != null) {
      return serie;
    }
    // Si no existe, guarda la serie actual y la devuelve
    serieDao.update(series);
    return series;
  }

  /** Muestra la localizacion de la pelicula o serie
   * 
   */
  public void muestraLocalizacion() {
    // Comprueba si es una pelicula
    if (tipo.equals(Constants.PELICULA)) {
      UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
      UsuarioPelicula up = upDao.searchByUsuarioAndMovieId(usuario.getUser(), pelicula.getId());

      if (up != null && up.getLocalizacion() != null) {
        guardadoEn.setText(up.getLocalizacion().getLugar());
      }

    } else if (tipo.equals(Constants.SERIES)) {
      UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
      UsuarioSerie us = usDao.searchByUsuarioAndSerieId(usuario.getUser(), series.getId());

      if (us != null && us.getLocalizacion() != null) {
        guardadoEn.setText(us.getLocalizacion().getLugar());
      }
    }
  }
}
