package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import constants.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Genero;
import models.Pelicula;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class BuscarGeneroController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView imagenLogoCabecera;

	@FXML
	private HBox peliculasPorGenero;

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
	private Text seEncontro;

	@FXML
	public HBox hBoxPeliculas;

	@FXML
	private ImageView cartel;

	// Lista para almacenar todas las películas obtenidas de la API
	static List<Pelicula> todasLasPeliculas = new ArrayList<>();

	// Lista para almacenar las películas filtradas por género
	private List<Pelicula> peliculasFiltradas = new ArrayList<>();

	private List<Genero> listaGeneros = new ArrayList<>();

	/**
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = imagenLogoCabecera.getScene();
		stage = (Stage) scene.getWindow();
	}

	@FXML
	void inicioClicked(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
		System.out.println("Mouse clicked en la cabecera de inicio");
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

	@FXML
	void initialize() {
		// Inicializar gestorVentanas
		gestorVentanas = new GestorVentanas();
		// No necesitas inicializar hBoxPeliculas aquí

		// Establecer la imagen del logo
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagenLogo);

	}

	public HBox getHBoxPeliculas() {
		return hBoxPeliculas;
	}

}
