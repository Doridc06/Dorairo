package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Clase con métodos comunes
 * 
 */
public class Utils {

	/** Stage principal */
	private static Stage ownerStage;

	/**
	 * Muestra un dialog con el mensaje proporcionado segun el tipo de alerta
	 * 
	 * @param mensaje a mostrar en el dialog
	 * @param tipo    de la alerta
	 */
	public static void mostrarAlerta(String mensaje, String tipo) {
		Alert alert = new Alert(AlertType.valueOf(tipo));
		alert.setTitle(tipo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.initOwner(ownerStage);
		alert.showAndWait();
	}

	/**
	 * Establece el stage proporcionado como ownerStage
	 * 
	 * @param stage que se va a establecer
	 */
	public static void setOwnerStage(Stage stage) {
		ownerStage = stage;
	}

}
