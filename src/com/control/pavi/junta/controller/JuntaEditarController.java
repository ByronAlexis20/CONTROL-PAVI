package com.control.pavi.junta.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.JuntaVoto;
import com.control.pavi.model.Recinto;
import com.control.pavi.model.dao.JuntaVotoDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class JuntaEditarController {
	@FXML private TableView<JuntaVoto> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtRecinto;
	@FXML private TextField txtProvincia;
	@FXML private TextField txtCanton;
	@FXML private TextField txtParroquia;
	
	Recinto recinto;
	JuntaVotoDAO juntaDAO = new JuntaVotoDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnEliminar.setStyle("-fx-cursor: hand;");
			btnNuevo.setStyle("-fx-cursor: hand;");
			
			btnEliminar.getStyleClass().add("botonEliminar");
			btnNuevo.getStyleClass().add("botonNuevo");
			
			if(Context.getInstance().getRecinto() != null) {
				recinto = Context.getInstance().getRecinto();
				txtCanton.setText(recinto.getParroquia().getCanton().getCanton());
				txtRecinto.setText(recinto.getRecinto());
				txtProvincia.setText(recinto.getParroquia().getCanton().getProvincia().getProvincia());
				txtParroquia.setText(recinto.getParroquia().getParroquia());
				llenarDatos();
				Context.getInstance().setRecinto(null);
			}else {
				recinto = new Recinto();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void llenarDatos() {
		try {
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<JuntaVoto> lista;
			ObservableList<JuntaVoto> datos = FXCollections.observableArrayList();
			lista = juntaDAO.buscarPorRecinto(recinto.getIdRecinto());
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<JuntaVoto, String> generoColum = new TableColumn<>("Género");
			generoColum.setMinWidth(10);
			generoColum.setPrefWidth(200);
			generoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getGenero());
				}
			});
			
			TableColumn<JuntaVoto, String> numeroColum = new TableColumn<>("Número");
			numeroColum.setMinWidth(10);
			numeroColum.setPrefWidth(200);
			numeroColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getNumero()));
				}
			});
			
			TableColumn<JuntaVoto, String> descripcionColum = new TableColumn<>("Descripción");
			descripcionColum.setMinWidth(10);
			descripcionColum.setPrefWidth(200);
			descripcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getDescripcion());
				}
			});
			
			tvDatos.getColumns().addAll(generoColum,numeroColum,descripcionColum);
			tvDatos.setItems(datos);
		}catch(Exception ex) {
			
		}
	}
	
	public void nuevo() {
		try {
			Context.getInstance().setRecinto(recinto);
			helper.abrirPantallaModal("/junta/DatosJunta.fxml","Datos Junta", Context.getInstance().getStageModal());
			llenarDatos();
		}catch(Exception ex) {
			
		}
	}

	
	public void eliminar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a dar de baja la Junta.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					JuntaVoto seleccion = new JuntaVoto();
					seleccion = tvDatos.getSelectionModel().getSelectedItem();
					seleccion.setEstado(false);
					juntaDAO.getEntityManager().getTransaction().begin();
					juntaDAO.getEntityManager().merge(seleccion);
					juntaDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Junta Dado de Baja", Context.getInstance().getStage());
					llenarDatos();
				}
			}else {
				helper.mostrarAlertaError("Debe Seleccionar una Junta a Dar de Baja", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
