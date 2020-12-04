package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the supervisor_principal database table.
 * 
 */
@Entity
@Table(name="supervisor_principal")
@NamedQuery(name="SupervisorPrincipal.bucarPatron", query="SELECT s FROM SupervisorPrincipal s where s.estado = 1 and (lower(s.nombres) like lower(:patron) or lower(s.apellidos) like lower(:patron))")
public class SupervisorPrincipal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_supervisor")
	private Integer idSupervisor;

	private String apellidos;

	private String cedula;

	private String direccion;

	private boolean estado;

	private String nombres;

	private String telefono;

	//bi-directional many-to-one association to AsignacionSupervisorPrincipal
	@OneToMany(mappedBy="supervisorPrincipal", cascade = CascadeType.ALL)
	private List<AsignacionSupervisorPrincipal> asignacionSupervisorPrincipals;

	public SupervisorPrincipal() {
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

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
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

	public List<AsignacionSupervisorPrincipal> getAsignacionSupervisorPrincipals() {
		return this.asignacionSupervisorPrincipals;
	}

	public void setAsignacionSupervisorPrincipals(List<AsignacionSupervisorPrincipal> asignacionSupervisorPrincipals) {
		this.asignacionSupervisorPrincipals = asignacionSupervisorPrincipals;
	}

	public AsignacionSupervisorPrincipal addAsignacionSupervisorPrincipal(AsignacionSupervisorPrincipal asignacionSupervisorPrincipal) {
		getAsignacionSupervisorPrincipals().add(asignacionSupervisorPrincipal);
		asignacionSupervisorPrincipal.setSupervisorPrincipal(this);

		return asignacionSupervisorPrincipal;
	}

	public AsignacionSupervisorPrincipal removeAsignacionSupervisorPrincipal(AsignacionSupervisorPrincipal asignacionSupervisorPrincipal) {
		getAsignacionSupervisorPrincipals().remove(asignacionSupervisorPrincipal);
		asignacionSupervisorPrincipal.setSupervisorPrincipal(null);

		return asignacionSupervisorPrincipal;
	}

}