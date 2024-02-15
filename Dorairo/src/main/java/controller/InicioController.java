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
import models.RespuestaApiTrending;
import models.RespuestaApiTrending.MediaItem;
import models.Series;
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
			handleMovieApiCall(client, "https://api.themoviedb.org/3/movie/now_playing?language=es-ES&page=1", novedades);

			// Llamada a la API para top 5
			handleMovieApiCall(client, "https://api.themoviedb.org/3/trending/all/day?language=es-ES", top10);

			mostrarMiListaPeliculas();
			mostrarMiListaSeries();
			miLista.setSpacing(50.0);
		} catch (IOException e) {
			// Manejar excepciones
			e.printStackTrace();
		}
	}

	private void mostrarMiListaPeliculas() {
		UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
		String user = UsuarioController.getUsuarioRegistrado().getUser();
		// Trae las peliculas guardadas en mi lista
		List<UsuarioPelicula> upLista = upDao.searchPeliculasMiLista(user);
		for (UsuarioPelicula up : upLista) {
			// Obtener la imagen de la película
			ImageView imageView = getImageViewFromPelicula(up.getId().getPelicula());

			// Establecer el tamaño de la imagen si es necesario
			imageView.setFitWidth(200); // Ajusta el ancho según tus necesidades
			imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
			imageView.setOnMouseClicked(
					event -> abrirVentanaDetalles(String.valueOf(up.getId().getPelicula().getId()), Constants.PELICULA));

			// Agregar la imagen al HBox de "Mi Lista"
			miLista.getChildren().add(imageView);
		}
	}

	private void mostrarMiListaSeries() {
		UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
		String user = UsuarioController.getUsuarioRegistrado().getUser();
		// Trae las series guardadas en mi lista
		for (UsuarioSerie us : usDao.searchSeriesMiLista(user)) {
			// Obtener la imagen de la serie
			ImageView imageView = getImageViewFromSeries(us.getId().getSeries());

			// Establecer el tamaño de la imagen si es necesario
			imageView.setFitWidth(200);
			imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
			imageView.setOnMouseClicked(
					event -> abrirVentanaDetalles(String.valueOf(us.getId().getSeries().getId()), Constants.SERIES));

			// Agregar la imagen al HBox de "Mi Lista"
			miLista.getChildren().add(imageView);
		}
	}

	private ImageView getImageViewFromSeries(Series series) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setPreserveRatio(true);
		String id = "" + series.getId();
		Image image;
		// Comprueba si se trata de una serie manual y recoge su imagen en ambos casos
		if (id.startsWith(Constants.PREFIJO_ID_SERIES_MANUALES)) {
			image = new Image("file:" + series.getPoster_path());
		} else {
			String imageUrl = "https://image.tmdb.org/t/p/w500" + series.getPoster_path();
			image = new Image(imageUrl);
		}
		// Configurar la imagen en el ImageView
		imageView.getStyleClass().add("sombraDerecha");
		imageView.setImage(image);

		return imageView;
	}

	/**
	 * Devuelve el id almacenado en el userData de la imagen pasada
	 * 
	 * @param imageView Imagen de la que se saca el id
	 * @return Id almacenado
	 */
	private String getIdFromImageView(ImageView imageView) {
		// Obtén el ID almacenado en el userData del ImageView
		Object userData = imageView.getUserData();

		if (userData instanceof String) {
			return (String) userData;
		} else {
			return "";
		}
	}

	/**
	 * Abre la ventana de detalles con los datos de la pelicula/serie con el id
	 * proporcionado
	 * 
	 * @param id   Id de la obra
	 * @param tipo Tipo de obra (tv/movie)
	 */
	private void abrirVentanaDetalles(String id, String tipo) {
		setSceneAndStage();
		gestorVentanas.muestraDetalles(stage, id, tipo);
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
		// Comprueba si es la lista de top10
		if (targetHBox.getId().equalsIgnoreCase("top10")) {
			getResultTrending(targetHBox, responseBody, gson);
		} else {
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
							imageView.setOnMouseClicked(
									event -> abrirVentanaDetalles(String.valueOf(pelicula.getId()), Constants.PELICULA));

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
		}

		targetHBox.setSpacing(50.0);
	}

	/**
	 * Recoge el resultado de la api sobre las peliculas/series trending y las añade
	 * al hbox
	 * 
	 * @param targetHBox
	 * @param responseBody
	 * @param gson
	 */
	public void getResultTrending(HBox targetHBox, String responseBody, Gson gson) {
		RespuestaApiTrending respApi = gson.fromJson(responseBody, RespuestaApiTrending.class);
		// Verifica si hay resultados en la respuesta
		if (respApi.getResults() != null && respApi.getResults().length > 0) {
			int contador = 0;
			for (MediaItem item : respApi.getResults()) {
				if (contador < 10) { // Limitar a 10 películas
					ImageView imageView = null;
					if (item.getPoster_path() != null) {
						imageView = getImageViewFromItem(item);
						imageView
								.setOnMouseClicked(event -> abrirVentanaDetalles(String.valueOf(item.getId()), item.getMediaType()));
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
	}

	private ImageView getImageViewFromItem(MediaItem item) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setPreserveRatio(true);
		Image image;
		String imageUrl = "https://image.tmdb.org/t/p/w500" + item.getPoster_path();
		image = new Image(imageUrl);
		// Configurar la imagen en el ImageView
		imageView.getStyleClass().add("sombraDerecha");
		imageView.setImage(image);

		return imageView;
	}

	private ImageView getImageViewFromPelicula(Pelicula pelicula) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setPreserveRatio(true);
		String id = "" + pelicula.getId();
		Image image;
		// Comprueba si se trata de una peli manual y recoge su imagen en ambos casos
		if (id.startsWith(Constants.PREFIJO_ID_PELIS_MANUALES)) {
			image = new Image("file:" + pelicula.getPoster_path());
		} else {
			String imageUrl = "https://image.tmdb.org/t/p/w500" + pelicula.getPoster_path();
			image = new Image(imageUrl);
		}
		// Configurar la imagen en el ImageView
		imageView.getStyleClass().add("sombraDerecha");
		imageView.setImage(image);

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
