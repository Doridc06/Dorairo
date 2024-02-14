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
import dao.UsuarioDaoImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Usuario;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ApplicationExtension.class)
class AppTest {

	private Stage stage;
	private Parent mainNode;
	private Usuario usuarioTest = new Usuario("UsuarioPrueba", "Enrique Martínez", "enrique@ejemplo.com", "1234",
			new Date());

	@Start
	public void start(Stage primaryStage) {
		// Comprueba si existe el usuario para eliminarlo
		UsuarioDaoImpl uDao = new UsuarioDaoImpl(HibernateUtil.openSession());
		Usuario user = uDao.searchByUsuario(usuarioTest.getUser());
		if (user != null) {
			uDao.delete(user);
		}
		stage = primaryStage;
		try {
			mainNode = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			primaryStage.setScene(new Scene(mainNode));
			primaryStage.show();
			primaryStage.toFront();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	@Test
	@Order(2)
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
		FxAssert.verifyThat("#alertaInformacion", NodeMatchers.isEnabled());
	}

	@Test
	@Order(3)
	void testUsuarioExiste() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Introduce un usuario y contraseña de una persona registrada
		fxRobot.clickOn("#txtUser");
		fxRobot.write(usuarioTest.getUser());
		fxRobot.clickOn("#pwPassword");
		fxRobot.write(usuarioTest.getClave());
		// Le da al boton de logearse
		fxRobot.clickOn("#btnLogin");
		// Verifica que salta la alerta de informacion
		FxAssert.verifyThat("#alertaInformacion", NodeMatchers.isEnabled());
		// Le da al boton de Aceptar para cerrar la alerta
		fxRobot.clickOn("#btnAceptar");
		// Comprueba que se abre la ventana de inicio
		FxAssert.verifyThat(fxRobot.window("Inicio"), WindowMatchers.isShowing());
	}

}
