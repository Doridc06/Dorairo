package models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Clase que contiene un objeto de usuario y otro de series, como clave primaria
 * en la tabla de unión de estas
 * 
 * @author JairoAB
 *
 */
@Embeddable
public class UsuarioSerieID implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_serie")
	private Series series;

	/**
	 * Constructor de usuario y seriess id
	 * 
	 * @param usuario
	 * @param series
	 */
	public UsuarioSerieID(Usuario usuario, Series series) {
		super();
		this.usuario = usuario;
		this.series = series;
	}

	public UsuarioSerieID() {
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
	 * @return the series
	 */
	public Series getSeries() {
		return series;
	}

	/**
	 * @param series the series to set
	 */
	public void setSeries(Series series) {
		this.series = series;
	}
}