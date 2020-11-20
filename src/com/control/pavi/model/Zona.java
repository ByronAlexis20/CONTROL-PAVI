package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the zona database table.
 * 
 */
@Entity
@Table(name="zona")
@NamedQueries({
	@NamedQuery(name="Zona.findAll", query="SELECT z FROM Zona z"),
	@NamedQuery(name="Zona.bucarPatron", query="SELECT z FROM Zona z where z.estado = 1 and lower(z.zona) like lower(:patron)")
})
public class Zona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_zona")
	private int idZona;

	private boolean estado;

	private String zona;

	//bi-directional many-to-one association to Recinto
	@OneToMany(mappedBy="zona")
	private List<Recinto> recintos;

	//bi-directional many-to-one association to Parroquia
	@ManyToOne
	@JoinColumn(name="id_parroquia")
	private Parroquia parroquia;

	public Zona() {
	}

	public int getIdZona() {
		return this.idZona;
	}

	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public List<Recinto> getRecintos() {
		return this.recintos;
	}

	public void setRecintos(List<Recinto> recintos) {
		this.recintos = recintos;
	}

	public Recinto addRecinto(Recinto recinto) {
		getRecintos().add(recinto);
		recinto.setZona(this);

		return recinto;
	}

	public Recinto removeRecinto(Recinto recinto) {
		getRecintos().remove(recinto);
		recinto.setZona(null);

		return recinto;
	}

	public Parroquia getParroquia() {
		return this.parroquia;
	}

	public void setParroquia(Parroquia parroquia) {
		this.parroquia = parroquia;
	}

}