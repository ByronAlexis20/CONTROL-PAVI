package com.control.pavi.partido.controller;

import com.control.pavi.model.Candidato;
import com.control.pavi.model.PartidoPolitico;
import com.control.pavi.model.dao.PartidoPoliticoDAO;
import com.control.pavi.util.Context;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PartidoEditarController {
	@FXML private Button btnSalir;
	@FXML private TextField txtNombresCandidato;
	@FXML private TextField txtSlogan;
	@FXML private TextField txtLista;
	@FXML private TextField txtCodigo;
	@FXML private Button btnAgregar;
	@FXML private TextField txtDescripcion;
	@FXML private Button btnGrabar;
	@FXML private TextField txtApellidosCandidato;
	@FXML private Button btnQuitar;

	PartidoPoliticoDAO partidoDAO = new PartidoPoliticoDAO();
	PartidoPolitico partido;
	Candidato candidato;
	
	public void initialize() {
		try {
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			btnAgregar.setStyle("-fx-cursor: hand;");
			btnQuitar.setStyle("-fx-cursor: hand;");
			txtCodigo.setText("0");
			txtCodigo.setDisable(true);
			txtNombresCandidato.setDisable(true);
			txtApellidosCandidato.setDisable(true);
			
			txtLista.requestFocus();
			
			if(Context.getInstance().getPartido() != null) {
				partido = Context.getInstance().getPartido();
				cargarDatos();
				Context.getInstance().setPartido(null);
			}else {
				partido = new PartidoPolitico();
			}
		}catch(Exception ex) {
			
		}
	}
	private void cargarDatos() {
		txtCodigo.setText(String.valueOf(partido.getIdPartido()));
		txtLista.setText(partido.getLista());
		txtSlogan.setText(partido.getSlogan());
		txtDescripcion.setText(partido.getDescripcion());
		//datos del candidato
		if(partido.getCandidato() != null) {
			candidato = partido.getCandidato();
			txtNombresCandidato.setText(candidato.getNombre());
			txtApellidosCandidato.setText(candidato.getApellido());
		}
	}
	
	public void grabar() {

	}

	public void salir() {

	}

	public void agregarCandidato() {

	}

	
	public void quitarCandidato() {

	}
}
