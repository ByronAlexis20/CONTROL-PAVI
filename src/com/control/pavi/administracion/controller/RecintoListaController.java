package com.control.pavi.administracion.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Recinto;
import com.control.pavi.model.dao.RecintoDAO;
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

public class RecintoListaController {
	@FXML private TableView<Recinto> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;

	RecintoDAO recintoDAO = new RecintoDAO();
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
			List<Recinto> lista;
			ObservableList<Recinto> datos = FXCollections.observableArrayList();
			lista = recintoDAO.buscarPorPatron(busqueda);
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<Recinto, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Recinto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Recinto, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdRecinto()));
				}
			});
			TableColumn<Recinto, String> provinciaColum = new TableColumn<>("Provincia");
			provinciaColum.setMinWidth(10);
			provinciaColum.setPrefWidth(200);
			provinciaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Recinto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Recinto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getParroquia().getCanton().getProvincia().getProvincia());
				}
			});
			
			TableColumn<Recinto, String> cantonColum = new TableColumn<>("Cantón");
			cantonColum.setMinWidth(10);
			cantonColum.setPrefWidth(200);
			cantonColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Recinto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Recinto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getParroquia().getCanton().getCanton());
				}
			});
			
			TableColumn<Recinto, String> parroquiaColum = new TableColumn<>("Parroquia");
			parroquiaColum.setMinWidth(10);
			parroquiaColum.setPrefWidth(200);
			parroquiaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Recinto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Recinto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getParroquia().getParroquia());
				}
			});
			
			TableColumn<Recinto, String> recintoColum = new TableColumn<>("Recinto");
			recintoColum.setMinWidth(10);
			recintoColum.setPrefWidth(200);
			recintoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Recinto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Recinto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRecinto());
				}
			});
			
			
			TableColumn<Recinto, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Recinto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Recinto, String> param) {
					String estado = "";
					if(param.getValue().getEstado() == true)
						estado = "Activo";
					else
						estado = "Inactivo";
					return new SimpleObjectProperty<String>(estado);
				}
			});

			
			tvDatos.getColumns().addAll(idColum,provinciaColum,cantonColum,parroquiaColum,recintoColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void buscar() {
		try {
			List<Recinto> lista;
			ObservableList<Recinto> datos = FXCollections.observableArrayList();
			lista = recintoDAO.buscarPorPatron(txtBuscar.getText().toString());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevo() {
		try {
			Context.getInstance().setRecinto(null);
			helper.abrirPantallaModal("/administracion/RecintoEditar.fxml","Datos del recinto", Context.getInstance().getStage());
			llenarDatos("");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void editar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Context.getInstance().setRecinto(tvDatos.getSelectionModel().getSelectedItem());
				helper.abrirPantallaModal("/administracion/RecintoEditar.fxml","Datos del recinto", Context.getInstance().getStage());
				llenarDatos("");
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Recinto a Editar", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void eliminar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a dar de baja el recinto.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					Recinto seleccion = new Recinto();
					seleccion = tvDatos.getSelectionModel().getSelectedItem();
					seleccion.setEstado(false);
					recintoDAO.getEntityManager().getTransaction().begin();
					recintoDAO.getEntityManager().merge(seleccion);
					recintoDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Recinto dado de baja", Context.getInstance().getStage());
					llenarDatos("");
				}
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Recinto a Dar de Baja", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
