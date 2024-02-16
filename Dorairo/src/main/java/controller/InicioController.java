package controller;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import com.google.gson.Gson;

import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioPeliculaDaoImpl;
import dao.UsuarioSerieDaoImpl;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Pelicula;
import models.RespuestaApi;
import models.RespuestaApiTrending;
import models.RespuestaApiTrending.MediaItem;
import models.Series;
import models.UsuarioPelicula;
import models.UsuarioSerie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;

/**
 * Clase con las funciones del Inicio
 * 
 * @author JairoAB
 */
public class InicioController {

	/** ImageView para el logo en la cabecera */
	@FXML
	private ImageView imagenLogoCabecera;

	/** HBox para mi lista */
	@FXML
	private HBox miLista;

	/** HBox para novedades */
	@FXML
	private HBox novedades;

	/** HBox para top10 */
	@FXML
	private HBox top10;

	/** Stage de la ventana de Inicio */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	/** ImageView para la lupa */
	@FXML
	private ImageView lupa;

	/** Conexion con la base de datos */
	private Session session;

	/** Clave para la API */
	private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

	/**
	 * Método que se ejecuta al iniciar la clase
	 */
	@FXML
	void initialize() {
		session = HibernateUtil.openSession();
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagenLogo);
		// Establece la imagen de la lupa
		Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
		lupa.setImage(imagenLupa);
		try {
			// Configuración del cliente HTTP (OkHttpClient)
			OkHttpClient client = new OkHttpClient();
			// Llamada a la API para películas novedades
			handleMovieApiCall(client, "https://api.themoviedb.org/3/movie/now_playing?language=es-ES&page=1", novedades);
			// Llamada a la API para top 10
			handleMovieApiCall(client, "https://api.themoviedb.org/3/trending/all/day?language=es-ES", top10);
			// Muestra mi lista
			muestraMiLista();
			miLista.setSpacing(50.0);
		} catch (IOException e) {
			// Manejar excepciones
			e.printStackTrace();
		}
	}

	/**
	 * Gestiona la muestra de Mi Lista en la interfaz
	 */
	public void muestraMiLista() {
		// Recibe el número de películas en mi lista
		String user = UsuarioController.getUsuarioRegistrado().getUser();
		UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
		int contPelisMiLista = upDao.searchPeliculasMiLista(user).size();
		// Recibe el número de series en mi lista
		UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
		int contSeriesMiLista = usDao.searchSeriesMiLista(user).size();
		// Recoge el total y comprueba si hay alguna peli/serie
		int total = contPelisMiLista + contSeriesMiLista;
		if (total > 0) {
			mostrarMiListaPeliculas();
			mostrarMiListaSeries();
		} else {
			mostrarMensajeEnHBox(miLista, "No hay nada en mi lista");
		}
	}

	/**
	 * Añade la lista de peliculas que se encuentran en Mi Lista y las muestra por
	 * pantalla
	 */
	private void mostrarMiListaPeliculas() {
		// Trae las peliculas guardadas en mi lista
		UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
		String user = UsuarioController.getUsuarioRegistrado().getUser();
		List<UsuarioPelicula> upLista = upDao.searchPeliculasMiLista(user);

		for (UsuarioPelicula up : upLista) {
			// Obtener la imagen de la película
			ImageView imageView = getImageViewFromPelicula(up.getId().getPelicula());
			// Establecer el tamaño de la imagen si es necesario
			imageView.setFitWidth(200); // Ajusta el ancho según tus necesidades
			imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
			imageView.setOnMouseClicked(
					event -> abrirVentanaDetalles(String.valueOf(up.getId().getPelicula().getId()), Constants.PELICULA));
			// Agregar la imagen al HBox de "Mi Lista"
			miLista.getChildren().add(imageView);
		}
	}

	/**
	 * Añade la lista de series que se encuentran en Mi Lista y las muestra por
	 * pantalla
	 */
	private void mostrarMiListaSeries() {
		// Trae las series guardadas en mi lista
		UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
		String user = UsuarioController.getUsuarioRegistrado().getUser();
		List<UsuarioSerie> listaUS = usDao.searchSeriesMiLista(user);

		// Trae las series guardadas en mi lista
		for (UsuarioSerie us : listaUS) {
			// Obtener la imagen de la serie
			ImageView imageView = getImageViewFromSeries(us.getId().getSeries());

			// Establecer el tamaño de la imagen si es necesario
			imageView.setFitWidth(200);
			imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
			imageView.setOnMouseClicked(
					event -> abrirVentanaDetalles(String.valueOf(us.getId().getSeries().getId()), Constants.SERIES));
			// Agregar la imagen al HBox de "Mi Lista"
			miLista.getChildren().add(imageView);
		}
	}

	/**
	 * Crea y configura un ImageView para mostrar la imagen de una serie.
	 * 
	 * @param series La serie de la que se va a mostrar la imagen.
	 * @return El ImageView configurado.
	 */
	private ImageView getImageViewFromSeries(Series series) {
		// Crea el imageView con la configuracion
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setFitWidth(290.0);
		imageView.setPreserveRatio(true);
		String id = "" + series.getId();
		Image image;
		// Comprueba si se trata de una serie manual y recoge su imagen en ambos casos
		if (id.startsWith(Constants.PREFIJO_ID_SERIES_MANUALES)) {
			image = new Image("file:" + series.getPoster_path());
		} else {
			String imageUrl = Constants.URL_API_IMAGE + series.getPoster_path();
			image = new Image(imageUrl);
		}
		// Configurar la imagen en el ImageView
		imageView.getStyleClass().add(Constants.SOMBRA_STYLE_CLASS);
		imageView.setImage(image);

		return imageView;
	}

	/**
	 * Abre la ventana de detalles con los datos de la pelicula/serie con el id
	 * proporcionado
	 * 
	 * @param id   Id de la obra
	 * @param tipo Tipo de obra (tv/movie)
	 */
	private void abrirVentanaDetalles(String id, String tipo) {
		setStage();
		gestorVentanas.muestraDetalles(stage, id, tipo);
	}

	/**
	 * Método de la llamada a la api para la información de cada película
	 * 
	 * @param client
	 * @param apiUrl
	 * @param targetHBox
	 * @throws IOException
	 */
	private void handleMovieApiCall(OkHttpClient client, String apiUrl, HBox targetHBox) throws IOException {
		// Construir la solicitud para la API de películas
		Request request = new Request.Builder().url(apiUrl).get().addHeader("accept", "application/json")
				.addHeader("Authorization", "Bearer " + API_KEY).build();

		// Ejecutar la solicitud y obtener la respuesta
		Response response = client.newCall(request).execute();

		// Obtener el cuerpo de la respuesta como cadena
		String responseBody = response.body().string();

		// Utilizar Gson para convertir la respuesta JSON a objetos Java
		Gson gson = new Gson();
		// Comprueba si es la lista de top10
		if (targetHBox.getId().equalsIgnoreCase("top10")) {
			getResultTrending(targetHBox, responseBody, gson);
		} else {
			RespuestaApi respApi = gson.fromJson(responseBody, RespuestaApi.class);
			// Verificar si hay resultados en la respuesta
			if (respApi.getResults() != null && respApi.getResults().length > 0) {
				// Iterar sobre las películas y agregar imágenes al HBox
				int contador = 0;
				for (Pelicula pelicula : respApi.getResults()) {
					if (contador < 10) { // Limitar a 10 películas
						ImageView imageView = null;
						if (pelicula.getPoster_path() != null) {
							imageView = getImageViewFromPelicula(pelicula);
							imageView.setOnMouseClicked(
									event -> abrirVentanaDetalles(String.valueOf(pelicula.getId()), Constants.PELICULA));

						}
						// Verificar si imageView no es nulo antes de agregarlo al HBox
						if (imageView != null) {
							targetHBox.getChildren().add(imageView);
						}
						contador++;
					} else {
						break; // Se han agregado 10 películas, salir del bucle
					}
				}
			}
		}
		targetHBox.setSpacing(50.0);
	}

	/**
	 * Recoge el resultado de la api sobre las peliculas/series trending y las añade
	 * al hbox
	 * 
	 * @param targetHBox
	 * @param responseBody
	 * @param gson
	 */
	public void getResultTrending(HBox targetHBox, String responseBody, Gson gson) {
		RespuestaApiTrending respApi = gson.fromJson(responseBody, RespuestaApiTrending.class);
		// Verifica si hay resultados en la respuesta
		if (respApi.getResults() != null && respApi.getResults().length > 0) {
			int contador = 0;
			for (MediaItem item : respApi.getResults()) {
				if (contador < 10) { // Limitar a 10 películas
					ImageView imageView = null;
					if (item.getPoster_path() != null) {
						imageView = getImageViewFromItem(item);
						imageView
								.setOnMouseClicked(event -> abrirVentanaDetalles(String.valueOf(item.getId()), item.getMediaType()));
					}
					// Verificar si imageView no es nulo antes de agregarlo al HBox
					if (imageView != null) {
						targetHBox.getChildren().add(imageView);
					}
					contador++;
				} else {
					break; // Se han agregado 10 películas, salir del bucle
				}
			}
		}
	}

	/**
	 * Crea y configura un ImageView para mostrar la imagen de un MediaItem.
	 * 
	 * @param item La MediaItem de la que se va a mostrar la imagen.
	 * @return El ImageView configurado.
	 */
	private ImageView getImageViewFromItem(MediaItem item) {
		// Crea el imageView con la configuracion
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setFitWidth(290.0);
		imageView.setPreserveRatio(true);
		Image image;
		String imageUrl = Constants.URL_API_IMAGE + item.getPoster_path();
		image = new Image(imageUrl);
		// Configurar la imagen en el ImageView
		imageView.getStyleClass().add(Constants.SOMBRA_STYLE_CLASS);
		imageView.setImage(image);
		return imageView;
	}

	/**
	 * Crea y configura un ImageView para mostrar la imagen de una película.
	 * 
	 * @param pelicula La película de la que se va a mostrar la imagen.
	 * @return El ImageView configurado.
	 */
	private ImageView getImageViewFromPelicula(Pelicula pelicula) {
		// Crea el imageView con la configuracion
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setFitWidth(290.0);
		imageView.setPreserveRatio(true);
		String id = "" + pelicula.getId();
		Image image;
		// Comprueba si se trata de una peli manual y recoge su imagen en ambos casos
		if (id.startsWith(Constants.PREFIJO_ID_PELIS_MANUALES)) {
			image = new Image("file:" + pelicula.getPoster_path());
		} else {
			String imageUrl = Constants.URL_API_IMAGE + pelicula.getPoster_path();
			image = new Image(imageUrl);
		}
		// Configurar la imagen en el ImageView
		imageView.getStyleClass().add(Constants.SOMBRA_STYLE_CLASS);
		imageView.setImage(image);

		return imageView;
	}

	/**
	 * Muestra la pantalla de inicio
	 * 
	 * @param event
	 */
	@FXML
	void inicioClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
	}

	/**
	 * Muestra la pantalla de peliculas
	 * 
	 * @param event
	 */
	@FXML
	void peliculasClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_PELICULA_FXML, "Pelicula");
	}

	/**
	 * Muestra la pantalla de series
	 * 
	 * @param event
	 */
	@FXML
	void seriesClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_SERIES_FXML, "Series");
	}

	/**
	 * Muestra la pantalla de buscador
	 * 
	 * @param event
	 */
	@FXML
	void buscadorClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_BUSCADOR_FXML, "Buscador");
	}

	/**
	 * Muestra la pantalla de perfil del usuario
	 * 
	 * @param event
	 */
	@FXML
	void perfilClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_USUARIO_FXML, "Perfil");
	}

	/**
	 * Establece el valor de stage
	 */
	public void setStage() {
		stage = (Stage) imagenLogoCabecera.getScene().getWindow();
	}

	
	  /**
	   * Método para mostrar un mensaje en un HBox.
	   * @param hbox El HBox en el que se mostrará el mensaje.
	   * @param mensaje El mensaje a mostrar.
	   */
	  private void mostrarMensajeEnHBox(HBox hbox, String mensaje) {
	      hbox.getChildren().clear(); // Limpiar cualquier contenido previo
	      Label mensajeLabel = new Label(mensaje); // Crear un Label con el mensaje
	      mensajeLabel.setFont(new Font(16));
	      hbox.getChildren().add(mensajeLabel); // Agregar el Label al HBox
	  }


}
