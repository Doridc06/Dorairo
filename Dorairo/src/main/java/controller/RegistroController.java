package controller;

import java.util.Date;

import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioDaoImpl;
import javafx.fxml.FXML;
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
 * @author JairoAB
 */
public class RegistroController {

	/** Botón para registrarse */
	@FXML
	private Button btnRegistrarse;

	/** Campo para contraseña */
	@FXML
	private PasswordField pwContrasena;

	/** Campo para repeticion de contraseña */
	@FXML
	private PasswordField pwRepetirContrasena;

	/** Campo para el correo */
	@FXML
	private TextField txtCorreo;

	/** Campo para el nombre */
	@FXML
	private TextField txtNombre;

	/** Campo para el usuario */
	@FXML
	private TextField txtUsuario;

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
		// Recoge los valores
		String usuario = txtUsuario.getText();
		String correo = txtCorreo.getText();
		String nombre = txtNombre.getText();
		String contrasena = pwContrasena.getText();
		String repeticionContrasena = pwRepetirContrasena.getText();

		// Comprueba que los campos estén llenos y no superen los
		// caracteres permitidos, las contraseñas coincidan, no exista
		// un perfil con un usuario o correo igual y que este tenga un formato correo
		if (camposLlenos(usuario, correo, nombre, contrasena, repeticionContrasena) && compruebaCorreo(correo)
				&& Utils.compruebaContrasenas(contrasena, repeticionContrasena) && !isPerfil(usuario, correo)) {
			// Crea el nuevo perfil
			usuarioDaoImpl.update(new Usuario(usuario, nombre, correo, repeticionContrasena, new Date()));
			setStage();
			// Cierra la ventana
			Utils.mostrarAlerta("¡El nuevo perfil se ha creado con éxito!", Constants.INFORMATION_TYPE);
			stage.close();
		}
	}

	/**
	 * Comprueba que el correo cumpla con el formatos
	 * 
	 * @param correo Correo a comprobar
	 * @return True si cumple; False si no cumple
	 */
	public boolean compruebaCorreo(String correo) {
		if (!correo.matches(Constants.FORMATO_CORREO)) {
			Utils.mostrarAlerta("El correo introducido parece no seguir el formato de un correo.", Constants.WARNING_TYPE);
			return false;
		}
		return true;
	}

	/**
	 * Comprueba que todos los campos estén llenos y cumplen con las medidas exactas
	 * 
	 * @param usuario              Usuario a comprobar
	 * @param correo               Correo a comprobar
	 * @param nombre               Nombre a comprobar
	 * @param contrasena           Contraseña a comprobar
	 * @param repeticionContrasena RepeticionContraseña a comprobar
	 * @return True si están llenos; false si hay alguno vacío.
	 */
	private boolean camposLlenos(String usuario, String correo, String nombre, String contrasena,
			String repeticionContrasena) {
		// Comprueba si hay algún campo vacío
		if (usuario.isBlank() || correo.isBlank() || nombre.isBlank() || contrasena.isBlank()
				|| repeticionContrasena.isBlank()) {
			Utils.mostrarAlerta("Alguno de los campos se encuentra vacío.", Constants.WARNING_TYPE);
			return false;
		} else {
			// Comprueba que no superen los caracteres maximos
			if (usuario.length() > 20 || correo.length() > 100 || nombre.length() > 20) {
				Utils.mostrarAlerta(
						"Reglas de campos:\nusuario y nombre -> máximo 20 caracteres.\ncorreo -> máximo 100 caracteres.",
						Constants.WARNING_TYPE);
				return false;
			}
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
	 * Establece el valor de stage
	 */
	public void setStage() {
		stage = (Stage) btnRegistrarse.getScene().getWindow();
	}

}
