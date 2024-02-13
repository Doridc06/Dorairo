package controller;

import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.Usuario;
import utilities.GestorVentanas;
import utilities.Utils;

/**
 * Clase con las funciones del login
 * 
 * @author JairoAB
 *
 */
public class LoginController {

	@FXML
	private Button btnLogin;

	@FXML
	private ImageView imgLogo;

	@FXML
	private ImageView imgPortada;

	@FXML
	private Label lblPulsaAqui;

	@FXML
	private PasswordField pwContrasena;

	@FXML
	private StackPane stackPaneLogo;

	@FXML
	private TextField txtUsuario;

	@FXML
	private StackPane stackPanePortada;

	/** Scene de la ventana de Login */
	private Scene scene;

	/** Stage de la ventana de Login */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	/** Instancia del dao de usuario */
	private UsuarioDaoImpl usuarioDaoImpl;

	/**
	 * Método al inicio de la ejecución
	 */
	@FXML
	void initialize() {
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();

		// Establece la imagen del logo
		Image imagen = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imgLogo.setImage(imagen);

		// Establece la imagen de la portada
		imagen = new Image(getClass().getResourceAsStream(Constants.URL_PORTADA));
		imgPortada.setImage(imagen);

		// Abre la session y crea el dao de usuario
		usuarioDaoImpl = new UsuarioDaoImpl(HibernateUtil.openSession());
	}

	/**
	 * Gestiona el inicio de sesion del usuario
	 * 
	 * @param event
	 */
	@FXML
	void handleLoginButtonAction(ActionEvent event) {
		setSceneAndStage();
		String usuario = txtUsuario.getText();
		String contrasena = pwContrasena.getText();

		// Comprueba que exista un perfil con ese usuario y contraseñas
		if (comprobarPerfil(usuario, contrasena)) {
			Utils.mostrarAlerta("Inicio de sesión exitoso. ¡Bienvenido/a, " + usuario + "!", Constants.INFORMATION_TYPE);
			// Muestra la ventana de inicio
			gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
		} else {
			Utils.mostrarAlerta("Error de inicio de sesión. Usuario o contraseña incorrectos", Constants.ERROR_TYPE);
		}
		vaciarCampos();
	}

	/**
	 * Elimina todo el contenido que se hubiera introducido en los campos de la
	 * pantalla
	 */
	public void vaciarCampos() {
		txtUsuario.setText("");
		pwContrasena.setText("");
	}

	/**
	 * Muestra la pantalla de registro para crear una nueva cuenta
	 * 
	 * @param event
	 */
	@FXML
	void pinchaAquiRegistroPressed(MouseEvent event) {
		vaciarCampos();
		setSceneAndStage();
		gestorVentanas.muestraRegistro(scene);
	}

	/**
	 * Verifica que exista un perfil con el usuario y contrasena proporcionados
	 * 
	 * @param user       a comprobar
	 * @param contrasena a comprobar
	 * @return true si existe o false si no existe
	 */
	public boolean comprobarPerfil(String user, String contrasena) {
		// Busca y recoge el perfil con dicho usuario y contraseña
		Usuario usuario = usuarioDaoImpl.searchByUsuarioAndPassword(user, contrasena);
		// Si es distinto de null (existe), se establece como perfil registrado
		if (usuario != null) {
			UsuarioController.setUsuarioRegistrado(usuario);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = btnLogin.getScene();
		stage = (Stage) scene.getWindow();
	}

}
