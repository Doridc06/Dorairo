package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.hibernate.Session;
import com.google.gson.Gson;
import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioPeliculaDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Pelicula;
import models.RespuestaApi;
import models.UsuarioPelicula;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;
import utilities.Utils;

/**
 * Clase Película, donde esta toda la información de las películas
 * 
 * @author Doriana dc
 * 
 */
public class PeliculaController {

	/**
	 * clave de la api
	 * 
	 */
	public static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

	/**
	 * CheckBox para el género Acción.
	 */
	@FXML
	private CheckBox generoAccion;

	/**
	 * CheckBox para el género Animación.
	 */
	@FXML
	private CheckBox generoAnimacion;

	/**
	 * CheckBox para el género Aventura.
	 */
	@FXML
	private CheckBox generoAventura;

	/**
	 * CheckBox para el género Ciencia Ficción.
	 */
	@FXML
	private CheckBox generoCienciaFiccion;

	/**
	 * CheckBox para el género Comedia.
	 */
	@FXML
	private CheckBox generoComedia;

	/**
	 * CheckBox para el género Drama.
	 */
	@FXML
	private CheckBox generoDrama;

	/**
	 * CheckBox para el género Misterio.
	 */
	@FXML
	private CheckBox generoMisterio;

	/**
	 * CheckBox para el género Musical.
	 */
	@FXML
	private CheckBox generoMusical;

	/**
	 * CheckBox para el género Suspenso.
	 */
	@FXML
	private CheckBox generoSuspenso;

	/**
	 * CheckBox para el género Terror.
	 */
	@FXML
	private CheckBox generoTerror;

	/**
	 * ImageView para el logo en la cabecera.
	 */
	@FXML
	private ImageView imagenLogoCabecera;

	/**
	 * HBox para las películas de estreno.
	 */
	@FXML
	private HBox peliculasEstrenos;

	/**
	 * HBox para las películas populares.
	 */
	@FXML
	private HBox peliculasPopulares;

	/**
	 * Stage de la ventana de Inicio.
	 */
	private Stage stage;

	/**
	 * Instancia del gestor de ventanas.
	 */
	private GestorVentanas gestorVentanas;

	/**
	 * MenuItem para la opción Aleatoria.
	 */
	@FXML
	private MenuItem aleatoria;

	/**
	 * ImageView para el icono de la lupa.
	 */
	@FXML
	private ImageView lupa;

	/**
	 * ImageView para el cartel de la película.
	 */
	@FXML
	private ImageView cartel;

	/**
	 * HBox para las películas en "Mi Lista".
	 */
	@FXML
	private HBox miLista;

	/**
	 * HBox para las películas ya vistas.
	 */
	@FXML
	private HBox peliculasVistas;

	/**
	 * Sesión para la conexión con la base de datos.
	 */
	private Session session;

	/**
	 * Lista para almacenar todas las películas obtenidas de la API.
	 */
	static List<Pelicula> todasLasPeliculas = new ArrayList<>();

