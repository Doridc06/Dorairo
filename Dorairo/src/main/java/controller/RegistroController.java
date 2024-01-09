package controller;

import java.util.Date;

import application.Main;
import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Perfil;
import utilities.Utils;

/**
 * Clase con las funciones del registro
 * 
 */
public class RegistroController {

	@FXML
	private Button btnRegistrarse;

	@FXML
	private PasswordField pwContrasena;

	@FXML
	private PasswordField pwRepetirContrasena;

	@FXML
	private TextField txtCorreo;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtUsuario;

	/**
	 * Registra un nuevo perfil según los datos que se hayan introducido
	 * 
	 * @param event
	 */
	@FXML
	void buttonRegistrarsePressed(MouseEvent event) {
		String usuario = txtUsuario.getText();
		String correo = txtCorreo.getText();
		String nombre = txtNombre.getText();
		String contrasena = pwContrasena.getText();
		String repeticionContrasena = pwRepetirContrasena.getText();

		// Comprueba que los campos estén llenos, las contraseñas coincidan y no exista
		// un perfil con un usuario o correo igual
		if (camposLlenos(usuario, correo, nombre, contrasena, repeticionContrasena)
				&& compruebaContrasenas(contrasena, repeticionContrasena) && !Main.isPerfil(usuario, correo)) {
			// Crea el nuevo perfil
			Main.anadirNuevoPerfil(new Perfil(usuario, nombre, correo, repeticionContrasena, new Date()));

			// Cierra la ventana de registro
			Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
			stage.close();

			Utils.mostrarAlerta("¡El nuevo perfil se ha creado con éxito!", Constants.INFORMATION_TYPE);

		}
	}

	/**
	 * Comprueba que todos los campos estén llenos
	 * 
	 * @param usuario
	 * @param correo
	 * @param nombre
	 * @param contrasena
	 * @param repeticionContrasena
	 * @return true si están llenos; false si hay alguno vacío.
	 */
	private boolean camposLlenos(String usuario, String correo, String nombre, String contrasena,
			String repeticionContrasena) {
		if (usuario.isBlank() || correo.isBlank() || nombre.isBlank() || contrasena.isBlank()
				|| repeticionContrasena.isBlank()) {
			Utils.mostrarAlerta("Alguno de los campos se encuentra vacío.", Constants.WARNING_TYPE);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Comprueba que la contraseña y la repetición sean iguales
	 * 
	 * @param contrasena
	 * @param repeticionContrasena
	 * @return true si coinciden; false si son distintas
	 */
	private boolean compruebaContrasenas(String contrasena, String repeticionContrasena) {
		if (contrasena.compareTo(repeticionContrasena) == 0) {
			return true;
		} else {
			Utils.mostrarAlerta("Las constraseñas no coinciden.", Constants.WARNING_TYPE);
			return false;
		}
	}

}
