package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.control.pavi.model.JuntaVoto;
import com.control.pavi.model.Recinto;
import com.control.pavi.model.dao.JuntaVotoDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DatosJuntaController {
	@FXML private Button btnSalir;
	@FXML private TextField txtNumero;
	@FXML private ComboBox<String> cboGenero;
	@FXML private TextField txtCodigo;
	@FXML private TextField txtDescripcion;
	@FXML private Button btnGrabar;

	ControllerHelper helper = new ControllerHelper();
	Recinto recinto;
	JuntaVotoDAO juntaDAO = new JuntaVotoDAO();
	JuntaVoto junta = new JuntaVoto();
	
	
	public void initialize() {
		try {
			llenarComboGenero();
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			txtCodigo.setText("0");
			txtCodigo.setDisable(true);
			txtNumero.requestFocus();
			txtNumero.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					String cadena = txtNumero.getText().toUpperCase();
					txtNumero.setText(cadena);
				}
			});
			
			if(Context.getInstance().getRecinto() != null) {
				recinto = Context.getInstance().getRecinto();
				Context.getInstance().setRecinto(null);
			}else {
				recinto = new Recinto();
			}
		}catch(Exception ex) {
		}
	}
	
	private void llenarComboGenero(){
		try{
			cboGenero.setPromptText("Seleccionar Género");
			List<String> lista = new ArrayList<>();
			lista.add("Masculino");
			lista.add("Femenino");
			ObservableList<String> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboGenero.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void grabar() {
		try {
			if(cboGenero.getSelectionModel().getSelectedItem() == null) {
    			helper.mostrarAlertaAdvertencia("Debe seleccionar género", Context.getInstance().getStageModal());
    			return;
    		}
			if(txtNumero.getText().toString().isEmpty()) {
    			helper.mostrarAlertaAdvertencia("Ingresar Número", Context.getInstance().getStage());
    			txtNumero.requestFocus();
    			return;
    		}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStageModal());
			if(result.get() == ButtonType.OK){
				juntaDAO.getEntityManager().getTransaction().begin();
				junta.setIdJunta(null);
				junta.setDescripcion(txtDescripcion.getText());
				junta.setEstado(true);
				junta.setGenero(cboGenero.getSelectionModel().getSelectedItem());
				junta.setNumero(Integer.parseInt(txtNumero.getText()));
				junta.setRecinto(recinto);
				juntaDAO.getEntityManager().persist(junta);
				juntaDAO.getEntityManager().getTransaction().commit();
				
				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			juntaDAO.getEntityManager().getTransaction().rollback();
		}
	}
	public void salir() {
		try {
			Context.getInstance().getStageModal().close();
		}catch(Exception ex) {
			
		}
	}
}
