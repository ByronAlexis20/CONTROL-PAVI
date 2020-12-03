package com.control.pavi.administracion.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Canton;
import com.control.pavi.model.dao.CantonDAO;
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

public class CantonListaController {
	@FXML private TableView<Canton> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;

	CantonDAO cantonDAO = new CantonDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnEditar.setStyle("-fx-cursor: hand;");
			btnEliminar.setStyle("-fx-cursor: hand;");
			btnNuevo.setStyle("-fx-cursor: hand;");
			
			btnEditar.getStyleClass().add("botonEditar");
			btnNuevo.getStyleClass().add("botonNuevo");
			btnEliminar.getStyleClass().add("botonEliminar");
			
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
						buscar();
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
			List<Canton> lista;
			ObservableList<Canton> datos = FXCollections.observableArrayList();
			lista = cantonDAO.buscarPorPatron(busqueda);
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<Canton, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Canton,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Canton, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdCanton()));
				}
			});
			TableColumn<Canton, String> provinciaColum = new TableColumn<>("Provincia");
			provinciaColum.setMinWidth(10);
			provinciaColum.setPrefWidth(200);
			provinciaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Canton,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Canton, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getProvincia().getProvincia());
				}
			});
			
			TableColumn<Canton, String> cantonColum = new TableColumn<>("Cantón");
			cantonColum.setMinWidth(10);
			cantonColum.setPrefWidth(200);
			cantonColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Canton,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Canton, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getCanton());
				}
			});
			
			TableColumn<Canton, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Canton,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Canton, String> param) {
					String estado = "";
					if(param.getValue().getEstado() == true)
						estado = "Activo";
					else
						estado = "Inactivo";
					return new SimpleObjectProperty<String>(estado);
				}
			});

			
			tvDatos.getColumns().addAll(idColum,provinciaColum,cantonColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void buscar() {
		try {
			List<Canton> lista;
			ObservableList<Canton> datos = FXCollections.observableArrayList();
			lista = cantonDAO.buscarPorPatron(txtBuscar.getText().toString());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevo() {
		try {
			Context.getInstance().setCanton(null);
			helper.abrirPantallaModal("/administracion/CantonEditar.fxml","Datos del cantón", Context.getInstance().getStage());
			llenarDatos("");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void editar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Context.getInstance().setCanton(tvDatos.getSelectionModel().getSelectedItem());
				helper.abrirPantallaModal("/administracion/CantonEditar.fxml","Datos del cantón", Context.getInstance().getStage());
				llenarDatos("");
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Cantón a Editar", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void eliminar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a dar de baja el Cantón.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					Canton seleccion = new Canton();
					seleccion = tvDatos.getSelectionModel().getSelectedItem();
					seleccion.setEstado(false);
					cantonDAO.getEntityManager().getTransaction().begin();
					cantonDAO.getEntityManager().merge(seleccion);
					cantonDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Cantón dado de baja", Context.getInstance().getStage());
					llenarDatos("");
				}
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Cantón a Dar de Baja", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