	/**
	 * Método para manejar el evento de clic en la cabecera para navegar a la página
	 * de inicio.
	 * 
	 * @param event El evento del mouse.
	 */
	@FXML
	void inicioClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
	}

	/**
	 * Método para manejar el evento de clic en la cabecera para navegar a la página
	 * de películas.
	 * 
	 * @param event El evento del mouse.
	 */
	@FXML
	void peliculasClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_PELICULA_FXML, "Pelicula");
	}

	/**
	 * Método para manejar el evento de clic en la cabecera para navegar a la página
	 * de series.
	 * 
	 * @param event El evento del mouse.
	 */
	@FXML
	void seriesClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_SERIES_FXML, "Series");
	}

	/**
	 * Método para manejar el evento de clic en la cabecera para navegar a la página
	 * del buscador.
	 * 
	 * @param event El evento del mouse.
	 */
	@FXML
	void buscadorClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_BUSCADOR_FXML, "Buscador");
	}

	/**
	 * Método para manejar el evento de clic en la cabecera para navegar a la página
	 * del perfil del usuario.
	 * 
	 * @param event El evento del mouse.
	 */
	@FXML
	void perfilClicked(MouseEvent event) {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_USUARIO_FXML, "Perfil");
	}

	/**
	 * Método para manejar el evento de clic en una imagen de película para ver los
	 * detalles.
	 * 
	 * @param clickedImageView El ImageView de la película clicada.
	 */
	void detallesClicked(ImageView clickedImageView) {
		String movieId = getMovieIdFromImageView(clickedImageView);
		abrirVentanaDetalles(movieId);
	}

	/**
	 * Método para abrir la ventana de detalles de una película.
	 * 
	 * @param movieId El ID de la película.
	 */
	private void abrirVentanaDetalles(String movieId) {
		setStage();
		gestorVentanas.muestraDetalles(stage, movieId, Constants.PELICULA);
	}

	/**
	 * Método de inicialización que se ejecuta después de que se carga el archivo
	 * FXML.
	 */
	@FXML
	void initialize() {
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagenLogo);

		Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
		lupa.setImage(imagenLupa);

		miLista.setSpacing(50);
		peliculasVistas.setSpacing(50);

		// Recogemos la sesion
		session = HibernateUtil.openSession();

		// Configuración de los eventos para los CheckBox de género
		generoAccion.setOnAction(event -> generoClicked("28"));
		generoAventura.setOnAction(event -> generoClicked("12"));
		generoComedia.setOnAction(event -> generoClicked("35"));
		generoTerror.setOnAction(event -> generoClicked("27"));
		generoSuspenso.setOnAction(event -> generoClicked("53"));
		generoDrama.setOnAction(event -> generoClicked("18"));
		generoMisterio.setOnAction(event -> generoClicked("9648"));
		generoMusical.setOnAction(event -> generoClicked("10402"));
		generoCienciaFiccion.setOnAction(event -> generoClicked("878"));
		generoAnimacion.setOnAction(event -> generoClicked("16"));

		aleatoria.setOnAction(event -> peliAleatoriaClicked());

		try {
			// Configuración del cliente HTTP (OkHttpClient)
			OkHttpClient client = new OkHttpClient();

			// Llamada a la API para películas populares
			handleMovieApiCall(client, "https://api.themoviedb.org/3/movie/popular?language=es-ES&page=1",
					peliculasPopulares);

			// Llamada a la API para próximas películas
			handleMovieApiCall(client, "https://api.themoviedb.org/3/movie/upcoming?language=es-ES&page=1",
					peliculasEstrenos);

			agregarImagenAMiLista();
			agregarImagenAYaVistas();

			// Configuración del evento para cada ImageView en peliculasEstrenos
			for (Node node : peliculasEstrenos.getChildren()) {
				if (node instanceof ImageView) {
					ImageView imageView = (ImageView) node;
					imageView.setOnMouseClicked(event -> detallesClicked(imageView));
				}
			}

			// Configuración del evento para cada ImageView en peliculasPopulares
			for (Node node : peliculasPopulares.getChildren()) {
				if (node instanceof ImageView) {
					ImageView imageView = (ImageView) node;
					imageView.setOnMouseClicked(event -> detallesClicked(imageView));
				}
			}

		} catch (IOException e) {
			// Manejar excepciones
			e.printStackTrace();
		}
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
		RespuestaApi respApi = gson.fromJson(responseBody, RespuestaApi.class);

		// Verificar si hay resultados en la respuesta
		if (respApi.getResults() != null && respApi.getResults().length > 0) {
			// Iterar sobre las películas y agregar imágenes al HBox
			int contador = 0;
			for (Pelicula datos : respApi.getResults()) {
				if (contador < 12) {
					ImageView imageView = null;
					if (datos.getPoster_path() != null) {
						imageView = getImageViewFromPelicula(datos);

						// Almacena el ID de la película en el userData del ImageView
						imageView.setUserData(String.valueOf(datos.getId()));
					}

					// Verificar si imageView no es nulo antes de agregarlo al HBox
					if (imageView != null) {
						targetHBox.getChildren().add(imageView);
					}
					todasLasPeliculas.add(datos); // Agrega la película a la lista de todas las películas

					contador++;
				} else {
					break;
				}
			}
		} else {
			// No hay resultados
		}
		targetHBox.setSpacing(50.0);
	}

	/**
	 * Crea y configura un ImageView para mostrar la imagen de una película.
	 * 
	 * @param pelicula La película de la que se va a mostrar la imagen.
	 * @return El ImageView configurado.
	 */
	private ImageView getImageViewFromPelicula(Pelicula pelicula) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(230.0);
		imageView.setFitWidth(290.0);
		imageView.setPreserveRatio(true);

		// Construir la URL del póster de la película
		String imageUrl = Constants.URL_API_IMAGE + pelicula.getPoster_path();

		// Configurar la imagen en el ImageView
		Image image = new Image(imageUrl);
		imageView.getStyleClass().add("imagenPelicula");
		imageView.getStyleClass().add(Constants.SOMBRA_STYLE_CLASS);
		imageView.setImage(image);

		// Configurar el evento de clic para llamar a detallesClicked
		imageView.setOnMouseClicked(event -> detallesClicked(imageView));
		return imageView;
	}

	/**
	 * Obtiene el ID de la película a partir de un ImageView.
	 * 
	 * @param imageView El ImageView del que se va a obtener el ID.
	 * @return El ID de la película.
	 */
	private String getMovieIdFromImageView(ImageView imageView) {
		// Obtén el ID de la película almacenado en el userData del ImageView
		Object userData = imageView.getUserData();

		if (userData instanceof String) {
			return (String) userData;
		} else {
			// Manejar la situación donde no hay un ID almacenado
			return "";
		}
	}

	/**
	 * Establece el valor del stage
	 */
	public void setStage() {
		stage = (Stage) imagenLogoCabecera.getScene().getWindow();
	}

	/**
	 * Maneja el evento de clic en un género y muestra las películas
	 * correspondientes.
	 * 
	 * @param generoId El ID del género seleccionado.
	 */
	void generoClicked(String generoId) {
		setStage();
		gestorVentanas.muestraBuscadorGenero(stage, Constants.PELICULA, generoId);
	}

	/**
	 * para la pelicula aleatoria
	 * 
	 */
	@FXML
	void peliAleatoriaClicked() {
		abrirVentanaPeliAleatoria();
	}

	/**
	 * Abre la ventana de detalles de una película aleatoria.
	 */
	private void abrirVentanaPeliAleatoria() {
		// Obtener una película aleatoria
		Pelicula peliculaAleatoria = obtenerPeliculaAleatoria();

		// Verificar si se encontró una película aleatoria
		if (peliculaAleatoria != null) {
			// Obtener el ID de la película aleatoria
			String movieId = String.valueOf(peliculaAleatoria.getId());

			// Abrir la ventana de detalles de la película aleatoria
			abrirVentanaDetalles(movieId);
		} else {
			Utils.mostrarAlerta("No se pudo obtener una película aleatoria", Constants.ERROR_TYPE);
		}
	}

	/**
	 * Obtiene una película aleatoria de la lista de todas las películas.
	 *
	 * @return La película aleatoria, o null si no hay películas disponibles.
	 */
	private Pelicula obtenerPeliculaAleatoria() {
		// Verificar si hay películas disponibles
		if (!todasLasPeliculas.isEmpty()) {
			// Obtener un índice aleatorio dentro del rango de la lista de películas
			int indiceAleatorio = new Random().nextInt(todasLasPeliculas.size());

			// Obtener y devolver la película aleatoria
			return todasLasPeliculas.get(indiceAleatorio);
		} else {
			return null;
		}
	}

	/**
	 * Método para mostrar un mensaje en un HBox.
	 * 
	 * @param hbox    El HBox en el que se mostrará el mensaje.
	 * @param mensaje El mensaje a mostrar.
	 */
	private void mostrarMensajeEnHBox(HBox hbox, String mensaje) {
		hbox.getChildren().clear(); // Limpiar cualquier contenido previo
		Label mensajeLabel = new Label(mensaje); // Crear un Label con el mensaje
		mensajeLabel.setFont(new Font(16));
		hbox.getChildren().add(mensajeLabel); // Agregar el Label al HBox
	}

	/**
	 * Agrega imágenes de películas a la sección "Mi Lista".
	 */
	public void agregarImagenAMiLista() {
		UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
		List<UsuarioPelicula> listaUsuarioPelicula = upDao
				.searchPeliculasMiLista(UsuarioController.getUsuarioRegistrado().getUser());

		// Verificar si la lista está vacía y mostrar el mensaje en caso afirmativo
		if (listaUsuarioPelicula.isEmpty()) {
			mostrarMensajeEnHBox(miLista, "No hay nada en mi lista");
			return;
		}

		for (UsuarioPelicula up : listaUsuarioPelicula) {
			// Obtener la imagen de la película
			ImageView imageView = getImageViewFromPelicula(up.getId().getPelicula());

			// Establecer el tamaño de la imagen si es necesario
			imageView.setFitWidth(200);
			imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
			// Almacena el ID de la película en el userData del ImageView
			imageView.setUserData(String.valueOf(up.getId().getPelicula().getId()));

			// Agregar la imagen al HBox de "Mi Lista"
			miLista.getChildren().add(imageView);

		}
	}

	/**
	 * Agrega imágenes de películas a la sección "Ya Vistas".
	 */
	public void agregarImagenAYaVistas() {
		UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);

		List<UsuarioPelicula> listaUsuarioPelicula = upDao
				.searchPeliculasYaVistas(UsuarioController.getUsuarioRegistrado().getUser());

		// Verificar si la lista está vacía y mostrar el mensaje en caso afirmativo
		if (listaUsuarioPelicula.isEmpty()) {
			mostrarMensajeEnHBox(peliculasVistas, "No hay nada en ya vistas");
			return;
		}

		for (UsuarioPelicula up : listaUsuarioPelicula) {
			// Obtener la imagen de la película
			ImageView imageView = getImageViewFromPelicula(up.getId().getPelicula());

			// Establecer el tamaño de la imagen si es necesario
			imageView.setFitWidth(200);
			imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen
			// Almacena el ID de la película en el userData del ImageView
			imageView.setUserData(String.valueOf(up.getId().getPelicula().getId()));

			// Agregar la imagen al HBox de "ya vistas"
			peliculasVistas.getChildren().add(imageView);
		}

	}

}
