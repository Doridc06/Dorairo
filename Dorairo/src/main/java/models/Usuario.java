package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	private String igamenPerfil;

	@Column(name = "clave", length = 20)
	private String clave;

	@Column(name = "fecha_miembro")
	private Date fechaMiembro;

	@OneToMany(mappedBy = "id.usuario", cascade = CascadeType.ALL)
	private Set<UsuarioPelicula> usuarioPelicula = new HashSet<>();
	@OneToMany(mappedBy = "id.usuario", cascade = CascadeType.ALL)
	private Set<UsuarioSerie> usuarioSerie = new HashSet<>();

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
		super();
		this.user = usuario;
		this.nombre = nombre;
		this.correo = correo;
		this.clave = clave;
		this.fechaMiembro = fechaMiembro;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return user;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.user = usuario;
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
	 * @return the igamenPerfil
	 */
	public String getIgamenPerfil() {
		return igamenPerfil;
	}

	/**
	 * @param igamenPerfil the igamenPerfil to set
	 */
	public void setIgamenPerfil(String igamenPerfil) {
		this.igamenPerfil = igamenPerfil;
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

	/**
	 * @param fechaMiembro the fechaMiembro to set
	 */
	public void setFechaMiembro(Date fechaMiembro) {
		this.fechaMiembro = fechaMiembro;
	}

	@Override
	public String toString() {
		return "Perfil [usuario=" + user + ", nombre=" + nombre + ", correo=" + correo + ", igamenPerfil=" + igamenPerfil
				+ ", clave=" + clave + ", fechaMiembro=" + fechaMiembro + "]";
	}
}
