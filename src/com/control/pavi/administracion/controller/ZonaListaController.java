package com.control.pavi.administracion.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Zona;
import com.control.pavi.model.dao.ZonaDAO;
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

public class ZonaListaController {
	@FXML private TableView<Zona> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;

	ZonaDAO zonaDAO = new ZonaDAO();
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
			List<Zona> lista;
			ObservableList<Zona> datos = FXCollections.observableArrayList();
			lista = zonaDAO.buscarPorPatron(busqueda);
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<Zona, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zona,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Zona, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdZona()));
				}
			});
			TableColumn<Zona, String> provinciaColum = new TableColumn<>("Provincia");
			provinciaColum.setMinWidth(10);
			provinciaColum.setPrefWidth(200);
			provinciaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zona,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Zona, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getParroquia().getCanton().getProvincia().getProvincia());
				}
			});
			
			TableColumn<Zona, String> cantonColum = new TableColumn<>("Cantón");
			cantonColum.setMinWidth(10);
			cantonColum.setPrefWidth(200);
			cantonColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zona,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Zona, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getParroquia().getCanton().getCanton());
				}
			});
			
			TableColumn<Zona, String> parroquiaColum = new TableColumn<>("Parroquia");
			parroquiaColum.setMinWidth(10);
			parroquiaColum.setPrefWidth(200);
			parroquiaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zona,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Zona, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getParroquia().getParroquia());
				}
			});
			TableColumn<Zona, String> zonaColum = new TableColumn<>("Zona");
			zonaColum.setMinWidth(10);
			zonaColum.setPrefWidth(200);
			zonaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zona,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Zona, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getZona());
				}
			});
			
			TableColumn<Zona, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zona,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Zona, String> param) {
					String estado = "";
					if(param.getValue().getEstado() == true)
						estado = "Activo";
					else
						estado = "Inactivo";
					return new SimpleObjectProperty<String>(estado);
				}
			});

			
			tvDatos.getColumns().addAll(idColum,provinciaColum,cantonColum,parroquiaColum,zonaColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void buscar() {
		try {
			List<Zona> lista;
			ObservableList<Zona> datos = FXCollections.observableArrayList();
			lista = zonaDAO.buscarPorPatron(txtBuscar.getText().toString());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevo() {
		try {
			Context.getInstance().setZona(null);
			helper.abrirPantallaModal("/administracion/ZonaEditar.fxml","Datos de la zona", Context.getInstance().getStage());
			llenarDatos("");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void editar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Context.getInstance().setZona(tvDatos.getSelectionModel().getSelectedItem());
				helper.abrirPantallaModal("/administracion/ZonaEditar.fxml","Datos de la zona", Context.getInstance().getStage());
				llenarDatos("");
			}else {
				helper.mostrarAlertaError("Debe Seleccionar una Zona a Editar", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void eliminar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a dar de baja la zona.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					Zona seleccion = new Zona();
					seleccion = tvDatos.getSelectionModel().getSelectedItem();
					seleccion.setEstado(false);
					zonaDAO.getEntityManager().getTransaction().begin();
					zonaDAO.getEntityManager().merge(seleccion);
					zonaDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Zona dado de baja", Context.getInstance().getStage());
					llenarDatos("");
				}
			}else {
				helper.mostrarAlertaError("Debe Seleccionar una Zona a Dar de Baja", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
