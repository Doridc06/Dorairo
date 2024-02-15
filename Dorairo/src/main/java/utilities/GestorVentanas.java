package utilities;

import java.io.IOException;

import constants.Constants;
import controller.BuscarGeneroController;
import controller.DetallesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Gestiona la vista de las distintas ventanas
 * 
 * @author JairoAB
 *
 */
public class GestorVentanas {

	/**
	 * Array con las pantallas que no deben seguir el tamaño normal, sino que tienen
	 * el suyo propio y se superponen a la ventana anterior
	 */
	private String[] pantallasTamanioDistintoYSuperpuestas = { "Registro", "Cambiar Contraseña", "Cambiar Nombre" };

	/**
	 * Muestra la ventana que corresponda y cierra la anterior
	 * 
	 * @param stageAnterior Stage que se va a cerrar
	 * @param urlFxml       URL del Fxml que se va a mostrar
	 * @param titulo        Titulo que va a recibir la ventana
	 */
	public void muestraVentana(Stage stageAnterior, String urlFxml, String titulo) {
		// Crea el nuevo stage
		setNewStage(urlFxml, titulo);

		// Cierra el stage anterior
		stageAnterior.close();
	}

	/**
	 * Muestra la ventana de Registro y desenfoca la anterior para que no se pueda
	 * hacer uso de ella hasta que se cierre la de Registro
	 * 
	 * @param anteriorScene
	 */
	public void muestraRegistro(Scene anteriorScene) {
		// Aplica el desenfoque a la escena
		BoxBlur blur = new BoxBlur(10, 10, 3);
		anteriorScene.getRoot().setEffect(blur);

		setNewStage(Constants.URL_REGISTRO_FXML, "Registro");

		// Desactiva el efecto de desenfoque
		anteriorScene.getRoot().setEffect(null);
	}

	/**
	 * Crea un nuevo stage con la url proporcionada, le cambia el titulo y la imagen
	 * y lo muestra por pantalla
	 * 
	 * @param titulo  Titulo de la ventana
	 * @param urlFxml URL del Fxml que se va a mostrar
	 */
	public void setNewStage(String urlFxml, String titulo) {
		try {
			// Ruta a la ventana
			FXMLLoader loader = new FXMLLoader(getClass().getResource(urlFxml));
			Parent root;
			root = loader.load();

			// Asigna la ventana al nuevo stage
			Stage stage = new Stage();
			Scene scene = null;

			// Comprueba si es Registro o Inicio para dejarlos con sus tamaños por defecto
			if (isPantallaTamanioDistintoYSuperpuesta(titulo) || titulo.equalsIgnoreCase("Dorairo")) {
				scene = new Scene(root);
			} else {
				scene = new Scene(root, 1512, 982);
			}
			stage.setScene(scene);

			// Cambia el icono de la ventana
			Image icon = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
			stage.getIcons().add(icon);

			// Cambia el titulo de la ventana
			stage.setTitle(titulo);

			// Define el tipo de modalidad
			stage.initModality(Modality.APPLICATION_MODAL);

			// Inhabilita la redimension de la ventana
			stage.setResizable(false);

			// Muestra la ventana
			if (isPantallaTamanioDistintoYSuperpuesta(titulo)) {
				stage.showAndWait();
			} else {
				stage.show();
			}
			// Establecemos el stage como owner Stage
			Utils.setOwnerStage(stage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Comprueba si el titulo pasado es una de las pantallas con tamaño distinto
	 * 
	 * @param titulo Titulo a comprobar
	 * @return True si es una de las pantallas con tamaño distinto; False si no lo
	 *         es.
	 */
	private boolean isPantallaTamanioDistintoYSuperpuesta(String titulo) {
		// Recorre el array de las pantallas con tamaño distinto para comprobar si se
		// encuentra el titulo
		for (String pantalla : pantallasTamanioDistintoYSuperpuestas) {
			if (pantalla.equalsIgnoreCase(titulo)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Muestra la ventana de Detalles
	 * 
	 * @param stageAnterior Stage anterior
	 * @param id            Id de la pelicula/serie
	 * @param tipo          Tipo de pelicula/serie
	 */
	public void muestraDetalles(Stage stageAnterior, String id, String tipo) {
		try {
			// Cierra el stage anterior
			stageAnterior.close();

			// Ruta a la ventana
			FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.URL_DETALLES_FXML));
			Parent root;
			root = loader.load();

			// Obtener el controlador de la ventana de detalles
			DetallesController detallesController = loader.getController();

			// Inicializar los datos en el controlador de detalles
			detallesController.initData(id, tipo);

			// Asigna la ventana al nuevo stage
			Stage stage = new Stage();
			Scene scene = null;

			// Establece el tamaño por defecto
			scene = new Scene(root, 1512, 982);
			stage.setScene(scene);

			// Cambia el icono de la ventana
			Image icon = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
			stage.getIcons().add(icon);

			// Cambia el titulo de la ventana
			stage.setTitle("Detalles");

			// Define el tipo de modalidad
			stage.initModality(Modality.APPLICATION_MODAL);

			// Inhabilita la redimension de la ventana
			stage.setResizable(false);

			// Muestra la ventana
			stage.show();

			// Establecemos el stage como owner Stage
			Utils.setOwnerStage(stage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Muestra la pantalla de Buscador por Genero
	 * 
	 * @param stageAnterior Stage anterior
	 * @param id            Id de la pelicula/serie
	 * @param tipo          Tipo de pelicula/serie
	 */
	public void muestraBuscadorGenero(Stage stageAnterior, String tipo, String id) {
		try {// Cierra el stage anterior
			stageAnterior.close();

			// Ruta a la ventana
			FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.URL_GENEROS_FXML));
			Parent root;
			root = loader.load();

			// Obtener el controlador de la ventana de generos
			BuscarGeneroController buscadorController = loader.getController();

			// Inicializar los datos en el controlador de detalles
			buscadorController.initData(tipo, id);

			// Asigna la ventana al nuevo stage
			Stage stage = new Stage();
			Scene scene = null;

			// Establece el tamaño por defecto
			scene = new Scene(root, 1512, 982);
			stage.setScene(scene);

			// Cambia el icono de la ventana
			Image icon = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
			stage.getIcons().add(icon);

			// Cambia el titulo de la ventana
			stage.setTitle("Generos");

			// Define el tipo de modalidad
			stage.initModality(Modality.APPLICATION_MODAL);

			// Inhabilita la redimension de la ventana
			stage.setResizable(false);

			// Muestra la ventana
			stage.show();

			// Establecemos el stage como owner Stage
			Utils.setOwnerStage(stage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Muestra la ventana de Cambiar contraseña o nombre y desenfoca la anterior
	 * para que no se pueda hacer uso de ella hasta que se cierre
	 * 
	 * @param anteriorScene Scena anterior
	 * @param titulo        Titulo de la ventana
	 */
	public void muestraCambiarPasswordNombre(Scene anteriorScene, String titulo) {
		// Aplica el desenfoque a la escena
		BoxBlur blur = new BoxBlur(10, 10, 3);
		anteriorScene.getRoot().setEffect(blur);

		if (titulo.equalsIgnoreCase("Cambiar Contraseña")) {
			setNewStage(Constants.URL_CAMBIAR_PASSWORD_FXML, titulo);
		} else if (titulo.equalsIgnoreCase("Cambiar Nombre")) {
			setNewStage(Constants.URL_CAMBIAR_NOMBRE_FXML, titulo);
		}

		// Desactiva el efecto de desenfoque
		anteriorScene.getRoot().setEffect(null);
	}

}
