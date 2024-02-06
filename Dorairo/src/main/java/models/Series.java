package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "series")
public class Series {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "titulo", length = 200)
	private String titulo;

	@Column(name = "año")
	private int año;

	@ManyToOne
	@JoinColumn(name = "id")
	private Compañia compañia;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "cartel")
	private String cartel;

	@Column(name = "valoracion")
	private int valoracion;

	@Column(name = "valoracion_usuario", precision = 2, scale = 0)
	private double valoracionUsuario;

	@Column(name = "fecha_visualizacion_usuario")
	private Date fechaVisualizacionUsuario;

	@Column(name = "comentarios_usuario")
	private String comentariosUsuario;

	@Column(name = "localizacion")
	private int localizacion;

	/**
	 * Constructor para el momento en el que se crea una serie
	 * 
	 * @param id
	 * @param titulo
	 * @param año
	 * @param compañia
	 * @param descripcion
	 * @param cartel
	 * @param valoracion
	 */
	public Series(int id, String titulo, int año, Compañia compañia, String descripcion, String cartel,
			int valoracion) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.año = año;
		this.compañia = compañia;
		this.descripcion = descripcion;
		this.cartel = cartel;
		this.valoracion = valoracion;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the año
	 */
	public int getAño() {
		return año;
	}

	/**
	 * @param año the año to set
	 */
	public void setAño(int año) {
		this.año = año;
	}

	/**
	 * @return the compañia
	 */
	public Compañia getCompañia() {
		return compañia;
	}

	/**
	 * @param compañia the compañia to set
	 */
	public void setCompañia(Compañia compañia) {
		this.compañia = compañia;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the cartel
	 */
	public String getCartel() {
		return cartel;
	}

	/**
	 * @param cartel the cartel to set
	 */
	public void setCartel(String cartel) {
		this.cartel = cartel;
	}

	/**
	 * @return the valoracion
	 */
	public int getValoracion() {
		return valoracion;
	}

	/**
	 * @param valoracion the valoracion to set
	 */
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
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
	public int getLocalizacion() {
		return localizacion;
	}

	/**
	 * @param localizacion the localizacion to set
	 */
	public void setLocalizacion(int localizacion) {
		this.localizacion = localizacion;
	}

}
