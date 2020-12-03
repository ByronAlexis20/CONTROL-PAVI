package com.control.pavi.administracion.controller;

import java.util.Optional;

import com.control.pavi.model.Provincia;
import com.control.pavi.model.dao.ProvinciaDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class ProvinciaEditarController {
	@FXML private TextField txtProvincia;
    @FXML private Button btnSalir;
    @FXML private TextField txtCodigo;
    @FXML private Button btnGrabar;
    
    Provincia provincia;
    ProvinciaDAO provinciaDAO = new ProvinciaDAO();
    ControllerHelper helper = new ControllerHelper();
    
    public void initialize() {
    	try {
    		btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			
			btnGrabar.getStyleClass().add("botonGrabar");
			btnSalir.getStyleClass().add("botonSalir");
			
			txtCodigo.setText("0");
			txtCodigo.setDisable(true);
			txtProvincia.requestFocus();
			txtProvincia.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtProvincia.getText().toUpperCase();
					txtProvincia.setText(cadena);
				}
			});
			
			if(Context.getInstance().getProvincia() != null) {
				provincia = Context.getInstance().getProvincia();
				Context.getInstance().setProvincia(null);
				cargarDatos();
			}else {
				provincia = new Provincia();
			}
    	}catch(Exception ex) {
    		
    	}
    }
    
    private void cargarDatos() {
    	txtCodigo.setText(String.valueOf(provincia.getIdProvincia()));
    	txtProvincia.setText(provincia.getProvincia());
    }
    
    public void grabar() {
    	try {
    		if(txtProvincia.getText().toString().isEmpty()) {
    			helper.mostrarAlertaAdvertencia("Ingresar Nombre de la provincia", Context.getInstance().getStage());
    			txtProvincia.requestFocus();
    			return;
    		}
    		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				provinciaDAO.getEntityManager().getTransaction().begin();
				
				provincia.setProvincia(txtProvincia.getText());
				provincia.setEstado(true);
				if(provincia.getIdProvincia() == null)
					provinciaDAO.getEntityManager().persist(provincia);
				else
					provinciaDAO.getEntityManager().merge(provincia);
				
				provinciaDAO.getEntityManager().getTransaction().commit();
				
				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
			}
    	}catch(Exception ex) {
    		provinciaDAO.getEntityManager().getTransaction().rollback();
    	}
    }

    public void salir() {
    	try {
    		Context.getInstance().getStageModal().close();
    	}catch(Exception ex) {
    		
    	}
    }

}
