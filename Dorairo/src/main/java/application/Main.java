package application;

import org.hibernate.Session;

import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioDaoImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Usuario;
import utilities.GestorVentanas;
import utilities.Utils;

/**
 * Clase principal, gestiona el lanzamiento del programa
 * 
 * @author JairoAB
 *
 */
public class Main extends Application {

	/** Perfil que se ha registrado */
	private static Usuario perfilRegsistrado = null;

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
			anadirNuevoPerfil(Constants.PERFIL_JAIRO);
			anadirNuevoPerfil(Constants.PERFIL_DORIANA);

			// Lanza la pantalla del Login
			iniciaLogin(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * Añade el perfil proporcionado a la lista de perfiles
	 * 
	 * @param perfil
	 */
	public static void anadirNuevoPerfil(Usuario perfil) {
		usuarioDaoImpl.insert(perfil);
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
		// Busca el usuario y correo, si es distinto de null es que existe
		if (usuarioDaoImpl.searchByUsuario(usuario) != null) {
			Utils.mostrarAlerta("El usuario introducido ya existe.", Constants.WARNING_TYPE);
			return true;
		} else if (usuarioDaoImpl.searchByCorreo(correo) != null) {
			Utils.mostrarAlerta("El correo introducido ya está registrado.", Constants.WARNING_TYPE);
			return true;
		}
		return false;
	}

	/**
	 * Verifica que exista un perfil con el usuario y contrasena proporcionados
	 * 
	 * @param user       a comprobar
	 * @param contrasena a comprobar
	 * @return true si existe o false si no existe
	 */
	public static boolean comprobarPerfil(String user, String contrasena) {

		// Busca y recoge el perfil con dicho usuario y contraseña
		Usuario usuario = usuarioDaoImpl.searchByUsuarioAndPassword(user, contrasena);

		// Si es distinto de null (existe), se establece como perfil registrado
		if (usuario != null) {
			setPerfilRegistrado(usuario);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the perfilRegsistrado
	 */
	public static Usuario getPerfilRegsistrado() {
		return perfilRegsistrado;
	}

	/**
	 * @param perfilRegsistrado the perfilRegsistrado to set
	 */
	public static void setPerfilRegistrado(Usuario perfilRegsistrado) {
		Main.perfilRegsistrado = perfilRegsistrado;
	}

}
