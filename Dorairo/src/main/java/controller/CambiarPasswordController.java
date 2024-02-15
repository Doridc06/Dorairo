package controller;

import constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import utilities.Utils;

/**
 * Clase que gestiona el cambio de contraseña
 * 
 * @author JairoAB
 *
 */
public class CambiarPasswordController {

	/** Campo para la contraseña */
	@FXML
	private PasswordField pssNuevaPassword;

	/** Campo para la repeticion de la contraseña */
	@FXML
	private PasswordField pssRepetirPassword;

	/** Stage de la ventana de Inicio */
	private Stage stage;

	/**
	 * Guarda la contraseña introducida para el usuario registrado
	 * 
	 * @param event
	 */
	@FXML
	void guardarPasswordPressed(ActionEvent event) {
		// Comprueba que ambas sean iguales
		if (Utils.compruebaContrasenas(pssNuevaPassword.getText(), pssRepetirPassword.getText())) {
			if (Utils.confirmacion()) {
				// Establece la contraseña
				UsuarioController.cambiarClave(pssNuevaPassword.getText());
				Utils.mostrarAlerta("Contraseña cambiada con éxito", Constants.INFORMATION_TYPE);
			}
			setSceneAndStage();
			// Cierra la ventana
			stage.close();
		}
	}

	/**
	 * Establece el valor del stage
	 * 
	 */
	public void setSceneAndStage() {
		stage = (Stage) pssNuevaPassword.getScene().getWindow();
	}

}
