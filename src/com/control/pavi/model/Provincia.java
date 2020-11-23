package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;



@Entity
@Table(name="provincia")
@NamedQueries({
	@NamedQuery(name="Provincia.findAll", query="SELECT p FROM Provincia p"),
	@NamedQuery(name="Provincia.bucarPatron", query="SELECT p FROM Provincia p where p.estado = 1 and lower(p.provincia) like lower(:patron)")
})
public class Provincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_provincia")
	private Integer idProvincia;

	private boolean estado;

	private String provincia;

	//bi-directional many-to-one association to Canton
	@OneToMany(mappedBy="provincia")
	private List<Canton> cantons;

	public Provincia() {
	}

	public Integer getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public List<Canton> getCantons() {
		return this.cantons;
	}

	public void setCantons(List<Canton> cantons) {
		this.cantons = cantons;
	}

	public Canton addCanton(Canton canton) {
		getCantons().add(canton);
		canton.setProvincia(this);

		return canton;
	}

	public Canton removeCanton(Canton canton) {
		getCantons().remove(canton);
		canton.setProvincia(null);

		return canton;
	}

	@Override
	public String toString() {
		return this.provincia;
	}

}