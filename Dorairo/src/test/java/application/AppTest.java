package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
	void compruebaTextoBoton() {
		FxAssert.verifyThat("#btnBuscar", LabeledMatchers.hasText("BUSCAR"));
	}

	@Test
	void testTexto() {
		FxRobot fxRobot = new FxRobot();
		fxRobot.clickOn("#txtUsuario");
		fxRobot.write("Usuario");
		fxRobot.clickOn("#pwContrasena");
		fxRobot.write("password");
		fxRobot.clickOn("#btnLogin");

		// Busca un nodo con el mensaje de error
		Node errorNode = mainNode.lookup(".dialog-pane");

		// Verifica si el nodo con el mensaje de error está presente
		assertNotNull(errorNode, "Se esperaba un mensaje de error, pero no se encontró.");
		assertEquals("Error de inicio de sesión. Usuario o contraseña incorrectos", ((Label) errorNode).getText(),
				"El mensaje de error no coincide con lo esperado.");
	}

	@Test
	void testVentana() {
//		FxRobot fxRobot = new FxRobot();
//		fxRobot.clickOn("#btnBuscar");
//		FxAssert.verifyThat("#lblTexto", NodeMatchers.isVisible());
	}

}
