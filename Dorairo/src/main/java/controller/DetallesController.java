package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Pelicula;
import models.RespuestaApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;

public class DetallesController {

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

    private Scene scene;

    private Stage stage;

    private GestorVentanas gestorVentanas;

    @FXML
    private Text titulo;

    @FXML
    private ImageView cartel;

    @FXML
    private Text detallesPelicula;

    @FXML
    private Text compania;

    @FXML
    private Text resumen;

    @FXML
    private Text fecha;

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

    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

    /**
     * Inicializa la información de la película en la ventana de detalles.
     * 
     * @param peli La película seleccionada.
     */
    public void initData(Pelicula peli) {
        String imageUrl = "https://image.tmdb.org/t/p/w500" + peli.getPoster_path();

        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://api.themoviedb.org/3/search/movie";
        String queryParams = "?query=Wonka&include_adult=false&language=es-ES&page=1";
        String fullUrl = apiUrl + queryParams;

        Request request = new Request.Builder()
                .url(fullUrl)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("Response Body: " + responseBody);

            Gson gson = new Gson();
            RespuestaApi respuestaApi = gson.fromJson(responseBody, RespuestaApi.class);

            if (respuestaApi != null && respuestaApi.getResults() != null && respuestaApi.getResults().length > 0) {
                Pelicula peliculaDetalles = respuestaApi.getResults()[0];

                // Mostrar información en la interfaz gráfica
                titulo.setText("Título: " + peliculaDetalles.getTitle());
                cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + peliculaDetalles.getPoster_path()));
                detallesPelicula.setText("Detalles de la película: " + peliculaDetalles.getOverview());
            } else {
                System.out.println("La respuesta de la API no contiene resultados válidos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
