package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import constants.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Genero;
import models.Pelicula;
import models.RespuestaApi;
import models.RespuestaApiSeries;
import models.Series;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class BuscarGeneroController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView imagenLogoCabecera;

	@FXML
	private HBox peliculasPorGenero;

	@FXML
	private StackPane stackPaneInicioCabecera;

	@FXML
	private StackPane stackPaneLogoCabecera;

	@FXML
	private StackPane stackPanePeliculasCabecera;

	/** Scene de la ventana de Inicio */
	private Scene scene;

	/** Stage de la ventana de Inicio */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	@FXML
	private Text seEncontro;

	@FXML
	public HBox hBoxPeliculas;

	@FXML
	private ImageView cartel;

	// Lista para almacenar todas las películas obtenidas de la API
	static List<Pelicula> todasLasPeliculas = new ArrayList<>();

	// Lista para almacenar las películas filtradas por género
	private List<Pelicula> peliculasFiltradas = new ArrayList<>();

	private List<Genero> listaGeneros = new ArrayList<>();

  private String tipo;

	/**
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = imagenLogoCabecera.getScene();
		stage = (Stage) scene.getWindow();
	}

	@FXML
	void inicioClicked(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
		System.out.println("Mouse clicked en la cabecera de inicio");
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
	void initialize() {
		// Inicializar gestorVentanas
		gestorVentanas = new GestorVentanas();
		// No necesitas inicializar hBoxPeliculas aquí

		// Establecer la imagen del logo
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagenLogo);
		
		
		// Configuración del evento para cada ImageView en peliculasEstrenos
	    for (Node node : hBoxPeliculas.getChildren()) {
	        if (node instanceof ImageView) {
	            ImageView imageView = (ImageView) node;
	            imageView.setOnMouseClicked(event -> detallesClicked(imageView));
	        }
	    }
	}
	
	
	   void detallesClicked(ImageView clickedImageView) {
	        // Obtener el identificador de la película desde el ImageView
	        String movieId = getMovieIdFromImageView(clickedImageView);

	        // Abrir la ventana de detalles
	        abrirVentanaDetalles(movieId);
	        
	    }
	    
	     private void abrirVentanaDetalles(String id) {
	            setSceneAndStage();
	            gestorVentanas.muestraDetalles(stage, id, tipo);
	      }
	    
	    
	    
	    private String getMovieIdFromImageView(ImageView imageView) {
	        // Obtén el ID de la película almacenado en el userData del ImageView
	        Object userData = imageView.getUserData();

	        if (userData instanceof String) {
	            return (String) userData;
	        } else {
	            // Manejar la situación donde no hay un ID almacenado
	            return "";
	        }
	    }

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
	    
	    
	       private ImageView getImageViewFromSerie(Series series) {
	            ImageView imageView = new ImageView();
	            imageView.setFitHeight(230.0);
	            imageView.setFitWidth(290.0);
	            imageView.setPreserveRatio(true);

	            // Construir la URL del póster de la película
	            String imageUrl = "https://image.tmdb.org/t/p/w500" + series.getPoster_path();

	            // Configurar la imagen en el ImageView
	            Image image = new Image(imageUrl);
	            imageView.getStyleClass().add("imagenPelicula");
	            imageView.setImage(image);

	            // Configurar el evento de clic para llamar a detallesClicked
	            imageView.setOnMouseClicked(event -> detallesClicked(imageView));

	            return imageView;
	        }
	    
	public void cargarImagenesPeliculasPorGenero(String generoId) {
      // Crear un cliente OkHttpClient para realizar la solicitud HTTP
      OkHttpClient client = new OkHttpClient();

      // Construir la URL de la API con los parámetros necesarios
      String apiUrl = "https://api.themoviedb.org/3/discover/movie";
      String queryParams = "?language=es-ES&page=1&with_genres=" + generoId;
      String fullUrl = apiUrl + queryParams;

      // Construir la solicitud HTTP GET con la URL completa y los encabezados
      // necesarios
      Request request = new Request.Builder().url(fullUrl).get().addHeader("accept", "application/json")
              .addHeader("Authorization", "Bearer " + PeliculaController.API_KEY).build();

      try (Response response = client.newCall(request).execute()) {
          // Verificar si la solicitud fue exitosa (código de estado 200)
          if (response.isSuccessful()) {
              // Obtener el cuerpo de la respuesta en formato de cadena JSON
              String responseBody = response.body().string();
              System.out.println("1" + responseBody);
              Gson gson = new Gson();

              // Convertir la respuesta JSON en un objeto RespuestaApi utilizando Gson
              RespuestaApi respApi = gson.fromJson(responseBody, RespuestaApi.class);
              System.out.println("2" + respApi);

              // Verificar si la respuesta contiene resultados de películas
              if (respApi != null && respApi.getResults() != null && respApi.getResults().length > 0) {
                  // Verificar si el controlador de búsqueda de género no es nulo
                 
                      // Limpiar el contenedor de imágenes de películas por género
                      hBoxPeliculas.getChildren().clear();
                      System.out.println("25" + hBoxPeliculas);
                      // Iterar sobre las películas obtenidas y agregar imágenes al contenedor
                      for (Pelicula pelicula : respApi.getResults()) {
                          System.out.println("3" + pelicula);
                          // Crear un nuevo ImageView para la película actual
                          ImageView imageView = new ImageView();
                          System.out.println("4" + imageView);
                          
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
                              System.out.println("5" + imageView);
                              imageView.setImage(image);
                              imageView.setFitHeight(230.0);
                              imageView.setFitWidth(290.0);
                              imageView.setPreserveRatio(true);

                              // Agregar el ImageView al contenedor HBox
                              hBoxPeliculas.getChildren().add(imageView);
                              System.out.println("6" + hBoxPeliculas);
                              
                          } catch (Exception e) {
                              // Manejar cualquier error al cargar la imagen
                              System.out.println("Error al cargar la imagen para la película: " + pelicula.getTitle());
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
              System.out.println("Error al obtener películas por género. Código de estado: " + response.code());
          }
      } catch (IOException e) {
          // Manejar cualquier error de entrada/salida al realizar la solicitud HTTP
          System.out.println("Error al realizar la solicitud HTTP: " + e.getMessage());
      }
  }
	
	
	
	public void cargarImagenesSeriesPorGenero(String generoId) {
      // Crear un cliente OkHttpClient para realizar la solicitud HTTP
      OkHttpClient client = new OkHttpClient();

      // Construir la URL de la API con los parámetros necesarios
      String apiUrl = "https://api.themoviedb.org/3/discover/tv";
      String queryParams = "?language=es-ES&page=1&with_genres=" + generoId;
      String fullUrl = apiUrl + queryParams;

      // Construir la solicitud HTTP GET con la URL completa y los encabezados
      // necesarios
      Request request = new Request.Builder().url(fullUrl).get().addHeader("accept", "application/json")
              .addHeader("Authorization", "Bearer " + PeliculaController.API_KEY).build();

      try (Response response = client.newCall(request).execute()) {
          // Verificar si la solicitud fue exitosa (código de estado 200)
          if (response.isSuccessful()) {
              // Obtener el cuerpo de la respuesta en formato de cadena JSON
              String responseBody = response.body().string();
              System.out.println("1" + responseBody);
              Gson gson = new Gson();

              // Convertir la respuesta JSON en un objeto RespuestaApi utilizando Gson
              RespuestaApiSeries respApi = gson.fromJson(responseBody, RespuestaApiSeries.class);
              System.out.println("2" + respApi);

              // Verificar si la respuesta contiene resultados de películas
              if (respApi != null && respApi.getResults() != null && respApi.getResults().length > 0) {
                  // Verificar si el controlador de búsqueda de género no es nulo
                 
                      // Limpiar el contenedor de imágenes de películas por género
                      hBoxPeliculas.getChildren().clear();
                      System.out.println("25" + hBoxPeliculas);
                      // Iterar sobre las películas obtenidas y agregar imágenes al contenedor
                      for (Series serie : respApi.getResults()) {
                          System.out.println("3" + serie);
                          // Crear un nuevo ImageView para la película actual
                          ImageView imageView = new ImageView();
                          System.out.println("4" + imageView);
                          
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
                              System.out.println("5" + imageView);
                              imageView.setImage(image);
                              imageView.setFitHeight(230.0);
                              imageView.setFitWidth(290.0);
                              imageView.setPreserveRatio(true);

                              // Agregar el ImageView al contenedor HBox
                              hBoxPeliculas.getChildren().add(imageView);
                              System.out.println("6" + hBoxPeliculas);
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
              System.out.println("Error al obtener películas por género. Código de estado: " + response.code());
          }
      } catch (IOException e) {
          // Manejar cualquier error de entrada/salida al realizar la solicitud HTTP
          System.out.println("Error al realizar la solicitud HTTP: " + e.getMessage());
      }
  }
	
	

	public HBox getHBoxPeliculas() {
		return hBoxPeliculas;
	}

  public void initData(String tipo,String id) {
    this.tipo = tipo;
    if(tipo.equals("movie")) {
       cargarImagenesPeliculasPorGenero(id);
    }else if (tipo.equals("tv")) {
      cargarImagenesSeriesPorGenero(id);
    }
   
    
    
  }
	

}
