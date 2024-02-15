package controller;

import org.hibernate.Session;

import conexion.HibernateUtil;
import constants.Constants;
import dao.UsuarioDaoImpl;
import dao.UsuarioPeliculaDaoImpl;
import dao.UsuarioSerieDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Usuario;
import utilities.GestorVentanas;
import utilities.Utils;

/**
 * Clase con las funciones del Usuario
 * 
 * @author JairoAB
 *
 */
public class UsuarioController {

	@FXML
	private ImageView imagenFondoPerfil;

	@FXML
	private ImageView imagenLogoCabecera;

	@FXML
	private ImageView imagenPerfil;

	@FXML
	private Label lblCorreo;

	@FXML
	private Label lblMiembro;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblNumeroPeliculas;

	@FXML
	private Label lblNumeroSeries;

	@FXML
	private Label lblUser;

	/** Scene de la ventana de Perfil */
	private Scene scene;

	/** Stage de la ventana de Perfil */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	@FXML
	private ImageView lupa;

	/** Perfil registrado */
	private static Usuario usuarioRegistrado;

	/** Instancia del dao de usuario */
	private static UsuarioDaoImpl usuarioDaoImpl;

	/** Conexion con la base de datos */
	private Session session;

	@FXML
	void initialize() {
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagen = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagen);
		// Establece la imagen de fondo
		imagen = new Image(getClass().getResourceAsStream(Constants.URL_FOTO_FONDO_PERFIL));
		imagenFondoPerfil.setImage(imagen);

	    Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
	    lupa.setImage(imagenLupa);
		session = HibernateUtil.openSession();

		// Abre la session y crea el dao de usuario
		usuarioDaoImpl = new UsuarioDaoImpl(session);

		// Cambiar los datos del perfil
		setDatosPerfil();
	}

	/**
	 * Modifica los elementos de la pantalla para que muestren los datos del perfil
	 * registrado
	 */
	private void setDatosPerfil() {
		lblNombre.setText(usuarioRegistrado.getNombre().toUpperCase());
		lblUser.setText(usuarioRegistrado.getUser());
		lblCorreo.setText(usuarioRegistrado.getCorreo());
		lblMiembro.setText(usuarioRegistrado.getFechaMiembroString());
		UsuarioPeliculaDaoImpl upDao = new UsuarioPeliculaDaoImpl(session);
		lblNumeroPeliculas.setText(upDao.searchNumeroPeliculas(usuarioRegistrado.getUser()));
		UsuarioSerieDaoImpl usDao = new UsuarioSerieDaoImpl(session);
		lblNumeroSeries.setText(usDao.searchNumeroSeries(usuarioRegistrado.getUser()));
		// Establece la imagen del perfil
		Image imagen;
		if (usuarioRegistrado.getImagenPerfil() == null || usuarioRegistrado.getImagenPerfil().isBlank()) {
			// si no tiene una asignada se pone la por defecto
			imagen = new Image(getClass().getResourceAsStream(Constants.URL_FOTO_PERFIL_DEFAULT), 190, 190, false, true);
		} else {
			imagen = new Image("file:" + usuarioRegistrado.getImagenPerfil());
		}
		imagenPerfil.setImage(imagen);
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
	 * Cierra la sesión del usuario llevándolo de vuelta al login.
	 * 
	 * @param event
	 */
	@FXML
	void cerrarSesion(MouseEvent event) {
		usuarioRegistrado = null;
		muestraLogin();
	}

	/**
	 * Muestra la pantalla de login
	 */
	public void muestraLogin() {
		setSceneAndStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_LOGIN_FXML, "Dorairo");
	}

	@FXML
	void cambiarFotoPerfil(MouseEvent event) {
		setSceneAndStage();
		// Coge la url de la nueva foto
		String fotoUrl = Utils.buscarFotoArchivos(stage);
		// La establece para el usuario registrado
		usuarioRegistrado.setImagenPerfil(fotoUrl);
		// Actualiza el perfil
		usuarioDaoImpl.insert(usuarioRegistrado);
		// Recarga los datos
		setDatosPerfil();
	}

	@FXML
	void eliminarDatos(MouseEvent event) {
		if (Utils.confirmacion()) {
			usuarioDaoImpl.deleteDataUser(usuarioRegistrado.getUser());
			setDatosPerfil();
		}
	}

	@FXML
	void modificarPassword(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraCambiarPasswordNombre(scene, "Cambiar Contraseña");
	}

	@FXML
	void modificarNombre(MouseEvent event) {
		setSceneAndStage();
		gestorVentanas.muestraCambiarPasswordNombre(scene, "Cambiar Nombre");
		setDatosPerfil();
	}

	@FXML
	void eliminarCuenta(MouseEvent event) {
		if (Utils.confirmacion()) {
			
			usuarioDaoImpl.delete(usuarioRegistrado);
			muestraLogin();
		}
	}

	/**
	 * Asigna los valores correspondientes del stage y el scene
	 * 
	 */
	public void setSceneAndStage() {
		scene = imagenLogoCabecera.getScene();
		stage = (Stage) scene.getWindow();
	}

	/**
	 * Establece el usuario registrado
	 * 
	 * @param usuario
	 */
	public static void setUsuarioRegistrado(Usuario usuario) {
		usuarioRegistrado = usuario;
	}

	/**
	 * @return the usuarioRegistrado
	 */
	public static Usuario getUsuarioRegistrado() {
		return usuarioRegistrado;
	}

	/**
	 * Cambia la clave del perfil registrado
	 * 
	 * @param clave Nueva clave
	 */
	public static void cambiarClave(String clave) {
		usuarioRegistrado.setClave(clave);
		usuarioDaoImpl.insert(usuarioRegistrado);
	}

	/**
	 * Cambia el nombre del perfil registrado
	 * 
	 * @param nombre Nuevo nombre
	 */
	public static void cambiarNombre(String nombre) {
		usuarioRegistrado.setNombre(nombre);
		usuarioDaoImpl.insert(usuarioRegistrado);
	}

}
