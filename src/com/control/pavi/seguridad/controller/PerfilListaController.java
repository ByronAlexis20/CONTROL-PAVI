package com.control.pavi.seguridad.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Perfil;
import com.control.pavi.model.dao.PerfilDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class PerfilListaController {
	@FXML private TableView<Perfil> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;

	PerfilDAO perfilDAO = new PerfilDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnEditar.setStyle("-fx-cursor: hand;");
			btnEliminar.setStyle("-fx-cursor: hand;");
			btnNuevo.setStyle("-fx-cursor: hand;");
			txtBuscar.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtBuscar.getText().toUpperCase();
					txtBuscar.setText(cadena);
				}
			});
			txtBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarPerfil();
					} 
				} 
			}); 
			llenarDatos("");
		}catch(Exception ex) {
			
		}
	}
	@SuppressWarnings("unchecked")
	void llenarDatos(String busqueda){
		try{
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<Perfil> listaPerfil;
			ObservableList<Perfil> datos = FXCollections.observableArrayList();
			listaPerfil = perfilDAO.buscarPorPatron(busqueda);
			datos.setAll(listaPerfil);

			//llenar los datos en la tabla
			TableColumn<Perfil, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Perfil,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Perfil, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdPerfil()));
				}
			});
			TableColumn<Perfil, String> perfilColum = new TableColumn<>("Perfil");
			perfilColum.setMinWidth(10);
			perfilColum.setPrefWidth(260);
			perfilColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Perfil,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Perfil, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getPerfil());
				}
			});
			TableColumn<Perfil, String> descripcionColum = new TableColumn<>("Descripción");
			descripcionColum.setMinWidth(10);
			descripcionColum.setPrefWidth(260);
			descripcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Perfil,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Perfil, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getDescripcion());
				}
			});
			
			TableColumn<Perfil, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Perfil,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Perfil, String> param) {
					String estado = "";
					if(param.getValue().getEstado() == true)
						estado = "Activo";
					else
						estado = "Inactivo";
					return new SimpleObjectProperty<String>(estado);
				}
			});

			
			tvDatos.getColumns().addAll(idColum,perfilColum,descripcionColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void buscarPerfil() {
		try {
			List<Perfil> listaClientes;
			ObservableList<Perfil> datos = FXCollections.observableArrayList();
			listaClientes = perfilDAO.buscarPorPatron(txtBuscar.getText().toString());
			datos.setAll(listaClientes);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevoPerfil() {
		try {
			Context.getInstance().setPerfil(null);
			helper.abrirPantallaModal("/seguridad/PerfilEditar.fxml","Datos del Perfil", Context.getInstance().getStage());
			llenarDatos("");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void editarPerfil() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Context.getInstance().setPerfil(tvDatos.getSelectionModel().getSelectedItem());
				helper.abrirPantallaModal("/seguridad/PerfilEditar.fxml","Datos del Perfil", Context.getInstance().getStage());
				llenarDatos("");
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Perfil a Editar", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void eliminarPerfil() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a dar de baja al Perfil.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					Perfil seleccion = new Perfil();
					seleccion = tvDatos.getSelectionModel().getSelectedItem();
					seleccion.setEstado(false);
					perfilDAO.getEntityManager().getTransaction().begin();
					perfilDAO.getEntityManager().merge(seleccion);
					perfilDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Perfil Dado de Baja", Context.getInstance().getStage());
					llenarDatos("");
				}
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Perfil a Dar de Baja", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
