package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import org.hibernate.Session;
import com.google.gson.Gson;
import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioSerieDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.RespuestaApiSeries;
import models.Series;
import models.UsuarioSerie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;

/**
 * Clase Series, donde esta toda la información de todas las series
 * 
 * @author Doriana dc
 * 
 */

public class SeriesController {

  /** ResourceBundle para cargar recursos de la interfaz de usuario */
  @FXML
  private ResourceBundle resources;

  /** URL de la ubicación del archivo FXML */
  @FXML
  private URL location;

  /** CheckBox para el género de acción */
  @FXML
  private CheckBox GeneroAccion;

  /** CheckBox para el género de animación */
  @FXML
  private CheckBox GeneroAnimacion;

  /** CheckBox para el género de aventura */
  @FXML
  private CheckBox GeneroAventura;

  /** CheckBox para el género de ciencia ficción */
  @FXML
  private CheckBox GeneroCienciaFiccion;

  /** CheckBox para el género de comedia */
  @FXML
  private CheckBox GeneroComedia;

  /** CheckBox para el género de drama */
  @FXML
  private CheckBox GeneroDrama;

  /** CheckBox para el género de misterio */
  @FXML
  private CheckBox GeneroMisterio;

  /** CheckBox para el género de documental */
  @FXML
  private CheckBox GeneroDocumental;

  /** CheckBox para el género de familia */
  @FXML
  private CheckBox GeneroFamilia;

  /** CheckBox para el género de infantil */
  @FXML
  private CheckBox GeneroInfantil;

  /** ImageView para la imagen del logo en la cabecera */
  @FXML
  private ImageView imagenLogoCabecera;

  /** Pane para el contenedor de la cabecera */
  @FXML
  private Pane paneCabecera;

  /** AnchorPane para el fondo */
  @FXML
  private AnchorPane paneFondo;

  /** StackPane para la cabecera del inicio */
  @FXML
  private StackPane stackPaneInicioCabecera;

  /** StackPane para el logo en la cabecera */
  @FXML
  private StackPane stackPaneLogoCabecera;

  /** StackPane para las películas en la cabecera */
  @FXML
  private StackPane stackPanePeliculasCabecera;

  /** HBox para las series mejor valoradas */
  @FXML
  private HBox seriesMejorValoradas;

  /** HBox para las series populares */
  @FXML
  private HBox seriesPopulares;

  /** Scene de la ventana de Inicio */
  private Scene scene;

  /** Stage de la ventana de Inicio */
  private Stage stage;

  /** Instancia del gestor de ventanas **/
  private GestorVentanas gestorVentanas;

  /** MenuItem para la opción aleatoria */
  @FXML
  private MenuItem Aleatoria;

  /** ImageView para la lupa */
  @FXML
  private ImageView lupa;

  /** HBox para la lista personal */
  @FXML
  private HBox miLista;

  /** HBox para las películas ya vistas */
  @FXML
  private HBox yaVisto;

  /** Conexion con la base de datos */
  private Session session;

  /** clave de la api */
  private static final String API_KEY =
      "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

  // Lista para almacenar todas las películas obtenidas de la API
  List<Series> todasLasSeries = new ArrayList<>();

