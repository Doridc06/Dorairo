package controller;

import constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Utils;

/**
 * Clase que gestiona el cambio de nombre
 * 
 * @author JairoAB
 *
 */
public class CambiarNombreController {

	/** Campo para el nombre */
	@FXML
	private TextField txtNombre;

	/** Stage de la ventana de Inicio */
	private Stage stage;

	/**
	 * Guarda el nombre introducido para el usuario registrado
	 * 
	 * @param event
	 */
	@FXML
	void guardarNombrePressed(ActionEvent event) {
		// Comprueba que no esté vacío
		if (!txtNombre.getText().isBlank()) {
			// Solicita confirmacion
			if (Utils.confirmacion()) {
				// Establece el nombre
				UsuarioController.cambiarNombre(txtNombre.getText());
				Utils.mostrarAlerta("Nombre cambiado con éxito", Constants.INFORMATION_TYPE);
			}
			setStage();
			// Cierra la ventana
			stage.close();
		} else {
			Utils.mostrarAlerta("El nombre no puede estar vacío", Constants.ERROR_TYPE);
		}
	}

	/**
	 * Establece el valor del stage
	 * 
	 */
	public void setStage() {
		stage = (Stage) txtNombre.getScene().getWindow();
	}

}
