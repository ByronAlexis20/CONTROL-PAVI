package com.control.pavi.partido.controller;

import java.util.List;

import com.control.pavi.model.Canton;
import com.control.pavi.model.PartidoPolitico;
import com.control.pavi.model.dao.PartidoPoliticoDAO;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class PartidoListaController {
	@FXML private TableView<?> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;
	
	ControllerHelper helper = new ControllerHelper();
	PartidoPoliticoDAO partidoDAO = new PartidoPoliticoDAO();

	public void initialize() {
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
	}
	
	@SuppressWarnings("unchecked")
	void llenarDatos(String busqueda){
		try{
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<PartidoPolitico> lista;
			ObservableList<PartidoPolitico> datos = FXCollections.observableArrayList();
			lista = partidoDAO.buscarPorPatron(busqueda);
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<PartidoPolitico, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PartidoPolitico,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<PartidoPolitico, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdPartido()));
				}
			});
			TableColumn<PartidoPolitico, String> listaColum = new TableColumn<>("Lista");
			listaColum.setMinWidth(10);
			listaColum.setPrefWidth(200);
			listaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PartidoPolitico,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<PartidoPolitico, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getLista());
				}
			});
			
			TableColumn<PartidoPolitico, String> sloganColum = new TableColumn<>("Slogan");
			sloganColum.setMinWidth(10);
			sloganColum.setPrefWidth(200);
			sloganColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PartidoPolitico,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<PartidoPolitico, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getSlogan());
				}
			});
			
			TableColumn<PartidoPolitico, String> candidatoColum = new TableColumn<>("Candidato");
			candidatoColum.setMinWidth(10);
			candidatoColum.setPrefWidth(200);
			candidatoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PartidoPolitico,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<PartidoPolitico, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getCandidato().getNombre() + " " + param.getValue().getCandidato().getApellido());
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
	
	public void nuevo() {

	}

	public void editar() {

	}

	public void eliminar() {

	}
}
