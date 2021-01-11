package com.control.pavi.administracion.controller;

import com.control.pavi.model.Canton;
import com.control.pavi.model.Parroquia;
import com.control.pavi.model.Provincia;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    public void initialize() {
    	try {
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    
    void grabar() {
    	try {
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }

    
    public void salir() {
    	try {
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
}
