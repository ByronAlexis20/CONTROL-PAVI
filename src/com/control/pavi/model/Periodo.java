package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the periodo database table.
 * 
 */
@Entity
@NamedQuery(name="Periodo.findAll", query="SELECT p FROM Periodo p")
public class Periodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_periodo")
	private Integer idPeriodo;

	@Column(name="anio_eleccion")
	private int anioEleccion;

	private boolean estado;

	//bi-directional many-to-one association to AsignacionSupervisor
	@OneToMany(mappedBy="periodo")
	private List<AsignacionSupervisor> asignacionSupervisors;

	public Periodo() {
	}

	public Integer getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public int getAnioEleccion() {
		return this.anioEleccion;
	}

	public void setAnioEleccion(int anioEleccion) {
		this.anioEleccion = anioEleccion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public List<AsignacionSupervisor> getAsignacionSupervisors() {
		return this.asignacionSupervisors;
	}

	public void setAsignacionSupervisors(List<AsignacionSupervisor> asignacionSupervisors) {
		this.asignacionSupervisors = asignacionSupervisors;
	}

	public AsignacionSupervisor addAsignacionSupervisor(AsignacionSupervisor asignacionSupervisor) {
		getAsignacionSupervisors().add(asignacionSupervisor);
		asignacionSupervisor.setPeriodo(this);

		return asignacionSupervisor;
	}

	public AsignacionSupervisor removeAsignacionSupervisor(AsignacionSupervisor asignacionSupervisor) {
		getAsignacionSupervisors().remove(asignacionSupervisor);
		asignacionSupervisor.setPeriodo(null);

		return asignacionSupervisor;
	}

}