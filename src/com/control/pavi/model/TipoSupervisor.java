package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_supervisor database table.
 * 
 */
@Entity
@Table(name="tipo_supervisor")
@NamedQuery(name="TipoSupervisor.findAll", query="SELECT t FROM TipoSupervisor t")
public class TipoSupervisor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_supervisor")
	private Integer idTipoSupervisor;

	private String descripcion;

	private boolean estado;

	@Column(name="tipo_supervisor")
	private String tipoSupervisor;

	//bi-directional many-to-one association to Supervisor
	@OneToMany(mappedBy="tipoSupervisor")
	private List<Supervisor> supervisors;

	public TipoSupervisor() {
	}

	public Integer getIdTipoSupervisor() {
		return this.idTipoSupervisor;
	}

	public void setIdTipoSupervisor(Integer idTipoSupervisor) {
		this.idTipoSupervisor = idTipoSupervisor;
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

	public String getTipoSupervisor() {
		return this.tipoSupervisor;
	}

	public void setTipoSupervisor(String tipoSupervisor) {
		this.tipoSupervisor = tipoSupervisor;
	}

	public List<Supervisor> getSupervisors() {
		return this.supervisors;
	}

	public void setSupervisors(List<Supervisor> supervisors) {
		this.supervisors = supervisors;
	}

	public Supervisor addSupervisor(Supervisor supervisor) {
		getSupervisors().add(supervisor);
		supervisor.setTipoSupervisor(this);

		return supervisor;
	}

	public Supervisor removeSupervisor(Supervisor supervisor) {
		getSupervisors().remove(supervisor);
		supervisor.setTipoSupervisor(null);

		return supervisor;
	}

}