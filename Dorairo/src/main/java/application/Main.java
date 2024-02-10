package application;

import org.hibernate.Session;

import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioDaoImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.GestorVentanas;

/**
 * Clase principal, gestiona el lanzamiento del programa
 * 
 * @author JairoAB
 *
 */
public class Main extends Application {

	/** Session conectada a la base de datos */
	private static Session session;

	/** Instancia del dao de usuario */
	private static UsuarioDaoImpl usuarioDaoImpl;

	/**
	 * Método principal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Abre la session y crea el dao de usuario
		session = HibernateUtil.openSession();
		usuarioDaoImpl = new UsuarioDaoImpl(session);
		launch(args);
	}

	@Override
	/**
	 * Método que gestiona el inicio de la aplicación
	 * 
	 */
	public void start(Stage primaryStage) {
		try {
			// Añadimos unos perfiles ya creados
			usuarioDaoImpl.update(Constants.PERFIL_JAIRO);
			usuarioDaoImpl.update(Constants.PERFIL_DORIANA);

			// Lanza la pantalla del Login
			iniciaLogin(primaryStage);
		} catch (Exception e) {
		}
	}

	/**
	 * Gestiona el lanzamiento de la pantalla de Login
	 * 
	 * @param primaryStage
	 */
	public void iniciaLogin(Stage primaryStage) {
		GestorVentanas gestorVentanas = new GestorVentanas();
		gestorVentanas.muestraVentana(primaryStage, Constants.URL_LOGIN_FXML, "Dorairo");
	}
}
