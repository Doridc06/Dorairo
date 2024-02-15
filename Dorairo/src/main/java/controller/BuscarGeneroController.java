package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.google.gson.Gson;
import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Pelicula;
import models.RespuestaApi;
import models.RespuestaApiSeries;
import models.Series;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;

/**
 * Clase de buscar género que nos ayuda a buscar cada genero seleccionado de la película o de la
 * serie
 * 
 * @author Doriana dc
 * 
 */

public class BuscarGeneroController {

  /**
   * ResourceBundle para la internacionalización de la aplicación.
   */
  @FXML
  private ResourceBundle resources;

  /**
   * URL de la ubicación del archivo FXML.
   */
  @FXML
  private URL location;

  /**
   * ImageView para mostrar el logotipo en la cabecera.
   */
  @FXML
  private ImageView imagenLogoCabecera;

  /**
   * HBox para mostrar películas por género.
   */
  @FXML
  private HBox peliculasPorGenero;

  /**
   * StackPane para la cabecera de la ventana de inicio.
   */
  @FXML
  private StackPane stackPaneInicioCabecera;

  /**
   * StackPane para el logotipo en la cabecera.
   */
  @FXML
  private StackPane stackPaneLogoCabecera;

  /**
   * StackPane para las películas en la cabecera.
   */
  @FXML
  private StackPane stackPanePeliculasCabecera;

  /**
   * Escena de la ventana de inicio.
   */
  private Scene scene;

  /**
   * Stage de la ventana de inicio.
   */
  private Stage stage;

  /**
   * Instancia del gestor de ventanas.
   */
  private GestorVentanas gestorVentanas;

  /**
   * Texto que indica si se encontraron resultados.
   */
  @FXML
  private Text seEncontro;

  /**
   * HBox para las películas.
   */
  @FXML
  public HBox hBoxPeliculas;

  /**
   * ImageView para mostrar el cartel de la película.
   */
  @FXML
  private ImageView cartel;

  /**
   * FlowPane para mostrar las películas.
   */
  @FXML
  private FlowPane flowPanePeliculas;

  /**
   * Lista para almacenar todas las películas obtenidas de la API.
   */
  static List<Pelicula> todasLasPeliculas = new ArrayList<>();

  /**
   * Tipo de visualización de películas.
   */
  private String tipo;

  /**
   * ImageView de la lupa para realizar búsquedas.
   */
  @FXML
  private ImageView lupa;


  /**
   * Asigna los valores correspondientes del stage y el scene
   * 
   */
  public void setSceneAndStage() {
    scene = imagenLogoCabecera.getScene();
    stage = (Stage) scene.getWindow();
  }

