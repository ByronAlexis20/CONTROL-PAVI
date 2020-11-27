package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;

import com.control.pavi.model.Canton;
import com.control.pavi.util.Context;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DatosJuntaController {
	@FXML private Button btnSalir;
	@FXML private TextField txtCanton;
	@FXML private ComboBox<String> cboGenero;
	@FXML private TextField txtCodigo;
	@FXML private TextField txtDescripcion;
	@FXML private Button btnGrabar;

	public void initialize() {
		try {
			llenarComboGenero();
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
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
			
			if(Context.getInstance().getRecinto() != null) {
				//canton = Context.getInstance().getCanton();
				//cargarDatos();
				Context.getInstance().setCanton(null);
			}else {
				//canton = new Canton();
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

	}

	public void salir() {

	}
}
