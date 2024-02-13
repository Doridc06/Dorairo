package controller;

import constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import utilities.Utils;

public class CambiarPasswordController {

	@FXML
	private PasswordField pssNuevaPassword;

	@FXML
	private PasswordField pssRepetirPassword;

	/** Scene de la ventana de Inicio */
	private Scene scene;

	/** Stage de la ventana de Inicio */
	private Stage stage;

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
			stage.close();
		}
	}

	/**
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = pssNuevaPassword.getScene();
		stage = (Stage) scene.getWindow();
	}

}