  /**
   * Método manejador para el evento de clic en el botón de inicio. Cambia la escena y el escenario
   * y muestra la ventana de inicio.
   * 
   * @param event El evento del ratón
   */
  @FXML
  void inicioClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
  }

  /**
   * Método manejador para el evento de clic en el botón de películas. Cambia la escena y el
   * escenario y muestra la ventana de películas.
   * 
   * @param event El evento del ratón
   */
  @FXML
  void peliculasClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_PELICULA_FXML, "Pelicula");
  }

  /**
   * Método manejador para el evento de clic en el botón de series. Cambia la escena y el escenario
   * y muestra la ventana de series.
   * 
   * @param event El evento del ratón
   */
  @FXML
  void seriesClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_SERIES_FXML, "Series");
  }

  /**
   * Método manejador para el evento de clic en el botón de buscador. Cambia la escena y el
   * escenario y muestra la ventana del buscador.
   * 
   * @param event El evento del ratón
   */
  @FXML
  void buscadorClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_BUSCADOR_FXML, "Buscador");
  }

  /**
   * Método manejador para el evento de clic en el botón de perfil. Cambia la escena y el escenario
   * y muestra la ventana del perfil.
   * 
   * @param event El evento del ratón
   */
  @FXML
  void perfilClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_USUARIO_FXML, "Perfil");
  }

  /**
   * Método manejador para el evento de clic en el botón de detalles. Cambia la escena y el
   * escenario y muestra la ventana de detalles.
   * 
   * @param event El evento del ratón
   */
  @FXML
  void detallesClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_DETALLES_FXML, "Detalles");
  }


  /**
   * Asigna los valores correspondientes del stage y el scene
   * 
   */
  public void setSceneAndStage() {
    scene = imagenLogoCabecera.getScene();
    stage = (Stage) scene.getWindow();
  }

  /**
   * Método que se llama automáticamente al inicializar el controlador FXML
   */
  @FXML
  void initialize() {

    // Recogemos la sesion
    session = HibernateUtil.openSession();
    // Inicializamos el Gestor de ventanas
    gestorVentanas = new GestorVentanas();
    // Establece la imagen del logo
    Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
    imagenLogoCabecera.setImage(imagenLogo);

    Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
    lupa.setImage(imagenLupa);

    miLista.setSpacing(50);
    yaVisto.setSpacing(50);

    Aleatoria.setOnAction(event -> peliAleatoriaClicked());

    GeneroAccion.setOnAction(event -> generoClicked("10759"));
    GeneroComedia.setOnAction(event -> generoClicked("35"));
    GeneroInfantil.setOnAction(event -> generoClicked("10762"));
    GeneroFamilia.setOnAction(event -> generoClicked("10751"));
    GeneroDrama.setOnAction(event -> generoClicked("18"));
    GeneroMisterio.setOnAction(event -> generoClicked("9648"));
    GeneroDocumental.setOnAction(event -> generoClicked("99"));
    GeneroCienciaFiccion.setOnAction(event -> generoClicked("10765"));
    GeneroAnimacion.setOnAction(event -> generoClicked("16"));

    agregarImagenAMiListaSerie();
    agregarImagenAYaVistaSerie();

    try {
      // Configuración del cliente HTTP (OkHttpClient)
      OkHttpClient client = new OkHttpClient();

      // Llamada a la API para películas populares
      handleSeriesApiCall(client, "https://api.themoviedb.org/3/tv/popular?language=en-US&page=1",
          seriesPopulares);

      // Llamada a la API para próximas películas
      handleSeriesApiCall(client, "https://api.themoviedb.org/3/tv/top_rated?language=en-US&page=1",
          seriesMejorValoradas);

    } catch (IOException e) {
      // Manejar excepciones
      e.printStackTrace();
    }
  }

  /** Método para la llamada de la api para la información de las series
   * 
   * @param client
   * @param apiUrl
   * @param targetHBox
   * @throws IOException
   */
  private void handleSeriesApiCall(OkHttpClient client, String apiUrl, HBox targetHBox)
      throws IOException {
    // Construir la solicitud para la API de películas
    Request request =
        new Request.Builder().url(apiUrl).get().addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + API_KEY).build();

    // Ejecutar la solicitud y obtener la respuesta
    Response response = client.newCall(request).execute();

    // Obtener el cuerpo de la respuesta como cadena
    String responseBody = response.body().string();

    // Utilizar Gson para convertir la respuesta JSON a objetos Java
    Gson gson = new Gson();
    RespuestaApiSeries respApi = gson.fromJson(responseBody, RespuestaApiSeries.class);

    // Verificar si hay resultados en la respuesta
    if (respApi.getResults() != null && respApi.getResults().length > 0) {
      // Iterar sobre las películas y agregar imágenes al HBox
      int contador = 0;
      for (Series datos : respApi.getResults()) {
        todasLasSeries.add(datos);
        // System.out.println("Adding image: " + serie.getPoster_path());
        if (contador < 12) { // Limitar a 10 películas
          ImageView imageView = null;
          if (datos.getPoster_path() != null) {
            imageView = getImageViewFromUrl(
                "https://image.tmdb.org/t/p/w500" + datos.getPoster_path(), datos);
          }
          // Verificar si imageView no es nulo antes de agregarlo al HBox
          if (imageView != null) {
            targetHBox.getChildren().add(imageView);
          }
          contador++;
        } else {
          break; // Se han agregado 10 películas, salir del bucle
        }
      }
    } else {
      // No hay resultados, manejar de acuerdo a tus necesidades
    }
    targetHBox.setSpacing(50.0);
  }

  /**
   * Obtiene un ImageView a partir de una URL de imagen y datos de la serie.
   * @param imageUrl La URL de la imagen.
   * @param datos Los datos de la serie.
   * @return El ImageView configurado con la imagen y el evento de clic.
   */
  private ImageView getImageViewFromUrl(String imageUrl, Series datos) {
    ImageView imageView = new ImageView();
    imageView.setFitHeight(250.0);
    imageView.setFitWidth(290.0);
    imageView.setPreserveRatio(true);

    // Construir la URL del póster de la película
    Image image = new Image(imageUrl);
    imageView.setImage(image);

    // Almacenar el ID de la película en el userData del ImageView
    imageView.setUserData(String.valueOf(datos.getId()));

    // Configurar el evento de clic para llamar a detallesClicked
    imageView.setOnMouseClicked(event -> detallesClicked(imageView));

    return imageView;
  }

  /**
   * Maneja el evento de clic en un ImageView para mostrar los detalles de la serie.
   * @param clickedImageView El ImageView que fue clicado.
   */

  void detallesClicked(ImageView clickedImageView) {
    String serieId = getSerieIdFromImageView(clickedImageView);
    abrirVentanaDetalles(serieId);
  }

  /**
   * Abre la ventana de detalles de una serie.
   * @param serieId El identificador de la serie.
   */
  private void abrirVentanaDetalles(String serieId) {
    setSceneAndStage();
    gestorVentanas.muestraDetalles(stage, serieId, "tv");
  }


