package models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Perfil")
public class Perfil {

	@Id
	@Column(name = "usuario", length = 20)
	private String usuario;

	@Column(name = "nombre", length = 20)
	private String nombre;

	@Column(name = "correo", length = 100)
	private String correo;

	@Column(name = "imagen_de_perfil")
	private String igamenPerfil;

	@Column(name = "clave", length = 20)
	private String clave;

	@Column(name = "fecha_miembro")
	private LocalDate fechaMiembro;

	/**
	 * Constructor para el momento en el que se crea un perfil
	 * 
	 * @param usuario
	 * @param nombre
	 * @param correo
	 * @param clave
	 * @param fechaMiembro
	 */
	public Perfil(String usuario, String nombre, String correo, String clave, LocalDate fechaMiembro) {
		super();
		this.usuario = usuario;
		this.nombre = nombre;
		this.correo = correo;
		this.clave = clave;
		this.fechaMiembro = fechaMiembro;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	public LocalDate getFechaMiembro() {
		return fechaMiembro;
	}

	public String getFechaMiembroString() {
		return fechaMiembro.getDayOfMonth() + " de " + fechaMiembro.getMonthValue() + " de " + fechaMiembro.getYear();
	}

	/**
	 * @param fechaMiembro the fechaMiembro to set
	 */
	public void setFechaMiembro(LocalDate fechaMiembro) {
		this.fechaMiembro = fechaMiembro;
	}

	@Override
	public String toString() {
		return "Perfil [usuario=" + usuario + ", nombre=" + nombre + ", correo=" + correo + ", igamenPerfil="
				+ igamenPerfil + ", clave=" + clave + ", fechaMiembro=" + fechaMiembro + "]";
	}
}
