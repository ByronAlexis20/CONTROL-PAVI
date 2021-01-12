package com.control.pavi.administracion.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Parroquia;
import com.control.pavi.model.ZonaRural;
import com.control.pavi.model.dao.ParroquiaDAO;
import com.control.pavi.model.dao.ZonaRuralDAO;
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
	@FXML private TableView<ZonaRural> tvDatos;
    @FXML private Button btnEliminar;
    @FXML private Button btnNuevo;
    @FXML private TextField txtBuscar;
    @FXML private Button btnEditar;

    ControllerHelper helper = new ControllerHelper();
    ZonaRuralDAO zonaRuralDAO = new ZonaRuralDAO();
    ParroquiaDAO parroquiaDAO = new ParroquiaDAO();
    
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
    		System.out.println(ex.getMessage());
    	}
    }
    @SuppressWarnings("unchecked")
	void llenarDatos(String busqueda){
		try{
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<ZonaRural> lista;
			ObservableList<ZonaRural> datos = FXCollections.observableArrayList();
			lista = zonaRuralDAO.buscarPorPatron(busqueda);
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<ZonaRural, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ZonaRural,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<ZonaRural, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdZona()));
				}
			});
			
			TableColumn<ZonaRural, String> provinciaColum = new TableColumn<>("Provincia");
			provinciaColum.setMinWidth(10);
			provinciaColum.setPrefWidth(200);
			provinciaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ZonaRural,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<ZonaRural, String> param) {
					Parroquia parroquia = new Parroquia();
					List<Parroquia> lista = parroquiaDAO.buscarPorCodigo(param.getValue().getParroquia().getIdParroquia());
					for(Parroquia pa : lista) {
						parroquia = pa;
					}
					return new SimpleObjectProperty<String>(parroquia.getCanton().getProvincia().getProvincia());
				}
			});
			
			TableColumn<ZonaRural, String> cantonColum = new TableColumn<>("Cantón");
			cantonColum.setMinWidth(10);
			cantonColum.setPrefWidth(200);
			cantonColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ZonaRural,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<ZonaRural, String> param) {
					Parroquia parroquia = new Parroquia();
					List<Parroquia> lista = parroquiaDAO.buscarPorCodigo(param.getValue().getParroquia().getIdParroquia());
					for(Parroquia pa : lista) {
						parroquia = pa;
					}
					return new SimpleObjectProperty<String>(parroquia.getCanton().getCanton());
				}
			});
			
			TableColumn<ZonaRural, String> parroquiaColum = new TableColumn<>("Parroquia");
			parroquiaColum.setMinWidth(10);
			parroquiaColum.setPrefWidth(200);
			parroquiaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ZonaRural,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<ZonaRural, String> param) {
					String parroquia = "";
					List<Parroquia> lista = parroquiaDAO.buscarPorCodigo(param.getValue().getParroquia().getIdParroquia());
					for(Parroquia pa : lista) {
						parroquia = pa.getParroquia();
					}
					return new SimpleObjectProperty<String>(parroquia);
				}
			});
			
			TableColumn<ZonaRural, String> zonaColum = new TableColumn<>("Zona");
			zonaColum.setMinWidth(10);
			zonaColum.setPrefWidth(200);
			zonaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ZonaRural,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<ZonaRural, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getNombre());
				}
			});
			
			
			TableColumn<ZonaRural, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ZonaRural,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<ZonaRural, String> param) {
					String estado = "";
					if(param.getValue().getEstado().equals("A"))
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
			List<ZonaRural> lista;
			ObservableList<ZonaRural> datos = FXCollections.observableArrayList();
			lista = zonaRuralDAO.buscarPorPatron(txtBuscar.getText().toString());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
    public void nuevo() {
    	try {
    		Context.getInstance().setZonaRural(null);
			helper.abrirPantallaModal("/administracion/ZonaEditar.fxml","Datos de la Zona", Context.getInstance().getStage());
			llenarDatos("");
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }

    
    public void editar() {
    	try {
    		if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Context.getInstance().setZonaRural(tvDatos.getSelectionModel().getSelectedItem());
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
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a dar de baja el recinto.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					ZonaRural seleccion = new ZonaRural();
					seleccion = tvDatos.getSelectionModel().getSelectedItem();
					seleccion.setEstado("I");
					zonaRuralDAO.getEntityManager().getTransaction().begin();
					zonaRuralDAO.getEntityManager().merge(seleccion);
					zonaRuralDAO.getEntityManager().getTransaction().commit();
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
