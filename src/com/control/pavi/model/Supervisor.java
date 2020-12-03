package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the supervisor database table.
 * 
 */
@Entity
@NamedQuery(name="Supervisor.bucarPatron", query="SELECT s FROM Supervisor s where s.estado = 1 and (lower(s.nombres) like lower(:patron) or lower(s.apellidos) like lower(:patron))")
public class Supervisor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_supervisor")
	private Integer idSupervisor;

	private String apellidos;

	private String direccion;

	private boolean estado;

	@Column(name="no_identificacion")
	private String noIdentificacion;

	private String nombres;

	private String telefono;

	//bi-directional many-to-one association to AsignacionSupervisor
	@OneToMany(mappedBy="supervisor")
	private List<AsignacionSupervisor> asignacionSupervisors;

	//bi-directional many-to-one association to TipoSupervisor
	@ManyToOne
	@JoinColumn(name="id_tipo_supervisor")
	private TipoSupervisor tipoSupervisor;

	public Supervisor() {
	}

	public Integer getIdSupervisor() {
		return this.idSupervisor;
	}

	public void setIdSupervisor(Integer idSupervisor) {
		this.idSupervisor = idSupervisor;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getNoIdentificacion() {
		return this.noIdentificacion;
	}

	public void setNoIdentificacion(String noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<AsignacionSupervisor> getAsignacionSupervisors() {
		return this.asignacionSupervisors;
	}

	public void setAsignacionSupervisors(List<AsignacionSupervisor> asignacionSupervisors) {
		this.asignacionSupervisors = asignacionSupervisors;
	}

	public AsignacionSupervisor addAsignacionSupervisor(AsignacionSupervisor asignacionSupervisor) {
		getAsignacionSupervisors().add(asignacionSupervisor);
		asignacionSupervisor.setSupervisor(this);

		return asignacionSupervisor;
	}

	public AsignacionSupervisor removeAsignacionSupervisor(AsignacionSupervisor asignacionSupervisor) {
		getAsignacionSupervisors().remove(asignacionSupervisor);
		asignacionSupervisor.setSupervisor(null);

		return asignacionSupervisor;
	}

	public TipoSupervisor getTipoSupervisor() {
		return this.tipoSupervisor;
	}

	public void setTipoSupervisor(TipoSupervisor tipoSupervisor) {
		this.tipoSupervisor = tipoSupervisor;
	}

}