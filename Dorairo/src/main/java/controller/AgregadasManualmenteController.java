package controller;

import constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Pelicula;
import utilities.GestorVentanas;
import utilities.Utils;

/**
 * Clase con las funciones del Agregado Manual
 * 
 * @author JairoAB
 *
 */
public class AgregadasManualmenteController {

	@FXML
	private ChoiceBox<String> choiceTipo;

	@FXML
	private ImageView imagenLogoCabecera;

	@FXML
	private ImageView imagenCartel;

	@FXML
	private TextField txtActores;

	@FXML
	private TextArea txtComentarios;

	@FXML
	private TextField txtCompañia;

	@FXML
	private TextArea txtDescripcion;

	@FXML
	private TextField txtDirectores;

	@FXML
	private TextField txtEpisodios;

	@FXML
	private TextField txtEstreno;

	@FXML
	private TextField txtGenero;

	@FXML
	private TextField txtGuardado;

	@FXML
	private TextField txtValoracionPersonal;

	@FXML
	private TextField txtTemporadas;

	@FXML
	private TextField txtTitulo;

	@FXML
	private TextField txtValoracionGlobal;

	/** Scene de la ventana de Agregado Manual */
	private Scene scene;

	/** Stage de la ventana de Agregado Manual */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	/** Indica el tipo del que se trata */
	private String tipo = "";

	/** Indica si se trata de una serie (true) o una pelicula (false) */
	private boolean isSerie = false;

	@FXML
	void initialize() {
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagen = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagen);
		// Establece la imagen de subir foto
		imagen = new Image(getClass().getResourceAsStream(Constants.URL_FOTO_SUBIR_FOTO));
		imagenCartel.setImage(imagen);
		// Establece las opciones de tipo serie o pelicula
		choiceTipo.getItems().addAll("Pelicula", "Serie");
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
	 * Comprueba si se ha seleccionado el tipo de Serie para activar las casillas de
	 * temporadas y episodios
	 */
	@FXML
	void compruebaTipo() {
		choiceTipo.setOnAction(event -> {
			tipo = choiceTipo.getValue();
			if (tipo != null) {
				isSerie = tipo.equalsIgnoreCase("Serie");
				if (!isSerie) {
					// Si no es una serie quita el texto que tuviera
					txtEpisodios.setText("");
					txtTemporadas.setText("");
				}
				txtEpisodios.setDisable(!isSerie);
				txtTemporadas.setDisable(!isSerie);
			}
		});
	}

	/**
	 * Gestiona la creacion de la pelicula o serie
	 * 
	 * @param event
	 */
	@FXML
	void guardarPressed(ActionEvent event) {
		// Comprueba que haya seleccionado el tipo
		if (tipo.isBlank()) {
			// Muestra alerta de error
			Utils.mostrarAlerta("No se ha seleccionado ningún tipo.", Constants.ERROR_TYPE);
		} else {
			// Comprueba si es una pelicula o serie y la crea
			if (isSerie) {
				crearSerie();
			} else {
				crearPelicula();
			}
		}
	}

	/**
	 * Realiza la creación de una película
	 */
	private void crearPelicula() {
		// Comprueba si todos los campos se han rellenado
		if (isCompleto()) {

			Pelicula pelicula = new Pelicula(txtTitulo.getText(), txtCompañia.getText(), txtGuardado.getText(),
					txtEstreno.getText(), txtValoracionPersonal.getText(), txtValoracionGlobal.getText(), txtGenero.getText(),
					txtActores.getText(), txtDirectores.getText(), txtDescripcion.getText(), txtComentarios.getText());

		} else {
			// Muestra alerta de error
			Utils.mostrarAlerta("Falta algún campo por rellenar.", Constants.ERROR_TYPE);
		}

	}

	private void crearSerie() {
		// Comprueba si todos los campos se han rellenado
		if (isCompleto() && !txtEpisodios.getText().isBlank() && !txtTemporadas.getText().isBlank()) {

		} else {
			// Muestra alerta de error
			Utils.mostrarAlerta("Falta algún campo por rellenar.", Constants.ERROR_TYPE);
		}

	}

	/**
	 * Indica si se han rellanado todos los campos (excepto número de episodios y
	 * temporadas
	 * 
	 * @return True: todos rellenos. False: alguno sin rellenar
	 */
	private boolean isCompleto() {
		return !txtTitulo.getText().isBlank() && !txtCompañia.getText().isBlank() && !txtGuardado.getText().isBlank()
				&& !txtEstreno.getText().isBlank() && !txtValoracionPersonal.getText().isBlank()
				&& !txtValoracionGlobal.getText().isBlank() && !txtGenero.getText().isBlank() && !txtActores.getText().isBlank()
				&& !txtDirectores.getText().isBlank() && !txtDescripcion.getText().isBlank()
				&& !txtComentarios.getText().isBlank();
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
