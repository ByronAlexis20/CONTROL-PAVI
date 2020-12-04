package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;

import com.control.pavi.model.AsignacionSupervisor;
import com.control.pavi.model.JuntaVoto;
import com.control.pavi.model.dao.AsignacionSupervisorDAO;
import com.control.pavi.model.dao.JuntaVotoDAO;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class SupervisorSeleccionJuntaController {
	@FXML private TableView<JuntaVoto> tvDatos;
	@FXML private RadioButton rbParroquia;
	@FXML private RadioButton rbProvincia;
	@FXML private RadioButton rbRecinto;
	@FXML private RadioButton rbCanton;
	@FXML private TextField txtBuscar;
	
	
	RecintoDAO recintoDAO = new RecintoDAO();
	ControllerHelper helper = new ControllerHelper();
	JuntaVotoDAO juntaDAO = new JuntaVotoDAO();
	AsignacionSupervisorDAO asignacionDAO = new AsignacionSupervisorDAO();
	List<JuntaVoto> listaAsignados = new ArrayList<>();

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
		rbProvincia.setSelected(true);
		if(Context.getInstance().getListaJuntaVoto() != null) {
			listaAsignados = Context.getInstance().getListaJuntaVoto();
			Context.getInstance().setListaJuntaVoto(null);
		}
		tvDatos.setRowFactory(tv -> {
			TableRow<JuntaVoto> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					if(tvDatos.getSelectionModel().getSelectedItem() != null){
						Context.getInstance().setJuntaVoto(tvDatos.getSelectionModel().getSelectedItem());
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
			List<JuntaVoto> lista = new ArrayList<>();
			List<JuntaVoto> listaLibres = new ArrayList<>();
			ObservableList<JuntaVoto> datos = FXCollections.observableArrayList();
			
			if(rbProvincia.isSelected())
				lista = juntaDAO.buscarTodosActivoProvincia(txtBuscar.getText().toString());
			else if(rbCanton.isSelected())
				lista = juntaDAO.buscarTodosActivoCanton(txtBuscar.getText().toString());
			else if(rbParroquia.isSelected())
				lista = juntaDAO.buscarTodosActivoParroquia(txtBuscar.getText().toString());
			else if(rbRecinto.isSelected())
				lista = juntaDAO.buscarTodosActivoRecinto(txtBuscar.getText().toString());
			List<AsignacionSupervisor> asignados = asignacionDAO.buscarAsignaciones();//los que se encuentran asignados
			for(JuntaVoto jun : lista) {
				bandera = false;
				//primer filtro es con los que estan registrados ya en la base
				for(AsignacionSupervisor asig : asignados) {
					if(asig.getJuntaVoto().getIdJunta() == jun.getIdJunta())
						bandera = true;
				}
				//segundo filtro son los que se han agregado a la lista aun de manera temporal
				for(JuntaVoto junta : listaAsignados) {
					if(junta.getIdJunta() == jun.getIdJunta())
						bandera = true;
				}
				if(bandera == false)
					listaLibres.add(jun);
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
			List<JuntaVoto> lista;
			List<JuntaVoto> listaLibres = new ArrayList<>();
			ObservableList<JuntaVoto> datos = FXCollections.observableArrayList();
			lista = juntaDAO.buscarTodosActivo();
			List<AsignacionSupervisor> asignados = asignacionDAO.buscarAsignaciones();//los que se encuentran asignados
			for(JuntaVoto jun : lista) {
				bandera = false;
				//primer filtro es con los que estan registrados ya en la base
				for(AsignacionSupervisor asig : asignados) {
					if(asig.getJuntaVoto().getIdJunta() == jun.getIdJunta())
						bandera = true;
				}
				//segundo filtro son los que se han agregado a la lista aun de manera temporal
				for(JuntaVoto junta : listaAsignados) {
					if(junta.getIdJunta() == jun.getIdJunta())
						bandera = true;
				}
				if(bandera == false)
					listaLibres.add(jun);
			}
			
			datos.setAll(listaLibres);

			//llenar los datos en la tabla
			TableColumn<JuntaVoto, String> provinciaColum = new TableColumn<>("Provincia");
			provinciaColum.setMinWidth(10);
			provinciaColum.setPrefWidth(150);
			provinciaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRecinto().getParroquia().getCanton().getProvincia().getProvincia());
				}
			});
			
			TableColumn<JuntaVoto, String> cantonColum = new TableColumn<>("Cantón");
			cantonColum.setMinWidth(10);
			cantonColum.setPrefWidth(150);
			cantonColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRecinto().getParroquia().getCanton().getCanton());
				}
			});
			
			TableColumn<JuntaVoto, String> parroquiaColum = new TableColumn<>("Parroquia");
			parroquiaColum.setMinWidth(10);
			parroquiaColum.setPrefWidth(220);
			parroquiaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRecinto().getParroquia().getParroquia());
				}
			});
			
			TableColumn<JuntaVoto, String> recintoColum = new TableColumn<>("Recinto");
			recintoColum.setMinWidth(10);
			recintoColum.setPrefWidth(300);
			recintoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRecinto().getRecinto());
				}
			});
				
			TableColumn<JuntaVoto, String> juntasColum = new TableColumn<>("Junta");
			juntasColum.setMinWidth(10);
			juntasColum.setPrefWidth(200);
			juntasColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getNumero() + " - " + param.getValue().getGenero());
				}
			});
			
			tvDatos.getColumns().addAll(provinciaColum,cantonColum,parroquiaColum,recintoColum,juntasColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
    public void cambiaProvincia() {
    	if(rbProvincia.isSelected()) {
    		rbProvincia.setSelected(true);
    		rbCanton.setSelected(false);
    		rbParroquia.setSelected(false);
    		rbRecinto.setSelected(false);
    	}
    }

    
    public void cambiaCanton() {
    	if(rbCanton.isSelected()) {
    		rbProvincia.setSelected(false);
    		rbCanton.setSelected(true);
    		rbParroquia.setSelected(false);
    		rbRecinto.setSelected(false);
    	}
    }

    
    public void cambiaParroquia() {
    	if(rbParroquia.isSelected()) {
    		rbProvincia.setSelected(false);
    		rbCanton.setSelected(false);
    		rbParroquia.setSelected(true);
    		rbRecinto.setSelected(false);
    	}
    }

    
    public void cambiaRecinto() {
    	if(rbRecinto.isSelected()) {
    		rbProvincia.setSelected(false);
    		rbCanton.setSelected(false);
    		rbParroquia.setSelected(false);
    		rbRecinto.setSelected(true);
    	}
    }
}
