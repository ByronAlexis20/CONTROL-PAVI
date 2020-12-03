package com.control.pavi.reportes.controller;

import java.util.ArrayList;
import java.util.List;

import com.control.pavi.model.AsignacionJunta;
import com.control.pavi.model.Canton;
import com.control.pavi.model.Parroquia;
import com.control.pavi.model.Provincia;
import com.control.pavi.model.Recinto;
import com.control.pavi.model.dao.AsignacionJuntaDAO;
import com.control.pavi.model.dao.CantonDAO;
import com.control.pavi.model.dao.ParroquiaDAO;
import com.control.pavi.model.dao.ProvinciaDAO;
import com.control.pavi.model.dao.RecintoDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class DelegadosPoliticosController {
	@FXML private TableView<AsignacionJunta> tvDatos;
	@FXML private RadioButton rbParroquia;
	@FXML private RadioButton rbProvincia;
	@FXML private RadioButton rbRecinto;
	@FXML private RadioButton rbCanton;
	@FXML private Button txtBuscar;
	@FXML private Button txtImprimir;
	@FXML private ComboBox<Provincia> cboProvincia;
	@FXML private ComboBox<Canton> cboCanton;
	@FXML private ComboBox<Parroquia> cboParroquia;
	@FXML private ComboBox<Recinto> cboRecinto;

	AsignacionJuntaDAO asignacionDAO = new AsignacionJuntaDAO();
	ProvinciaDAO provinciaDAO = new ProvinciaDAO();
	CantonDAO cantonDAO = new CantonDAO();
	ParroquiaDAO parroquiaDAO = new ParroquiaDAO();
	RecintoDAO recintoDAO = new RecintoDAO();
	ControllerHelper helper = new ControllerHelper();

	public void initialize() {
		txtImprimir.setStyle("-fx-cursor: hand;");
		txtBuscar.setStyle("-fx-cursor: hand;");
		rbProvincia.setSelected(true);
		llenarDatos();
		llenarComboProvincia();
		cboProvincia.setDisable(false);
		cboCanton.setDisable(true);
		cboParroquia.setDisable(true);
		cboRecinto.setDisable(true);
	}

	public void buscar() {
		List<AsignacionJunta> lista;
		ObservableList<AsignacionJunta> datos = FXCollections.observableArrayList();
		if(rbProvincia.isSelected()) {
			if(cboProvincia.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar la provincia", Context.getInstance().getStage());
				return;
			}
			lista = asignacionDAO.buscarPorProvincia(cboProvincia.getSelectionModel().getSelectedItem().getIdProvincia());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}else if(rbCanton.isSelected()) {
			if(cboCanton.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar Cantón", Context.getInstance().getStage());
				return;
			}
			lista = asignacionDAO.buscarPorCanton(cboCanton.getSelectionModel().getSelectedItem().getIdCanton());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}else if(rbParroquia.isSelected()) {
			if(cboParroquia.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar Parroquia", Context.getInstance().getStage());
				return;
			}
			lista = asignacionDAO.buscarPorParroquia(cboParroquia.getSelectionModel().getSelectedItem().getIdParroquia());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}else if(rbRecinto.isSelected()) {
			if(cboRecinto.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar Recinto", Context.getInstance().getStage());
				return;
			}
			lista = asignacionDAO.buscarPorRecinto(cboRecinto.getSelectionModel().getSelectedItem().getIdRecinto());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}
	}
	@SuppressWarnings("unchecked")
	void llenarDatos(){
		try{
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<AsignacionJunta> lista = new ArrayList<AsignacionJunta>();
			ObservableList<AsignacionJunta> datos = FXCollections.observableArrayList();
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<AsignacionJunta, String> provinciaColum = new TableColumn<>("Provincia");
			provinciaColum.setMinWidth(10);
			provinciaColum.setPrefWidth(150);
			provinciaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionJunta,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionJunta, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getJunta().getRecinto().getParroquia().getCanton().getProvincia().getProvincia());
				}
			});

			TableColumn<AsignacionJunta, String> cantonColum = new TableColumn<>("Cantón");
			cantonColum.setMinWidth(10);
			cantonColum.setPrefWidth(150);
			cantonColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionJunta,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionJunta, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getJunta().getRecinto().getParroquia().getCanton().getCanton());
				}
			});

			TableColumn<AsignacionJunta, String> parroquiaColum = new TableColumn<>("Parroquia");
			parroquiaColum.setMinWidth(10);
			parroquiaColum.setPrefWidth(200);
			parroquiaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionJunta,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionJunta, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getJunta().getRecinto().getParroquia().getParroquia());
				}
			});

			TableColumn<AsignacionJunta, String> recintoColum = new TableColumn<>("Recinto");
			recintoColum.setMinWidth(10);
			recintoColum.setPrefWidth(250);
			recintoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionJunta,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionJunta, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getJunta().getRecinto().getRecinto());
				}
			});

			TableColumn<AsignacionJunta, String> juntasColum = new TableColumn<>("Junta");
			juntasColum.setMinWidth(10);
			juntasColum.setPrefWidth(90);
			juntasColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionJunta,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionJunta, String> param) {

					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getJunta().getNumero() + " - " + param.getValue().getJunta().getGenero()));
				}
			});
			TableColumn<AsignacionJunta, String> delegadoColum = new TableColumn<>("Delegado");
			delegadoColum.setMinWidth(10);
			delegadoColum.setPrefWidth(200);
			delegadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionJunta,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionJunta, String> param) {

					return new SimpleObjectProperty<String>(param.getValue().getRepresentante().getNoIdentificacion() + " - " + param.getValue().getRepresentante().getNombre() + " " + param.getValue().getRepresentante().getApellidos());
				}
			});

			TableColumn<AsignacionJunta, String> partidoColum = new TableColumn<>("Partido");
			partidoColum.setMinWidth(10);
			partidoColum.setPrefWidth(100);
			partidoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionJunta,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionJunta, String> param) {

					return new SimpleObjectProperty<String>(param.getValue().getRepresentante().getPartidoPolitico().getLista());
				}
			});

			tvDatos.getColumns().addAll(provinciaColum,cantonColum,parroquiaColum,recintoColum,juntasColum,delegadoColum,partidoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	private void llenarComboProvincia() {
		try{
			cboProvincia.setPromptText("Seleccionar provincia");
			List<Provincia> lista;
			lista = provinciaDAO.buscarPorPatron("");
			ObservableList<Provincia> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboProvincia.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void seleccionaProvincia() {
		try {

			cboCanton.setPromptText("Seleccionar canton");
			List<Canton> lista;
			lista = cantonDAO.buscarPorIdProvincia(cboProvincia.getSelectionModel().getSelectedItem().getIdProvincia());
			ObservableList<Canton> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboCanton.setItems(datos);

		}catch(Exception ex) {

		}
	}
	public void seleccionaCanton() {
		try {
			cboParroquia.setPromptText("Seleccionar Parroquia");
			List<Parroquia> lista;
			lista = parroquiaDAO.buscarPorIdCanton(cboCanton.getSelectionModel().getSelectedItem().getIdCanton());
			ObservableList<Parroquia> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboParroquia.setItems(datos);

		}catch(Exception ex) {

		}
	}
	public void seleccionaParroquia() {
		try {

			cboRecinto.setPromptText("Seleccionar Recinto");
			List<Recinto> lista;
			lista = recintoDAO.buscarPorIdParroquia(cboParroquia.getSelectionModel().getSelectedItem().getIdParroquia());
			ObservableList<Recinto> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboRecinto.setItems(datos);

		}catch(Exception ex) {

		}
	}
	public void imprimir() {

	}
	public void cambiaProvincia() {
		if(rbProvincia.isSelected()) {
			rbProvincia.setSelected(true);
			rbCanton.setSelected(false);
			rbParroquia.setSelected(false);
			rbRecinto.setSelected(false);

			cboProvincia.setDisable(false);
			cboCanton.setDisable(true);
			cboParroquia.setDisable(true);
			cboRecinto.setDisable(true);

		}
	}


	public void cambiaCanton() {
		if(rbCanton.isSelected()) {
			rbProvincia.setSelected(false);
			rbCanton.setSelected(true);
			rbParroquia.setSelected(false);
			rbRecinto.setSelected(false);

			cboProvincia.setDisable(false);
			cboCanton.setDisable(false);
			cboParroquia.setDisable(true);
			cboRecinto.setDisable(true);

		}
	}


	public void cambiaParroquia() {
		if(rbParroquia.isSelected()) {
			rbProvincia.setSelected(false);
			rbCanton.setSelected(false);
			rbParroquia.setSelected(true);
			rbRecinto.setSelected(false);

			cboProvincia.setDisable(false);
			cboCanton.setDisable(false);
			cboParroquia.setDisable(false);
			cboRecinto.setDisable(true);
		}
	}


	public void cambiaRecinto() {
		if(rbRecinto.isSelected()) {
			rbProvincia.setSelected(false);
			rbCanton.setSelected(false);
			rbParroquia.setSelected(false);
			rbRecinto.setSelected(true);

			cboProvincia.setDisable(false);
			cboCanton.setDisable(false);
			cboParroquia.setDisable(false);
			cboRecinto.setDisable(false);
		}
	}
}
