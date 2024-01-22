package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.Pelicula;
import models.RespuestaApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PeliculasController {

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
	private ImageView imgLogo;

	@FXML
	private ImageView posterImageView;

	@FXML
	private TextArea resultTextArea;
	
	@FXML
	private HBox proximamenteHBox;

	@FXML
	private HBox tendenciasHBox;

	@FXML
	private HBox peliculasVistasHBox;

	@FXML
	private HBox miListaHBox;

	// Clave de la API utilizada para realizar consultas
	private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

	@FXML
	void initialize() {
		try {
			// Configuración del cliente HTTP (OkHttpClient)
			OkHttpClient client = new OkHttpClient();

			// Construir la solicitud para la API de películas populares
			Request request = new Request.Builder()
					.url("https://api.themoviedb.org/3/movie/popular?language=es-ES&page=1").get()
					.addHeader("accept", "application/json").addHeader("Authorization", "Bearer " + API_KEY).build();

			// Ejecutar la solicitud y obtener la respuesta
			Response response = client.newCall(request).execute();

			// Obtener el cuerpo de la respuesta como cadena
			String responseBody = response.body().string();

			// Utilizar Gson para convertir la respuesta JSON a objetos Java
			Gson gson = new Gson();
			RespuestaApi respApi = gson.fromJson(responseBody, RespuestaApi.class);

			// Verificar si hay resultados en la respuesta
	        if (respApi.getResults() != null && respApi.getResults().length > 0) {
	            // Iterar sobre las películas y agregar imágenes a los HBox correspondientes
	            int contador = 0;
	            for (Pelicula pelicula : respApi.getResults()) {
	                if (contador < 10) { // Limitar a 10 películas por categoría
	                    ImageView imageView = new ImageView();
	                    imageView.setFitHeight(230.0);
	                    imageView.setFitWidth(180.0);
	                    imageView.setPreserveRatio(true);
	                    
	                    // Construir la URL del póster de la película
	                    String posterUrl = "https://image.tmdb.org/t/p/w500" + pelicula.getPoster_path();
	                    Image posterImage = new Image(posterUrl);
	                    imageView.setImage(posterImage);

	                    // Agregar la imagen al HBox correspondiente
	                    addToHBoxByCategory(imageView, contador);
	                    contador++;
	                } else {
	                    break; // Se han agregado 10 películas, salir del bucle
	                }
	            }
	        } else {
	            // No hay resultados, manejar de acuerdo a tus necesidades
	        }
	    } catch (IOException e) {
	        // Manejar excepciones
	        e.printStackTrace();
	    }
	}

	// Método para agregar la imagen al HBox según la categoría
	private void addToHBoxByCategory(ImageView imageView, int index) {
	    HBox targetHBox;
	    if (index < 10) {
	        targetHBox = proximamenteHBox;
	    } else if (index < 20) {
	        targetHBox = tendenciasHBox;
	    } else if (index < 30) {
	        targetHBox = peliculasVistasHBox;
	    } else {
	        targetHBox = miListaHBox;
	    }

	    // Agregar la imagen al HBox
	    targetHBox.getChildren().add(imageView);
	}
	
}
