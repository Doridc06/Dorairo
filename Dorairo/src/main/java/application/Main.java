package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Perfil;
import utilities.Utils;

/**
 * Clase principal, gestiona el lanzamiento del programa
 * 
 */
public class Main extends Application {

	/** Lista donde se guardan cada uno de los perfiles registrados */
	private static List<Perfil> listaPerfiles = new ArrayList<>();

	/**
	 * Método principal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	/**
	 * Método que gestiona el inicio de la aplicación
	 * 
	 */
	public void start(Stage primaryStage) {
		try {

			// Añadimos a la lista de perfiles unos ya creados
			anadirNuevoPerfil(Constants.PERFIL_JAIRO);
			anadirNuevoPerfil(Constants.PERFIL_DORIANA);

			// Lanza la pantalla del Login
			muestraLogin(primaryStage);

			// Establecemos el primaryStage como owner Stage
			Utils.setOwnerStage(primaryStage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gestiona el lanzamiento de la pantalla de Login
	 * 
	 * @param primaryStage stage a mostrar
	 * @throws IOException
	 */
	public void muestraLogin(Stage primaryStage) throws IOException {
		// Ruta al Login
		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource(Constants.URL_LOGIN_FXML));
		Parent loginRoot = loginLoader.load();

		// Configura la ventana para mostrar el login con unas medidas determinadas
		Scene scene = new Scene(loginRoot, 920, 619);
		primaryStage.setScene(scene);

		// Cambia el icono de la ventana
		Image icon = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		primaryStage.getIcons().add(icon);

		// Cambia el titulo de la ventana
		primaryStage.setTitle("Dorairo");

		// Inhabilita la redimension de la ventana
		primaryStage.setResizable(false);

		// Muestra la ventana del login
		primaryStage.show();
	}

	/**
	 * Añade el perfil proporcionado a la lista de perfiles
	 * 
	 * @param perfil
	 */
	public static void anadirNuevoPerfil(Perfil perfil) {
		listaPerfiles.add(perfil);
	}

	/**
	 * Comprueba si existe un perfil con el correo o el usuario proporcionados
	 * 
	 * @param usuario a comprobar
	 * @param correo  a comprobar
	 * @return true si el correo o usuario ya han sido usados; false si ninguno se
	 *         ha usado
	 */
	public static boolean isPerfil(String usuario, String correo) {
		// Recorre la lista de perfiles
		for (Perfil perfil : listaPerfiles) {
			// Comprueba si coincide el correo o usuario
			if (perfil.getCorreo().equals(correo)) {
				Utils.mostrarAlerta("El correo introducido ya está registrado.", Constants.WARNING_TYPE);
				return true;
			} else if (perfil.getUsuario().equals(usuario)) {
				Utils.mostrarAlerta("El usuario introducido ya existe.", Constants.WARNING_TYPE);
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica que exista un perfil con el usuario y contrasena proporcionados
	 * 
	 * @param usuario    a comprobar
	 * @param contrasena a comprobar
	 * @return true si existe o false si no existe
	 */
	public static boolean comprobarPerfil(String usuario, String contrasena) {
		// Recorre la lista de perfiles
		for (Perfil perfil : listaPerfiles) {
			// Comprueba si coincide el usuario y contrasena
			if (perfil.getUsuario().equals(usuario) && perfil.getContrasena().equals(contrasena)) {
				return true;
			}
		}
		return false;
	}

}
