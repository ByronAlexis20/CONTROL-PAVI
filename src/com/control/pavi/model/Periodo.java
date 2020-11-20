package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the periodo database table.
 * 
 */
@Entity
@NamedQuery(name="Periodo.findAll", query="SELECT p FROM Periodo p")
public class Periodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_periodo")
	private int idPeriodo;

	@Column(name="anio_eleccion")
	private int anioEleccion;

	private byte estado;

	public Periodo() {
	}

	public int getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public int getAnioEleccion() {
		return this.anioEleccion;
	}

	public void setAnioEleccion(int anioEleccion) {
		this.anioEleccion = anioEleccion;
	}

	public byte getEstado() {
		return this.estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}

}