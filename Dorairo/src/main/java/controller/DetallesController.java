package controller;

import java.io.IOException;
import com.google.gson.Gson;
import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Datos;
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
    private Text detallesPelicula;

    private Scene scene;

    private Stage stage;

    private GestorVentanas gestorVentanas;

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
        //    System.out.println("Response Body: " + responseBody);

            Gson gson = new Gson();
            Datos peliculaDetalles = gson.fromJson(responseBody, Datos.class);

            // Mostrar información en la interfaz gráfica
            titulo.setText(peliculaDetalles.getTitle());
            cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + peliculaDetalles.getPoster_path()));
            detallesPelicula.setText("Descripción: " + peliculaDetalles.getOverview());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}