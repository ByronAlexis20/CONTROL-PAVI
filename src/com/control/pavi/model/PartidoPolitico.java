package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the partido_politico database table.
 * 
 */
@Entity
@Table(name="partido_politico")
@NamedQuery(name="PartidoPolitico.findAll", query="SELECT p FROM PartidoPolitico p")
public class PartidoPolitico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_partido")
	private Integer idPartido;

	private String descripcion;

	private boolean estado;

	@Column(name="id_periodo")
	private int idPeriodo;

	private String lista;

	private String slogan;

	//bi-directional many-to-one association to AsignacionRepresentante
	@OneToMany(mappedBy="partidoPolitico")
	private List<AsignacionRepresentante> asignacionRepresentantes;

	//bi-directional many-to-one association to Candidato
	@ManyToOne
	@JoinColumn(name="id_candidato")
	private Candidato candidato;

	public PartidoPolitico() {
	}

	public Integer getIdPartido() {
		return this.idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
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

	public int getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getLista() {
		return this.lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public String getSlogan() {
		return this.slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public List<AsignacionRepresentante> getAsignacionRepresentantes() {
		return this.asignacionRepresentantes;
	}

	public void setAsignacionRepresentantes(List<AsignacionRepresentante> asignacionRepresentantes) {
		this.asignacionRepresentantes = asignacionRepresentantes;
	}

	public AsignacionRepresentante addAsignacionRepresentante(AsignacionRepresentante asignacionRepresentante) {
		getAsignacionRepresentantes().add(asignacionRepresentante);
		asignacionRepresentante.setPartidoPolitico(this);

		return asignacionRepresentante;
	}

	public AsignacionRepresentante removeAsignacionRepresentante(AsignacionRepresentante asignacionRepresentante) {
		getAsignacionRepresentantes().remove(asignacionRepresentante);
		asignacionRepresentante.setPartidoPolitico(null);

		return asignacionRepresentante;
	}

	public Candidato getCandidato() {
		return this.candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

}