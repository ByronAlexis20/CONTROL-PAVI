package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;

import com.control.pavi.model.JuntaVoto;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class AsignarListaController {
	@FXML private TableView<Recinto> tvDatos;
	@FXML private RadioButton rbParroquia;
	@FXML private RadioButton rbProvincia;
	@FXML private Button btnJuntas;
	@FXML private RadioButton rbRecinto;
	@FXML private RadioButton rbCanton;
	@FXML private TextField txtBuscar;
	
	RecintoDAO recintoDAO = new RecintoDAO();
	ControllerHelper helper = new ControllerHelper();

	public void initialize() {
		btnJuntas.setStyle("-fx-cursor: hand;");
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
		llenarDatos("");
	}
	public void buscar() {
		try {
			List<Recinto> lista = new ArrayList<Recinto>();
			ObservableList<Recinto> datos = FXCollections.observableArrayList();
			if(rbProvincia.isSelected())
				lista = recintoDAO.buscarPorProvincia(txtBuscar.getText().toString());
			else if(rbCanton.isSelected())
				lista = recintoDAO.buscarPorCanton(txtBuscar.getText().toString());
			else if(rbParroquia.isSelected())
				lista = recintoDAO.buscarPorParroquia(txtBuscar.getText().toString());
			else if(rbRecinto.isSelected())
				lista = recintoDAO.buscarPorRecinto(txtBuscar.getText().toString());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
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
			TableColumn<Recinto, String> provinciaColum = new TableColumn<>("Provincia");
			provinciaColum.setMinWidth(10);
			provinciaColum.setPrefWidth(150);
			provinciaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Recinto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Recinto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getParroquia().getCanton().getProvincia().getProvincia());
				}
			});
			
			TableColumn<Recinto, String> cantonColum = new TableColumn<>("Cantón");
			cantonColum.setMinWidth(10);
			cantonColum.setPrefWidth(150);
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
			recintoColum.setPrefWidth(250);
			recintoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Recinto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Recinto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRecinto());
				}
			});
			
			TableColumn<Recinto, String> juntasColum = new TableColumn<>("No Juntas");
			juntasColum.setMinWidth(10);
			juntasColum.setPrefWidth(90);
			juntasColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Recinto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Recinto, String> param) {
					int cont = 0;
					for(JuntaVoto junt : param.getValue().getJuntaVotos()) {
						if(junt.getEstado() == true)
							cont ++;
					}
					return new SimpleObjectProperty<String>(String.valueOf(cont));
				}
			});
			
			tvDatos.getColumns().addAll(provinciaColum,cantonColum,parroquiaColum,recintoColum,juntasColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void juntas() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar un registro", Context.getInstance().getStage());
				return;
			}
			Context.getInstance().setRecinto(tvDatos.getSelectionModel().getSelectedItem());
			helper.abrirPantallaModal("/junta/JuntaRecinto.fxml","Juntas Registradas", Context.getInstance().getStage());
			llenarDatos("");
		}catch(Exception ex) {
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
