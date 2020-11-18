package com.control.pavi.seguridad.controller;

import java.util.List;

import com.control.pavi.model.Perfil;
import com.control.pavi.model.dao.PerfilDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class Permisos {
	@FXML private TableView<?> tvPermiso;
	@FXML private Button btnGuardar;
	@FXML private TableView<?> tvMenu;
	@FXML private ComboBox<Perfil> cboPerfil;
	@FXML private Button btnAnadir;
	@FXML private Button btnQuitar;

	PerfilDAO perfilDAO = new PerfilDAO();
	
	public void initialize() {
		try {
			
		}catch(Exception ex) {
			
		}
	}
	private void llenarComboPerfil(){
		try{
			cboPerfil.setPromptText("Seleccionar perfil");
			List<Perfil> listaPerfiles;
			listaPerfiles = perfilDAO.buscarPorPatron("");
			ObservableList<Perfil> datos = FXCollections.observableArrayList();
			datos.addAll(listaPerfiles);
			cboPerfil.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void cargarAccesos() {

	}

	public void grabar() {

	}

	public void anadir() {

	}

	public void agregarTodo() {

	}

	public void quitar() {

	}

	public void eliminarTodo() {

	}
}
