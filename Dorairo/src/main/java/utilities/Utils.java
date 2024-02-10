package utilities;

import java.util.Optional;

import constants.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
	 * @return True si coinciden; false si son distintas
	 */
	public static boolean compruebaContrasenas(String contrasena, String repeticionContrasena) {
		if (contrasena.compareTo(repeticionContrasena) == 0) {
			if (contrasena.length() > 0 && contrasena.length() <= 20) {
				return true;
			} else {
				Utils.mostrarAlerta("La contraseña no puede ser mayor de 20 caracteres (ni estar vacía).",
						Constants.WARNING_TYPE);
				return false;
			}
		} else {
			Utils.mostrarAlerta("Las constraseñas no coinciden.", Constants.WARNING_TYPE);
			return false;
		}
	}

	/**
	 * Muestra una alerta de confirmacion
	 * 
	 * @return True si se pulsa aceptar; False si se pulsa cancelar
	 */
	public static boolean confirmacion() {
		// Crea una alerta de tipo confirmacion
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Confirmación");
		alert.initOwner(ownerStage);
		alert.setContentText("¿Estás seguro de querer realizar la acción?");
		// Muestra la alerta y espera hasta que se cierre
		Optional<ButtonType> result = alert.showAndWait();

		// Comprueba si se ha pulsado el boton de ok y lo envia
		return result.isPresent() && result.get() == ButtonType.OK;
	}

}
