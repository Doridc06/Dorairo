package application;

import java.io.IOException;
import java.util.Date;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.WindowMatchers;
import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioDaoImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Usuario;

/**
 * Clase que realiza las pruebas con JUnit del login y registro
 * 
 * @author JairoAB
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ApplicationExtension.class)
class AppTest {

	/** Stage */
	private Stage stage;
	/** Main node */
	private Parent mainNode;
	/** Usuario para testear */
	private Usuario usuarioTest = new Usuario("UsuarioPrueba", "Enrique Martínez", "enrique@ejemplo.com", "1234",
			new Date());

	/**
	 * Método al inicio que carga la pantalla de login
	 * 
	 * @param primaryStage
	 */
	@Start
	public void start(Stage primaryStage) {
		// Comprueba si existe el usuario para eliminarlo
		UsuarioDaoImpl uDao = new UsuarioDaoImpl(HibernateUtil.openSession());
		Usuario user = uDao.searchByUsuario(usuarioTest.getUser());
		if (user != null) {
			uDao.delete(user);
		}
		stage = primaryStage;
		// Intenta cargar el login
		try {
			mainNode = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			primaryStage.setScene(new Scene(mainNode));
			primaryStage.show();
			primaryStage.toFront();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prueba para cuando un usuario no existe
	 */
	@Test
	@Order(1)
	void testUsuarioNoExiste() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Introduce un usuario y contraseña de una persona no registrada
		fxRobot.clickOn("#txtUser");
		fxRobot.write(usuarioTest.getUser());
		fxRobot.clickOn("#pwPassword");
		fxRobot.write(usuarioTest.getClave());
		// Le da al boton de logearse
		fxRobot.clickOn("#btnLogin");
		// Verifica que salta la alerta de error
		FxAssert.verifyThat("#alertaError", NodeMatchers.isEnabled());
		// Le da al boton de Aceptar para cerrar la alerta
		fxRobot.clickOn("#btnAceptar");
	}

	/**
	 * Prueba para cuando un usuario existe
	 */
	@Test
	@Order(2)
	void testUsuarioExiste() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Introduce un usuario y contraseña de una persona registrada
		fxRobot.clickOn("#txtUser");
		fxRobot.write(".");
		fxRobot.clickOn("#pwPassword");
		fxRobot.write(".");
		// Le da al boton de logearse
		fxRobot.clickOn("#btnLogin");
		// Verifica que salta la alerta de informacion
		FxAssert.verifyThat("#alertaInformacion", NodeMatchers.isEnabled());
		// Le da al boton de Aceptar para cerrar la alerta
		fxRobot.clickOn("#btnAceptar");
		// Comprueba que se abre la ventana de inicio
		FxAssert.verifyThat(fxRobot.window("Inicio"), WindowMatchers.isShowing());
	}

	/**
	 * Prueba para cuando se dejan campos vacios en registro
	 */
	@Test
	@Order(3)
	void testRegistroCamposVacio() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Abre la pantalla de registro
		fxRobot.clickOn("#lblPulsaAqui");
		// Introduce los datos
		fxRobot.clickOn("#txtUsuario");
		fxRobot.write(usuarioTest.getUser());
		// Presiona el boton de registro
		fxRobot.clickOn("#btnRegistrarse");
		// Verifica que salta la alerta de warning
		FxAssert.verifyThat("#alertaWarning", NodeMatchers.isEnabled());
	}

	/**
	 * Prueba para cuando se introduce un usuario existente en registro
	 */
	@Test
	@Order(4)
	void testRegistroUsuarioYaExistente() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Abre la pantalla de registro
		fxRobot.clickOn("#lblPulsaAqui");
		// Introduce los datos
		fxRobot.clickOn("#txtUsuario");
		fxRobot.write(".");
		fxRobot.clickOn("#txtCorreo");
		fxRobot.write(usuarioTest.getCorreo());
		fxRobot.clickOn("#txtNombre");
		fxRobot.write(usuarioTest.getNombre());
		fxRobot.clickOn("#pwContrasena");
		fxRobot.write(usuarioTest.getClave());
		fxRobot.clickOn("#pwRepetirContrasena");
		fxRobot.write(usuarioTest.getClave());
		// Presiona el boton de registro
		fxRobot.clickOn("#btnRegistrarse");
		// Verifica que salta la alerta de warning
		FxAssert.verifyThat("#alertaWarning", NodeMatchers.isEnabled());
	}

	/**
	 * Prueba para cuando se introduce un correo existente en registro
	 */
	@Test
	@Order(5)
	void testRegistroCorreoYaExistente() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Abre la pantalla de registro
		fxRobot.clickOn("#lblPulsaAqui");
		// Introduce los datos
		fxRobot.clickOn("#txtUsuario");
		fxRobot.write(".");
		fxRobot.clickOn("#txtCorreo");
		fxRobot.write(usuarioTest.getCorreo());
		fxRobot.clickOn("#txtNombre");
		fxRobot.write(usuarioTest.getNombre());
		fxRobot.clickOn("#pwContrasena");
		fxRobot.write(usuarioTest.getClave());
		fxRobot.clickOn("#pwRepetirContrasena");
		fxRobot.write(usuarioTest.getClave());
		// Presiona el boton de registro
		fxRobot.clickOn("#btnRegistrarse");
		// Verifica que salta la alerta de warning
		FxAssert.verifyThat("#alertaWarning", NodeMatchers.isEnabled());
	}

	/**
	 * Prueba para cuando se introducen mal las contraseñas en registro
	 */
	@Test
	@Order(6)
	void testRegistroContraseñasIncorrectas() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Abre la pantalla de registro
		fxRobot.clickOn("#lblPulsaAqui");
		// Introduce los datos
		fxRobot.clickOn("#txtUsuario");
		fxRobot.write(usuarioTest.getUser());
		fxRobot.clickOn("#txtCorreo");
		fxRobot.write(usuarioTest.getCorreo());
		fxRobot.clickOn("#txtNombre");
		fxRobot.write(usuarioTest.getNombre());
		fxRobot.clickOn("#pwContrasena");
		fxRobot.write(usuarioTest.getClave());
		fxRobot.clickOn("#pwRepetirContrasena");
		fxRobot.write("mal");
		// Presiona el boton de registro
		fxRobot.clickOn("#btnRegistrarse");
		// Verifica que salta la alerta de warning
		FxAssert.verifyThat("#alertaWarning", NodeMatchers.isEnabled());
	}

	/**
	 * Prueba para cuando se introducen todos los campos correctamente
	 */
	@Test
	@Order(7)
	void testRegistroFunciona() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Abre la pantalla de registro
		fxRobot.clickOn("#lblPulsaAqui");
		// Introduce los datos
		fxRobot.clickOn("#txtUsuario");
		fxRobot.write(usuarioTest.getUser());
		fxRobot.clickOn("#txtCorreo");
		fxRobot.write(usuarioTest.getCorreo());
		fxRobot.clickOn("#txtNombre");
		fxRobot.write(usuarioTest.getNombre());
		fxRobot.clickOn("#pwContrasena");
		fxRobot.write(usuarioTest.getClave());
		fxRobot.clickOn("#pwRepetirContrasena");
		fxRobot.write(usuarioTest.getClave());
		// Presiona el boton de registro
		fxRobot.clickOn("#btnRegistrarse");
		// Verifica que salta la alerta de informacion
		// FxAssert.verifyThat("#alertaInformacion", NodeMatchers.isEnabled());
		FxAssert.verifyThat(fxRobot.window(Constants.INFORMATION_TYPE), WindowMatchers.isShowing());
	}

}
