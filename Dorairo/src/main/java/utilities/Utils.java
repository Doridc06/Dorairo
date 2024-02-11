package utilities;

import java.util.Optional;

import constants.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

	private static int sufijoIdPelisManuales = 0;

	private static int sufijoIdSeriesManuales = 0;

	private static int sufijoIdCompaniesManuales = 0;

	private static int sufijoIdActoresManuales = 0;

	private static int sufijoIdDirectoresManuales = 0;

	private static int sufijoIdGeneroManuales = 0;

	/**
	 * Private Utils Constructor
	 */
	private Utils() {
	}

	/**
	 * Muestra un dialog con el mensaje proporcionado segun el tipo de alerta
	 * 
	 * @param mensaje a mostrar en el dialog
	 * @param tipo    de la alerta
	 */
	public static void mostrarAlerta(String mensaje, String tipo) {
		Alert alert = new Alert(AlertType.valueOf(tipo));
		alert.getDialogPane().getStyleClass().add(".dialog-pane");
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

	/**
	 * Genera un nuevo id para las peliculas
	 * 
	 * @return Nuevo id
	 */
	public static Integer generaMovieId() {
		// Une ambas partes como cadena y lo pasa a un numero entero
		int id = Integer.parseInt(Constants.PREFIJO_ID_PELIS_MANUALES + sufijoIdPelisManuales);
		sufijoIdPelisManuales++;
		return id;
	}

	/**
	 * Genera un nuevo id para las series
	 * 
	 * @return Nuevo id
	 */
	public static Integer generaSerieId() {
		// Une ambas partes como cadena y lo pasa a un numero entero
		int id = Integer.parseInt(Constants.PREFIJO_ID_SERIES_MANUALES + sufijoIdSeriesManuales);
		sufijoIdSeriesManuales++;
		return id;
	}

	/**
	 * Genera un nuevo id para las compañias
	 * 
	 * @return Nuevo id
	 */
	public static Integer generaCompanyId() {
		// Une ambas partes como cadena y lo pasa a un numero entero
		int id = Integer.parseInt(Constants.PREFIJO_ID_SERIES_MANUALES + sufijoIdCompaniesManuales);
		sufijoIdCompaniesManuales++;
		return id;
	}

	/**
	 * Genera un nuevo id para los actores
	 * 
	 * @return Nuevo id
	 */
	public static Integer generaActorId() {
		// Une ambas partes como cadena y lo pasa a un numero entero
		int id = Integer.parseInt(Constants.PREFIJO_ID_ACTORES_MANUALES + sufijoIdActoresManuales);
		sufijoIdActoresManuales++;
		return id;
	}

	/**
	 * Genera un nuevo id para los directores
	 * 
	 * @return Nuevo id
	 */
	public static Integer generaDirectorId() {
		// Une ambas partes como cadena y lo pasa a un numero entero
		int id = Integer.parseInt(Constants.PREFIJO_ID_DIRECTORES_MANUALES + sufijoIdDirectoresManuales);
		sufijoIdDirectoresManuales++;
		return id;
	}

	/**
	 * Genera un nuevo id para los generos
	 * 
	 * @return Nuevo id
	 */
	public static Integer generaGeneroID() {
		
		
		
		// Une ambas partes como cadena y lo pasa a un numero entero
		int id = Integer.parseInt(Constants.PREFIJO_ID_GENERO_MANUALES + sufijoIdGeneroManuales);
		sufijoIdGeneroManuales++;
		return id;
	}
}
