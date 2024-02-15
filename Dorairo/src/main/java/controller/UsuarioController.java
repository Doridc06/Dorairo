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

	/** ImageView para fondo de la pantalla del perfil */
	@FXML
	private ImageView imagenFondoPerfil;

	/** ImageView para logo de cabecera */
	@FXML
	private ImageView imagenLogoCabecera;

	/** ImageView para imagen del perfil */
	@FXML
	private ImageView imagenPerfil;

	/** Label para el correo */
	@FXML
	private Label lblCorreo;

	/** Label para la fecha de membresía */
	@FXML
	private Label lblMiembro;

	/** Label para el nombre */
	@FXML
	private Label lblNombre;

	/** Label para el numero de pelis vistas */
	@FXML
	private Label lblNumeroPeliculas;

	/** Label para el numero de series vistas */
	@FXML
	private Label lblNumeroSeries;

	/** Label para el usuario */
	@FXML
	private Label lblUser;

	/** Scene de la ventana de Perfil */
	private Scene scene;

	/** Stage de la ventana de Perfil */
	private Stage stage;

	/** Instancia del gestor de ventanas **/
	private GestorVentanas gestorVentanas;

	/** ImageView para imagen de la lupa */
	@FXML
	private ImageView lupa;

	/** Perfil registrado */
	private static Usuario usuarioRegistrado;

	/** Instancia del dao de usuario */
	private static UsuarioDaoImpl usuarioDaoImpl;

	/** Conexion con la base de datos */
	private Session session;

	/**
	 * Método que se ejecuta al iniciar la clase
	 */
	@FXML
	void initialize() {
		session = HibernateUtil.openSession();
		// Inicializamos el Gestor de ventanas
		gestorVentanas = new GestorVentanas();
		// Establece la imagen del logo
		Image imagen = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_AMPLIADO));
		imagenLogoCabecera.setImage(imagen);
		// Establece la imagen de fondo
		imagen = new Image(getClass().getResourceAsStream(Constants.URL_FOTO_FONDO_PERFIL));
		imagenFondoPerfil.setImage(imagen);

		// Establece la imagen de la lupa
		Image imagenLupa = new Image(getClass().getResourceAsStream(Constants.URL_LUPA));
		lupa.setImage(imagenLupa);

		// Abre la session y crea el dao de usuario
		setUsuarioDaoImpl(new UsuarioDaoImpl(session));
		// Cambiar los datos del perfil
		setDatosPerfil();
	}

	/**
	 * Modifica los elementos de la pantalla para que muestren los datos del usuario
	 * registrado
	 */
	private void setDatosPerfil() {
		// Cambio todos los labels por los datos del usuario
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
		// Comprueba si tiene una imagen asignada
		if (usuarioRegistrado.getImagenPerfil() == null || usuarioRegistrado.getImagenPerfil().isBlank()) {
			// si no tiene una asignada se pone la por defecto
			imagen = new Image(getClass().getResourceAsStream(Constants.URL_FOTO_PERFIL_DEFAULT), 190, 190, false, true);
		} else {
			imagen = new Image("file:" + usuarioRegistrado.getImagenPerfil());
		}
		// Establece la imagen
		imagenPerfil.setImage(imagen);
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
	 * Cierra la sesión del usuario llevándolo de vuelta al login
	 * 
	 * @param event
	 */
	@FXML
	void cerrarSesion(MouseEvent event) {
		// Reestablece el usuario registrado
		setUsuarioRegistrado(null);
		muestraLogin();
	}

	/**
	 * Muestra la pantalla de login
	 */
	public void muestraLogin() {
		setStage();
		gestorVentanas.muestraVentana(stage, Constants.URL_LOGIN_FXML, "Dorairo");
	}

	/**
	 * Cambia la foto de perfil
	 * 
	 * @param event
	 */
	@FXML
	void cambiarFotoPerfil(MouseEvent event) {
		setStage();
		// Coge la url de la nueva foto
		String fotoUrl = Utils.buscarFotoArchivos(stage);
		// La establece para el usuario registrado
		usuarioRegistrado.setImagenPerfil(fotoUrl);
		// Actualiza el perfil
		usuarioDaoImpl.insert(usuarioRegistrado);
		// Recarga los datos
		setDatosPerfil();
	}

	/**
	 * Elimina los datos del usuario
	 * 
	 * @param event
	 */
	@FXML
	void eliminarDatos(MouseEvent event) {
		// Solicita confirmacion
		if (Utils.confirmacion()) {
			usuarioDaoImpl.deleteDataUser(usuarioRegistrado.getUser());
			setDatosPerfil();
		}
	}

	/**
	 * Muestra la ventana para cambiar la contraseña
	 * 
	 * @param event
	 */
	@FXML
	void modificarPassword(MouseEvent event) {
		setStage();
		gestorVentanas.muestraCambiarPasswordNombre(scene, "Cambiar Contraseña");
	}

	/**
	 * Muestra la ventana para cambiar el nombre
	 * 
	 * @param event
	 */
	@FXML
	void modificarNombre(MouseEvent event) {
		setStage();
		gestorVentanas.muestraCambiarPasswordNombre(scene, "Cambiar Nombre");
		setDatosPerfil();
	}

	/**
	 * Elimina la cuenta de la base de datos
	 * 
	 * @param event
	 */
	@FXML
	void eliminarCuenta(MouseEvent event) {
		// Solicita confirmacion
		if (Utils.confirmacion()) {
			// Recoge el verdadero objeto del usuario en la base de datos y lo elimina
			setUsuarioRegistrado(usuarioDaoImpl.searchByUsuario(usuarioRegistrado.getUser()));
			usuarioDaoImpl.delete(usuarioRegistrado);
			muestraLogin();
		}
	}

	/**
	 * Establece el valor del stage
	 */
	public void setStage() {
		stage = (Stage) imagenLogoCabecera.getScene().getWindow();
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
	 * Devuelve el usuario registrado
	 * 
	 * @return the usuarioRegistrado
	 */
	public static Usuario getUsuarioRegistrado() {
		return usuarioRegistrado;
	}

	/**
	 * @return the usuarioDaoImpl
	 */
	public static UsuarioDaoImpl getUsuarioDaoImpl() {
		return usuarioDaoImpl;
	}

	/**
	 * @param usuarioDaoImpl the usuarioDaoImpl to set
	 */
	public static void setUsuarioDaoImpl(UsuarioDaoImpl usuarioDaoImpl) {
		UsuarioController.usuarioDaoImpl = usuarioDaoImpl;
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
