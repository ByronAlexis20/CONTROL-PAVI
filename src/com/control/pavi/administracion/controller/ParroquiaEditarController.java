package com.control.pavi.administracion.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Canton;
import com.control.pavi.model.Parroquia;
import com.control.pavi.model.Provincia;
import com.control.pavi.model.Zona;
import com.control.pavi.model.dao.CantonDAO;
import com.control.pavi.model.dao.ParroquiaDAO;
import com.control.pavi.model.dao.ProvinciaDAO;
import com.control.pavi.model.dao.ZonaDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ParroquiaEditarController {
	@FXML private Button btnSalir;
	@FXML private TextField txtParroquia;
	@FXML private ComboBox<Provincia> cboProvincia;
	@FXML private ComboBox<Zona> cboZona;
	@FXML private CheckBox chkEstado;
	@FXML private TextField txtCodigo;
	@FXML private Button btnGrabar;
	@FXML private ComboBox<Canton> cboCanton;

	ProvinciaDAO provinciaDAO = new ProvinciaDAO();
	CantonDAO cantonDAO = new CantonDAO();
	Parroquia parroquia = new Parroquia();
	ParroquiaDAO parroquiaDAO = new ParroquiaDAO();
	ControllerHelper helper = new ControllerHelper();
	ZonaDAO zonaDAO = new ZonaDAO();
	
	public void initialize() {
		try {
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			
			btnGrabar.getStyleClass().add("botonGrabar");
			btnSalir.getStyleClass().add("botonSalir");
			
			llenarComboProvincia();
			llenarComboZona();
			txtCodigo.setText("0");
			txtCodigo.setDisable(true);
			txtParroquia.requestFocus();
			txtParroquia.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtParroquia.getText().toUpperCase();
					txtParroquia.setText(cadena);
				}
			});
			if(Context.getInstance().getParroquia() != null) {
				parroquia = Context.getInstance().getParroquia();
				cargarDatos();
				Context.getInstance().setParroquia(null);
			}else {
				parroquia = new Parroquia();
			}
		}catch(Exception ex) {
		}
	}
	private void cargarDatos() {
		txtCodigo.setText(String.valueOf(parroquia.getIdParroquia()));
		txtParroquia.setText(parroquia.getParroquia());
		cboProvincia.getSelectionModel().select(parroquia.getCanton().getProvincia());
		CambiarCanton();
		cboCanton.getSelectionModel().select(parroquia.getCanton());
		if(parroquia.getZona() != null)
			cboZona.getSelectionModel().select(parroquia.getZona());
	}
	private void llenarComboProvincia(){
		try{
			cboProvincia.setPromptText("Seleccionar provincia");
			List<Provincia> lista;
			lista = provinciaDAO.buscarPorPatron("");
			ObservableList<Provincia> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboProvincia.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	private void llenarComboZona(){
		try{
			cboZona.setPromptText("Seleccionar zona");
			List<Zona> lista;
			lista = zonaDAO.buscarActivos();
			ObservableList<Zona> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboZona.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void grabar() {
		try {
    		if(txtParroquia.getText().toString().isEmpty()) {
    			helper.mostrarAlertaAdvertencia("Ingresar Nombre de la parroquia", Context.getInstance().getStage());
    			txtParroquia.requestFocus();
    			return;
    		}
    		if(cboProvincia.getSelectionModel().getSelectedItem() == null) {
    			helper.mostrarAlertaAdvertencia("Debe seleccionar la provincia", Context.getInstance().getStage());
    			return;
    		}
    		if(cboCanton.getSelectionModel().getSelectedItem() == null) {
    			helper.mostrarAlertaAdvertencia("Debe seleccionar el Cantón", Context.getInstance().getStage());
    			return;
    		}
    		if(cboZona.getSelectionModel().getSelectedItem() == null) {
    			helper.mostrarAlertaAdvertencia("Debe seleccionar la Zona", Context.getInstance().getStage());
    			return;
    		}
    		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				parroquiaDAO.getEntityManager().getTransaction().begin();
				
				parroquia.setCanton(cboCanton.getSelectionModel().getSelectedItem());
				parroquia.setParroquia(txtParroquia.getText());
				parroquia.setZona(cboZona.getSelectionModel().getSelectedItem());
				parroquia.setEstado(true);
				if(parroquia.getIdParroquia() == null)
					parroquiaDAO.getEntityManager().persist(parroquia);
				else
					parroquiaDAO.getEntityManager().merge(parroquia);
				
				parroquiaDAO.getEntityManager().getTransaction().commit();
				
				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
			}
    	}catch(Exception ex) {
    		parroquiaDAO.getEntityManager().getTransaction().rollback();
    	}
	}

	public void CambiarCanton() {
		try {
			cboCanton.getItems().clear();
			cboCanton.setPromptText("Seleccionar cantón");
			List<Canton> lista;
			lista = cantonDAO.buscarPorIdProvincia(cboProvincia.getSelectionModel().getSelectedItem().getIdProvincia());
			ObservableList<Canton> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboCanton.setItems(datos);
		}catch(Exception ex) {
			
		}
	}
	public void salir() {
		try {
    		Context.getInstance().getStageModal().close();
    	}catch(Exception ex) {
    		
    	}
	}
}
