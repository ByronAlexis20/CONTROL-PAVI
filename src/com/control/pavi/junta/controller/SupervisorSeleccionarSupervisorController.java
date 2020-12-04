package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;

import com.control.pavi.model.AsignacionSupervisor;
import com.control.pavi.model.AsignacionSupervisorPrincipal;
import com.control.pavi.model.Supervisor;
import com.control.pavi.model.dao.AsignacionSupervisorPrincipalDAO;
import com.control.pavi.model.dao.SupervisorDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class SupervisorSeleccionarSupervisorController {
	@FXML private TableView<Supervisor> tvDatos;
	@FXML private TextField txtBuscar;
	
	
	ControllerHelper helper = new ControllerHelper();
	AsignacionSupervisorPrincipalDAO asignacionDAO = new AsignacionSupervisorPrincipalDAO();
	List<Supervisor> listaAsignados = new ArrayList<>();
	SupervisorDAO supervisorDAO = new SupervisorDAO();

	public void initialize() {
		
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
		if(Context.getInstance().getSupervisor() != null) {
			listaAsignados = Context.getInstance().getListaSupervisor();
			Context.getInstance().setListaSupervisor(null);
		}
		tvDatos.setRowFactory(tv -> {
			TableRow<Supervisor> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					if(tvDatos.getSelectionModel().getSelectedItem() != null){
						Context.getInstance().setSupervisor(tvDatos.getSelectionModel().getSelectedItem());
						Context.getInstance().getStageModalSecundario().close();
					}
				}
			});
			return row ;
		});
		llenarDatos("");
	}
	public void buscar() {
		try {
			boolean bandera = false;
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<Supervisor> lista = new ArrayList<>();
			List<Supervisor> listaLibres = new ArrayList<>();
			ObservableList<Supervisor> datos = FXCollections.observableArrayList();
			lista = supervisorDAO.buscarPorPatron(txtBuscar.getText());

			List<AsignacionSupervisorPrincipal> asignados = asignacionDAO.buscarAsignaciones();//los que se encuentran asignados
			for(Supervisor sup : lista) {
				bandera = false;
				//primer filtro es con los que estan registrados ya en la base
				for(AsignacionSupervisorPrincipal asig : asignados) {
					if(asig.getSupervisor().getIdSupervisor() == sup.getIdSupervisor())
						bandera = true;
				}
				//segundo filtro son los que se han agregado a la lista aun de manera temporal
				for(Supervisor junta : listaAsignados) {
					if(junta.getIdSupervisor() == sup.getIdSupervisor())
						bandera = true;
				}
				if(bandera == false)
					listaLibres.add(sup);
			}
			
			datos.setAll(listaLibres);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	void llenarDatos(String busqueda){
		try{
			boolean bandera = false;
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<Supervisor> lista;
			List<Supervisor> listaLibres = new ArrayList<>();
			ObservableList<Supervisor> datos = FXCollections.observableArrayList();
			lista = supervisorDAO.buscarPorPatron("");
			List<AsignacionSupervisorPrincipal> asignados = asignacionDAO.buscarAsignaciones();//los que se encuentran asignados
			for(Supervisor sup : lista) {
				bandera = false;
				//primer filtro es con los que estan registrados ya en la base
				for(AsignacionSupervisorPrincipal asig : asignados) {
					if(asig.getSupervisor().getIdSupervisor() == sup.getIdSupervisor())
						bandera = true;
				}
				//segundo filtro son los que se han agregado a la lista aun de manera temporal
				for(Supervisor junta : listaAsignados) {
					if(junta.getIdSupervisor() == sup.getIdSupervisor())
						bandera = true;
				}
				if(bandera == false)
					listaLibres.add(sup);
			}
			
			datos.setAll(listaLibres);

			//llenar los datos en la tabla
			TableColumn<Supervisor, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(150);
			cedulaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Supervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Supervisor, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getNoIdentificacion());
				}
			});
			
			TableColumn<Supervisor, String> supervisorColum = new TableColumn<>("Supervisor");
			supervisorColum.setMinWidth(10);
			supervisorColum.setPrefWidth(300);
			supervisorColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Supervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Supervisor, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getNombres() + " - " + param.getValue().getApellidos());
				}
			});
			
			TableColumn<Supervisor, String> telefonoColum = new TableColumn<>("Teléfono");
			telefonoColum.setMinWidth(10);
			telefonoColum.setPrefWidth(100);
			telefonoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Supervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Supervisor, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getTelefono());
				}
			});
			
			TableColumn<Supervisor, String> juntasColum = new TableColumn<>("Juntas Asignadas");
			juntasColum.setMinWidth(10);
			juntasColum.setPrefWidth(300);
			juntasColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Supervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Supervisor, String> param) {
					int cont = 0;
					for(AsignacionSupervisor asig : param.getValue().getAsignacionSupervisors()) {
						if(asig.getEstado() == true)
							cont ++;
					}
					return new SimpleObjectProperty<String>(String.valueOf(cont));
				}
			});
						
			tvDatos.getColumns().addAll(cedulaColum,supervisorColum,telefonoColum,juntasColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
}
