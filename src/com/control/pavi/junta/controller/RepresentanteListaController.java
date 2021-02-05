package com.control.pavi.junta.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Representante;
import com.control.pavi.model.dao.RepresentanteDAO;
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

public class RepresentanteListaController {
	@FXML private TableView<Representante> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;

	RepresentanteDAO representanteDAO = new RepresentanteDAO();
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
			List<Representante> lista;
			ObservableList<Representante> datos = FXCollections.observableArrayList();
			lista = representanteDAO.buscarPorNombreApellido(busqueda);
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<Representante, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Representante,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Representante, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdRepresentante()));
				}
			});
			TableColumn<Representante, String> nombresColum = new TableColumn<>("Nombres");
			nombresColum.setMinWidth(10);
			nombresColum.setPrefWidth(200);
			nombresColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Representante,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Representante, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getNombre());
				}
			});
			
			TableColumn<Representante, String> apellidoColum = new TableColumn<>("Apellidos");
			apellidoColum.setMinWidth(10);
			apellidoColum.setPrefWidth(200);
			apellidoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Representante,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Representante, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getApellidos());
				}
			});
			
			TableColumn<Representante, String> correoColum = new TableColumn<>("Correo");
			correoColum.setMinWidth(10);
			correoColum.setPrefWidth(200);
			correoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Representante,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Representante, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getCorreo());
				}
			});
			
			TableColumn<Representante, String> telefonoColum = new TableColumn<>("Teléfono");
			telefonoColum.setMinWidth(10);
			telefonoColum.setPrefWidth(100);
			telefonoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Representante,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Representante, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getTelefono());
				}
			});
			
			
			TableColumn<Representante, String> listaColum = new TableColumn<>("Partido Político");
			listaColum.setMinWidth(10);
			listaColum.setPrefWidth(150);
			listaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Representante,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Representante, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getPartidoPolitico().getLista());
				}
			});
			
			TableColumn<Representante, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Representante,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Representante, String> param) {
					String estado = "";
					if(param.getValue().getEstado() == true)
						estado = "Activo";
					else
						estado = "Inactivo";
					return new SimpleObjectProperty<String>(estado);
				}
			});

			
			tvDatos.getColumns().addAll(idColum,nombresColum,apellidoColum,correoColum,telefonoColum,listaColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void buscar() {
		try {
			List<Representante> lista;
			ObservableList<Representante> datos = FXCollections.observableArrayList();
			lista = representanteDAO.buscarPorNombreApellido(txtBuscar.getText().toString());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevo() {
		try {
			Context.getInstance().setRepresentante(null);
			helper.abrirPantallaModal("/junta/RepresentanteEditar.fxml","Datos del Representante", Context.getInstance().getStage());
			llenarDatos("");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void editar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Context.getInstance().setRepresentante(tvDatos.getSelectionModel().getSelectedItem());
				helper.abrirPantallaModal("/junta/RepresentanteEditar.fxml","Datos del Representante", Context.getInstance().getStage());
				llenarDatos("");
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Representante a Editar", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void eliminar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a dar de baja el Representante.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					Representante seleccion = new Representante();
					seleccion = tvDatos.getSelectionModel().getSelectedItem();
					seleccion.setEstado(false);
					representanteDAO.getEntityManager().getTransaction().begin();
					representanteDAO.getEntityManager().merge(seleccion);
					representanteDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Representante dado de baja", Context.getInstance().getStage());
					llenarDatos("");
				}
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Representante a Dar de Baja", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
