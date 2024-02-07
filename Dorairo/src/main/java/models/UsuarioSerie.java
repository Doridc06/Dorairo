package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario_Serie")
public class UsuarioSerie implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioSerieID id;

	@Column(name = "valoracion_usuario", precision = 2, scale = 2)
	private double valoracionUsuario;

	@Column(name = "fecha_visualizacion_usuario")
	private Date fechaVisualizacionUsuario;

	@Column(name = "comentarios_usuario")
	private String comentariosUsuario;

	@ManyToOne
	@JoinColumn(name = "id")
	private Localizacion localizacion;

	@Column(name = "vista")
	private boolean vista;

	@Column(name = "mi_lista")
	private boolean miLista;

	/**
	 * Constructor para la tabla UsuarioSerie
	 * 
	 * @param id
	 * @param valoracionUsuario
	 * @param fechaVisualizacionUsuario
	 * @param comentariosUsuario
	 * @param localizacion
	 * @param vista
	 * @param miLista
	 */
	public UsuarioSerie(UsuarioSerieID id, double valoracionUsuario, Date fechaVisualizacionUsuario,
			String comentariosUsuario, Localizacion localizacion, boolean vista, boolean miLista) {
		this.id = id;
		this.valoracionUsuario = valoracionUsuario;
		this.fechaVisualizacionUsuario = fechaVisualizacionUsuario;
		this.comentariosUsuario = comentariosUsuario;
		this.localizacion = localizacion;
		this.vista = vista;
		this.miLista = miLista;
	}

	/**
	 * @return the id
	 */
	public UsuarioSerieID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UsuarioSerieID id) {
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
