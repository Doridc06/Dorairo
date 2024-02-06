package controller;

import java.io.IOException;
import com.google.gson.Gson;
import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Genero;
import models.Pelicula;
import models.Serie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;

public class DetallesController {

    @FXML
    private ImageView imagenLogoCabecera;

    @FXML
    private Text titulo;

    @FXML
    private ImageView cartel;

    @FXML
    private Text detalles;

    private Scene scene;

    private Stage stage;

    private GestorVentanas gestorVentanas;
    
    @FXML
    private ImageView fondoIm;

    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

    @FXML
    void initialize() {
        gestorVentanas = new GestorVentanas();
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

    public void setSceneAndStage() {
        scene = imagenLogoCabecera.getScene();
        stage = (Stage) scene.getWindow();
    }

    /**
     * Inicializa la información de la película en la ventana de detalles.
     * 
     * @param id El ID de la película seleccionada.
     */
    public void initData(String id, String tipo) {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://api.themoviedb.org/3/" + tipo + "/" + id; 
        String queryParams = "?language=es-ES";
        String fullUrl = apiUrl + queryParams;

        Request request = new Request.Builder()
                .url(fullUrl)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();

            Gson gson = new Gson();
            
            if (tipo.equals("movie")) {
              Pelicula datos = gson.fromJson(responseBody, Pelicula.class);
              titulo.setText(datos.getTitle());
              cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
              
           // Configurar la imagen de fondo con transparencia
              // Obtener las dimensiones de la pantalla
              double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
              double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
              // Mantener la relación de aspecto original de la imagen
              fondoIm.setPreserveRatio(true);
           // Ajustar la posición de la imagen para que esté en el lado derecho de la pantalla
              fondoIm.setLayoutX(screenWidth / 2); // Colocar en el centro horizontalmente
              fondoIm.setLayoutY(0); // Colocar en la parte superior
              
              // Establecer las dimensiones del ImageView para que abarque toda la pantalla
              fondoIm.setFitWidth(screenWidth);
              fondoIm.setFitHeight(screenHeight);
              fondoIm.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
              fondoIm.setOpacity(0.5);
              fondoIm.toBack();


              // Construir la cadena de géneros
              StringBuilder generosString = new StringBuilder();
              for (Genero genero : datos.getGenres()) {
                  generosString.append(genero.getName()).append(", ");
              }
              // Eliminar la coma y el espacio extra al final
              if (generosString.length() > 0) {
                  generosString.setLength(generosString.length() - 2);
              }

              detalles.setText("Descripción: " + datos.getOverview() + "\n" 
                  + "Fecha de estreno: " + datos.getRelease_date() + "\n"
                  + "Géneros: " + generosString.toString() + "\n" 
                  + "Valoracion: " + datos.getVote_average() + "\n" 
                  + "Duración: " + datos.getRuntime() + " Minutos" + "\n" 
                  + "Popularidad: " + datos.getPopularity() + "\n");
              
              
          } else if (tipo.equals("tv")) {
              Serie datos = gson.fromJson(responseBody, Serie.class);
              titulo.setText(datos.getName());
              cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
           // Configurar la imagen de fondo con transparencia
              // Obtener las dimensiones de la pantalla
              double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
              double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
              
              // Mantener la relación de aspecto original de la imagen
              fondoIm.setPreserveRatio(true);
           // Ajustar la posición de la imagen para que esté en el lado derecho de la pantalla
              fondoIm.setLayoutX(screenWidth / 2); // Colocar en el centro horizontalmente
              fondoIm.setLayoutY(0); // Colocar en la parte superior
              
              // Establecer las dimensiones del ImageView para que abarque toda la pantalla
              fondoIm.setFitWidth(screenWidth);
              fondoIm.setFitHeight(screenHeight);
              fondoIm.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
              fondoIm.setOpacity(0.15);
              fondoIm.toBack();
              


              // Construir la cadena de géneros
              StringBuilder generosString = new StringBuilder();
              for (Genero genero : datos.getGenres()) {
                  generosString.append(genero.getName()).append(", ");
              }
              // Eliminar la coma y el espacio extra al final
              if (generosString.length() > 0) {
                  generosString.setLength(generosString.length() - 2);
              }

              detalles.setText("Descripción: " + datos.getOverview() + "\n" 
                  + "Fecha de estreno: " + datos.getFirst_air_date() + "\n"
                  + "Géneros: " + generosString.toString() + "\n" 
                  + "Valoracion: " + datos.getVote_average() + "\n" 
                  + "Episodios: " + datos.getNumber_of_episodes() + "\n"
                  + "Temporadas: " + datos.getNumber_of_seasons() + "\n"
                  + "Popularidad: " + datos.getPopularity() + "\n");
             
          }

           
           
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}