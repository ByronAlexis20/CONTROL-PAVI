package com.control.pavi.administracion.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Canton;
import com.control.pavi.model.Parroquia;
import com.control.pavi.model.Provincia;
import com.control.pavi.model.ZonaRural;
import com.control.pavi.model.dao.CantonDAO;
import com.control.pavi.model.dao.ParroquiaDAO;
import com.control.pavi.model.dao.ProvinciaDAO;
import com.control.pavi.model.dao.RecintoDAO;
import com.control.pavi.model.dao.ZonaRuralDAO;
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

public class ZonaEditarController {
	@FXML private Button btnSalir;
    @FXML private ComboBox<Parroquia> cboParroquia;
    @FXML private ComboBox<Provincia> cboProvincia;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtZona;
    @FXML private Button btnGrabar;
    @FXML private ComboBox<Canton> cboCanton;

    ProvinciaDAO provinciaDAO = new ProvinciaDAO();
    CantonDAO cantonDAO = new CantonDAO();
    ParroquiaDAO parroquiaDAO = new ParroquiaDAO();
    RecintoDAO recintoDAO = new RecintoDAO();
    ZonaRural zona;
    ControllerHelper helper = new ControllerHelper();
    ZonaRuralDAO zonaRuralDAO = new ZonaRuralDAO();
    
    public void initialize() {
    	try {
    		btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			
			btnGrabar.getStyleClass().add("botonGrabar");
			btnSalir.getStyleClass().add("botonSalir");
			
			txtCodigo.setText("0");
			txtCodigo.setDisable(true);
			llenarComboProvincia();
			txtZona.requestFocus();
			txtZona.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtZona.getText().toUpperCase();
					txtZona.setText(cadena);
				}
			});
			if(Context.getInstance().getZonaRural() != null) {
				zona = Context.getInstance().getZonaRural();
				cargarDatos();
				Context.getInstance().setZonaRural(null);
			}else {
				zona = new ZonaRural();
			}
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    
    private void cargarDatos() {
    	txtCodigo.setText(String.valueOf(zona.getIdZona()));
    	txtZona.setText(zona.getNombre());
    	
    	cboProvincia.getSelectionModel().select(zona.getParroquia().getCanton().getProvincia());
    	cambiarCanton();
    	cboCanton.getSelectionModel().select(zona.getParroquia().getCanton());
    	cambiarParroquia();
    	cboParroquia.getSelectionModel().select(zona.getParroquia());
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
    		if(txtZona.getText().toString().isEmpty()) {
    			helper.mostrarAlertaAdvertencia("Ingresar Nombre de la Zona", Context.getInstance().getStage());
    			txtZona.requestFocus();
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
				zonaRuralDAO.getEntityManager().getTransaction().begin();
				
				zona.setNombre(txtZona.getText());
				zona.setParroquia(cboParroquia.getSelectionModel().getSelectedItem());
				zona.setEstado("A");
				if(zona.getIdZona() == null)
					zonaRuralDAO.getEntityManager().persist(zona);
				else
					zonaRuralDAO.getEntityManager().merge(zona);
				
				zonaRuralDAO.getEntityManager().getTransaction().commit();
				
				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
			}
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }

    
    public void salir() {
    	try {
    		Context.getInstance().getStageModal().close();
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
}
