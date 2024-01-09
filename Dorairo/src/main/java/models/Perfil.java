package models;

import java.util.Date;

/**
 * Clase que gestiona el perfil del usuario
 * 
 */
public class Perfil {

	private String usuario;
	private String nombre;
	private String correo;
	private String contrasena;
	private String fotoPerfil;
	private Date fecha;

	/**
	 * Constructor para generar un nuevo perfil de usuario
	 * 
	 * @param usuario
	 * @param nombre
	 * @param correo
	 * @param contrasena
	 * @param fecha
	 */
	public Perfil(String usuario, String nombre, String correo, String contrasena, Date fecha) {
		super();
		this.usuario = usuario;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
