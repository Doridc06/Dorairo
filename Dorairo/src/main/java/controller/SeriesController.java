package controller;

import java.net.URL;
import java.util.ResourceBundle;

import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utilities.GestorVentanas;

public class SeriesController {

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
    private ImageView imagenLogoCabecera;

    @FXML
    private Pane paneCabecera;

    @FXML
    private AnchorPane paneFondo;

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

}
