package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import constants.Constants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.Datos;
import models.RespuestaApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;

public class BuscadorController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

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

	/** Scene de la ventana de Inicio */
	private Scene scene;

	/** Stage de la ventana de Inicio */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	@FXML
	private TextField estasBuscando;

	@FXML
	private HBox seEncontro;

	@FXML
	private Button buscar;

	@FXML
	void initialize() {
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagenLogo);
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
		gestorVentanas.muestraVentana(stage, Constants.URL_PERFIL_FXML, "Perfil");
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
	void clickBuscar(MouseEvent event) {
		String searchTerm = estasBuscando.getText();
		if (!searchTerm.isEmpty()) {
			buscarPeliculaPorTitulo(searchTerm);
		}
	}

	private void buscarPeliculaPorTitulo(String titulo) {
		OkHttpClient client = new OkHttpClient();
		String apiUrl = "https://api.themoviedb.org/3/search/movie";
		String queryParams = "?language=es-ES&query=" + titulo + "&page=1";
		String fullUrl = apiUrl + queryParams;

		Request request = new Request.Builder().url(fullUrl).get().addHeader("accept", "application/json")
				.addHeader("Authorization", "Bearer " + PeliculaController.API_KEY).build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				// Manejar respuestas no exitosas
				System.out.println("Error: " + response.code());
				seEncontro.getChildren().clear();
				return;
			}

			String responseBody = response.body().string();
			Gson gson = new Gson();
			RespuestaApi respApi = gson.fromJson(responseBody, RespuestaApi.class);

			if (respApi.getResults() != null && respApi.getResults().length > 0) {
				mostrarResultados(respApi.getResults());
			} else {
				// Manejar la situación donde no hay resultados
				seEncontro.getChildren().clear();
				System.out.println("No se encontraron resultados.");
			}
		} catch (IOException e) {
			// Manejar excepciones relacionadas con la conexión a la API
			e.printStackTrace();
			seEncontro.getChildren().clear();
			System.out.println("Error al conectar con la API: " + e.getMessage());
		}
	}

	private void mostrarResultados(Datos[] datos) {
		seEncontro.getChildren().clear();

		for (Datos dato : datos) {
			ImageView imageView = getImageView(dato);
			seEncontro.getChildren().add(imageView);

			// Configurar el evento de clic para llamar a un método que maneje la lógica
			// deseada
			imageView.setOnMouseClicked(event -> handleImageClick(dato));
			// Establecer el espaciado entre las imágenes en el HBox
			seEncontro.setSpacing(50.0);
		}
	}

	private ImageView getImageView(Datos datos) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(250.0);
		imageView.setFitWidth(290.0);
		imageView.setPreserveRatio(true);

		// Construir la URL del póster de la película
		String imageUrl = "https://image.tmdb.org/t/p/w500" + datos.getPoster_path();

		// Configurar la imagen en el ImageView
		Image image = new Image(imageUrl);
		imageView.setImage(image);

		return imageView;
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
	
	
	@FXML
	void detallesClicked(ImageView clickedImageView) {
		// Obtener el identificador de la serie desde el ImageView
		String serieId = getSerieIdFromImageView(clickedImageView);

		// Abrir la ventana de detalles
		abrirVentanaDetalles(serieId);
	}

	
	private void abrirVentanaDetalles(String serieId) {
		setSceneAndStage();
		gestorVentanas.muestraDetalles(stage,serieId,"tv");
	}
	
	
	
	private void handleImageClick(Datos datos) {
		// lógica que deseas realizar cuando se hace clic en una imagen
		
		ImageView imageView = new ImageView();
		
		// Almacenar el ID de la película en el userData del ImageView
		imageView.setUserData(String.valueOf(datos.getId()));

		// Configurar el evento de clic para llamar a detallesClicked
		imageView.setOnMouseClicked(event -> detallesClicked(imageView));
	}
}