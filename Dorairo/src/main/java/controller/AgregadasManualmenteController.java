package controller;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.google.gson.Gson;

import conexion.HibernateUtil;
import constants.Constants;
import dao.ActoresDaoImpl;
import dao.CompañiaDaoImpl;
import dao.DirectoresDaoImpl;
import dao.GeneroDaoImpl;
import dao.LocalizacionDaoImpl;
import dao.PeliculaDaoImpl;
import dao.SeriesDaoImpl;
import dao.UsuarioPeliculaDaoImpl;
import dao.UsuarioSerieDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Actores;
import models.Compañia;
import models.Directores;
import models.Genero;
import models.Localizacion;
import models.Pelicula;
import models.RespuestaApiPersona;
import models.Series;
import models.UsuarioPelicula;
import models.UsuarioPeliculaID;
import models.UsuarioSerie;
import models.UsuarioSerieID;
import models.RespuestaApiCompany;
import models.RespuestaApiGenero;
import models.PersonaApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utilities.GestorVentanas;
import utilities.Utils;

/**
 * Clase con las funciones del Agregado Manual
 * 
 * @author JairoAB
 *
 */
public class AgregadasManualmenteController {

	@FXML
	private ChoiceBox<String> choiceTipo;

	@FXML
	private ImageView imagenLogoCabecera;

	@FXML
	private ImageView imagenCartel;

	@FXML
	private TextField txtActores;

	@FXML
	private TextArea txtComentarios;

	@FXML
	private TextField txtCompañia;

	@FXML
	private TextArea txtDescripcion;

	@FXML
	private TextField txtDirectores;

	@FXML
	private TextField txtEpisodios;

	@FXML
	private TextField txtEstreno;

	@FXML
	private TextField txtGenero;

	@FXML
	private TextField txtGuardado;

	@FXML
	private TextField txtValoracionPersonal;

	@FXML
	private TextField txtTemporadas;

	@FXML
	private TextField txtTitulo;

	@FXML
	private TextField txtValoracionGlobal;

	/** Scene de la ventana de Agregado Manual */
	private Scene scene;

	/** Stage de la ventana de Agregado Manual */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	/** Indica el tipo del que se trata */
	private String tipo = "";

	/** Indica si se trata de una serie (true) o una pelicula (false) */
	private boolean isSerie = false;

	/** Conexion con la base de datos */
	private Session session;

	/** URL del poster añadido */
	private String poster = null;

	@FXML
	private ImageView lupa;

	/** Cliente para la api */
	private OkHttpClient client;

	/** API KEY */
	public static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjc0NTA5ZjRiZDBlODJlMTFlYzA2YWM1MDRhMGRlMCIsInN1YiI6IjY1Mzc3ZmRmZjQ5NWVlMDBmZjY1YTEyOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ehIu08LoiMRTccPoD4AfADXOpQPlqNAKUMvGgwY3XU8";

	@FXML
	void initialize() {

		// Configuración del cliente HTTP (OkHttpClient)
		client = new OkHttpClient();

		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagen = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagen);

		Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
		lupa.setImage(imagenLupa);
		// Establece la imagen de subir foto
		imagen = new Image(getClass().getResourceAsStream(Constants.URL_FOTO_SUBIR_FOTO));
		imagenCartel.setImage(imagen);
		// Establece las opciones de tipo serie o pelicula
		choiceTipo.getItems().addAll("Pelicula", "Serie");

