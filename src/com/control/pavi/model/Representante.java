package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the representante database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Representante.buscarPorPatron", query="SELECT r FROM Representante r where r.estado = 1 and (lower(r.nombre) like lower(:patron) or lower(r.apellidos) like lower(:patron))"),
	@NamedQuery(name="Representante.buscarPorCedula", query="SELECT r FROM Representante r where r.estado = 1 and r.noIdentificacion = :cedula")
})
public class Representante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_representante")
	private Integer idRepresentante;

	private String apellidos;

	private int edad;

	private boolean estado;

	@Column(name="no_identificacion")
	private String noIdentificacion;

	private String nombre;

	private String telefono;

	//bi-directional many-to-one association to AsignacionJunta
	@OneToMany(mappedBy="representante",cascade = CascadeType.ALL)
	private List<AsignacionJunta> asignacionJuntas;

	//bi-directional many-to-one association to PartidoPolitico
	@ManyToOne
	@JoinColumn(name="id_partido")
	private PartidoPolitico partidoPolitico;

	public Representante() {
	}

	public Integer getIdRepresentante() {
		return this.idRepresentante;
	}

	public void setIdRepresentante(Integer idRepresentante) {
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public PartidoPolitico getPartidoPolitico() {
		return this.partidoPolitico;
	}

	public void setPartidoPolitico(PartidoPolitico partidoPolitico) {
		this.partidoPolitico = partidoPolitico;
	}

}