package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the asignacion_junta database table.
 * 
 */
@Entity
@Table(name="asignacion_junta")
@NamedQuery(name="AsignacionJunta.findAll", query="SELECT a FROM AsignacionJunta a")
public class AsignacionJunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_asignacion_junta")
	private int idAsignacionJunta;

	private String descripcion;

	private byte estado;

	//bi-directional many-to-one association to Representante
	@ManyToOne
	@JoinColumn(name="id_representante")
	private Representante representante;

	public AsignacionJunta() {
	}

	public int getIdAsignacionJunta() {
		return this.idAsignacionJunta;
	}

	public void setIdAsignacionJunta(int idAsignacionJunta) {
		this.idAsignacionJunta = idAsignacionJunta;
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

	public Representante getRepresentante() {
		return this.representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

}