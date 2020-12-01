package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the junta_voto database table.
 * 
 */
@Entity
@Table(name="junta_voto")
@NamedQuery(name="JuntaVoto.bucarPorRecinto", query="SELECT j FROM JuntaVoto j where j.recinto.idRecinto = :id and j.estado = 1")
public class JuntaVoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_junta")
	private Integer idJunta;

	private String descripcion;

	private boolean estado;

	private String genero;

	private int numero;

	//bi-directional many-to-one association to AsignacionSupervisor
	@OneToMany(mappedBy="juntaVoto")
	private List<AsignacionSupervisor> asignacionSupervisors;
	
	@OneToMany(mappedBy="junta",cascade = CascadeType.ALL)
	private List<AsignacionJunta> asignacionJuntas;

	//bi-directional many-to-one association to Recinto
	@ManyToOne
	@JoinColumn(name="id_recinto")
	private Recinto recinto;

	public JuntaVoto() {
	}

	public Integer getIdJunta() {
		return this.idJunta;
	}

	public void setIdJunta(Integer idJunta) {
		this.idJunta = idJunta;
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

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<AsignacionSupervisor> getAsignacionSupervisors() {
		return this.asignacionSupervisors;
	}

	public void setAsignacionSupervisors(List<AsignacionSupervisor> asignacionSupervisors) {
		this.asignacionSupervisors = asignacionSupervisors;
	}

	public AsignacionSupervisor addAsignacionSupervisor(AsignacionSupervisor asignacionSupervisor) {
		getAsignacionSupervisors().add(asignacionSupervisor);
		asignacionSupervisor.setJuntaVoto(this);

		return asignacionSupervisor;
	}

	public AsignacionSupervisor removeAsignacionSupervisor(AsignacionSupervisor asignacionSupervisor) {
		getAsignacionSupervisors().remove(asignacionSupervisor);
		asignacionSupervisor.setJuntaVoto(null);

		return asignacionSupervisor;
	}

	public Recinto getRecinto() {
		return this.recinto;
	}

	public void setRecinto(Recinto recinto) {
		this.recinto = recinto;
	}

	public List<AsignacionJunta> getAsignacionJuntas() {
		return asignacionJuntas;
	}

	public void setAsignacionJuntas(List<AsignacionJunta> asignacionJuntas) {
		this.asignacionJuntas = asignacionJuntas;
	}
	public AsignacionJunta addAsignacionJunta(AsignacionJunta asignacionJunta) {
		getAsignacionJuntas().add(asignacionJunta);
		asignacionJunta.setJunta(this);

		return asignacionJunta;
	}

	public AsignacionJunta removeAsignacionJunta(AsignacionJunta asignacionJunta) {
		getAsignacionJuntas().remove(asignacionJunta);
		asignacionJunta.setJunta(null);

		return asignacionJunta;
	}
}