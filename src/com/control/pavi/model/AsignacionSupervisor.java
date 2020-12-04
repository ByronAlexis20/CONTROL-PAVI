package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the asignacion_supervisor database table.
 * 
 */
@Entity
@Table(name="asignacion_supervisor")
@NamedQuery(name="AsignacionSupervisor.buscarAsignados", query="SELECT a FROM AsignacionSupervisor a where a.estado = 1 order by a.supervisor.idSupervisor asc")
public class AsignacionSupervisor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_asignacion_supervisor")
	private Integer idAsignacionSupervisor;

	private boolean estado;

	private String observaciones;

	//bi-directional many-to-one association to JuntaVoto
	@ManyToOne
	@JoinColumn(name="id_junta")
	private JuntaVoto juntaVoto;

	//bi-directional many-to-one association to Periodo
	@ManyToOne
	@JoinColumn(name="id_periodo")
	private Periodo periodo;

	//bi-directional many-to-one association to Supervisor
	@ManyToOne
	@JoinColumn(name="id_supervisor")
	private Supervisor supervisor;

	public AsignacionSupervisor() {
	}

	public Integer getIdAsignacionSupervisor() {
		return this.idAsignacionSupervisor;
	}

	public void setIdAsignacionSupervisor(Integer idAsignacionSupervisor) {
		this.idAsignacionSupervisor = idAsignacionSupervisor;
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

	public JuntaVoto getJuntaVoto() {
		return this.juntaVoto;
	}

	public void setJuntaVoto(JuntaVoto juntaVoto) {
		this.juntaVoto = juntaVoto;
	}

	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Supervisor getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

}