package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="parroquia")
@NamedQueries({
	@NamedQuery(name="Parroquia.findAll", query="SELECT p FROM Parroquia p"),
	@NamedQuery(name="Parroquia.bucarPatron", query="SELECT p FROM Parroquia p where p.estado = 1 and lower(p.parroquia) like lower(:patron)"),
	@NamedQuery(name="Parroquia.bucarPorCanton", query="SELECT p FROM Parroquia p where p.estado = 1 and p.canton.idCanton = :id")
})
public class Parroquia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parroquia")
	private Integer idParroquia;

	private boolean estado;

	private String parroquia;
	
	private Zona zona;

	//bi-directional many-to-one association to Canton
	@ManyToOne
	@JoinColumn(name="id_canton")
	private Canton canton;

	//bi-directional many-to-one association to Recinto
	@OneToMany(mappedBy="parroquia")
	private List<Recinto> recintos;

	public Parroquia() {
	}

	public Integer getIdParroquia() {
		return this.idParroquia;
	}

	public void setIdParroquia(Integer idParroquia) {
		this.idParroquia = idParroquia;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getParroquia() {
		return this.parroquia;
	}

	public void setParroquia(String parroquia) {
		this.parroquia = parroquia;
	}

	public Canton getCanton() {
		return this.canton;
	}

	public void setCanton(Canton canton) {
		this.canton = canton;
	}

	public List<Recinto> getRecintos() {
		return this.recintos;
	}

	public void setRecintos(List<Recinto> recintos) {
		this.recintos = recintos;
	}

	public Recinto addRecinto(Recinto recinto) {
		getRecintos().add(recinto);
		recinto.setParroquia(this);

		return recinto;
	}

	public Recinto removeRecinto(Recinto recinto) {
		getRecintos().remove(recinto);
		recinto.setParroquia(null);

		return recinto;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	@Override
	public String toString() {
		return this.parroquia;
	}

}