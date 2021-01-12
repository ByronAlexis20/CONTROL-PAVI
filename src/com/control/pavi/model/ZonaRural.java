package com.control.pavi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "zona_rural")
@NamedQueries({
	@NamedQuery(name = "ZonaRural.findAll", query = "SELECT z FROM ZonaRural z where z.estado = 'A'"),
	@NamedQuery(name = "ZonaRural.bucarPatron", query = "SELECT z FROM ZonaRural z where z.estado = 'A' and  lower(z.nombre) like lower(:patron) "),
	@NamedQuery(name = "ZonaRural.bucarPorIdParroquia", query = "SELECT z FROM ZonaRural z where z.estado = 'A' and z.parroquia.idParroquia = :idParroquia")
})
public class ZonaRural implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_zona")
	private Integer idZona;

	@ManyToOne
	@JoinColumn(name = "id_parroquia")
	private Parroquia parroquia;

	private String nombre;

	private String estado;

	@OneToMany(mappedBy="zonaRural", cascade = CascadeType.ALL)
	private List<Recinto> recintos;
	
	public ZonaRural() {
		super();
	}

	public ZonaRural(Integer idZona, Parroquia parroquia, String estado, String nombre) {
		super();
		this.idZona = idZona;
		this.parroquia = parroquia;
		this.estado = estado;
		this.nombre = nombre;
	}

	public Integer getIdZona() {
		return idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	public Parroquia getParroquia() {
		return parroquia;
	}

	public void setParroquia(Parroquia parroquia) {
		this.parroquia = parroquia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Recinto> getRecintos() {
		return recintos;
	}

	public void setRecintos(List<Recinto> recintos) {
		this.recintos = recintos;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

}