  /**
   * Maneja el evento de clic en la cabecera de inicio. Configura la escena y el escenario y muestra
   * la ventana de inicio. Imprime un mensaje en la consola.
   * @param event El evento de clic del mouse.
   */
  @FXML
  void inicioClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
    System.out.println("Mouse clicked en la cabecera de inicio");
  }

  /**
   * Maneja el evento de clic en la opción de películas. Configura la escena y el escenario y
   * muestra la ventana de películas.
   * @param event El evento de clic del mouse.
   */
  @FXML
  void peliculasClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_PELICULA_FXML, "Pelicula");
  }

  /**
   * Maneja el evento de clic en la opción de series. Configura la escena y el escenario y muestra
   * la ventana de series.
   * @param event El evento de clic del mouse.
   */
  @FXML
  void seriesClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_SERIES_FXML, "Series");
  }

  /**
   * Maneja el evento de clic en la opción de buscador. Configura la escena y el escenario y muestra
   * la ventana de buscador.
   * @param event El evento de clic del mouse.
   */
  @FXML
  void buscadorClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_BUSCADOR_FXML, "Buscador");
  }

  /**
   * Maneja el evento de clic en la opción de perfil. Configura la escena y el escenario y muestra
   * la ventana de perfil.
   * @param event El evento de clic del mouse.
   */
  @FXML
  void perfilClicked(MouseEvent event) {
    setSceneAndStage();
    gestorVentanas.muestraVentana(stage, Constants.URL_USUARIO_FXML, "Perfil");
  }


  /**
   * Método que inicia la aplicación
   * 
   */
  @FXML
  void initialize() {
    // Inicializar gestorVentanas
    gestorVentanas = new GestorVentanas();

    // Establecer la imagen del logo
    Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
    imagenLogoCabecera.setImage(imagenLogo);

    Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
    lupa.setImage(imagenLupa);

    // Establecer el espacio entre las imágenes en el HBox
    hBoxPeliculas.setSpacing(50);
  }


  /**
   * Maneja el evento de clic en un ImageView de detalles.
   * Obtiene el identificador de la película desde el ImageView
   * y luego abre la ventana de detalles utilizando este identificador.
   * 
   * @param clickedImageView El ImageView en el que se hizo clic.
   */
  void detallesClicked(ImageView clickedImageView) {
      // Obtener el identificador de la película desde el ImageView
      String movieId = getIdFromImageView(clickedImageView);

      // Abrir la ventana de detalles
      abrirVentanaDetalles(movieId);
  }

  /**
   * Abre la ventana de detalles utilizando el identificador de la película proporcionado.
   * Configura la escena y el escenario y muestra la ventana de detalles.
   * 
   * @param id El identificador de la película.
   */
  private void abrirVentanaDetalles(String id) {
      setSceneAndStage();
      gestorVentanas.muestraDetalles(stage, id, tipo);
  }

  /**
   * Obtiene el identificador de la película almacenado en el userData del ImageView.
   * 
   * @param imageView El ImageView del que se extraerá el ID.
   * @return El identificador de la película, o una cadena vacía si no se encuentra.
   */
  private String getIdFromImageView(ImageView imageView) {
      // Obtén el ID de la película almacenado en el userData del ImageView
      Object userData = imageView.getUserData();

      if (userData instanceof String) {
          return (String) userData;
      } else {
          // Manejar la situación donde no hay un ID almacenado
          return "";
      }
  }

  /**
   * Crea y configura un ImageView para una película dada.
   * 
   * @param pelicula La película para la que se creará el ImageView.
   * @return El ImageView configurado para mostrar el póster de la película.
   */
  private ImageView getImageViewFromPelicula(Pelicula pelicula) {
      ImageView imageView = new ImageView();
      imageView.setFitHeight(230.0);
      imageView.setFitWidth(290.0);
      imageView.setPreserveRatio(true);

      // Construir la URL del póster de la película
      String imageUrl = "https://image.tmdb.org/t/p/w500" + pelicula.getPoster_path();

      // Configurar la imagen en el ImageView
      Image image = new Image(imageUrl);
      imageView.getStyleClass().add("imagenPelicula");
      imageView.setImage(image);

      // Configurar el evento de clic para llamar a detallesClicked
      imageView.setOnMouseClicked(event -> detallesClicked(imageView));

      return imageView;
  }

  /**
   * Crea y configura un ImageView para una serie dada.
   * 
   * @param serie La serie para la que se creará el ImageView.
   * @return El ImageView configurado para mostrar el póster de la serie.
   */
  private ImageView getImageViewFromSerie(Series serie) {
      ImageView imageView = new ImageView();
      imageView.setFitHeight(230.0);
      imageView.setFitWidth(290.0);
      imageView.setPreserveRatio(true);

      // Construir la URL del póster de la serie
      String imageUrl = "https://image.tmdb.org/t/p/w500" + serie.getPoster_path();

      // Configurar la imagen en el ImageView
      Image image = new Image(imageUrl);
      imageView.getStyleClass().add("imagenPelicula");
      imageView.setImage(image);

      // Configurar el evento de clic para llamar a detallesClicked
      imageView.setOnMouseClicked(event -> detallesClicked(imageView));

      return imageView;
  }

  /**
   * Carga y muestra las imágenes de las películas asociadas a un género específico.
   *
   * @param generoId El ID del género para el cual se cargarán las películas.
   */

  public void cargarImagenesPeliculasPorGenero(String generoId) {
    // Crear un cliente OkHttpClient para realizar la solicitud HTTP
    OkHttpClient client = new OkHttpClient();

    // Construir la URL de la API con los parámetros necesarios
    String apiUrl = "https://api.themoviedb.org/3/discover/movie";
    String queryParams = "?language=es-ES&page=1&with_genres=" + generoId;
    String fullUrl = apiUrl + queryParams;

    // Construir la solicitud HTTP GET con la URL completa y los encabezados
    // necesarios
    Request request =
        new Request.Builder().url(fullUrl).get().addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + PeliculaController.API_KEY).build();

    try (Response response = client.newCall(request).execute()) {
      // Verificar si la solicitud fue exitosa (código de estado 200)
      if (response.isSuccessful()) {
        // Obtener el cuerpo de la respuesta en formato de cadena JSON
        String responseBody = response.body().string();
        Gson gson = new Gson();

        // Convertir la respuesta JSON en un objeto RespuestaApi utilizando Gson
        RespuestaApi respApi = gson.fromJson(responseBody, RespuestaApi.class);

        // Verificar si la respuesta contiene resultados de películas
        if (respApi != null && respApi.getResults() != null && respApi.getResults().length > 0) {
          // Verificar si el controlador de búsqueda de género no es nulo

          // Limpiar el contenedor de imágenes de películas por género
          hBoxPeliculas.getChildren().clear();

          // Iterar sobre las películas obtenidas y agregar imágenes al contenedor
          for (Pelicula pelicula : respApi.getResults()) {
            // Crear un nuevo ImageView para la película actual
            ImageView imageView = new ImageView();

            if (pelicula.getPoster_path() != null) {
              imageView = getImageViewFromPelicula(pelicula);

              // Almacena el ID de la película en el userData del ImageView
              imageView.setUserData(String.valueOf(pelicula.getId()));
            }

            // Configurar la imagen en el ImageView
            try {
              // Construir la URL completa de la imagen de la película
              String imageUrl = "https://image.tmdb.org/t/p/w500" + pelicula.getPoster_path();
              Image image = new Image(imageUrl);

              imageView.setImage(image);
              imageView.setFitHeight(230.0);
              imageView.setFitWidth(290.0);
              imageView.setPreserveRatio(true);

              // Agregar el ImageView al contenedor HBox
              hBoxPeliculas.getChildren().add(imageView);

            } catch (Exception e) {
              // Manejar cualquier error al cargar la imagen
              System.out
                  .println("Error al cargar la imagen para la película: " + pelicula.getTitle());
              e.printStackTrace(); // Imprimir detalles del error
            }
          }

        } else {
          // Informar si no se encontraron películas para el género especificado
          System.out.println("No se encontraron películas para el género con ID: " + generoId);
        }
      } else {
        // Informar si hubo un error al obtener películas por género (código de estado
        // no 200)
        System.out
            .println("Error al obtener películas por género. Código de estado: " + response.code());
      }
    } catch (IOException e) {
      // Manejar cualquier error de entrada/salida al realizar la solicitud HTTP
      System.out.println("Error al realizar la solicitud HTTP: " + e.getMessage());
    }
  }


  /**
   * Carga y muestra las imágenes de las series asociadas a un género específico.
   *
   * @param generoId El ID del género para el cual se cargarán las series
   */

  public void cargarImagenesSeriesPorGenero(String generoId) {
    // Crear un cliente OkHttpClient para realizar la solicitud HTTP
    OkHttpClient client = new OkHttpClient();

    // Construir la URL de la API con los parámetros necesarios
    String apiUrl = "https://api.themoviedb.org/3/discover/tv";
    String queryParams = "?language=es-ES&page=1&with_genres=" + generoId;
    String fullUrl = apiUrl + queryParams;

    // Construir la solicitud HTTP GET con la URL completa y los encabezados
    // necesarios
    Request request =
        new Request.Builder().url(fullUrl).get().addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + PeliculaController.API_KEY).build();

    try (Response response = client.newCall(request).execute()) {
      // Verificar si la solicitud fue exitosa (código de estado 200)
      if (response.isSuccessful()) {
        // Obtener el cuerpo de la respuesta en formato de cadena JSON
        String responseBody = response.body().string();
        Gson gson = new Gson();

        // Convertir la respuesta JSON en un objeto RespuestaApi utilizando Gson
        RespuestaApiSeries respApi = gson.fromJson(responseBody, RespuestaApiSeries.class);

        // Verificar si la respuesta contiene resultados de películas
        if (respApi != null && respApi.getResults() != null && respApi.getResults().length > 0) {
          // Verificar si el controlador de búsqueda de género no es nulo

          // Limpiar el contenedor de imágenes de películas por género
          hBoxPeliculas.getChildren().clear();
          // Iterar sobre las películas obtenidas y agregar imágenes al contenedor
          for (Series serie : respApi.getResults()) {
            // Crear un nuevo ImageView para la película actual
            ImageView imageView = new ImageView();

            if (serie.getPoster_path() != null) {
              imageView = getImageViewFromSerie(serie);

              // Almacena el ID de la película en el userData del ImageView
              imageView.setUserData(String.valueOf(serie.getId()));
            }

            // Configurar la imagen en el ImageView
            try {
              // Construir la URL completa de la imagen de la película
              String imageUrl = "https://image.tmdb.org/t/p/w500" + serie.getPoster_path();
              Image image = new Image(imageUrl);

              imageView.setImage(image);
              imageView.setFitHeight(230.0);
              imageView.setFitWidth(290.0);
              imageView.setPreserveRatio(true);

              // Agregar el ImageView al contenedor HBox
              hBoxPeliculas.getChildren().add(imageView);

            } catch (Exception e) {
              // Manejar cualquier error al cargar la imagen
              System.out.println("Error al cargar la imagen para la película: " + serie.getName());
              e.printStackTrace(); // Imprimir detalles del error
            }
          }

        } else {
          // Informar si no se encontraron películas para el género especificado
          System.out.println("No se encontraron películas para el género con ID: " + generoId);
        }
      } else {
        // Informar si hubo un error al obtener películas por género (código de estado
        // no 200)
        System.out
            .println("Error al obtener películas por género. Código de estado: " + response.code());
      }
    } catch (IOException e) {
      // Manejar cualquier error de entrada/salida al realizar la solicitud HTTP
      System.out.println("Error al realizar la solicitud HTTP: " + e.getMessage());
    }
  }


  /**
   * Retorna el contenedor HBox utilizado para mostrar las imágenes de las películas o series.
   *
   * @return El contenedor HBox que muestra las imágenes de las películas o series.
   */
  public HBox getHBoxPeliculas() {
      return hBoxPeliculas;
  }

  /**
   * Inicializa los datos necesarios para la visualización de películas o series.
   *
   * @param tipo El tipo de contenido a mostrar (película o serie).
   * @param id El ID del género del contenido a mostrar.
   */
  public void initData(String tipo, String id) {
      this.tipo = tipo;
      if (tipo.equals("movie")) {
          cargarImagenesPeliculasPorGenero(id); // Cargar imágenes de películas por género
      } else if (tipo.equals("tv")) {
          cargarImagenesSeriesPorGenero(id); // Cargar imágenes de series por género
      }
  }



}
