package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import utilities.Utils;

public class CambiarPasswordController {

	@FXML
	private PasswordField pssNuevaContraseña;

	@FXML
	private PasswordField pssRepetirContraseña;

	@FXML
	void guardarPressed(ActionEvent event) {
		if (Utils.compruebaContrasenas(pssNuevaContraseña.getText(), pssRepetirContraseña.getText())) {

		}
	}

}
