package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="canton")
@NamedQueries({
	@NamedQuery(name="Canton.findAll", query="SELECT c FROM Canton c"),
	@NamedQuery(name="Canton.bucarPatron", query="SELECT c FROM Canton c where c.estado = 1 and lower(c.canton) like lower(:patron) order by c.idCanton asc"),
	@NamedQuery(name="Canton.bucarPorProvincia", query="SELECT c FROM Canton c where c.estado = 1 and c.provincia.idProvincia = :id order by c.idCanton asc")
})
public class Canton implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_canton")
	private Integer idCanton;

	private String canton;

	private boolean estado;

	//bi-directional many-to-one association to Provincia
	@ManyToOne
	@JoinColumn(name="id_provincia")
	private Provincia provincia;

	//bi-directional many-to-one association to Parroquia
	@OneToMany(mappedBy="canton", cascade = CascadeType.ALL)
	private List<Parroquia> parroquias;

	public Canton() {
	}

	public Integer getIdCanton() {
		return this.idCanton;
	}

	public void setIdCanton(Integer idCanton) {
		this.idCanton = idCanton;
	}

	public String getCanton() {
		return this.canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Provincia getProvincia() {
		return this.provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public List<Parroquia> getParroquias() {
		return this.parroquias;
	}

	public void setParroquias(List<Parroquia> parroquias) {
		this.parroquias = parroquias;
	}

	public Parroquia addParroquia(Parroquia parroquia) {
		getParroquias().add(parroquia);
		parroquia.setCanton(this);

		return parroquia;
	}

	public Parroquia removeParroquia(Parroquia parroquia) {
		getParroquias().remove(parroquia);
		parroquia.setCanton(null);

		return parroquia;
	}
	
	@Override
	public String toString() {
		return this.canton;
	}

}