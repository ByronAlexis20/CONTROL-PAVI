package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="recinto")
@NamedQueries({
	@NamedQuery(name="Recinto.findAll", query="SELECT r FROM Recinto r"),
	@NamedQuery(name="Recinto.bucarPatron", query="SELECT r FROM Recinto r where r.estado = 1 and lower(r.recinto) like lower(:patron) order by r.parroquia.idParroquia asc"),
	@NamedQuery(name="Recinto.bucarIdParroquia", query="SELECT r FROM Recinto r where r.estado = 1 and r.parroquia.idParroquia = :id"),
	
})
public class Recinto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_recinto")
	private Integer idRecinto;

	private String direccion;

	private boolean estado;

	private String recinto;

	private String telefono;

	//bi-directional many-to-one association to JuntaVoto
	@OneToMany(mappedBy="recinto")
	private List<JuntaVoto> juntaVotos;

	//bi-directional many-to-one association to Parroquia
	@ManyToOne
	@JoinColumn(name="id_parroquia")
	private Parroquia parroquia;

	public Recinto() {
	}

	public Integer getIdRecinto() {
		return this.idRecinto;
	}

	public void setIdRecinto(Integer idRecinto) {
		this.idRecinto = idRecinto;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getRecinto() {
		return this.recinto;
	}

	public void setRecinto(String recinto) {
		this.recinto = recinto;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<JuntaVoto> getJuntaVotos() {
		return this.juntaVotos;
	}

	public void setJuntaVotos(List<JuntaVoto> juntaVotos) {
		this.juntaVotos = juntaVotos;
	}

	public JuntaVoto addJuntaVoto(JuntaVoto juntaVoto) {
		getJuntaVotos().add(juntaVoto);
		juntaVoto.setRecinto(this);

		return juntaVoto;
	}

	public JuntaVoto removeJuntaVoto(JuntaVoto juntaVoto) {
		getJuntaVotos().remove(juntaVoto);
		juntaVoto.setRecinto(null);

		return juntaVoto;
	}

	public Parroquia getParroquia() {
		return this.parroquia;
	}

	public void setParroquia(Parroquia parroquia) {
		this.parroquia = parroquia;
	}

	@Override
	public String toString() {
		return this.recinto;
	}

}