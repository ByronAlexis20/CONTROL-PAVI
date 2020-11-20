package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the junta_voto database table.
 * 
 */
@Entity
@Table(name="junta_voto")
@NamedQuery(name="JuntaVoto.findAll", query="SELECT j FROM JuntaVoto j")
public class JuntaVoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_junta")
	private int idJunta;

	private String descripcion;

	private byte estado;

	private String genero;

	private int numero;

	//bi-directional many-to-one association to Recinto
	@ManyToOne
	@JoinColumn(name="id_recinto")
	private Recinto recinto;

	public JuntaVoto() {
	}

	public int getIdJunta() {
		return this.idJunta;
	}

	public void setIdJunta(int idJunta) {
		this.idJunta = idJunta;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte getEstado() {
		return this.estado;
	}

	public void setEstado(byte estado) {
		this.estado = estado;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Recinto getRecinto() {
		return this.recinto;
	}

	public void setRecinto(Recinto recinto) {
		this.recinto = recinto;
	}

}