package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import com.google.gson.Gson;

import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioPeliculaDaoImpl;
import dao.UsuarioSerieDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.Pelicula;
import models.RespuestaApi;
import models.UsuarioPelicula;
import models.UsuarioSerie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;

/**
 * Clase con las funciones del Inicio
 * 
 * @author JairoAB
 *
 */
public class InicioController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView imagenLogoCabecera;

	@FXML
	private HBox miLista;

	@FXML
	private HBox novedades;

	@FXML
	private Pane paneCabecera;

	@FXML
	private StackPane stackPaneInicioCabecera;

	@FXML
	private StackPane stackPaneLogoCabecera;

	@FXML
	private StackPane stackPanePeliculasCabecera;

	@FXML
	private HBox top10;

	/** Scene de la ventana de Inicio */
	private Scene scene;

	/** Stage de la ventana de Inicio */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	@FXML
	private ImageView lupa;

	/** Conexion con la base de datos */
	private Session session;

	private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

	@FXML
	void initialize() {
		session = HibernateUtil.openSession();
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagenLogo);

		Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
		lupa.setImage(imagenLupa);
		try {
			// Configuración del cliente HTTP (OkHttpClient)
			OkHttpClient client = new OkHttpClient();

			// Llamada a la API para películas novedades
			handleMovieApiCall(client, "https://api.themoviedb.org/3/movie/popular?language=es-ES&page=1", novedades);

			// Llamada a la API para top 5
			handleMovieApiCall(client, "https://api.themoviedb.org/3/trending/all/day?language=es-ES", top10);

			// Muestra la lista
			mostrarMiLista();

			// Sacar pelis de mi lista, de la bbdd
			/* METODO QUE BUSQUE EN BBDD LAS PELIS // SERIES QUE TIENEN MI LISTA = TRUE */

			// Configuración del evento para cada ImageView en novedades
			for (Node node : novedades.getChildren()) {
				if (node instanceof ImageView) {
					ImageView imageView = (ImageView) node;
					imageView.setOnMouseClicked(event -> detallesClicked(imageView));
				}
			}

			// Configuración del evento para cada ImageView en top10
			for (Node node : top10.getChildren()) {
				if (node instanceof ImageView) {
					ImageView imageView = (ImageView) node;
					imageView.setOnMouseClicked(event -> detallesClicked(imageView));
				}
			}

			// Configuración del evento para cada ImageView en miLista
			for (Node node : miLista.getChildren()) {
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

	private void mostrarMiLista() {
		// Coge el numero de peliculas y series que hay registradas para el usuario
		UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
		UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
		String user = UsuarioController.getUsuarioRegistrado().getNombre();
		int numeroSeries = Integer.parseInt(usDao.searchNumeroSeries(user));
		int numeroPeliculas = Integer.parseInt(upDao.searchNumeroPeliculas(user));

		// Comprueba que haya algo guardado
		if (numeroSeries > 0 || numeroPeliculas > 0) {
			// Trae la lista de series y peliculas guardadas
			List<UsuarioSerie> listaUserSerie = usDao.searchByUsuario(user);
			List<UsuarioPelicula> listaUserPeli = upDao.searchByUsuario(user);
			// Coge el numero total de pelis y series guardados
			int totalSeries = listaUserSerie.size();

//			for (int i = 1; i <= totalGuardadas; i++) {
//				// Muestra una de peli y otra de serie, para que salgan mezcladas
//				if (i % 2 == 0) {
//							ImageView imageView = null;
//							if (pelicula.getPoster_path() != null) {
//								imageView = getImageViewFromPelicula(pelicula);
//
//								// Almacena el ID de la película en el userData del ImageView
//								imageView.setUserData(String.valueOf(pelicula.getId()));
//
//							}
//							// Verificar si imageView no es nulo antes de agregarlo al HBox
//							if (imageView != null) {
//								targetHBox.getChildren().add(imageView);
//							}
//
//							contador++;
//						} else {
//							break; // Se han agregado 10 películas, salir del bucle
//						}
//					}
//				}
//				} else {
//					// peli
//				}
//			}

		}

//			for (Pelicula pelicula : respApi.getResults()) {
//				System.out.println("Adding image: " + pelicula.getPoster_path());
//				if (contador < 10) { // Limitar a 10 películas
//					ImageView imageView = null;
//					if (pelicula.getPoster_path() != null) {
//						imageView = getImageViewFromPelicula(pelicula);
//
//						// Almacena el ID de la película en el userData del ImageView
//						imageView.setUserData(String.valueOf(pelicula.getId()));
//
//					}
//					// Verificar si imageView no es nulo antes de agregarlo al HBox
//					if (imageView != null) {
//						targetHBox.getChildren().add(imageView);
//					}
//
//					contador++;
//				} else {
//					break; // Se han agregado 10 películas, salir del bucle
//				}
//			}
//		}
	}

	void detallesClicked(ImageView clickedImageView) {
		// Obtener el identificador de la película desde el ImageView
		String movieId = getMovieIdFromImageView(clickedImageView);

		// Abrir la ventana de detalles
		abrirVentanaDetalles(movieId);
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

	private void abrirVentanaDetalles(String movieId) {
		setSceneAndStage();
		gestorVentanas.muestraDetalles(stage, movieId, "movie");
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
			for (Pelicula pelicula : respApi.getResults()) {
				if (contador < 10) { // Limitar a 10 películas
					ImageView imageView = null;
					if (pelicula.getPoster_path() != null) {
						imageView = getImageViewFromPelicula(pelicula);

						// Almacena el ID de la película en el userData del ImageView
						imageView.setUserData(String.valueOf(pelicula.getId()));

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
		}
		targetHBox.setSpacing(50.0);
	}

	private ImageView getImageViewFromPelicula(Pelicula pelicula) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setPreserveRatio(true);

		// Construir la URL del póster de la película
		String imageUrl = "https://image.tmdb.org/t/p/w500" + pelicula.getPoster_path();

		// Configurar la imagen en el ImageView
		Image image = new Image(imageUrl);
		imageView.getStyleClass().add("sombraDerecha");
		imageView.setImage(image);

		// Configurar el evento de clic para llamar a detallesClicked
		imageView.setOnMouseClicked(event -> detallesClicked(imageView));
		return imageView;
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

	/**
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = imagenLogoCabecera.getScene();
		stage = (Stage) scene.getWindow();
	}

}
