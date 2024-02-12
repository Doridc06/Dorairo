package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase que representa la tabla de enlace de Usuario y Peliculas
 * 
 * @author JairoAB
 *
 */
@Entity
@Table(name = "Usuario_Pelicula")
public class UsuarioPelicula implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioPeliculaID id;

	@Column(name = "valoracion_usuario", precision = 2, scale = 2)
	private double valoracionUsuario;

	@Column(name = "fecha_visualizacion_usuario")
	private Date fechaVisualizacionUsuario;

	@Column(name = "comentarios_usuario")
	private String comentariosUsuario;

	@ManyToOne
	@JoinColumn(name = "localizacion")
	private Localizacion localizacion;

	@Column(name = "vista")
	private boolean vista;

	@Column(name = "mi_lista")
	private boolean miLista;

	/**
	 * Constructor para la tabla UsuarioPelicula
	 * 
	 * @param id
	 * @param valoracionUsuario
	 * @param fechaVisualizacionUsuario
	 * @param comentariosUsuario
	 * @param localizacion
	 * @param vista
	 * @param miLista
	 */
	public UsuarioPelicula(UsuarioPeliculaID id, double valoracionUsuario, Date fechaVisualizacionUsuario,
			String comentariosUsuario, Localizacion localizacion, boolean vista, boolean miLista) {
		super();
		this.id = id;
		this.valoracionUsuario = valoracionUsuario;
		this.fechaVisualizacionUsuario = fechaVisualizacionUsuario;
		this.comentariosUsuario = comentariosUsuario;
		this.localizacion = localizacion;
		this.vista = vista;
		this.miLista = miLista;
	}

	public UsuarioPelicula(Usuario usuario, Pelicula p) {
		this.id = new UsuarioPeliculaID(usuario, p);
	}

	public UsuarioPelicula() {
	}

	/**
	 * @return the id
	 */
	public UsuarioPeliculaID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UsuarioPeliculaID id) {
		this.id = id;
	}

	/**
	 * @return the valoracionUsuario
	 */
	public double getValoracionUsuario() {
		return valoracionUsuario;
	}

	/**
	 * @param valoracionUsuario the valoracionUsuario to set
	 */
	public void setValoracionUsuario(double valoracionUsuario) {
		this.valoracionUsuario = valoracionUsuario;
	}

	/**
	 * @return the fechaVisualizacionUsuario
	 */
	public Date getFechaVisualizacionUsuario() {
		return fechaVisualizacionUsuario;
	}

	/**
	 * @param fechaVisualizacionUsuario the fechaVisualizacionUsuario to set
	 */
	public void setFechaVisualizacionUsuario(Date fechaVisualizacionUsuario) {
		this.fechaVisualizacionUsuario = fechaVisualizacionUsuario;
	}

	/**
	 * @return the comentariosUsuario
	 */
	public String getComentariosUsuario() {
		return comentariosUsuario;
	}

	/**
	 * @param comentariosUsuario the comentariosUsuario to set
	 */
	public void setComentariosUsuario(String comentariosUsuario) {
		this.comentariosUsuario = comentariosUsuario;
	}

	/**
	 * @return the localizacion
	 */
	public Localizacion getLocalizacion() {
		return localizacion;
	}

	/**
	 * @param localizacion the localizacion to set
	 */
	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

	/**
	 * @return the vista
	 */
	public boolean isVista() {
		return vista;
	}

	/**
	 * @param vista the vista to set
	 */
	public void setVista(boolean vista) {
		this.vista = vista;
	}

	/**
	 * @return the miLista
	 */
	public boolean isMiLista() {
		return miLista;
	}

	/**
	 * @param miLista the miLista to set
	 */
	public void setMiLista(boolean miLista) {
		this.miLista = miLista;
	}

}
