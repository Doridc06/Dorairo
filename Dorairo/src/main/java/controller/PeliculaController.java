package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import constants.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Genero;
import models.Pelicula;
import models.RespuestaApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;

import utilities.GestorVentanas;

public class PeliculaController {

	public static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

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
	private HBox peliculasEstrenos;

	@FXML
	private HBox peliculasPopulares;

	/** Scene de la ventana de Inicio */
	private Scene scene;

	/** Stage de la ventana de Inicio */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	@FXML
	private MenuItem Aleatoria;



	private BuscarGeneroController buscarGeneroController;

	@FXML
	private ImageView cartel;

	// para los generos

	// Lista para almacenar todas las películas obtenidas de la API
	static List<Pelicula> todasLasPeliculas = new ArrayList<>();

	// Lista para almacenar las películas filtradas por género
	private List<Pelicula> peliculasFiltradas = new ArrayList<>();

	private List<Genero> listaGeneros = new ArrayList<>();

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

	   
	void detallesClicked(ImageView clickedImageView) {
		// Obtener el identificador de la película desde el ImageView
		String movieId = getMovieIdFromImageView(clickedImageView);

		// Abrir la ventana de detalles
		abrirVentanaDetalles(movieId);
	}

	private void abrirVentanaDetalles(String movieId) {
		setSceneAndStage();
		gestorVentanas.muestraDetalles(stage, movieId, "movie");
	}

	@FXML
	void initialize() {
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagenLogo);

		

		// Configuración de los eventos para los CheckBox de género
		GeneroAccion.setOnAction(event -> generoClicked("28"));
		GeneroAventura.setOnAction(event -> generoClicked("12"));
		GeneroComedia.setOnAction(event -> generoClicked("35"));
		GeneroTerror.setOnAction(event -> generoClicked("27"));
		GeneroSuspenso.setOnAction(event -> generoClicked("53"));
		GeneroDrama.setOnAction(event -> generoClicked("18"));
		GeneroMisterio.setOnAction(event -> generoClicked(""));
		GeneroMusical.setOnAction(event -> generoClicked(""));
		GeneroCienciaFiccion.setOnAction(event -> generoClicked(""));
		GeneroAnimacion.setOnAction(event -> generoClicked(""));

		Aleatoria.setOnAction(event -> peliAleatoriaClicked());

		try {
			// Configuración del cliente HTTP (OkHttpClient)
			OkHttpClient client = new OkHttpClient();

			// Llamada a la API para películas populares
			handleMovieApiCall(client, "https://api.themoviedb.org/3/movie/popular?language=es-ES&page=1",
					peliculasPopulares);

			// Llamada a la API para próximas películas
			handleMovieApiCall(client, "https://api.themoviedb.org/3/movie/upcoming?language=es-ES&page=1",
					peliculasEstrenos);

			// Configuración del evento para cada ImageView en peliculasEstrenos
			for (Node node : peliculasEstrenos.getChildren()) {
				if (node instanceof ImageView) {
					ImageView imageView = (ImageView) node;
					imageView.setOnMouseClicked(event -> detallesClicked(imageView));
				}
			}

			// Configuración del evento para cada ImageView en peliculasPopulares
			for (Node node : peliculasPopulares.getChildren()) {
				if (node instanceof ImageView) {
					ImageView imageView = (ImageView) node;
					imageView.setOnMouseClicked(event -> detallesClicked(imageView));
				}
			}
			
		} catch (IOException e) {
			// Manejar excepciones
			e.printStackTrace();
		}
	}

	private void handleMovieApiCall(OkHttpClient client, String apiUrl, HBox targetHBox) throws IOException {
		// Construir la solicitud para la API de películas
		Request request = new Request.Builder().url(apiUrl).get().addHeader("accept", "application/json")
				.addHeader("Authorization", "Bearer " + API_KEY).build();

		// Ejecutar la solicitud y obtener la respuesta
		Response response = client.newCall(request).execute();

		// Obtener el cuerpo de la respuesta como cadena
		String responseBody = response.body().string();

		// Utilizar Gson para convertir la respuesta JSON a objetos Java
		Gson gson = new Gson();
		RespuestaApi respApi = gson.fromJson(responseBody, RespuestaApi.class);

		// Verificar si hay resultados en la respuesta
		if (respApi.getResults() != null && respApi.getResults().length > 0) {
			// Iterar sobre las películas y agregar imágenes al HBox
			int contador = 0;
			for (Pelicula datos : respApi.getResults()) {
				if (contador < 12) {
					ImageView imageView = null;
					if (datos.getPoster_path() != null) {
						imageView = getImageViewFromPelicula(datos);

						// Almacena el ID de la película en el userData del ImageView
						imageView.setUserData(String.valueOf(datos.getId()));

					}

					// Verificar si imageView no es nulo antes de agregarlo al HBox
					if (imageView != null) {
						targetHBox.getChildren().add(imageView);
					}
					todasLasPeliculas.add(datos); // Agrega la película a la lista de todas las películas

					contador++;
				} else {
					break;
				}
			}
		} else {
			// No hay resultados
		}
		targetHBox.setSpacing(50.0);
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

	/**
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = imagenLogoCabecera.getScene();
		stage = (Stage) scene.getWindow();
	}

	void generoClicked(String generoId) {
	  setSceneAndStage();
      gestorVentanas. muestraBuscadorGenero(stage,"movie",generoId);
	}


// para la pelicula aleatoria
	@FXML
	void peliAleatoriaClicked() {
		abrirVentanaPeliAleatoria();
	}

    private void abrirVentanaPeliAleatoria() {
      // Obtener una película aleatoria
      Pelicula peliculaAleatoria = obtenerPeliculaAleatoria();

      // Verificar si se encontró una película aleatoria
      if (peliculaAleatoria != null) {
          // Obtener el ID de la película aleatoria
          String movieId = String.valueOf(peliculaAleatoria.getId());

          // Abrir la ventana de detalles de la película aleatoria
          abrirVentanaDetalles(movieId);
      } else {
          // Manejar la situación en la que no se pudo obtener una película aleatoria
          System.out.println("No se pudo obtener una película aleatoria");
      }
  }

  private Pelicula obtenerPeliculaAleatoria() {
      // Verificar si hay películas disponibles
      if (!todasLasPeliculas.isEmpty()) {
          // Obtener un índice aleatorio dentro del rango de la lista de películas
          int indiceAleatorio = new Random().nextInt(todasLasPeliculas.size());

          // Obtener y devolver la película aleatoria
          return todasLasPeliculas.get(indiceAleatorio);
      } else {
          return null;
      }
  }


}
