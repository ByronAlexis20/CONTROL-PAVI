package com.control.pavi.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="usuario")
@NamedQueries({
	@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="Usuario.buscarUsuario", query="SELECT u FROM Usuario u WHERE "
			+ "u.usuario = (:usu) and u.clave = (:cla) and u.estado = 1"),
	@NamedQuery(name="Usuario.validarUsuario", query="SELECT u FROM Usuario u "
			+ "WHERE u.usuario = (:usuario) AND u.idUsuario <> (:idUsuario) and u.estado = 1"),
	@NamedQuery(name="Usuario.buscarPorRol", query="SELECT u FROM Usuario u WHERE u.perfil.idPerfil = :idRol and u.estado = 1"),
	@NamedQuery(name="Usuario.recuperaUsuario", query="SELECT u FROM Usuario u WHERE u.cedula = (:cedula) and u.estado = 1"),
	@NamedQuery(name="Usuario.recuperaUsuarioPorId", query="SELECT u FROM Usuario u WHERE u.idUsuario = (:idUsuario) and u.estado = 1"),
})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private int idUsuario;

	private String apellidos;

	private String cedula;

	private String clave;

	private String direccion;

	private String email;

	private boolean estado;

	@Lob
	private byte[] foto;

	private String nombres;

	private String telefono;

	private String usuario;

	//bi-directional many-to-one association to Perfil
	@ManyToOne
	@JoinColumn(name="id_perfil")
	private Perfil perfil;

	public Usuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}