package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import constants.Constants;
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

public class BuscadorController {

	@FXML
	private ImageView imagenLogoCabecera;

	@FXML
	private Pane paneCabecera;

	@FXML
	private StackPane stackPaneInicioCabecera;

	@FXML
	private StackPane stackPaneLogoCabecera;

	@FXML
	private StackPane stackPanePeliculasCabecera;

	private Stage stage;

	private GestorVentanas gestorVentanas;

	@FXML
	private TextField estasBuscando;

	@FXML
	private HBox seEncontro;

	@FXML
	private Button buscar;

	@FXML
	private ImageView lupa;

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
		stage = (Stage) imagenLogoCabecera.getScene().getWindow();
	}

	@FXML
	void initialize() {
		gestorVentanas = new GestorVentanas();
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagenLogo);
		Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
		lupa.setImage(imagenLupa);
	}

	@FXML
	void clickBuscar(MouseEvent event) {
		String searchTerm = estasBuscando.getText();
		if (!searchTerm.isEmpty()) {
			buscarPeliculasYSeriesPorTitulo(searchTerm);
		}
	}

	private void buscarPeliculasYSeriesPorTitulo(String titulo) {
		OkHttpClient client = new OkHttpClient();
		String movieApiUrl = "https://api.themoviedb.org/3/search/movie";
		String tvApiUrl = "https://api.themoviedb.org/3/search/tv";
		String queryParams = "?language=es-ES&query=" + titulo + "&page=1";

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

	private void mostrarResultados(List<Pelicula> datosPeliculas, List<Series> datosSeries) {
		// Limpiar el contenido solo si hay datos disponibles para mostrar
		if ((datosPeliculas != null && !datosPeliculas.isEmpty()) || (datosSeries != null && !datosSeries.isEmpty())) {
			seEncontro.getChildren().clear();

			// Procesar películas
			if (datosPeliculas != null && !datosPeliculas.isEmpty()) {
				for (Pelicula pelicula : datosPeliculas) {
					ImageView imageView = getImageViewFromPelicula(pelicula);
					seEncontro.getChildren().add(imageView);
					asociarEventoClicPelicula(imageView, pelicula); // Asociar evento de clic
				}
			}

			// Procesar series
			if (datosSeries != null && !datosSeries.isEmpty()) {
				for (Series serie : datosSeries) {
					ImageView imageView = getImageViewFromSerie(serie);
					seEncontro.getChildren().add(imageView);
					asociarEventoClicSerie(imageView, serie); // Asociar evento de clic
				}
			}
		}
		seEncontro.setSpacing(50.0);
	}

	private void mostrarDetallesPelicula(Pelicula pelicula) {
		// Obtener el ID de la película
		int id = pelicula.getId();
		// Llamar al método para abrir la ventana de detalles con el tipo "movie"
		abrirVentanaDetalles(id, "movie");
	}

	private void mostrarDetallesSerie(Series serie) {
		// Obtener el ID de la serie
		int id = serie.getId();
		// Llamar al método para abrir la ventana de detalles con el tipo "tv"
		abrirVentanaDetalles(id, "tv");
	}

	private void asociarEventoClicPelicula(ImageView imageView, Pelicula pelicula) {
		imageView.setOnMouseClicked(event -> mostrarDetallesPelicula(pelicula));
	}

	private void asociarEventoClicSerie(ImageView imageView, Series serie) {
		imageView.setOnMouseClicked(event -> mostrarDetallesSerie(serie));
	}

	private ImageView getImageViewFromSerie(Series serie) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setFitWidth(290.0);
		imageView.setPreserveRatio(true);

		// Construir la URL del póster de la película o serie
		String imageUrl = "https://image.tmdb.org/t/p/w500" + serie.getPoster_path();

		// Configurar la imagen en el ImageView
		Image image = new Image(imageUrl);
		imageView.setImage(image);

		// Asociar el evento de clic para mostrar los detalles
		asociarEventoClicSerie(imageView, serie);

		return imageView;
	}

	private ImageView getImageViewFromPelicula(Pelicula pelicula) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setFitWidth(290.0);
		imageView.setPreserveRatio(true);

		// Construir la URL del póster de la película o serie
		String imageUrl = "https://image.tmdb.org/t/p/w500" + pelicula.getPoster_path();

		// Configurar la imagen en el ImageView
		Image image = new Image(imageUrl);
		imageView.setImage(image);

		// Asociar el evento de clic para mostrar los detalles
		asociarEventoClicPelicula(imageView, pelicula);

		return imageView;
	}

	public void abrirVentanaDetalles(int id, String tipo) {
		setSceneAndStage();

		// Mostrar la ventana de detalles con el ID y el tipo proporcionados
		gestorVentanas.muestraDetalles(stage, String.valueOf(id), tipo);
	}

}
