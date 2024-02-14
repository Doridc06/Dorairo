package application;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.WindowMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class AppTest {

	private Stage stage;
	private Parent mainNode;

	@Start
	public void start(Stage primaryStage) {
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
	void testUsuarioNoExiste() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Introduce un usuario y contraseña inexistentes
		fxRobot.clickOn("#txtUsuario");
		fxRobot.write("Usuario");
		fxRobot.clickOn("#pwContrasena");
		fxRobot.write("password");
		// Le da al boton de logearse
		fxRobot.clickOn("#btnLogin");
		// Verifica que salta la alerta de error
		FxAssert.verifyThat("#alertaError", NodeMatchers.isEnabled());
		// Le da al boton de Aceptar para cerrar la alerta
		fxRobot.clickOn("#btnAceptar");
	}

	@Test
	void testUsuarioExiste() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Introduce un usuario y contraseña existentes
		fxRobot.clickOn("#txtUsuario");
		fxRobot.write(".");
		fxRobot.clickOn("#pwContrasena");
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

	@Test
	void testRegistroFunciona() {
		// Crea el FxRobot
		FxRobot fxRobot = new FxRobot();
		// Introduce un nombre y correo no existentes
		fxRobot.clickOn("#txtUsuario");
		fxRobot.write("Enrique");
		fxRobot.clickOn("#txtCorreo");
		fxRobot.write("enrique@ejemplo.com");
		fxRobot.clickOn("#txtNombre");
		fxRobot.write("Enrique Martínez Fernández");
		fxRobot.clickOn("#pwContrasena");
		fxRobot.write("enrique");
		// terminar
	}
}
