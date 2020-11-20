package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the representante database table.
 * 
 */
@Entity
@NamedQuery(name="Representante.findAll", query="SELECT r FROM Representante r")
public class Representante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_representante")
	private int idRepresentante;

	private String apellidos;

	private int edad;

	private byte estado;

	@Column(name="no_identificacion")
	private String noIdentificacion;

	private String nombre;

	//bi-directional many-to-one association to AsignacionJunta
	@OneToMany(mappedBy="representante")
	private List<AsignacionJunta> asignacionJuntas;

	//bi-directional many-to-one association to AsignacionRepresentante
	@OneToMany(mappedBy="representante")
	private List<AsignacionRepresentante> asignacionRepresentantes;

	public Representante() {
	}

	public int getIdRepresentante() {
		return this.idRepresentante;
	}

	public void setIdRepresentante(int idRepresentante) {
		this.idRepresentante = idRepresentante;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return this.edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public byte getEstado() {
		return this.estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}

	public String getNoIdentificacion() {
		return this.noIdentificacion;
	}

	public void setNoIdentificacion(String noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AsignacionJunta> getAsignacionJuntas() {
		return this.asignacionJuntas;
	}

	public void setAsignacionJuntas(List<AsignacionJunta> asignacionJuntas) {
		this.asignacionJuntas = asignacionJuntas;
	}

	public AsignacionJunta addAsignacionJunta(AsignacionJunta asignacionJunta) {
		getAsignacionJuntas().add(asignacionJunta);
		asignacionJunta.setRepresentante(this);

		return asignacionJunta;
	}

	public AsignacionJunta removeAsignacionJunta(AsignacionJunta asignacionJunta) {
		getAsignacionJuntas().remove(asignacionJunta);
		asignacionJunta.setRepresentante(null);

		return asignacionJunta;
	}

	public List<AsignacionRepresentante> getAsignacionRepresentantes() {
		return this.asignacionRepresentantes;
	}

	public void setAsignacionRepresentantes(List<AsignacionRepresentante> asignacionRepresentantes) {
		this.asignacionRepresentantes = asignacionRepresentantes;
	}

	public AsignacionRepresentante addAsignacionRepresentante(AsignacionRepresentante asignacionRepresentante) {
		getAsignacionRepresentantes().add(asignacionRepresentante);
		asignacionRepresentante.setRepresentante(this);

		return asignacionRepresentante;
	}

	public AsignacionRepresentante removeAsignacionRepresentante(AsignacionRepresentante asignacionRepresentante) {
		getAsignacionRepresentantes().remove(asignacionRepresentante);
		asignacionRepresentante.setRepresentante(null);

		return asignacionRepresentante;
	}

}