/**
 * Obtiene el ID de la serie desde un ImageView.
 * @param imageView El ImageView del que se obtendrá el ID de la serie.
 * @return El ID de la serie almacenado en el userData del ImageView.
 */
  private String getSerieIdFromImageView(ImageView imageView) {
    // Obtén el ID de la serie almacenado en el userData del ImageView
    Object userData = imageView.getUserData();

    if (userData instanceof String) {
      return (String) userData;
    } else {
      // Manejar la situación donde no hay un ID almacenado
      return "";
    }
  }

  
  /**
   * Maneja el evento de clic en un género para mostrar las series correspondientes.
   * @param generoId El ID del género.
   */
  void generoClicked(String generoId) {
    setSceneAndStage();
    gestorVentanas.muestraBuscadorGenero(stage, "tv", generoId);
  }

  /**
   * Maneja el evento de clic en el botón de serie aleatoria para abrir una serie aleatoria.
   */
  @FXML
  void peliAleatoriaClicked() {
    abrirVentanaSerieAleatoria();
  }

  
  /**
   * Abre la ventana de la serie aleatoria si se encuentra una serie aleatoria disponible.
   * Si no se encuentra ninguna serie aleatoria, imprime un mensaje de error.
   */
  private void abrirVentanaSerieAleatoria() {
    // Obtener una serie aleatoria
    Series serieAleatoria = obtenerSerieAleatoria();

    // Verificar si se encontró una serie aleatoria
    if (serieAleatoria != null) {
      // Obtener el ID de la serie aleatoria
      String serieId = String.valueOf(serieAleatoria.getId());

      // Abrir la ventana de detalles de la serie aleatoria
      abrirVentanaDetalles(serieId);
    } else {
      // Manejar la situación en la que no se pudo obtener una serie aleatoria
      System.out.println("No se pudo obtener una serie aleatoria");
    }
  }

  
  /**
   * Obtiene una serie aleatoria de la lista de todas las series disponibles.
   * @return La serie aleatoria, o null si no se encuentra ninguna serie disponible.
   */
  private Series obtenerSerieAleatoria() {
    // Verificar si hay series disponibles
    if (!todasLasSeries.isEmpty()) {
      // Obtener un índice aleatorio dentro del rango de la lista de series
      int indiceAleatorio = new Random().nextInt(todasLasSeries.size());

      // Obtener y devolver la serie aleatoria
      return todasLasSeries.get(indiceAleatorio);
    } else {
      return null;
    }
  }
  
  /**
   * Método para mostrar un mensaje en un HBox.
   * @param hbox El HBox en el que se mostrará el mensaje.
   * @param mensaje El mensaje a mostrar.
   */
  private void mostrarMensajeEnHBox(HBox hbox, String mensaje) {
      hbox.getChildren().clear(); // Limpiar cualquier contenido previo
      Label mensajeLabel = new Label(mensaje); // Crear un Label con el mensaje
      hbox.getChildren().add(mensajeLabel); // Agregar el Label al HBox
  }


