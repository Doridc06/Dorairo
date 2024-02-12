package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.Pelicula;
import models.RespuestaApi;
import models.RespuestaApiSeries;
import models.Series;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;

public class SeriesController {

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private CheckBox GeneroAccion;

  @FXML
  private CheckBox GeneroAnimacion;

  @FXML
  private CheckBox GeneroAventura;

  @FXML
  private CheckBox GeneroCienciaFiccion;

  @FXML
  private CheckBox GeneroComedia;

  @FXML
  private CheckBox GeneroDrama;

  @FXML
  private CheckBox GeneroMisterio;

  @FXML
  private CheckBox GeneroMusical;

  @FXML
  private CheckBox GeneroSuspenso;

  @FXML
  private CheckBox GeneroTerror;

  @FXML
  private ImageView imagenLogoCabecera;

  @FXML
  private Pane paneCabecera;

  @FXML
  private AnchorPane paneFondo;

  @FXML
  private StackPane stackPaneInicioCabecera;

  @FXML
  private StackPane stackPaneLogoCabecera;

  @FXML
  private StackPane stackPanePeliculasCabecera;

  @FXML
  private HBox seriesMejorValoradas;

  @FXML
  private HBox seriesPopulares;

  /** Scene de la ventana de Inicio */
  private Scene scene;

  /** Stage de la ventana de Inicio */
  private Stage stage;

  /** Instancia del gestor de ventanas **/
  private GestorVentanas gestorVentanas;

  @FXML
  private MenuItem Aleatoria;

  @FXML
  private ImageView lupa;

  private static final String API_KEY =
      "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

  // Lista para almacenar todas las películas obtenidas de la API
  List<Series> todasLasSeries = new ArrayList<>();


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

  @FXML
  void initialize() {
    // Inicializamos el Gestor de ventanas
    gestorVentanas = new GestorVentanas();
    // Establece la imagen del logo
    Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
    imagenLogoCabecera.setImage(imagenLogo);

    Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
    lupa.setImage(imagenLupa);


    Aleatoria.setOnAction(event -> peliAleatoriaClicked());
    GeneroAccion.setOnAction(event -> generoClicked("28"));
    GeneroAventura.setOnAction(event -> generoClicked("12"));
    GeneroComedia.setOnAction(event -> generoClicked("35"));
    GeneroTerror.setOnAction(event -> generoClicked("27"));
    GeneroSuspenso.setOnAction(event -> generoClicked("53"));
    GeneroDrama.setOnAction(event -> generoClicked("18"));

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


  void detallesClicked(ImageView clickedImageView) {
    // Obtener el identificador de la serie desde el ImageView
    String serieId = getSerieIdFromImageView(clickedImageView);

    // Abrir la ventana de detalles
    abrirVentanaDetalles(serieId);
  }



  private void abrirVentanaDetalles(String serieId) {
    setSceneAndStage();
    gestorVentanas.muestraDetalles(stage, serieId, "tv");
  }



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



  void generoClicked(String generoId) {
    setSceneAndStage();
    gestorVentanas.muestraBuscadorGenero(stage, "tv", generoId);
  }



  // para las series aleatorias
  @FXML
  void peliAleatoriaClicked() {
    abrirVentanaSerieAleatoria();
  }

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

}
