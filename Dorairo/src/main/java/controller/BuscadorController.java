package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import conexion.HibernateUtil;
import constants.Constants;
import dao.PeliculaDaoImpl;
import dao.SeriesDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

/**
 * Clase buscador que nos ayuda a buscar la película o la serie por su nombre
 * 
 * @author Doriana dc
 * 
 */

public class BuscadorController {

	/** ImageView para mostrar el logotipo en la cabecera */
	@FXML
	private ImageView imagenLogoCabecera;

	/** Panel contenedor de la cabecera */
	@FXML
	private Pane paneCabecera;

	/**
	 * StackPane para organizar elementos en la cabecera relacionados con la página
	 * de inicio
	 */
	@FXML
	private StackPane stackPaneInicioCabecera;

	/**
	 * StackPane para organizar elementos en la cabecera relacionados con el
	 * logotipo
	 */
	@FXML
	private StackPane stackPaneLogoCabecera;

	/**
	 * StackPane para organizar elementos en la cabecera relacionados con las
	 * películas
	 */
	@FXML
	private StackPane stackPanePeliculasCabecera;

	/** Referencia al escenario de la aplicación */
	private Stage stage;

	/** Gestor de ventanas para controlar la navegación entre diferentes vistas */
	private GestorVentanas gestorVentanas;

	/** TextField donde el usuario ingresa el término de búsqueda */
	@FXML
	private TextField estasBuscando;

	/** HBox que muestra un mensaje cuando se encuentra un resultado de búsqueda */
	@FXML
	private HBox seEncontro;

	/** Botón para iniciar la búsqueda */
	@FXML
	private Button buscar;

	/**
	 * ImageView que muestra un ícono de lupa para representar la acción de búsqueda
	 */
	@FXML
	private ImageView lupa;

	/** Término de búsqueda ingresado por el usuario */
	private String searchTerm;

	/**
	 * Método para manejar el evento de clic en la opción de inicio
	 * 
	 * @param event
	 */
	@FXML
	void inicioClicked(MouseEvent event) {
		// Establecer la escena y el escenario actual
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
	}

