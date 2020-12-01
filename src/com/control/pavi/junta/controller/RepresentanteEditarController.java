package com.control.pavi.junta.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.PartidoPolitico;
import com.control.pavi.model.Representante;
import com.control.pavi.model.dao.PartidoPoliticoDAO;
import com.control.pavi.model.dao.RepresentanteDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RepresentanteEditarController {
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
    ControllerHelper helper = new ControllerHelper();
    
    Representante representante;
    
    public void initialize() {
    	try {
    		btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			llenarComboPartido();
			if(Context.getInstance().getRepresentante() != null) {
				representante = Context.getInstance().getRepresentante();
				recuperarDatos();
				Context.getInstance().setRepresentante(null);
			}else {
				representante = new Representante();
			}
    	}catch(Exception ex) {
    		
    	}
    }
    public void recuperarDatos(){
		try{
			txtCedula.setText(representante.getNoIdentificacion());
			txtNombres.setText(representante.getNombre());
			txtApellidos.setText(representante.getApellidos());
			txtEdad.setText(String.valueOf(representante.getEdad()));
			cboPartidoPolitico.getSelectionModel().select(representante.getPartidoPolitico());
		}catch(Exception e) {
			e.printStackTrace();
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
