package com.control.pavi.partido.controller;

import java.util.Optional;

import com.control.pavi.model.PartidoPolitico;
import com.control.pavi.model.dao.PartidoPoliticoDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class PartidoEditarController {
	@FXML private Button btnSalir;
	@FXML private TextField txtNombresCandidato;
	@FXML private TextField txtSlogan;
	@FXML private TextField txtLista;
	@FXML private TextField txtCodigo;
	@FXML private TextField txtDescripcion;
	@FXML private Button btnGrabar;
	@FXML private TextField txtApellidosCandidato;
	@FXML private TextField txtEdad;

	PartidoPoliticoDAO partidoDAO = new PartidoPoliticoDAO();
	ControllerHelper helper = new ControllerHelper();
	PartidoPolitico partido;

	public void initialize() {
		try {
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");

			btnGrabar.getStyleClass().add("botonGrabar");
			btnSalir.getStyleClass().add("botonSalir");
			
			txtCodigo.setText("0");
			txtCodigo.setDisable(true);

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

		txtNombresCandidato.setText(partido.getNombreCandidato());
		txtApellidosCandidato.setText(partido.getApellidoCandidato());
		txtEdad.setText(String.valueOf(partido.getEdad()));

	}

	public void grabar() {
		try {
			if(txtLista.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Ingresar Lista del partido politico", Context.getInstance().getStage());
				txtLista.requestFocus();
				return;
			}

			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				partidoDAO.getEntityManager().getTransaction().begin();

				partido.setDescripcion(txtDescripcion.getText());
				partido.setEstado(true);
				partido.setLista(txtLista.getText());
				partido.setSlogan(txtSlogan.getText());
				partido.setEdad(Integer.parseInt(txtEdad.getText()));
				partido.setNombreCandidato(txtNombresCandidato.getText());
				partido.setApellidoCandidato(txtApellidosCandidato.getText());

				if(partido.getIdPartido()== null)
					partidoDAO.getEntityManager().persist(partido);
				else
					partidoDAO.getEntityManager().merge(partido);

				partidoDAO.getEntityManager().getTransaction().commit();

				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			partidoDAO.getEntityManager().getTransaction().rollback();
		}
	}

	public void salir() {
		try {
			Context.getInstance().getStageModal().close();
		}catch(Exception ex) {

		}
	}
}