		// Recogemos la sesion
		session = HibernateUtil.openSession();
	}

	@FXML
	void inicioClicked(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_INICIO_FXML, "Inicio");
	}

	@FXML
	void peliculasClicked(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_PELICULA_FXML, "Pelicula");
	}

	@FXML
	void seriesClicked(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_SERIES_FXML, "Series");
	}

	@FXML
	void buscadorClicked(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_BUSCADOR_FXML, "Buscador");
	}

	@FXML
	void perfilClicked(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_USUARIO_FXML, "Perfil");
	}

	/**
	 * Busca una foto en los archivos y la sube
	 * 
	 * @param event
	 */
	@FXML
	void subirFoto(MouseEvent event) {
		setSceneAndStage();
		// Coge la url de la nueva foto
		poster = Utils.buscarFotoArchivos(stage);
		// Muestra la imagen
		Image image = new Image("file:" + poster);
		imagenCartel.setImage(image);
	}

	/**
	 * Comprueba si se ha seleccionado el tipo de Serie para activar las casillas de
	 * temporadas y episodios
	 */
	@FXML
	void compruebaTipo() {
		choiceTipo.setOnAction(event -> {
			tipo = choiceTipo.getValue();
			if (tipo != null) {
				isSerie = tipo.equalsIgnoreCase("Serie");
				if (!isSerie) {
					// Si no es una serie quita el texto que tuviera
					txtEpisodios.setText("");
					txtTemporadas.setText("");
				}
				txtEpisodios.setDisable(!isSerie);
				txtTemporadas.setDisable(!isSerie);
			}
		});
	}

	/**
	 * Gestiona la creacion de la pelicula o serie
	 * 
	 * @param event
	 */
	@FXML
	void guardarPressed(ActionEvent event) {
		// Comprueba que haya seleccionado el tipo
		if (tipo.isBlank()) {
			// Muestra alerta de error
			Utils.mostrarAlerta("No se ha seleccionado ningún tipo.", Constants.ERROR_TYPE);
		} else {
			// Comprueba si es una pelicula o serie y la crea
			if (isSerie) {
				crearSerie();
			} else {
				crearPelicula();
			}
		}
	}

	/**
	 * Realiza la creación de una película
	 */
	private void crearPelicula() {
		try {
			// Comprueba si todos los campos se han rellenado
			if (isCompleto()) {
				Compañia company = searchCompany();
				double vote = getVote(txtValoracionGlobal.getText().strip());
				getVote(txtValoracionPersonal.getText());
				List<Actores> listActores = getListaActores();
				List<Directores> listDirectores = getListaDirectores();
				List<Genero> listGenero = getListaGenero();
				comprobarFecha();
				Pelicula pelicula = new Pelicula(Utils.generaMovieId(), txtTitulo.getText(), txtEstreno.getText(), company,
						txtDescripcion.getText(), poster, vote, listActores, listDirectores, listGenero);
				// Si acepta, se guarda la peli
				if (Utils.confirmacion()) {
					// Guarda todos los datos en la bbdd
					guardarCompany(company);
					guardarPelicula(pelicula);
					guardarDatosPersonalesPelicula(pelicula);
					Utils.mostrarAlerta("Pelicula guardada correctamente.", Constants.INFORMATION_TYPE);
					borrarDatos();
				}
			} else {
				// Muestra alerta de error
				Utils.mostrarAlerta(
						"Falta algún campo por rellenar.\n(No obligatorios: valoración personal, comentarios y guardado en...)",
						Constants.ERROR_TYPE);
			}
		} catch (Exception e) {
			HibernateUtil.rollbackCambios();
			e.printStackTrace();
			Utils.mostrarAlerta("Error en la creacion del elemento.\nMensaje original: " + e.getMessage(),
					Constants.ERROR_TYPE);
		}
	}

	/**
	 * Guarda la pelicula
	 * 
	 * @param pelicula Pelicula a buscar
	 */
	public void guardarPelicula(Pelicula pelicula) {
		// Comprueba si ya existe, para no guardarla de nuevo
		PeliculaDaoImpl peliDao = new PeliculaDaoImpl(session);
		if (peliDao.searchById(pelicula.getId()) == null) {
			peliDao.update(pelicula);
		}
	}

	/**
	 * Guarda la serie
	 * 
	 * @param serie Serie a buscar
	 */
	public void guardarSerie(Series serie) {
		SeriesDaoImpl serieDao = new SeriesDaoImpl(session);
		if (serieDao.searchById(serie.getId()) == null) {
			serieDao.update(serie);
		}
	}

	/**
	 * Guarda la compañia
	 * 
	 * @param company Compañia a guardar
	 */
	public void guardarCompany(Compañia company) {
		// Comprueba si ya existe, para no guardarla de nuevo
		CompañiaDaoImpl compDao = new CompañiaDaoImpl(session);
		if (compDao.searchById(company.getId()) == null) {
			compDao.update(company);
		}
	}

	/**
	 * Comprueba que la fecha escrita sea válida
	 */
	private void comprobarFecha() {
		if (!txtEstreno.getText().matches("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01])")) {
			throw new DateTimeException("Error en el formato de la fecha");
		}
	}

	/**
	 * Guarda los datos personales del usuario sobre la pelicula
	 * 
	 * @param pelicula
	 */
	private void guardarDatosPersonalesPelicula(Pelicula pelicula) {
		UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
		Localizacion localizacion = searchLocalizacion();
		UsuarioPelicula up = new UsuarioPelicula(new UsuarioPeliculaID(UsuarioController.getUsuarioRegistrado(), pelicula),
				(getVote(txtValoracionPersonal.getText())), new Date(), txtComentarios.getText(), localizacion, true, false);
		upDao.update(up);
	}

	/**
	 * Busca la localizacion en la bbdd, si no existe, se crea una nueva
	 * 
	 * @return Localizacion
	 */
	private Localizacion searchLocalizacion() {
		LocalizacionDaoImpl lDao = new LocalizacionDaoImpl(session);
		Localizacion lugar = lDao.searchByName(txtGuardado.getText());
		if (lugar == null && !txtGuardado.getText().isBlank()) {
			lugar = new Localizacion(txtGuardado.getText());
			lDao.update(lugar);
		} else if (txtGuardado.getText().isBlank()) {
			return null;
		}
		return lugar;
	}

	/**
	 * Guarda la lista de generos en la bbdd
	 * 
	 * @param listGenero Lista a guardar
	 */
	private void guardarGenero(Genero g) {
		// Comprueba si ya existe, para no guardarlo de nuevo
		GeneroDaoImpl genDao = new GeneroDaoImpl(session);
		if (genDao.searchById(g.getId()) == null) {
			genDao.update(g);
		}
	}

	/**
	 * Guarda la lista de directores en la bbdd listDirectores
	 * 
	 * @param d Lista a guardar
	 */
	private void guardarDirector(Directores d) {
		// Comprueba si ya existe, para no guardarlo de nuevo
		DirectoresDaoImpl dirDao = new DirectoresDaoImpl(session);
		if (dirDao.searchById(d.getId()) == null) {
			dirDao.update(d);
		}
	}

	/**
	 * Guarda la lista de actores en la bbdd
	 * 
	 * @param listActores Lista a guardar
	 */
	private void guardarActor(Actores a) {
		// Comprueba si ya existe, para no guardarlo de nuevo
		ActoresDaoImpl actDao = new ActoresDaoImpl(session);
		if (actDao.searchById(a.getId()) == null) {
			actDao.update(a);
		}
	}

	/**
	 * Devuelve una lista de Generos
	 * 
	 * @return Lista de Generos
	 * @throws IOException
	 */
	private List<Genero> getListaGenero() throws IOException {
		// Paso a array
		String[] generosArr = txtGenero.getText().split(",");
		GeneroDaoImpl generoDaoImpl = new GeneroDaoImpl(session);
		// Lista de generos
		List<Genero> listGenero = new ArrayList<>();
		for (String genero : generosArr) {
			List<Genero> genList = generoDaoImpl.searchByName(genero.strip());

			// Comprueba si está en la bbdd
			if (!genList.isEmpty()) {
				listGenero.add(genList.get(0));
			} else {
				// Construir la solicitud para la API
				Request request;
				if (isSerie) {
					request = new Request.Builder().url("https://api.themoviedb.org/3/genre/tv/list?language=es").get()
							.addHeader("accept", "application/json").addHeader("Authorization", "Bearer " + API_KEY).build();

				} else {
					request = new Request.Builder().url("https://api.themoviedb.org/3/genre/movie/list?language=es").get()
							.addHeader("accept", "application/json").addHeader("Authorization", "Bearer " + API_KEY).build();
				}

				// Ejecuta la solicitud y obtiene la respuesta
				Response response;
				response = client.newCall(request).execute();

				// Obtiene el cuerpo de la respuesta como cadena
				String responseBody = response.body().string();

				// Utiliza Gson para convertir la respuesta JSON a objetos Java
				Gson gson = new Gson();
				RespuestaApiGenero respApi = gson.fromJson(responseBody, RespuestaApiGenero.class);

				Genero g = searchGenero(respApi, genero.strip());
				// Si se ha encontrado, se añade a la lista
				if (g != null) {
					listGenero.add(g);
				} else {
					// Se crea uno nuevo y se añade
					g = new Genero(Utils.generaGeneroID(), genero.strip());
					listGenero.add(g);

				}
				guardarGenero(g);
			}
		}
		return listGenero;
	}

	/**
	 * Busca el genero en la respuesta de la api
	 * 
	 * @param respApi
	 * @param name
	 * @return Genero encontrado
	 */
	private Genero searchGenero(RespuestaApiGenero respApi, String name) {
		// Busca el genero
		if (respApi != null && respApi.getGeneros() != null) {
			for (Genero genre : respApi.getGeneros()) {
				// Si coinciden los nombres, se devuelve
				if (name.equalsIgnoreCase(genre.getName())) {
					return genre;
				}
			}
		}
		return null;
	}

	/**
	 * Devuelve una lista de Directores
	 * 
	 * @return Lista de directores
	 * @throws IOException
	 */
	private List<Directores> getListaDirectores() throws IOException {
		// Paso a array
		String[] directoresArr = txtDirectores.getText().split(",");
		DirectoresDaoImpl directoresDaoImpl = new DirectoresDaoImpl(session);

		List<Directores> listDirectores = new ArrayList<>();
		for (String director : directoresArr) {
			List<Directores> dirList = directoresDaoImpl.searchByName(director.strip());

			// Comprueba si está en la bbdd
			if (!dirList.isEmpty()) {
				listDirectores.add(dirList.get(0));
			} else {
				// Construir la solicitud para la API
				Request request = new Request.Builder()
						.url("https://api.themoviedb.org/3/search/person?query=" + director
								+ "&include_adult=false&language=es-ES&page=1")
						.get().addHeader("accept", "application/json").addHeader("Authorization", "Bearer " + API_KEY).build();

				// Ejecutar la solicitud y obtener la respuesta
				Response response;
				response = client.newCall(request).execute();

				// Obtener el cuerpo de la respuesta como cadena
				String responseBody = response.body().string();

				// Utilizar Gson para convertir la respuesta JSON a objetos Java
				Gson gson = new Gson();
				RespuestaApiPersona respApi = gson.fromJson(responseBody, RespuestaApiPersona.class);

				// Busca la persona
				PersonaApi persona = searchPersona(respApi, "Directing");
				Directores d;
				if (persona != null) {
					d = new Directores(persona.getId(), persona.getName());
					listDirectores.add(d);
				} else {
					// Crea un nuevo actor y añade a la lista
					d = new Directores(Utils.generaDirectorId(), director.strip());
					listDirectores.add(d);
				}
				guardarDirector(d);
			}
		}
		return listDirectores;
	}

	/**
	 * Devuelve una lista de Actores
	 * 
	 * @return Lista de Actores
	 * @throws IOException
	 */
	private List<Actores> getListaActores() throws IOException {
		// Paso a array
		String[] actoresArr = txtActores.getText().split(",");
		ActoresDaoImpl actoresDaoImpl = new ActoresDaoImpl(session);

		List<Actores> listActores = new ArrayList<>();
		for (String actor : actoresArr) {
			List<Actores> actList = actoresDaoImpl.searchByName(actor.strip());

			// Comprueba si está en la bbdd
			if (!actList.isEmpty()) {
				listActores.add(actList.get(0));
			} else {
				// Construir la solicitud para la API
				Request request = new Request.Builder()
						.url("https://api.themoviedb.org/3/search/person?query=" + actor
								+ "&include_adult=false&language=es-ES&page=1")
						.get().addHeader("accept", "application/json").addHeader("Authorization", "Bearer " + API_KEY).build();

				// Ejecutar la solicitud y obtener la respuesta
				Response response;
				response = client.newCall(request).execute();

				// Obtener el cuerpo de la respuesta como cadena
				String responseBody = response.body().string();

				// Utilizar Gson para convertir la respuesta JSON a objetos Java
				Gson gson = new Gson();
				RespuestaApiPersona respApi = gson.fromJson(responseBody, RespuestaApiPersona.class);

				// Busca la persona
				PersonaApi persona = searchPersona(respApi, "Acting");
				Actores a;
				if (persona != null) {
					a = new Actores(persona.getId(), persona.getName());
					listActores.add(a);
				} else {
					// Crea un nuevo actor y añade a la lista
					a = new Actores(Utils.generaActorId(), actor.strip());
					listActores.add(a);
				}
				guardarActor(a);
			}
		}
		return listActores;
	}

	/**
	 * Busca la persona en la respuesta de la api
	 * 
	 * @param respApi
	 * @param departamento Departamento de la persona
	 * @return Persona encontrada
	 */
	private PersonaApi searchPersona(RespuestaApiPersona respApi, String departamento) {
		// Verifica si hay resultados en la respuesta
		if (respApi != null && respApi.getResults() != null && !respApi.getResults().isEmpty()) {
			// Recorre la lista de resultados
			for (PersonaApi respPers : respApi.getResults()) {
				// Comprueba si es del departamento correcto
				if (respPers.getKnownForDepartment() != null
						&& respPers.getKnownForDepartment().equalsIgnoreCase(departamento)) {
					return respPers;
				}
			}
		}
		return null;
	}

	/**
	 * Devuelve el voto en formato decimal
	 * 
	 * @param voto Voto a devolver
	 * @return Double voto
	 * @throws NumberFormatException
	 */
	public Double getVote(String voto) throws NumberFormatException {
		// Reemplaza coma por punto
		String voteString = voto.replace(',', '.');

		// Intenta parsear a double
		if (!voto.isEmpty()) {
			double parsedVote;
			try {
				parsedVote = Double.parseDouble(voteString);
			} catch (NullPointerException | NumberFormatException e) {
				throw new NumberFormatException(
						"Error con el numero decimal: '" + voto + "' Debe ser mayor de 0 y menor de 10.");
			}

			// Verificar condiciones de decimales y dígitos en la parte entera
			if (Math.abs(parsedVote) <= 10 && Math.abs(parsedVote) >= 0) {
				// Redondear a 3 decimales
				return Math.round(parsedVote * 1000.0) / 1000.0;
			} else {
				throw new NumberFormatException(
						"Error con el numero decimal: '" + voto + "' Debe ser mayor de 0 y menor de 10.");
			}
		}
		return 0.0;

	}

	/**
	 * Busca la compañia por el nombre
	 * 
	 * @return Compañia
	 * @throws IOException
	 */
	private Compañia searchCompany() throws IOException {
		CompañiaDaoImpl compañiaDaoImpl = new CompañiaDaoImpl(session);
		List<Compañia> compList = compañiaDaoImpl.searchByName(txtCompañia.getText());

		// Comprueba si la compañia está registrada en la bbdd
		if (!compList.isEmpty()) {
			return compList.get(0);
		} else {
			// Construir la solicitud para la API de compañias
			Request request = new Request.Builder()
					.url("https://api.themoviedb.org/3/search/company?query=" + txtCompañia.getText() + "&page=1").get()
					.addHeader("accept", "application/json").addHeader("Authorization", "Bearer " + API_KEY).build();

			// Ejecutar la solicitud y obtener la respuesta
			Response response;
			response = client.newCall(request).execute();

			// Obtener el cuerpo de la respuesta como cadena
			String responseBody = response.body().string();

			// Utilizar Gson para convertir la respuesta JSON a objetos Java
			Gson gson = new Gson();
			RespuestaApiCompany respApi = gson.fromJson(responseBody, RespuestaApiCompany.class);

			// Verificar si hay resultados en la respuesta
			if (respApi != null && respApi.getResults() != null && !respApi.getResults().isEmpty()) {
				return respApi.getResults().get(0);
			} else {
				// Crea una nueva compañia y la suba a la bbdd
				Compañia c = new Compañia(Utils.generaCompanyId(), txtCompañia.getText());
				return c;
			}
		}
	}

	/**
	 * Gestiona la creacion de una serie nueva
	 */
	private void crearSerie() {
		try {
			// Comprueba si todos los campos se han rellenado
			if (isCompleto() && !txtEpisodios.getText().isBlank() && !txtTemporadas.getText().isBlank()) {
				Compañia company = searchCompany();
				double vote = getVote(txtValoracionGlobal.getText());
				getVote(txtValoracionPersonal.getText());
				int numEpisodios = Integer.parseInt(txtEpisodios.getText());
				int numTemporadas = Integer.parseInt(txtTemporadas.getText());
				List<Actores> listActores = getListaActores();
				List<Directores> listDirectores = getListaDirectores();
				List<Genero> listGenero = getListaGenero();
				comprobarFecha();
				Series serie = new Series(Utils.generaSerieId(), txtTitulo.getText(), txtEstreno.getText(), company,
						txtDescripcion.getText(), poster, vote, numEpisodios, numTemporadas, listActores, listDirectores,
						listGenero);
				// Si acepta, se guarda la serie
				if (Utils.confirmacion()) {
					// Guarda todos los datos en la bbdd
					guardarCompany(company);
					guardarSerie(serie);
					guardarDatosPersonalesSerie(serie);
					Utils.mostrarAlerta("Serie guardada correctamente.", Constants.INFORMATION_TYPE);
					borrarDatos();
				}
			} else {
				// Muestra alerta de error
				Utils.mostrarAlerta(
						"Falta algún campo por rellenar.\n(No obligatorios: valoración personal, comentarios y guardado en...)",
						Constants.ERROR_TYPE);
			}
		} catch (Exception e) {
			Utils.mostrarAlerta("Error en la creacion del elemento.\nMensaje original: " + e.getMessage(),
					Constants.ERROR_TYPE);
		}
	}

	/**
	 * Quita todos los datos de la pantalla
	 */
	private void borrarDatos() {
		choiceTipo.setValue("");
		txtTitulo.setText("");
		txtCompañia.setText("");
		txtActores.setText("");
		txtComentarios.setText("");
		txtDescripcion.setText("");
		txtDirectores.setText("");
		txtEpisodios.setText("");
		txtEstreno.setText("");
		txtGenero.setText("");
		txtGuardado.setText("");
		txtTemporadas.setText("");
		txtValoracionGlobal.setText("");
		txtValoracionPersonal.setText("");
		poster = "";
		Image imagen = new Image(getClass().getResourceAsStream(Constants.URL_FOTO_SUBIR_FOTO));
		imagenCartel.setImage(imagen);
	}

	/**
	 * Guarda los datos personales del usuario sobre la pelicula
	 * 
	 * @param serie
	 */
	private void guardarDatosPersonalesSerie(Series serie) {
		UsuarioSerieDaoImpl upDao = new UsuarioSerieDaoImpl(session);
		Localizacion localizacion = searchLocalizacion();
		UsuarioSerie us = new UsuarioSerie(new UsuarioSerieID(UsuarioController.getUsuarioRegistrado(), serie),
				getVote(txtValoracionPersonal.getText()), new Date(), txtComentarios.getText(), localizacion, false, true);
		upDao.update(us);
	}

	/**
	 * Indica si se han rellanado todos los campos (excepto número de episodios y
	 * temporadas
	 * 
	 * @return True: todos rellenos. False: alguno sin rellenar
	 */
	private boolean isCompleto() {
		return !txtTitulo.getText().isBlank() && !txtCompañia.getText().isBlank() && !txtEstreno.getText().isBlank()
				&& !txtValoracionGlobal.getText().isBlank() && !txtGenero.getText().isBlank() && !txtActores.getText().isBlank()
				&& !txtDirectores.getText().isBlank() && !txtDescripcion.getText().isBlank() && poster != null
				&& !poster.isBlank();
	}

	/**
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = imagenLogoCabecera.getScene();
		stage = (Stage) scene.getWindow();
	}

}
