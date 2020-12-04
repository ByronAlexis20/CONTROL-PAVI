package com.control.pavi.junta.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.AsignacionSupervisorPrincipal;
import com.control.pavi.model.SupervisorPrincipal;
import com.control.pavi.model.dao.SupervisorPrincipalDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.property.SimpleObjectProperty;
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

public class SupervisorPrincipalListaController {
	@FXML private TableView<SupervisorPrincipal> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;

	SupervisorPrincipalDAO supervisorDAO = new SupervisorPrincipalDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnEditar.setStyle("-fx-cursor: hand;");
			btnEliminar.setStyle("-fx-cursor: hand;");
			btnNuevo.setStyle("-fx-cursor: hand;");
			
			btnEditar.getStyleClass().add("botonEditar");
			btnNuevo.getStyleClass().add("botonNuevo");
			btnEliminar.getStyleClass().add("botonEliminar");
			
			cargarSupervisoresAsignador();
			txtBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarSupervisor();
					} 
				} 
			}); 
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void buscarSupervisor() {
		try {
			List<SupervisorPrincipal> lista;
			ObservableList<SupervisorPrincipal> datos = FXCollections.observableArrayList();
			lista = supervisorDAO.buscarPorPatron(txtBuscar.getText().toString());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void cargarSupervisoresAsignador() {
		try {
			tvDatos.getItems().clear();
			tvDatos.getColumns().clear();
			List<SupervisorPrincipal> lista = supervisorDAO.buscarPorPatron("");
			ObservableList<SupervisorPrincipal> datos = FXCollections.observableArrayList();
			datos.setAll(lista);
			TableColumn<SupervisorPrincipal, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(100);
			cedulaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SupervisorPrincipal,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<SupervisorPrincipal, String> param) {
					
					return new SimpleObjectProperty<String>(param.getValue().getCedula());
				}
			});
			
			TableColumn<SupervisorPrincipal, String> nombreColum = new TableColumn<>("Supervisor");
			nombreColum.setMinWidth(10);
			nombreColum.setPrefWidth(200);
			nombreColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SupervisorPrincipal,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<SupervisorPrincipal, String> param) {
					String supervisor = "";
					supervisor = param.getValue().getNombres() + " " + param.getValue().getApellidos();
					return new SimpleObjectProperty<String>(supervisor);
				}
			});
			
			TableColumn<SupervisorPrincipal, String> telefonoColum = new TableColumn<>("Telefono");
			telefonoColum.setMinWidth(10);
			telefonoColum.setPrefWidth(150);
			telefonoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SupervisorPrincipal,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<SupervisorPrincipal, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getTelefono());
				}
			});
			
			TableColumn<SupervisorPrincipal, String> asignacionColum = new TableColumn<>("Supervisores a cargo");
			asignacionColum.setMinWidth(10);
			asignacionColum.setPrefWidth(350);
			asignacionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SupervisorPrincipal,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<SupervisorPrincipal, String> param) {
					int cont = 0;
					for(AsignacionSupervisorPrincipal asig : param.getValue().getAsignacionSupervisorPrincipals()) {
						if(asig.getEstado() == true)
							cont ++;
					}
					return new SimpleObjectProperty<String>(String.valueOf(cont));
				}
			});
			
			tvDatos.getColumns().addAll(cedulaColum,nombreColum,telefonoColum,asignacionColum);
			tvDatos.setItems(datos);
		}catch(Exception ex) {
			
		}
	}
	public void nuevo() {
		try {
			Context.getInstance().setSupervisorPrincipal(null);
			helper.abrirPantallaModal("/junta/SupervisorPrincipalEditar.fxml","Supervisor", Context.getInstance().getStage());
			cargarSupervisoresAsignador();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			Context.getInstance().setSupervisorPrincipal(tvDatos.getSelectionModel().getSelectedItem());
			helper.abrirPantallaModal("/junta/SupervisorPrincipalEditar.fxml","Supervisor", Context.getInstance().getStage());
			cargarSupervisoresAsignador();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void eliminar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar un registro", Context.getInstance().getStage());
    			return;
			}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion( "Se procederá a eliminar el supervisor, por consiguiente se eliminaran las asignaciones de juntas realizadas .. Desea Continuar?", Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				SupervisorPrincipal seleccion = tvDatos.getSelectionModel().getSelectedItem();
				seleccion.setEstado(false);
				supervisorDAO.getEntityManager().getTransaction().begin();
				supervisorDAO.getEntityManager().merge(seleccion);
				
				for(AsignacionSupervisorPrincipal asig : seleccion.getAsignacionSupervisorPrincipals()) {
					AsignacionSupervisorPrincipal dato = asig;
					dato.setEstado(false);
					supervisorDAO.getEntityManager().merge(dato);
				}
				supervisorDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Parroquia dado de baja", Context.getInstance().getStage());
				cargarSupervisoresAsignador();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
