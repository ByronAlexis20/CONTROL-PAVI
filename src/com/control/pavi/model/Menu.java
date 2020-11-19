package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="menu")
@NamedQueries({
	@NamedQuery(name="Menu.findAll", query="SELECT s FROM Menu s ORDER BY s.posicion"),
	@NamedQuery(name="Menu.buscarMenu", query="SELECT s FROM Menu s where s.idMenuPadre <> 0 ORDER BY s.idMenuPadre"),
	@NamedQuery(name="Menu.BuscarPadre", query="SELECT s FROM Menu s where s.idMenuPadre = 0"),
	@NamedQuery(name="Menu.BuscarPadrePorId", query="SELECT s FROM Menu s where (s.idMenu = :patron) ORDER BY s.posicion")
})
public class Menu implements Serializable, Comparable<Menu> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_menu")
	private Integer idMenu;

	private String descripcion;

	private boolean estado;

	private String formulario;

	@Column(name="id_menu_padre")
	private Integer idMenuPadre;

	private int posicion;
	
	private String icono;

	//bi-directional many-to-one association to Permiso
	@OneToMany(mappedBy="menu")
	private List<Permiso> permisos;

	public Menu() {
	}

	public Integer getIdMenu() {
		return this.idMenu;
	}

	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getFormulario() {
		return this.formulario;
	}

	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}

	public Integer getIdMenuPadre() {
		return this.idMenuPadre;
	}

	public void setIdMenuPadre(Integer idMenuPadre) {
		this.idMenuPadre = idMenuPadre;
	}

	public int getPosicion() {
		return this.posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public List<Permiso> getPermisos() {
		return this.permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	public Permiso addPermiso(Permiso permiso) {
		getPermisos().add(permiso);
		permiso.setMenu(this);

		return permiso;
	}

	public Permiso removePermiso(Permiso permiso) {
		getPermisos().remove(permiso);
		permiso.setMenu(null);

		return permiso;
	}
	
	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	@Override
    public int compareTo(Menu o) {
        if (this.posicion < o.posicion) {
            return -1;
        }
        if (this.posicion  > o.posicion) {
            return 1;
        }
        return 0;
    }
}