	/**
	 * Método para manejar el evento de clic en la opción de películas
	 * 
	 * @param event
	 */
	@FXML
	void peliculasClicked(MouseEvent event) {
		// Establecer la escena y el escenario actual
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_PELICULA_FXML, "Pelicula");
	}

	/**
	 * Método para manejar el evento de clic en la opción de series
	 * 
	 * @param event
	 */
	@FXML
	void seriesClicked(MouseEvent event) {
		// Establecer la escena y el escenario actual
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_SERIES_FXML, "Series");
	}

	/**
	 * Método para manejar el evento de clic en el buscador
	 * 
	 * @param event
	 */
	@FXML
	void buscadorClicked(MouseEvent event) {
		// Establecer la escena y el escenario actual
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_BUSCADOR_FXML, "Buscador");
	}

	/**
	 * Método para manejar el evento de clic en el perfil
	 * 
	 * @param event
	 */
	@FXML
	void perfilClicked(MouseEvent event) {
		// Establecer la escena y el escenario actual
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_USUARIO_FXML, "Perfil");
	}

	/**
	 * Método para manejar el evento de clic en la opción de agregar manualmente
	 * 
	 * @param event
	 */
	@FXML
	void agregarManualmenteClicked(ActionEvent event) {
		// Establecer la escena y el escenario actual
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_AGREGADAS_MANUALMENTE_FXML, "Agregar Manualmente");
	}

	/**
	 * Método para establecer la escena y el escenario actual
	 */
	public void setSceneAndStage() {
		stage = (Stage) imagenLogoCabecera.getScene().getWindow();
	}

	/**
	 * Método que se llama automáticamente al inicializar el controlador FXML
	 */
	@FXML
	void initialize() {
		// Inicializar el gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Cargar y establecer la imagen del logo de la cabecera
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagenLogo);
		// Cargar y establecer la imagen de la lupa de búsqueda
		Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
		lupa.setImage(imagenLupa);
	}

	/**
	 * Metodo donde se escribe para buscar la pelicula o la serie por su titulo
	 * 
	 * @param event
	 */
	@FXML
	void clickBuscar(MouseEvent event) {
		searchTerm = estasBuscando.getText();
		if (!searchTerm.isEmpty()) {
			buscarPeliculasYSeriesPorTitulo();
			agregarImagenABuscador();
			agregarImagenABuscadorSeries();
		}
	}

	/**
	 * Metodo para buscar la pelicula o la serie por su titulo
	 * 
	 */
	private void buscarPeliculasYSeriesPorTitulo() {
		OkHttpClient client = new OkHttpClient();
		String movieApiUrl = "https://api.themoviedb.org/3/search/movie";
		String tvApiUrl = "https://api.themoviedb.org/3/search/tv";
		String queryParams = "?language=es-ES&query=" + searchTerm + "&page=1";

		String movieFullUrl = movieApiUrl + queryParams;
		Request movieRequest = new Request.Builder().url(movieFullUrl).get().addHeader("accept", "application/json")
				.addHeader("Authorization", "Bearer " + PeliculaController.API_KEY).build();

		String tvFullUrl = tvApiUrl + queryParams;
		Request tvRequest = new Request.Builder().url(tvFullUrl).get().addHeader("accept", "application/json")
				.addHeader("Authorization", "Bearer " + PeliculaController.API_KEY).build();

		try {
			List<Pelicula> movieResults = getSearchResults(client.newCall(movieRequest).execute());
			List<Series> tvResults = getSeriesSearchResults(client.newCall(tvRequest).execute());

			mostrarResultados(movieResults, tvResults); // Pasar ambas listas como argumentos
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error al conectar con la API: " + e.getMessage());
		}
	}

	/**
	 * Método para obtener los resultados de la búsqueda de series a partir de la
	 * respuesta HTTP.
	 * 
	 * @param response La respuesta HTTP obtenida de la API.
	 * @return Una lista de objetos Series que representan los resultados de la
	 *         búsqueda.
	 * @throws IOException Si ocurre un error al procesar la respuesta HTTP.
	 */
	private List<Series> getSeriesSearchResults(Response response) throws IOException {
		List<Series> results = new ArrayList<>();
		if (!response.isSuccessful()) {
			System.out.println("Error: " + response.code());
			return results;
		}

		String responseBody = response.body().string();
		Gson gson = new Gson();
		RespuestaApiSeries respApi = gson.fromJson(responseBody, RespuestaApiSeries.class);

		if (respApi.getResults() != null) {
			results.addAll(Arrays.asList(respApi.getResults()));
		}

		return results;
	}

	/**
	 * Método para obtener los resultados de la búsqueda de películas a partir de la
	 * respuesta HTTP.
	 * 
	 * @param response La respuesta HTTP obtenida de la API.
	 * @return Una lista de objetos Pelicula que representan los resultados de la
	 *         búsqueda.
	 * @throws IOException Si ocurre un error al procesar la respuesta HTTP.
	 */
	private List<Pelicula> getSearchResults(Response response) throws IOException {
		List<Pelicula> results = new ArrayList<>();
		if (!response.isSuccessful()) {
			System.out.println("Error: " + response.code());
			return results;
		}
		String responseBody = response.body().string();
		Gson gson = new Gson();
		RespuestaApi respApi = gson.fromJson(responseBody, RespuestaApi.class);

		if (respApi.getResults() != null) {
			results.addAll(Arrays.asList(respApi.getResults()));
		}

		return results;
	}

	/**
	 * Método para mostrar los resultados de la búsqueda de películas y series en la
	 * interfaz gráfica.
	 * 
	 * @param datosPeliculas La lista de películas obtenidas de la búsqueda.
	 * @param datosSeries    La lista de series obtenidas de la búsqueda.
	 */
	private void mostrarResultados(List<Pelicula> datosPeliculas, List<Series> datosSeries) {
		// Limpiar el contenido solo si hay datos disponibles para mostrar
		if ((datosPeliculas != null && !datosPeliculas.isEmpty()) || (datosSeries != null && !datosSeries.isEmpty())) {
			seEncontro.getChildren().clear();

			// Procesar películas si hay datos disponibles
			if (datosPeliculas != null && !datosPeliculas.isEmpty()) {
				for (Pelicula pelicula : datosPeliculas) {
					ImageView imageView = getImageViewFromPelicula(pelicula);
					seEncontro.getChildren().add(imageView);
					asociarEventoClicPelicula(imageView, pelicula); // Asociar evento de clic para películas
				}
			}

			// Procesar series si hay datos disponibles
			if (datosSeries != null && !datosSeries.isEmpty()) {
				for (Series serie : datosSeries) {
					ImageView imageView = getImageViewFromSerie(serie);
					seEncontro.getChildren().add(imageView);
					asociarEventoClicSerie(imageView, serie); // Asociar evento de clic para series
				}
			}
		}
		// Establecer el espaciado entre las imágenes
		seEncontro.setSpacing(50.0);
	}

	/**
	 * Método para mostrar los detalles de la pelicula
	 * 
	 * @param pelicula
	 */
	private void mostrarDetallesPelicula(Pelicula pelicula) {
		// Obtener el ID de la película
		int id = pelicula.getId();
		abrirVentanaDetalles(id, "movie");
	}

	/**
	 * Método para mostrar los detalles de la serie
	 * 
	 * @param serie
	 */
	private void mostrarDetallesSerie(Series serie) {
		// Obtener el ID de la serie
		int id = serie.getId();
		abrirVentanaDetalles(id, "tv");
	}

	/**
	 * Asocia un evento de clic a una imagen de película para mostrar los detalles
	 * de la película.
	 * 
	 * @param imageView El ImageView que representa la imagen de la película.
	 * @param pelicula  La película asociada a la imagen.
	 */
	private void asociarEventoClicPelicula(ImageView imageView, Pelicula pelicula) {
		imageView.setOnMouseClicked(event -> mostrarDetallesPelicula(pelicula));
	}

	/**
	 * Asocia un evento de clic a una imagen de serie para mostrar los detalles de
	 * la serie.
	 * 
	 * @param imageView El ImageView que representa la imagen de la serie.
	 * @param serie     La serie asociada a la imagen.
	 */
	private void asociarEventoClicSerie(ImageView imageView, Series serie) {
		imageView.setOnMouseClicked(event -> mostrarDetallesSerie(serie));
	}

	/**
	 * Crea un ImageView a partir de un objeto de tipo Serie.
	 * 
	 * @param serie La serie de la cual se va a crear el ImageView.
	 * @return El ImageView creado a partir de la serie.
	 */
	private ImageView getImageViewFromSerie(Series serie) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setFitWidth(290.0);
		imageView.setPreserveRatio(true);
		imageView.getStyleClass().add("sombraDerecha");

		// Construir la URL del póster de la serie
		String imageUrl = "https://image.tmdb.org/t/p/w500" + serie.getPoster_path();

		// Configurar la imagen en el ImageView
		Image image = new Image(imageUrl);
		imageView.setImage(image);

		// Asociar el evento de clic para mostrar los detalles de la serie
		asociarEventoClicSerie(imageView, serie);

		return imageView;
	}

	/**
	 * Crea un ImageView a partir de un objeto de tipo Película.
	 * 
	 * @param pelicula La película de la cual se va a crear el ImageView.
	 * @return El ImageView creado a partir de la película.
	 */
	private ImageView getImageViewFromPelicula(Pelicula pelicula) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setFitWidth(290.0);
		imageView.setPreserveRatio(true);
		imageView.getStyleClass().add("sombraDerecha");

		// Construir la URL del póster de la película
		String imageUrl = "https://image.tmdb.org/t/p/w500" + pelicula.getPoster_path();

		// Configurar la imagen en el ImageView
		Image image = new Image(imageUrl);
		imageView.setImage(image);

		// Asociar el evento de clic para mostrar los detalles de la película
		asociarEventoClicPelicula(imageView, pelicula);

		return imageView;
	}

	/**
	 * Método para abrir la ventana de detalles de la pelicula o serie
	 * 
	 * @param id
	 * @param tipo
	 */
	public void abrirVentanaDetalles(int id, String tipo) {
		setSceneAndStage();
		gestorVentanas.muestraDetalles(stage, String.valueOf(id), tipo);
	}

	/**
	 * Método para mostrar las imagenes de las peliculas de la base de datos en el
	 * buscador
	 * 
	 */
	public void agregarImagenABuscador() {
		PeliculaDaoImpl pDao = new PeliculaDaoImpl(HibernateUtil.openSession());
		List<Pelicula> listaPelicula = pDao.searchByTitle(searchTerm);

		for (Pelicula peli : listaPelicula) {
			// Obtener la imagen de la película
			ImageView imageView = new ImageView(new Image("file:" + peli.getPoster_path()));

			// Establecer el tamaño de la imagen
			imageView.setFitWidth(200); // Ancho fijo
			imageView.setFitHeight(300); // Alto fijo (opcional)
			imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
			// Almacena el ID de la película en el userData del ImageView
			imageView.setUserData(String.valueOf(peli.getId()));
			imageView.setOnMouseClicked(event -> mostrarDetallesPelicula(peli));
			seEncontro.getChildren().add(imageView);
		}
	}

	/**
	 * Método para mostrar las imagenes de las series de la base de datos en el
	 * buscador
	 * 
	 */
	public void agregarImagenABuscadorSeries() {
		SeriesDaoImpl sDao = new SeriesDaoImpl(HibernateUtil.openSession());
		List<Series> listaSeries = sDao.searchByTitle(searchTerm);

		for (Series serie : listaSeries) {
			// Obtener la imagen de la película
			ImageView imageView = new ImageView(new Image("file:" + serie.getPoster_path()));

			// Establecer el tamaño de la imagen
			imageView.setFitWidth(200); // Ancho fijo
			imageView.setFitHeight(300); // Alto fijo (opcional)
			imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
			// Almacena el ID de la película en el userData del ImageView
			imageView.setUserData(String.valueOf(serie.getId()));
			imageView.setOnMouseClicked(event -> mostrarDetallesSerie(serie));
			seEncontro.getChildren().add(imageView);
		}
	}
}