/**
 * Agrega imágenes de series a la sección "Mi Lista" del usuario.
 */
  public void agregarImagenAMiListaSerie() {
    UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
    List<UsuarioSerie> listaUsuarioSerie =
        usDao.searchSeriesMiLista(UsuarioController.getUsuarioRegistrado().getUser());
    
    // Verificar si la lista está vacía y mostrar el mensaje en caso afirmativo
    if (listaUsuarioSerie.isEmpty()) {
        mostrarMensajeEnHBox(miLista, "No hay nada en mi lista");
        return;
    }

    for (UsuarioSerie us : listaUsuarioSerie) {
      // Obtener la imagen de la película
      ImageView imageView = getImageViewFromSerie(us.getId().getSeries());
      // Establecer el tamaño de la imagen si es necesario
      imageView.setFitWidth(200); // Ajusta el ancho según tus necesidades
      imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
      // Almacena el ID de la película en el userData del ImageView
      imageView.setUserData(String.valueOf(us.getId().getSeries().getId()));
      // Agregar la imagen al HBox de "Mi Lista"
      miLista.getChildren().add(imageView);
    }

  }

  
  /**
   * Agrega imágenes de series a la sección "Ya Vista" del usuario.
   */
  public void agregarImagenAYaVistaSerie() {
    UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
    List<UsuarioSerie> listaUsuarioSerie =
        usDao.searchSeriesYaVista(UsuarioController.getUsuarioRegistrado().getUser());
    
    // Verificar si la lista está vacía y mostrar el mensaje en caso afirmativo
    if (listaUsuarioSerie.isEmpty()) {
        mostrarMensajeEnHBox(yaVisto, "No hay nada en ya vistas");
        return;
    }

    for (UsuarioSerie us : listaUsuarioSerie) {
      // Obtener la imagen de la película
      ImageView imageView = getImageViewFromSerie(us.getId().getSeries());

      // Establecer el tamaño de la imagen si es necesario
      imageView.setFitWidth(200); // Ajusta el ancho según tus necesidades
      imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
      // Almacena el ID de la película en el userData del ImageView
      imageView.setUserData(String.valueOf(us.getId().getSeries().getId()));
      // Agregar la imagen al HBox de "ya vistas"
      yaVisto.getChildren().add(imageView);
    }

  }

  
  /**
   * Crea un ImageView a partir de una serie y configura su tamaño y evento de clic.
   * @param series La serie de la que se obtendrá la imagen.
   * @return 
   */
  private ImageView getImageViewFromSerie(Series series) {
    ImageView imageView = new ImageView();
    imageView.setFitHeight(230.0);
    imageView.setFitWidth(290.0);
    imageView.setPreserveRatio(true);

    // Construir la URL del póster de la película
    String imageUrl = "https://image.tmdb.org/t/p/w500" + series.getPoster_path();

    // Configurar la imagen en el ImageView
    Image image = new Image(imageUrl);
    imageView.getStyleClass().add("imagenSeries");
    imageView.setImage(image);

    // Configurar el evento de clic para llamar a detallesClicked
    imageView.setOnMouseClicked(event -> detallesClicked(imageView));
    return imageView;
  }

}
