package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the asignacion_representante database table.
 * 
 */
@Entity
@Table(name="asignacion_representante")
@NamedQuery(name="AsignacionRepresentante.findAll", query="SELECT a FROM AsignacionRepresentante a")
public class AsignacionRepresentante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_asignacion")
	private int idAsignacion;

	private String descripcion;

	private byte estado;

	//bi-directional many-to-one association to PartidoPolitico
	@ManyToOne
	@JoinColumn(name="id_partido")
	private PartidoPolitico partidoPolitico;

	//bi-directional many-to-one association to Representante
	@ManyToOne
	@JoinColumn(name="id_representante")
	private Representante representante;

	public AsignacionRepresentante() {
	}

	public int getIdAsignacion() {
		return this.idAsignacion;
	}

	public void setIdAsignacion(int idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte getEstado() {
		return this.estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}

	public PartidoPolitico getPartidoPolitico() {
		return this.partidoPolitico;
	}

	public void setPartidoPolitico(PartidoPolitico partidoPolitico) {
		this.partidoPolitico = partidoPolitico;
	}

	public Representante getRepresentante() {
		return this.representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

}