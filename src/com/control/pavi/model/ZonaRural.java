package com.control.pavi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "zona_rural")
@NamedQueries({
	@NamedQuery(name = "ZonaRural.findAll", query = "SELECT z FROM ZonaRural z where z.estado = 'A'"),
	@NamedQuery(name = "ZonaRural.bucarPatron", query = "SELECT z FROM ZonaRural z where z.estado = 'A' and  lower(z.nombre) like lower(:patron) ")
})
public class ZonaRural implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_zona")
	private Integer idZona;

	@Column(name = "id_parroquia")
	private Integer idParroquia;

	private String nombre;

	private String estado;

	public ZonaRural() {
		super();
	}

	public ZonaRural(Integer idZona, Integer idParroquia, String estado, String nombre) {
		super();
		this.idZona = idZona;
		this.idParroquia = idParroquia;
		this.estado = estado;
		this.nombre = nombre;
	}

	public Integer getIdZona() {
		return idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	public Integer getIdParroquia() {
		return idParroquia;
	}

	public void setIdParroquia(Integer idParroquia) {
		this.idParroquia = idParroquia;
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

	@Override
	public String toString() {
		return this.nombre;
	}

}
