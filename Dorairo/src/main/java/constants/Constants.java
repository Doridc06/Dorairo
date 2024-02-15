package constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Usuario;

/**
 * Clase que guarda las constantes comunes
 * 
 * @author JairoAB
 *
 */
public class Constants {

	/**
	 * Constructor privado para que no se cree objeto
	 */
	private Constants() {
	}

	// Tipos de alertas

	/** Tipo de alerta cuando ocurre un fallo grave */
	public static final String ERROR_TYPE = "ERROR";

	/** Tipo de alerta cuando ocurre un fallo leve */
	public static final String WARNING_TYPE = "WARNING";

	/** Tipo de alerta para mensajes comunes */
	public static final String INFORMATION_TYPE = "INFORMATION";

	// Perfiles predeterminados

	/** Perfil del usuario Jairo */
	public static final Usuario PERFIL_JAIRO = new Usuario(".", "Jairo", "jairo@ejemplo.com", ".",
			getFecha("25-02-2004"));

	/** Perfil del usuario Doriana */
	public static final Usuario PERFIL_DORIANA = new Usuario("Doridc", "Doriana", "doriana@ejemplo.com", "456",
			getFecha("06-12-1997"));

	// Rutas a los Fxml de las ventanas

	/** Ruta al documento fxml del login */
	public static final String URL_LOGIN_FXML = "/view/Login.fxml";

	/** Ruta al documento fxml del registro */
	public static final String URL_REGISTRO_FXML = "/view/Registro.fxml";

	/** Ruta al documento fxml del inicio */
	public static final String URL_INICIO_FXML = "/view/Inicio.fxml";

	/** Ruta al documento fxml de pelicula */
	public static final String URL_PELICULA_FXML = "/view/Pelicula.fxml";

	/** Ruta al documento fxml de series */
	public static final String URL_SERIES_FXML = "/view/Series.fxml";

	/** Ruta al documento fxml de usuario */
	public static final String URL_USUARIO_FXML = "/view/Usuario.fxml";

	/** Ruta al documento fxml de buscador */
	public static final String URL_BUSCADOR_FXML = "/view/Buscador.fxml";

	/** Ruta al documento fxml de agregadas manualmente */
	public static final String URL_AGREGADAS_MANUALMENTE_FXML = "/view/AgregadasManualmente.fxml";

	/** Ruta al documento fxml de Detalles */
	public static final String URL_DETALLES_FXML = "/view/Detalles.fxml";

	/** Ruta al documento fxml de Generos */
	public static final String URL_GENEROS_FXML = "/view/BuscarGenero.fxml";

	/** Ruta al documento fxml de Aleatoria */
	public static final String URL_PELI_SERIE_ALEATORIA_FXML = "/view/Aleatoria.fxml";

	/** Ruta al documento fxml de Cambiar Password */
	public static final String URL_CAMBIAR_PASSWORD_FXML = "/view/CambiarPassword.fxml";

	/** Ruta al documento fxml de Cambiar Nombre */
	public static final String URL_CAMBIAR_NOMBRE_FXML = "/view/CambiarNombre.fxml";

	// Rutas a imagenes

	/** Ruta a la imagen de la portada */
	public static final String URL_PORTADA = "/application/images/portada.png";

	/** Ruta a la imagen de la foto de perfl por defecto */
	public static final String URL_FOTO_PERFIL_DEFAULT = "/application/images/fotoPerfilDefault.png";

	/** Ruta a la imagen de fondo de la ventana del perfil */
	public static final String URL_FOTO_FONDO_PERFIL = "/application/images/fondoPerfil.png";

	/** Ruta a la imagen de subir foto */
	public static final String URL_FOTO_SUBIR_FOTO = "/application/images/subirFoto.png";

	/** Ruta a la imagen del icono de la aplicación */
	public static final String URL_LOGO_AMPLIADO = "/application/images/logoAmpliado.jpg";

	/** Ruta a la imagen del logo */
	public static final String URL_LOGO_NORMAL = "/application/images/logoDorairo.jpg";

	/** Ruta a la imagen de la lupa de la aplicación */
	public static final String URL_LUPA = "/application/images/lupa.png";

	// ID
	/** Prefijo para las series manuales */
	public static final String PREFIJO_ID_SERIES_MANUALES = "3333333";

	/** Prefijo para las peliculas manuales */
	public static final String PREFIJO_ID_PELIS_MANUALES = "4444444";

	/** Prefijo para las compañias manuales */
	public static final String PREFIJO_ID_COMPANIES_MANUALES = "5555555";

	/** Prefijo para los actores manuales */
	public static final String PREFIJO_ID_ACTORES_MANUALES = "6666666";

	/** Prefijo para los actores manuales */
	public static final String PREFIJO_ID_DIRECTORES_MANUALES = "7777777";

	/** Prefijo para los actores manuales */
	public static final String PREFIJO_ID_GENEROS_MANUALES = "88888";

	// Otros

	/** Formato de un correo electronico */
	public static final String FORMATO_CORREO = "(([^<>()\\[\\]\\\\.,;:\\s@”]+(\\.[^<>()\\[\\]\\\\.,;:\\s@”]+)*)|(“.+”))@((\\[[0–9]{1,3}\\.[0–9]{1,3}\\.[0–9]{1,3}\\.[0–9]{1,3}])|(([a-zA-Z\\-0–9]+\\.)+[a-zA-Z]{2,}))";

	/** Identificador de peliculas en api */
	public static final String PELICULA = "movie";

	/** Identificador de series en api */
	public static final String SERIES = "tv";

	// Metodos

	/**
	 * Devuelve una fecha tipo Date a partir de una fecha string proporcionada
	 * 
	 * @param fecha Fecha en string
	 * @return Fecha tipo Date formate dd-MM-yyyy
	 */
	private static Date getFecha(String fecha) {
		// Formato de la cadena de fecha
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		try {
			// Convertir la cadena a un objeto Date
			return formato.parse(fecha);

			// Imprimir el resultado
		} catch (ParseException e) {
			return new Date();
		}
	}

}
