package com.control.pavi.util;

import java.sql.Connection;
import java.util.List;

import com.control.pavi.model.Candidato;
import com.control.pavi.model.Canton;
import com.control.pavi.model.JuntaVoto;
import com.control.pavi.model.Parroquia;
import com.control.pavi.model.PartidoPolitico;
import com.control.pavi.model.Perfil;
import com.control.pavi.model.Provincia;
import com.control.pavi.model.Recinto;
import com.control.pavi.model.Representante;
import com.control.pavi.model.Supervisor;
import com.control.pavi.model.SupervisorPrincipal;
import com.control.pavi.model.Usuario;
import com.control.pavi.model.ZonaRural;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Context {
	private final static Context instance = new Context();

	Connection conexion = null;

	private Stage stage;
	private Stage stageModal;
	private Stage stageModalSecundario;
	private Stage stagePrincipal;
	private AnchorPane apInicioSesion;
	
	private Usuario usuario;
	private Usuario usuarioEditar;
	private Perfil perfil;
	private Provincia provincia;
	private Canton canton;
	private Parroquia parroquia;
	private Recinto recinto;
	private PartidoPolitico partido;
	private Candidato candidato;
	private Representante representante;
	private JuntaVoto juntaVoto;
	private Supervisor supervisor;
	private SupervisorPrincipal supervisorPrincipal;
	private List<JuntaVoto> listaJuntaVoto;
	private List<Supervisor> listaSupervisor;
	private ZonaRural zonaRural;
	
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

	public Stage getStageModalSecundario() {
		return stageModalSecundario;
	}

	public void setStageModalSecundario(Stage stageModalSecundario) {
		this.stageModalSecundario = stageModalSecundario;
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

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Canton getCanton() {
		return canton;
	}

	public void setCanton(Canton canton) {
		this.canton = canton;
	}

	public Parroquia getParroquia() {
		return parroquia;
	}

	public void setParroquia(Parroquia parroquia) {
		this.parroquia = parroquia;
	}

	public Recinto getRecinto() {
		return recinto;
	}

	public void setRecinto(Recinto recinto) {
		this.recinto = recinto;
	}

	public PartidoPolitico getPartido() {
		return partido;
	}

	public void setPartido(PartidoPolitico partido) {
		this.partido = partido;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public JuntaVoto getJuntaVoto() {
		return juntaVoto;
	}

	public void setJuntaVoto(JuntaVoto juntaVoto) {
		this.juntaVoto = juntaVoto;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public List<JuntaVoto> getListaJuntaVoto() {
		return listaJuntaVoto;
	}

	public void setListaJuntaVoto(List<JuntaVoto> listaJuntaVoto) {
		this.listaJuntaVoto = listaJuntaVoto;
	}

	public SupervisorPrincipal getSupervisorPrincipal() {
		return supervisorPrincipal;
	}

	public void setSupervisorPrincipal(SupervisorPrincipal supervisorPrincipal) {
		this.supervisorPrincipal = supervisorPrincipal;
	}

	public List<Supervisor> getListaSupervisor() {
		return listaSupervisor;
	}

	public void setListaSupervisor(List<Supervisor> listaSupervisor) {
		this.listaSupervisor = listaSupervisor;
	}

	public ZonaRural getZonaRural() {
		return zonaRural;
	}

	public void setZonaRural(ZonaRural zonaRural) {
		this.zonaRural = zonaRural;
	}
	
}