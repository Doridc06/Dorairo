package controller;

import java.io.IOException;

import application.Main;
import constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilities.Utils;

/**
 * Clase con las funciones del login
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

	private Scene scene;

	private Stage stage;

	/**
	 * Método al inicio de la ejecución
	 */
	@FXML
	void initialize() {
		// Establece la imagen del logo
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_NORMAL));
		imgLogo.setImage(imagenLogo);

		// Establece la imagen de la portada
		Image imagenPortada = new Image(getClass().getResourceAsStream(Constants.URL_PORTADA));
		imgPortada.setImage(imagenPortada);
	}

	/**
	 * Gestiona el inicio de sesion del usuario
	 * 
	 * @param event
	 */
	@FXML
	void handleLoginButtonAction(ActionEvent event) {
		String usuario = txtUsuario.getText();
		String contrasena = pwContrasena.getText();

		// Comprueba que exista un perfil con ese usuario y contraseñas
		if (Main.comprobarPerfil(usuario, contrasena)) {
			Utils.mostrarAlerta("Inicio de sesión exitoso. ¡Bienvenido/a, " + usuario + "!", Constants.INFORMATION_TYPE);
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
		try {
			vaciarCampos();
			// Ruta al registro
			FXMLLoader registroLoader = new FXMLLoader(getClass().getResource(Constants.URL_REGISTRO_FXML));
			Parent registroRoot = registroLoader.load();

			// Asigna la ventana del registro al nuevo stage
			Stage stageRegistro = new Stage();
			Scene registroScene = new Scene(registroRoot);
			stageRegistro.setScene(registroScene);

			// Cambia el icono de la ventana
			Image icon = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
			stageRegistro.getIcons().add(icon);

			// Cambia el titulo de la ventana
			stageRegistro.setTitle("Registro");

			// Define el tipo de modalidad
			stageRegistro.initModality(Modality.APPLICATION_MODAL);

			// Inhabilita la redimension de la ventana
			stageRegistro.setResizable(false);

			// Aplica el desenfoque a la escena
			setSceneAndStage();
			BoxBlur blur = new BoxBlur(10, 10, 3);
			scene.getRoot().setEffect(blur);

			// Muestra la pantalla de Registro
			stageRegistro.showAndWait();

			// Desactiva el efecto de desenfoque
			scene.getRoot().setEffect(null);

		} catch (IOException e) {
			e.printStackTrace();
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
