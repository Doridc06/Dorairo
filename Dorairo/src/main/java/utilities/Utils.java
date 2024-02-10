package utilities;

import constants.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	/**
	 * Comprueba que la contraseña y la repetición sean iguales
	 * 
	 * @param contrasena
	 * @param repeticionContrasena
	 * @return true si coinciden; false si son distintas
	 */
	public static boolean compruebaContrasenas(String contrasena, String repeticionContrasena) {
		if (contrasena.compareTo(repeticionContrasena) == 0) {
			if (contrasena.length() <= 20) {
				return true;
			} else {
				Utils.mostrarAlerta("La contraseña no puede ser mayor de 20 caracteres.", Constants.WARNING_TYPE);
				return false;
			}
		} else {
			Utils.mostrarAlerta("Las constraseñas no coinciden.", Constants.WARNING_TYPE);
			return false;
		}
	}

}
