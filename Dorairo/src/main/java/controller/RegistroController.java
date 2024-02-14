package controller;

import java.util.Date;

import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Usuario;
import utilities.Utils;

/**
 * Clase con las funciones del registro
 * 
 */
public class RegistroController {

	@FXML
	private Button btnRegistrarse;

	@FXML
	private PasswordField pwContrasena;

	@FXML
	private PasswordField pwRepetirContrasena;

	@FXML
	private TextField txtCorreo;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtUsuario;

	/** Scene de la ventana de Inicio */
	private Scene scene;

	/** Stage de la ventana de Inicio */
	private Stage stage;

	/** Instancia del dao de usuario */
	private UsuarioDaoImpl usuarioDaoImpl;

	@FXML
	void initialize() {
		// Abre la session y crea el dao de usuario
		usuarioDaoImpl = new UsuarioDaoImpl(HibernateUtil.openSession());
	}

	/**
	 * Registra un nuevo perfil según los datos que se hayan introducido
	 * 
	 * @param event
	 */
	@FXML
	void buttonRegistrarsePressed(MouseEvent event) {
		String usuario = txtUsuario.getText();
		String correo = txtCorreo.getText();
		String nombre = txtNombre.getText();
		String contrasena = pwContrasena.getText();
		String repeticionContrasena = pwRepetirContrasena.getText();

		// Comprueba que los campos estén llenos, las contraseñas coincidan y no exista
		// un perfil con un usuario o correo igual
		if (camposLlenos(usuario, correo, nombre, contrasena, repeticionContrasena)
				&& Utils.compruebaContrasenas(contrasena, repeticionContrasena) && !isPerfil(usuario, correo)) {
			// Crea el nuevo perfil
			try {
				usuarioDaoImpl.update(new Usuario(usuario, nombre, correo, repeticionContrasena, new Date()));
				setSceneAndStage();
				stage.close();
				Utils.mostrarAlerta("¡El nuevo perfil se ha creado con éxito!", Constants.INFORMATION_TYPE);
			} catch (Exception e) {
				Utils.mostrarAlerta(
						"Error guardando el perfil. Reglas de campos (c = caracteres):\nusuario, nombre y clave máx. 20c\ncorreo máx. 100",
						repeticionContrasena);
			}
		}
	}

	/**
	 * Comprueba que todos los campos estén llenos
	 * 
	 * @param usuario
	 * @param correo
	 * @param nombre
	 * @param contrasena
	 * @param repeticionContrasena
	 * @return true si están llenos; false si hay alguno vacío.
	 */
	private boolean camposLlenos(String usuario, String correo, String nombre, String contrasena,
			String repeticionContrasena) {
		if (usuario.isBlank() || correo.isBlank() || nombre.isBlank() || contrasena.isBlank()
				|| repeticionContrasena.isBlank()) {
			Utils.mostrarAlerta("Alguno de los campos se encuentra vacío.", Constants.WARNING_TYPE);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Comprueba si existe un perfil con el correo o el usuario proporcionados
	 * 
	 * @param usuario a comprobar
	 * @param correo  a comprobar
	 * @return true si el correo o usuario ya han sido usados; false si ninguno se
	 *         ha usado
	 */
	public boolean isPerfil(String usuario, String correo) {
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
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = btnRegistrarse.getScene();
		stage = (Stage) scene.getWindow();
	}

}
