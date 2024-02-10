package controller;

import constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Utils;

public class CambiarNombreController {

	@FXML
	private TextField txtNombre;

	/** Scene de la ventana de Inicio */
	private Scene scene;

	/** Stage de la ventana de Inicio */
	private Stage stage;

	@FXML
	void guardarNombrePressed(ActionEvent event) {
		// Comprueba que ambas sean iguales
		if (!txtNombre.getText().isBlank()) {
			if (Utils.confirmacion()) {
				// Establece el nombre
				UsuarioController.cambiarNombre(txtNombre.getText());
				Utils.mostrarAlerta("Nombre cambiado con éxito", Constants.INFORMATION_TYPE);
			}
			setSceneAndStage();
			stage.close();
		} else {
			Utils.mostrarAlerta("El nombre no puede estar vacío", Constants.ERROR_TYPE);

		}
	}

	/**
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = txtNombre.getScene();
		stage = (Stage) scene.getWindow();
	}

}
