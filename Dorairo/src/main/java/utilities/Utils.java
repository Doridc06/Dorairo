package utilities;

import constants.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Clase con métodos comunes
 * 
 * @author JairoAB
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

	/**
	 * Habilita la busqueda de la foto por los ficheros
	 * 
	 * @param stage
	 */
	public static String buscarFotoArchivos(Stage stage) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Buscar Imagen");

			// Agrega filtros para facilitar la busqueda
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
					new FileChooser.ExtensionFilter("PNG", "*.png"));

			// Obtiene la imagen seleccionada
			return fileChooser.showOpenDialog(stage).getAbsolutePath();

		} catch (Exception e) {
			mostrarAlerta("Error al seleccionar una foto", Constants.ERROR_TYPE);
			return null;
		}
	}

}
