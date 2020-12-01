package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the asignacion_junta database table.
 * 
 */
@Entity
@Table(name="asignacion_junta")
@NamedQuery(name="AsignacionJunta.buscarDelegadoJunta", query="SELECT a FROM AsignacionJunta a where a.representante.idRepresentante = :idRep and a.estado = 1")
public class AsignacionJunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_asignacion_junta")
	private Integer idAsignacionJunta;

	private String descripcion;

	private boolean estado;

	//bi-directional many-to-one association to Representante
	@ManyToOne
	@JoinColumn(name="id_representante")
	private Representante representante;
	
	@ManyToOne
	@JoinColumn(name="id_junta")
	private JuntaVoto junta;
	

	public AsignacionJunta() {
	}

	public Integer getIdAsignacionJunta() {
		return this.idAsignacionJunta;
	}

	public void setIdAsignacionJunta(Integer idAsignacionJunta) {
		this.idAsignacionJunta = idAsignacionJunta;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Representante getRepresentante() {
		return this.representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public JuntaVoto getJunta() {
		return junta;
	}

	public void setJunta(JuntaVoto junta) {
		this.junta = junta;
	}

}