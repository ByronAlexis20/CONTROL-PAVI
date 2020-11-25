package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="partido_politico")
@NamedQueries({
	@NamedQuery(name="PartidoPolitico.findAll", query="SELECT p FROM PartidoPolitico p"),
	@NamedQuery(name="PartidoPolitico.bucarPatron", query="SELECT p FROM PartidoPolitico p where p.estado = 1 and lower(p.descripcion) like lower(:patron)")
})
public class PartidoPolitico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_partido")
	private Integer idPartido;

	@Column(name="apellido_candidato")
	private String apellidoCandidato;

	private String descripcion;

	private int edad;

	private boolean estado;

	@Column(name="id_periodo")
	private int idPeriodo;

	private String lista;

	@Column(name="nombre_candidato")
	private String nombreCandidato;

	private String slogan;

	//bi-directional many-to-one association to Candidato
	@ManyToOne
	@JoinColumn(name="id_candidato")
	private Candidato candidato;

	//bi-directional many-to-one association to Representante
	@OneToMany(mappedBy="partidoPolitico")
	private List<Representante> representantes;

	public PartidoPolitico() {
	}

	public Integer getIdPartido() {
		return this.idPartido;
	}

	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}

	public String getApellidoCandidato() {
		return this.apellidoCandidato;
	}

	public void setApellidoCandidato(String apellidoCandidato) {
		this.apellidoCandidato = apellidoCandidato;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getNombreCandidato() {
		return this.nombreCandidato;
	}

	public void setNombreCandidato(String nombreCandidato) {
		this.nombreCandidato = nombreCandidato;
	}

	public String getSlogan() {
		return this.slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public Candidato getCandidato() {
		return this.candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public List<Representante> getRepresentantes() {
		return this.representantes;
	}

	public void setRepresentantes(List<Representante> representantes) {
		this.representantes = representantes;
	}

	public Representante addRepresentante(Representante representante) {
		getRepresentantes().add(representante);
		representante.setPartidoPolitico(this);

		return representante;
	}

	public Representante removeRepresentante(Representante representante) {
		getRepresentantes().remove(representante);
		representante.setPartidoPolitico(null);

		return representante;
	}

}