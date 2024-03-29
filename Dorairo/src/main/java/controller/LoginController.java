package controller;

import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

	/** Botón de login */
	@FXML
	private Button btnLogin;

	/** ImageView para el logo */
	@FXML
	private ImageView imgLogo;

	/** ImageView para la portada */
	@FXML
	private ImageView imgPortada;

	/** Campo de la contraseña */
	@FXML
	private PasswordField pwPassword;

	/** Campo del usuario */
	@FXML
	private TextField txtUser;

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
		// Recoge los datos introducidos
		String usuario = txtUser.getText();
		String contrasena = pwPassword.getText();

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
	 * Elimina todos el contenido que se hubiera introducido en los campos de la
	 * pantalla
	 */
	public void vaciarCampos() {
		txtUser.setText("");
		pwPassword.setText("");
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
	 * Verifica que exista un perfil con el usuario y contraseña proporcionados
	 * 
	 * @param user       Usuario a comprobar
	 * @param contrasena Contraseña a comprobar
	 * @return True si existe o false si no existe
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
	 * Establece el valor de stage y scene
	 */
	public void setSceneAndStage() {
		scene = btnLogin.getScene();
		stage = (Stage) scene.getWindow();
	}
}
