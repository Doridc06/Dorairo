package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "usuario", length = 20)
	private String user;

	@Column(name = "nombre", length = 20)
	private String nombre;

	@Column(name = "correo", length = 100)
	private String correo;

	@Column(name = "imagen_de_perfil")
	private String imagenPerfil;

	@Column(name = "clave", length = 20)
	private String clave;

	@Column(name = "fecha_miembro")
	private Date fechaMiembro;

	@OneToMany(mappedBy = "id.usuario", cascade = CascadeType.ALL)
	private List<UsuarioPelicula> usuarioPelicula = new ArrayList<>();
	@OneToMany(mappedBy = "id.usuario", cascade = CascadeType.ALL)
	private List<UsuarioSerie> usuarioSerie = new ArrayList<>();

	/**
	 * Constructor para el momento en el que se crea un perfil
	 * 
	 * @param usuario
	 * @param nombre
	 * @param correo
	 * @param clave
	 * @param fechaMiembro
	 */
	public Usuario(String usuario, String nombre, String correo, String clave, Date fechaMiembro) {
		this.user = usuario;
		this.nombre = nombre;
		this.correo = correo;
		this.clave = clave;
		this.fechaMiembro = fechaMiembro;
	}

	/**
	 * Constructor por defecto, vacío
	 */
	public Usuario() {
	}

	public Usuario(String usuario, String nombre, String correo, String clave, Date fechaMiembro, List<UsuarioPelicula> listPe) {
		this.user = usuario;
		this.nombre = nombre;
		this.correo = correo;
		this.clave = clave;
		this.fechaMiembro = fechaMiembro;
		this.usuarioPelicula = listPe;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the imagenPerfil
	 */
	public String getImagenPerfil() {
		return imagenPerfil;
	}

	/**
	 * @param imagenPerfil the igamenPerfil to set
	 */
	public void setImagenPerfil(String imagenPerfil) {
		this.imagenPerfil = imagenPerfil;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the fechaMiembro
	 */
	public Date getFechaMiembro() {
		return fechaMiembro;
	}

	/**
	 * @param fechaMiembro the fechaMiembro to set
	 */
	public void setFechaMiembro(Date fechaMiembro) {
		this.fechaMiembro = fechaMiembro;
	}

	/**
	 * @return the usuarioPelicula
	 */
	public List<UsuarioPelicula> getUsuarioPelicula() {
		return usuarioPelicula;
	}

	/**
	 * @param usuarioPelicula the usuarioPelicula to set
	 */
	public void setUsuarioPelicula(List<UsuarioPelicula> usuarioPelicula) {
		this.usuarioPelicula = usuarioPelicula;
	}

	/**
	 * @return the usuarioSerie
	 */
	public List<UsuarioSerie> getUsuarioSerie() {
		return usuarioSerie;
	}

	/**
	 * @param usuarioSerie the usuarioSerie to set
	 */
	public void setUsuarioSerie(List<UsuarioSerie> usuarioSerie) {
		this.usuarioSerie = usuarioSerie;
	}

	/**
	 * Devuelve la fecha en la que se hizo miembro en formato string, dd-MM-yyyy
	 * 
	 * @return Fecha en string
	 */
	public String getFechaMiembroString() {
		// Define el formato de salida
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(fechaMiembro.toString());
		// Formatea la fecha como una cadena y la devuelve
		return formato.format(fechaMiembro);
	}

	@Override
	public String toString() {
		return "Perfil [usuario=" + user + ", nombre=" + nombre + ", correo=" + correo + ", igamenPerfil=" + imagenPerfil
				+ ", clave=" + clave + ", fechaMiembro=" + fechaMiembro + "]";
	}
}
