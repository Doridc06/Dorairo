package controller;

import application.Main;
import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.GestorVentanas;
import utilities.Utils;

/**
 * Clase con las funciones del Usuario
 * 
 * @author JairoAB
 *
 */
public class UsuarioController {

	@FXML
	private ImageView imagenFondoPerfil;

	@FXML
	private ImageView imagenLogoCabecera;

	@FXML
	private ImageView imagenPerfil;

	@FXML
	private Label lblCorreo;

	@FXML
	private Label lblMiembro;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblNumeroPeliculas;

	@FXML
	private Label lblNumeroSeries;

	@FXML
	private Label lblUser;

	/** Scene de la ventana de Perfil */
	private Scene scene;

	/** Stage de la ventana de Perfil */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	@FXML
	void initialize() {
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagen = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagen);
		// Establece la imagen del perfil
		if (Main.getPerfilRegsistrado().getIgamenPerfil() == null
				|| Main.getPerfilRegsistrado().getIgamenPerfil().isBlank()) {
			// si no tiene una asignada se pone la por defecto
			imagen = new Image(getClass().getResourceAsStream(Constants.URL_FOTO_PERFIL_DEFAULT), 190, 190, false, true);
		} else {
			imagen = new Image("file:" + Main.getPerfilRegsistrado().getIgamenPerfil());
		}
		imagenPerfil.setImage(imagen);
		// Establece la imagen de fondo
		imagen = new Image(getClass().getResourceAsStream(Constants.URL_FOTO_FONDO_PERFIL));
		imagenFondoPerfil.setImage(imagen);

		// Cambiar los datos del perfil
		setDatosPerfil();
	}

	/**
	 * Modifica los elementos de la pantalla para que muestren los datos del perfil
	 * registrado
	 */
	private void setDatosPerfil() {
		lblNombre.setText(Main.getPerfilRegsistrado().getNombre().toUpperCase());
		lblUser.setText(Main.getPerfilRegsistrado().getUsuario());
		lblCorreo.setText(Main.getPerfilRegsistrado().getCorreo());
		lblMiembro.setText(Main.getPerfilRegsistrado().getFechaMiembroString());
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

	@FXML
	void modificarContraseña(MouseEvent event) {

	}

	@FXML
	void modificarNombre(MouseEvent event) {

	}

	/**
	 * Cierra la sesión del usuario llevándolo de vuelta al login.
	 * 
	 * @param event
	 */
	@FXML
	void cerrarSesion(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_LOGIN_FXML, "Dorairo");
	}

	@FXML
	void cambiarFotoPerfil(MouseEvent event) {
		setSceneAndStage();
		String fotoUrl = Utils.buscarFotoArchivos(stage);

		Main.getPerfilRegsistrado().setIgamenPerfil(fotoUrl);

		// Mostar la imagen
		Image image = new Image("file:" + fotoUrl);
		imagenPerfil.setImage(image);
	}

	@FXML
	void eliminarDatos(MouseEvent event) {

	}

	@FXML
	void eliminarCuenta(MouseEvent event) {

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
