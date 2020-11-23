package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the candidato database table.
 * 
 */
@Entity
@NamedQuery(name="Candidato.findAll", query="SELECT c FROM Candidato c")
public class Candidato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_candidato")
	private Integer idCandidato;

	private String apellido;

	private int edad;

	private boolean estado;

	private String genero;

	private String nombre;

	//bi-directional many-to-one association to PartidoPolitico
	@OneToMany(mappedBy="candidato")
	private List<PartidoPolitico> partidoPoliticos;

	public Candidato() {
	}

	public Integer getIdCandidato() {
		return this.idCandidato;
	}

	public void setIdCandidato(Integer idCandidato) {
		this.idCandidato = idCandidato;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<PartidoPolitico> getPartidoPoliticos() {
		return this.partidoPoliticos;
	}

	public void setPartidoPoliticos(List<PartidoPolitico> partidoPoliticos) {
		this.partidoPoliticos = partidoPoliticos;
	}

	public PartidoPolitico addPartidoPolitico(PartidoPolitico partidoPolitico) {
		getPartidoPoliticos().add(partidoPolitico);
		partidoPolitico.setCandidato(this);

		return partidoPolitico;
	}

	public PartidoPolitico removePartidoPolitico(PartidoPolitico partidoPolitico) {
		getPartidoPoliticos().remove(partidoPolitico);
		partidoPolitico.setCandidato(null);

		return partidoPolitico;
	}

}