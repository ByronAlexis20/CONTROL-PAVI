package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.control.pavi.model.AsignacionJunta;
import com.control.pavi.model.JuntaVoto;
import com.control.pavi.model.PartidoPolitico;
import com.control.pavi.model.Representante;
import com.control.pavi.model.dao.AsignacionJuntaDAO;
import com.control.pavi.model.dao.PartidoPoliticoDAO;
import com.control.pavi.model.dao.RepresentanteDAO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AsignarDelegadosController {
	@FXML private Button btnSalir;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtEdad;
    @FXML private ComboBox<PartidoPolitico> cboPartidoPolitico;
    @FXML private TextField txtNombres;
    @FXML private TextField txtCedula;
    @FXML private Button btnGrabar;
    @FXML private Label lblMensajeCedula;

    RepresentanteDAO representanteDAO = new RepresentanteDAO();
    PartidoPoliticoDAO partidoDAO = new PartidoPoliticoDAO();
    AsignacionJuntaDAO asignacionDAO = new AsignacionJuntaDAO();
    ControllerHelper helper = new ControllerHelper();
    
    JuntaVoto junta;
    Representante representante = new Representante();
    
    public void initialize() {
    	try {
    		btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			
			btnGrabar.getStyleClass().add("botonGrabar");
			btnSalir.getStyleClass().add("botonSalir");
			
			llenarComboPartido();
			if(Context.getInstance().getJuntaVoto() != null) {
				junta = Context.getInstance().getJuntaVoto();
				Context.getInstance().setJuntaVoto(null);
			}
			txtCedula.focusedProperty().addListener(new ChangeListener<Boolean>() {
			    @Override
			    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
			        if (newPropertyValue) {
			        }
			        else {
			        	recuperarDelegado(txtCedula.getText());
			        }
			    }
			});
    	}catch(Exception ex) {
    	}
    }
  
    
    private void llenarComboPartido(){
		try{
			cboPartidoPolitico.setPromptText("Seleccionar partido politico");
			List<PartidoPolitico> lista;
			lista = partidoDAO.buscarPorPatron("");
			ObservableList<PartidoPolitico> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboPartidoPolitico.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
    
    private void recuperarDelegado(String cedula) {
    	try {
    		List<Representante> lista = representanteDAO.buscarPorCedula(cedula);
    		if(lista.size() > 0) {//si tiene datos
    			txtCedula.setText(lista.get(0).getNoIdentificacion());
    			txtNombres.setText(lista.get(0).getNombre());
    			txtApellidos.setText(lista.get(0).getApellidos());
    			txtEdad.setText(String.valueOf(lista.get(0).getEdad()));
    			representante =lista.get(0);
    		}
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    
    public void grabar() {
    	try {
			if(validarDatos() == false)
				return;

			representante.setEstado(true);
			representante.setApellidos(txtApellidos.getText());
			representante.setEdad(Integer.parseInt(txtEdad.getText()));
			representante.setNoIdentificacion(txtCedula.getText());
			representante.setNombre(txtNombres.getText());
			representante.setPartidoPolitico(cboPartidoPolitico.getSelectionModel().getSelectedItem());
			
			//agregar como delegado
			AsignacionJunta asignacion = new AsignacionJunta();
			asignacion.setEstado(true);
			asignacion.setDescripcion("");
			asignacion.setJunta(junta);
			asignacion.setRepresentante(representante);
			
			List<AsignacionJunta> lista = new ArrayList<AsignacionJunta>();
			lista.add(asignacion);
			
			representante.setAsignacionJuntas(lista);
			
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				representanteDAO.getEntityManager().getTransaction().begin();
				if(representante.getIdRepresentante() == null) {//inserta
					representanteDAO.getEntityManager().persist(representante);
				}else {//modifica
					representanteDAO.getEntityManager().merge(representante);
				}
				representanteDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				salir();
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			representanteDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
    }

    boolean validarDatos() {
		try {
			if(txtCedula.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Debe ingresar cedula del Usuario", Context.getInstance().getStage());
				txtCedula.requestFocus();
				return false;
			}
			if(helper.validarDeCedula(txtCedula.getText()) == false) {
				helper.mostrarAlertaAdvertencia("Cédula Ingresada Incorrecta", Context.getInstance().getStage());
				txtCedula.requestFocus();
				return false;
			}
			if(txtNombres.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del Usuario", Context.getInstance().getStage());
				txtNombres.requestFocus();
				return false;
			}
			if(txtApellidos.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el apellidos del Usuario", Context.getInstance().getStage());
				txtApellidos.requestFocus();
				return false;
			}
			if(cboPartidoPolitico.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar el partido politico", Context.getInstance().getStage());
				return false;
			}
			//validar si el delegado ya esta en otra junta
			if(representante.getIdRepresentante() != null) {
				List<AsignacionJunta> lista = asignacionDAO.buscarPordelegadoJunta(representante.getIdRepresentante());
				if(lista.size() > 0) {
					helper.mostrarAlertaAdvertencia("Delegado ya esta asignado en otra junta", Context.getInstance().getStage());
					return false;
				}
			}
			
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
    
    public void salir() {
    	Context.getInstance().getStageModal().close();
    }
}
