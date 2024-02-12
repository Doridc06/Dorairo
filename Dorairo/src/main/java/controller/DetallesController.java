package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Genero;
import models.Pelicula;
import models.Series;
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

  @FXML
  private ImageView lupa;

  @FXML
  private MenuButton Agregar;

  @FXML
  private MenuButton Exportar;

  @FXML
  private MenuItem csv;

  @FXML
  private MenuItem json;

  @FXML
  private MenuItem miLista;

  @FXML
  private MenuItem yaVisto;

  private Pelicula pelicula;

  private Series series;
  
  @FXML
  private Button guardar;

  private static final String API_KEY =
      "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

  @FXML
  void initialize() {
    gestorVentanas = new GestorVentanas();
    Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
    imagenLogoCabecera.setImage(imagenLogo);

    Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
    lupa.setImage(imagenLupa);

    csv.setOnAction(event -> exportarPeliculaYSerie("csv"));
    json.setOnAction(event -> exportarPeliculaYSerie("json"));
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

  public void setSceneAndStage() {
    scene = imagenLogoCabecera.getScene();
    stage = (Stage) scene.getWindow();
  }

  public void initData(String id, String tipo) {
    OkHttpClient client = new OkHttpClient();
    String apiUrl = "https://api.themoviedb.org/3/" + tipo + "/" + id;
    String queryParams = "?language=es-ES";
    String fullUrl = apiUrl + queryParams;
    Request request =
        new Request.Builder().url(fullUrl).get().addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + API_KEY).build();

    try (Response response = client.newCall(request).execute()) {
      String responseBody = response.body().string();
      Gson gson = new Gson();

      if (tipo.equals("movie")) {
        Pelicula datos = gson.fromJson(responseBody, Pelicula.class);
        titulo.setText(datos.getTitle());
        cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        fondoIm.setPreserveRatio(true);
        fondoIm.setLayoutX(screenWidth / 2);
        fondoIm.setLayoutY(0);
        fondoIm.setFitWidth(screenWidth);
        fondoIm.setFitHeight(screenHeight);
        fondoIm.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        fondoIm.setOpacity(0.5);
        fondoIm.toBack();

        StringBuilder generosString = new StringBuilder();
        for (Genero genero : datos.getGenres()) {
          generosString.append(genero.getName()).append(", ");
        }
        if (generosString.length() > 0) {
          generosString.setLength(generosString.length() - 2);
        }
        detalles.setText("Descripción: " + datos.getOverview() + "\n" + "Fecha de estreno: "
            + datos.getRelease_date() + "\n" + "Géneros: " + generosString.toString() + "\n"
            + "Valoracion: " + datos.getVote_average() + "\n");
        setPelicula(datos);
      } else if (tipo.equals("tv")) {
        Series datos = gson.fromJson(responseBody, Series.class);
        titulo.setText(datos.getName());
        cartel.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        fondoIm.setPreserveRatio(true);
        fondoIm.setLayoutX(screenWidth / 2);
        fondoIm.setLayoutY(0);
        fondoIm.setFitWidth(screenWidth);
        fondoIm.setFitHeight(screenHeight);
        fondoIm.setImage(new Image("https://image.tmdb.org/t/p/w500" + datos.getPoster_path()));
        fondoIm.setOpacity(0.15);
        fondoIm.toBack();

        StringBuilder generosString = new StringBuilder();
        for (Genero genero : datos.getGenres()) {
          generosString.append(genero.getName()).append(", ");
        }
        if (generosString.length() > 0) {
          generosString.setLength(generosString.length() - 2);
        }
        detalles.setText("Descripción: " + datos.getOverview() + "\n" + "Fecha de estreno: "
            + datos.getFirst_air_date() + "\n" + "Géneros: " + generosString.toString() + "\n"
            + "Valoracion: " + datos.getVote_average() + "\n" + "Episodios: "
            + datos.getNumber_of_episodes() + "\n" + "Temporadas: " + datos.getNumber_of_seasons());
        setSeries(datos);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setPelicula(Pelicula pelicula) {
    this.pelicula = pelicula;
  }

  public void setSeries(Series series) {
    this.series = series;


  }

  private void exportarPeliculaYSerie(String formato) {
    if (pelicula == null && series == null) {
      return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Guardar película o serie");

    FileChooser.ExtensionFilter extFilter = null;
    if (formato.equals("csv")) {
      extFilter = new FileChooser.ExtensionFilter("Archivos CSV (*.csv)", "*.csv");
    } else if (formato.equals("json")) {
      extFilter = new FileChooser.ExtensionFilter("Archivos JSON (*.json)", "*.json");
    }
    if (extFilter != null) {
      fileChooser.getExtensionFilters().add(extFilter);
    }

    File archivo = fileChooser.showSaveDialog(stage);

    if (archivo != null) {
      try {
        FileWriter escritor = new FileWriter(archivo);
        if (pelicula != null && formato.equals("csv")) {
          escritor.write(pelicula.toStringCsv());
        } else if (pelicula != null && formato.equals("json")) {
          escritor.write(pelicula.toStringJson());
        } else if (series != null && formato.equals("csv")) {
          escritor.write(series.toStringCsv());
        } else if (series != null && formato.equals("json")) {
          escritor.write(series.toStringJson());
        }
        escritor.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
