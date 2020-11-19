package com.control.pavi.util;

import java.sql.Connection;

import com.control.pavi.model.Perfil;
import com.control.pavi.model.Usuario;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Context {
	private final static Context instance = new Context();

	Connection conexion = null;

	private Stage stage;
	private Stage stageModal;
	private Stage stageModalSolicitud;
	private Stage stagePrincipal;
	private AnchorPane apInicioSesion;
	
	private Usuario usuario;
	private Usuario usuarioEditar;
	private Perfil perfil;
	
	public static Context getInstance() {
		return instance;
	}

	public Connection getConexion() {
		return conexion;
	}
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStageModal() {
		return stageModal;
	}

	public void setStageModal(Stage stageModal) {
		this.stageModal = stageModal;
	}

	public Stage getStageModalSolicitud() {
		return stageModalSolicitud;
	}

	public void setStageModalSolicitud(Stage stageModalSolicitud) {
		this.stageModalSolicitud = stageModalSolicitud;
	}

	public Stage getStagePrincipal() {
		return stagePrincipal;
	}

	public void setStagePrincipal(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}

	public AnchorPane getApInicioSesion() {
		return apInicioSesion;
	}

	public void setApInicioSesion(AnchorPane apInicioSesion) {
		this.apInicioSesion = apInicioSesion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Usuario getUsuarioEditar() {
		return usuarioEditar;
	}

	public void setUsuarioEditar(Usuario usuarioEditar) {
		this.usuarioEditar = usuarioEditar;
	}
	
}