package com.control.pavi.seguridad.controller;

import java.util.Optional;

import com.control.pavi.model.Perfil;
import com.control.pavi.model.dao.PerfilDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class PerfilEditarController {
	@FXML private Button btnSalir;
	@FXML private TextField txtPerfil;
	@FXML private CheckBox chkEstado;
	@FXML private TextField txtCodigo;
	@FXML private TextField txtDescripcion;
	@FXML private Button btnGrabar;
	
	Perfil perfil;
	PerfilDAO perfilDAO = new PerfilDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			
			btnGrabar.getStyleClass().add("botonGrabar");
			btnSalir.getStyleClass().add("botonSalir");
			
			txtPerfil.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtPerfil.getText().toUpperCase();
					txtPerfil.setText(cadena);
				}
			});
			txtDescripcion.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtDescripcion.getText().toUpperCase();
					txtDescripcion.setText(cadena);
				}
			});
			if(Context.getInstance().getPerfil() != null) {
				perfil = Context.getInstance().getPerfil();
				recuperarDatos(Context.getInstance().getPerfil());
				Context.getInstance().setPerfil(null);
			}else {
				txtCodigo.setText("0");
				perfil = new Perfil();
			}
		}catch(Exception ex) {
		}
	}
	
	private void recuperarDatos(Perfil perfil) {
		txtPerfil.setText(perfil.getPerfil());
		txtDescripcion.setText(perfil.getDescripcion());
		txtCodigo.setText(String.valueOf(perfil.getIdPerfil()));
		chkEstado.setSelected(perfil.getEstado());
	}
	public void grabar() {
		try {
			if(txtPerfil.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Ingresar Nombre de Perfil", Context.getInstance().getStage());
				txtPerfil.requestFocus();
				return;
			}
			if(txtDescripcion.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Ingresar Descripción de Perfil", Context.getInstance().getStage());
				txtDescripcion.requestFocus();
				return;
			}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				perfilDAO.getEntityManager().getTransaction().begin();
				perfil.setDescripcion(txtDescripcion.getText());
				perfil.setEstado(chkEstado.isSelected());
				perfil.setPerfil(txtPerfil.getText());
				if(perfil.getIdPerfil() == null) {
					perfilDAO.getEntityManager().persist(perfil);
				}else {
					perfilDAO.getEntityManager().merge(perfil);
				}
				perfilDAO.getEntityManager().getTransaction().commit();
				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			
		}
	}

	public void salir() {
		Context.getInstance().getStageModal().close();
	}
}
