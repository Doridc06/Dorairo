package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.Pelicula;
import models.RespuestaApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PeliculaController  {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView logo;

    @FXML
    private HBox peliculasPopulares;

    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

    @FXML
    private HBox peliculasEstrenos;

    @FXML
    void initialize() {
        try {
            // Configuración del cliente HTTP (OkHttpClient)
            OkHttpClient client = new OkHttpClient();

            // Llamada a la API para películas populares
            handleMovieApiCall(client, "https://api.themoviedb.org/3/movie/popular?language=es-ES&page=1", peliculasPopulares);

            // Llamada a la API para próximas películas
            handleMovieApiCall(client, "https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1", peliculasEstrenos);

        } catch (IOException e) {
            // Manejar excepciones
            e.printStackTrace();
        }
    }

    private void handleMovieApiCall(OkHttpClient client, String apiUrl, HBox targetHBox) throws IOException {
        // Construir la solicitud para la API de películas
        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

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
                System.out.println("Adding image: " + pelicula.getPoster_path());
                if (contador < 10) { // Limitar a 10 películas
                    ImageView imageView = null;
                    if (pelicula.getPoster_path() != null) {
                        imageView = getImageViewFromUrl(
                                "https://image.tmdb.org/t/p/w500" + pelicula.getPoster_path());
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
       
    private ImageView getImageViewFromUrl(String imageUrl) {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(250.0);
        imageView.setFitWidth(290.0);
        imageView.setPreserveRatio(true);

        // Construir la URL del póster de la película
        Image image = new Image(imageUrl);
        imageView.setImage(image);

        return imageView;
    }


}
