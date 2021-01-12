package com.control.pavi.administracion.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Canton;
import com.control.pavi.model.Parroquia;
import com.control.pavi.model.Provincia;
import com.control.pavi.model.Recinto;
import com.control.pavi.model.dao.CantonDAO;
import com.control.pavi.model.dao.ParroquiaDAO;
import com.control.pavi.model.dao.ProvinciaDAO;
import com.control.pavi.model.dao.RecintoDAO;
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

public class RecintoEditarController {
	@FXML private TextField txtRecinto;
    @FXML private Button btnSalir;
    @FXML private ComboBox<Parroquia> cboParroquia;
    @FXML private ComboBox<Provincia> cboProvincia;
    @FXML private CheckBox chkEstado;
    @FXML private TextField txtCodigo;
    @FXML private Button btnGrabar;
    @FXML private ComboBox<Canton> cboCanton;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    ProvinciaDAO provinciaDAO = new ProvinciaDAO();
    CantonDAO cantonDAO = new CantonDAO();
    ParroquiaDAO parroquiaDAO = new ParroquiaDAO();
    RecintoDAO recintoDAO = new RecintoDAO();
    Recinto recinto;
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
			txtRecinto.requestFocus();
			txtRecinto.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtRecinto.getText().toUpperCase();
					txtRecinto.setText(cadena);
				}
			});
			if(Context.getInstance().getRecinto() != null) {
				recinto = Context.getInstance().getRecinto();
				cargarDatos();
				Context.getInstance().setRecinto(null);
			}else {
				recinto = new Recinto();
			}
    	}catch(Exception ex) {
    		
    	}
    }
    private void cargarDatos() {
    	txtCodigo.setText(String.valueOf(recinto.getIdRecinto()));
    	txtRecinto.setText(recinto.getRecinto());
    	txtTelefono.setText(recinto.getTelefono());
    	txtDireccion.setText(recinto.getDireccion());
    	cboProvincia.getSelectionModel().select(recinto.getZonaRural().getParroquia().getCanton().getProvincia());
    	cambiarCanton();
    	cboCanton.getSelectionModel().select(recinto.getZonaRural().getParroquia().getCanton());
    	cambiarParroquia();
    	cboParroquia.getSelectionModel().select(recinto.getZonaRural().getParroquia());
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
 
    public void cambiarCanton() {
    	try {
			cboCanton.getItems().clear();
			cboParroquia.getItems().clear();
			cboCanton.setPromptText("Seleccionar cantón");
			List<Canton> lista;
			lista = cantonDAO.buscarPorIdProvincia(cboProvincia.getSelectionModel().getSelectedItem().getIdProvincia());
			ObservableList<Canton> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboCanton.setItems(datos);
		}catch(Exception ex) {
			
		}
    }
    
    public void cambiarParroquia(){
    	try {
			cboParroquia.getItems().clear();
			cboParroquia.setPromptText("Seleccionar parroquia");
			List<Parroquia> lista;
			lista = parroquiaDAO.buscarPorIdCanton(cboCanton.getSelectionModel().getSelectedItem().getIdCanton());
			ObservableList<Parroquia> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboParroquia.setItems(datos);
		}catch(Exception ex) {
			
		}
    }
    public void grabar() {
    	try {
    		if(txtRecinto.getText().toString().isEmpty()) {
    			helper.mostrarAlertaAdvertencia("Ingresar Nombre del Recinto", Context.getInstance().getStage());
    			txtRecinto.requestFocus();
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
    		if(cboParroquia.getSelectionModel().getSelectedItem() == null) {
    			helper.mostrarAlertaAdvertencia("Debe seleccionar la Parroquia", Context.getInstance().getStage());
    			return;
    		}
    		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				recintoDAO.getEntityManager().getTransaction().begin();
				
				//recinto.setParroquia(cboParroquia.getSelectionModel().getSelectedItem());
				recinto.setRecinto(txtRecinto.getText());
				recinto.setDireccion(txtDireccion.getText());
				recinto.setTelefono(txtTelefono.getText());
				
				recinto.setEstado(true);
				if(recinto.getIdRecinto() == null)
					recintoDAO.getEntityManager().persist(recinto);
				else
					recintoDAO.getEntityManager().merge(recinto);
				
				recintoDAO.getEntityManager().getTransaction().commit();
				
				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
			}
    	}catch(Exception ex) {
    		recintoDAO.getEntityManager().getTransaction().rollback();
    	}
    }
    public void salir() {
    	try {
    		Context.getInstance().getStageModal().close();
    	}catch(Exception ex) {
    		
    	}
    }
}
