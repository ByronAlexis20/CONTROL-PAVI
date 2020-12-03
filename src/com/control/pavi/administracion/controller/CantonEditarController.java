package com.control.pavi.administracion.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Canton;
import com.control.pavi.model.Provincia;
import com.control.pavi.model.dao.ProvinciaDAO;
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

public class CantonEditarController {
	@FXML private Button btnSalir;
	@FXML private TextField txtCanton;
	@FXML private ComboBox<Provincia> cboProvincia;
	@FXML private TextField txtCodigo;
	@FXML private Button btnGrabar;

	Canton canton;
	ProvinciaDAO provinciaDAO = new ProvinciaDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			
			btnGrabar.getStyleClass().add("botonGrabar");
			btnSalir.getStyleClass().add("botonSalir");
			
			llenarComboProvincia();
			txtCodigo.setText("0");
			txtCodigo.setDisable(true);
			txtCanton.requestFocus();
			txtCanton.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtCanton.getText().toUpperCase();
					txtCanton.setText(cadena);
				}
			});
			
			if(Context.getInstance().getCanton() != null) {
				canton = Context.getInstance().getCanton();
				cargarDatos();
				Context.getInstance().setCanton(null);
			}else {
				canton = new Canton();
			}
		}catch(Exception ex) {
			
		}
	}
	private void cargarDatos() {
		txtCodigo.setText(String.valueOf(canton.getIdCanton()));
		txtCanton.setText(canton.getCanton());
		cboProvincia.getSelectionModel().select(canton.getProvincia());
	}
	private void llenarComboProvincia(){
		try{
			cboProvincia.setPromptText("Seleccionar provincia");
			List<Provincia> listaPerfiles;
			listaPerfiles = provinciaDAO.buscarPorPatron("");
			ObservableList<Provincia> datos = FXCollections.observableArrayList();
			datos.addAll(listaPerfiles);
			cboProvincia.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void grabar() {
		try {
    		if(txtCanton.getText().toString().isEmpty()) {
    			helper.mostrarAlertaAdvertencia("Ingresar Nombre del Cantón", Context.getInstance().getStage());
    			txtCanton.requestFocus();
    			return;
    		}
    		if(cboProvincia.getSelectionModel().getSelectedItem() == null) {
    			helper.mostrarAlertaAdvertencia("Debe seleccionar la provincia", Context.getInstance().getStage());
    			return;
    		}
    		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				provinciaDAO.getEntityManager().getTransaction().begin();
				
				canton.setCanton(txtCanton.getText());
				canton.setProvincia(cboProvincia.getSelectionModel().getSelectedItem());
				canton.setEstado(true);
				if(canton.getIdCanton()== null)
					provinciaDAO.getEntityManager().persist(canton);
				else
					provinciaDAO.getEntityManager().merge(canton);
				
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
