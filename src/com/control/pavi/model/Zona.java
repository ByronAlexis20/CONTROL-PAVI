package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="zona")
@NamedQueries({
	@NamedQuery(name="Zona.findAll", query="SELECT z FROM Zona z where z.estado = 1")
})
public class Zona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_zona")
	private Integer idZona;

	private boolean estado;

	private String zona;

	//bi-directional many-to-one association to Parroquia
	@OneToMany(mappedBy="zona" ,cascade = CascadeType.ALL)
	private List<Parroquia> parroquias;

	public Zona() {
	}

	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
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

	public List<Parroquia> getParroquias() {
		return this.parroquias;
	}

	public void setParroquias(List<Parroquia> parroquias) {
		this.parroquias = parroquias;
	}

	public Parroquia addParroquia(Parroquia parroquia) {
		getParroquias().add(parroquia);
		parroquia.setZona(this);

		return parroquia;
	}

	public Parroquia removeParroquia(Parroquia parroquia) {
		getParroquias().remove(parroquia);
		parroquia.setZona(null);

		return parroquia;
	}

	@Override
	public String toString() {
		return this.zona;
	}

}