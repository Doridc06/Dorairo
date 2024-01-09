package constants;

import java.util.Date;

import models.Perfil;

/**
 * Clase que guarda las constantes comunes
 * 
 */
public class Constants {

	/** Tipo de alerta cuando ocurre un fallo grave */
	public static final String ERROR_TYPE = "ERROR";

	/** Tipo de alerta cuando ocurre un fallo leve */
	public static final String WARNING_TYPE = "WARNING";

	/** Tipo de alerta para mensajes comunes */
	public static final String INFORMATION_TYPE = "INFORMATION";

	/** Ruta a la imagen del icono de la aplicación */
	public static final String URL_LOGO_AMPLIADO = "/application/images/logoAmpliado.jpg";

	/** Ruta a la imagen del logo */
	public static final String URL_LOGO_NORMAL = "/application/images/logoDorairo.jpg";

	/** Ruta a la imagen de la portada */
	public static final String URL_PORTADA = "/application/images/portada.png";

	/** Ruta al documento fxml del login */
	public static final String URL_LOGIN_FXML = "/view/Login.fxml";

	/** Ruta al documento fxml del registro */
	public static final String URL_REGISTRO_FXML = "/view/Registro.fxml";

	/** Perfil del usuario Jairo */
	public static final Perfil PERFIL_JAIRO = new Perfil("Jairopo", "Jairo", "jairo@ejemplo.com", "123", new Date());

	/** Perfil del usuario Doriana */
	public static final Perfil PERFIL_DORIANA = new Perfil("Doridc", "Doriana", "doriana@ejemplo.com", "456", new Date());

}
