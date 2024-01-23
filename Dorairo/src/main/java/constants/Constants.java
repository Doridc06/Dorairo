package constants;

import java.util.Date;

import models.Perfil;

/**
 * Clase que guarda las constantes comunes
 * 
 * @author JairoAB
 *
 */
public class Constants {

	// Tipos de alertas
	
	/** Tipo de alerta cuando ocurre un fallo grave */
	public static final String ERROR_TYPE = "ERROR";
	
	/** Tipo de alerta cuando ocurre un fallo leve */
	public static final String WARNING_TYPE = "WARNING";
	
	/** Tipo de alerta para mensajes comunes */
	public static final String INFORMATION_TYPE = "INFORMATION";
	
	// Rutas a imagenes

	/** Ruta a la imagen del icono de la aplicación */
	public static final String URL_LOGO_AMPLIADO = "/application/images/logoAmpliado.jpg";

	/** Ruta a la imagen del logo */
	public static final String URL_LOGO_NORMAL = "/application/images/logoDorairo.jpg";

	/** Ruta a la imagen de la portada */
	public static final String URL_PORTADA = "/application/images/portada.png";
	
	// Perfiles predeterminados

	/** Perfil del usuario Jairo */
	public static final Perfil PERFIL_JAIRO = new Perfil("Jairopo", "Jairo", "jairo@ejemplo.com", "123", new Date());

	/** Perfil del usuario Doriana */
	public static final Perfil PERFIL_DORIANA = new Perfil("Doridc", "Doriana", "doriana@ejemplo.com", "456", new Date());

	// Rutas a los Fxml de las ventanas
	
	/** Ruta al documento fxml del login */
	public static final String URL_LOGIN_FXML = "/view/Login.fxml";
	
	/** Ruta al documento fxml del registro */
	public static final String URL_REGISTRO_FXML = "/view/Registro.fxml";
	
	/** Ruta al documento fxml del inicio */
	public static final String URL_INICIO_FXML = "/view/Inicio.fxml";
	
	/** Ruta al documento fxml de peliculas */
	public static final String URL_PELICULAS_FXML = "/view/Peliculas.fxml";
	
	/** Ruta al documento fxml de series */
	public static final String URL_SERIES_FXML = "/view/Series.fxml";
	
	/** Ruta al documento fxml de perfil */
	public static final String URL_PERFIL_FXML = "/view/Perfil.fxml";
	
	/** Ruta al documento fxml de buscador */
	public static final String URL_BUSCADOR_FXML = "/view/Buscador.fxml";
	
	/** Ruta al documento fxml de agregadas manualmente */
	public static final String URL_AGREGADAS_MANUALMENTE_FXML = "/view/AgregadasManualmente.fxml";

}
