package models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Clase que contiene un objeto de usuario y otro de película, como clave
 * primaria en la tabla de unión de estas
 * 
 * @author JairoAB
 *
 */
@Embeddable
public class UsuarioPeliculaID implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_pelicula")
	private Pelicula pelicula;

	/**
	 * Constructor de usuario y peliculas id
	 * 
	 * @param usuario
	 * @param pelicula
	 */
	public UsuarioPeliculaID(Usuario usuario, Pelicula pelicula) {
		super();
		this.usuario = usuario;
		this.pelicula = pelicula;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the pelicula
	 */
	public Pelicula getPelicula() {
		return pelicula;
	}

	/**
	 * @param pelicula the pelicula to set
	 */
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

}
