package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="parroquia")
@NamedQueries({
	@NamedQuery(name="Parroquia.findAll", query="SELECT p FROM Parroquia p"),
	@NamedQuery(name="Parroquia.bucarPatron", query="SELECT p FROM Parroquia p where p.estado = 1 and lower(p.parroquia) like lower(:patron)")
})
public class Parroquia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parroquia")
	private int idParroquia;

	private boolean estado;

	private String parroquia;

	//bi-directional many-to-one association to Canton
	@ManyToOne
	@JoinColumn(name="id_canton")
	private Canton canton;

	//bi-directional many-to-one association to Zona
	@OneToMany(mappedBy="parroquia")
	private List<Zona> zonas;

	public Parroquia() {
	}

	public int getIdParroquia() {
		return this.idParroquia;
	}

	public void setIdParroquia(int idParroquia) {
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

	public List<Zona> getZonas() {
		return this.zonas;
	}

	public void setZonas(List<Zona> zonas) {
		this.zonas = zonas;
	}

	public Zona addZona(Zona zona) {
		getZonas().add(zona);
		zona.setParroquia(this);

		return zona;
	}

	public Zona removeZona(Zona zona) {
		getZonas().remove(zona);
		zona.setParroquia(null);

		return zona;
	}

}