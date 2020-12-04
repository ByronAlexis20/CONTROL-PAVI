package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the asignacion_supervisor_principal database table.
 * 
 */
@Entity
@Table(name="asignacion_supervisor_principal")
@NamedQuery(name="AsignacionSupervisorPrincipal.buscarAsignados", query="SELECT a FROM AsignacionSupervisorPrincipal a where a.estado = 1")
public class AsignacionSupervisorPrincipal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_asignacion")
	private Integer idAsignacion;

	private boolean estado;

	private String observaciones;

	//bi-directional many-to-one association to SupervisorPrincipal
	@ManyToOne
	@JoinColumn(name="id_supervisor_principal")
	private SupervisorPrincipal supervisorPrincipal;

	//bi-directional many-to-one association to Supervisor
	@ManyToOne
	@JoinColumn(name="id_supervisor_secundario")
	private Supervisor supervisor;

	public AsignacionSupervisorPrincipal() {
	}

	public Integer getIdAsignacion() {
		return this.idAsignacion;
	}

	public void setIdAsignacion(Integer idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public SupervisorPrincipal getSupervisorPrincipal() {
		return this.supervisorPrincipal;
	}

	public void setSupervisorPrincipal(SupervisorPrincipal supervisorPrincipal) {
		this.supervisorPrincipal = supervisorPrincipal;
	}

	public Supervisor getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

}