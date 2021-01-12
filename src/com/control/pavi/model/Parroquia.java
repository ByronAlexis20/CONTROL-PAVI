package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="parroquia")
@NamedQueries({
	@NamedQuery(name="Parroquia.findAll", query="SELECT p FROM Parroquia p"),
	@NamedQuery(name="Parroquia.bucarPatron", query="SELECT p FROM Parroquia p where p.estado = 1 and lower(p.parroquia) like lower(:patron)"),
	@NamedQuery(name="Parroquia.bucarPorCanton", query="SELECT p FROM Parroquia p where p.estado = 1 and p.canton.idCanton = :id"),
	@NamedQuery(name="Parroquia.bucarCodigo", query="SELECT p FROM Parroquia p where p.estado = 1 and p.idParroquia = :codigo")
})
public class Parroquia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parroquia")
	private Integer idParroquia;

	private boolean estado;

	private String parroquia;

	//bi-directional many-to-one association to Canton
	@ManyToOne
	@JoinColumn(name="id_canton")
	private Canton canton;

	//bi-directional many-to-one association to Zona
	@ManyToOne
	@JoinColumn(name="id_zona")
	private Zona zona;

	//bi-directional many-to-one association to Recinto
	@OneToMany(mappedBy="parroquia", cascade = CascadeType.ALL)
	private List<ZonaRural> zonaRurales;

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

	public Zona getZona() {
		return this.zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	
	public List<ZonaRural> getZonaRurales() {
		return zonaRurales;
	}

	public void setZonaRurales(List<ZonaRural> zonaRurales) {
		this.zonaRurales = zonaRurales;
	}

	@Override
	public String toString() {
		return this.parroquia;
	}
}