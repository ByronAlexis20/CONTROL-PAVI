package com.control.pavi.reportes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.control.pavi.model.AsignacionSupervisor;
import com.control.pavi.model.dao.AsignacionSupervisorDAO;
import com.control.pavi.util.PrintReport;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class SupervisoresJuntasController {
	@FXML private TableView<AsignacionSupervisor> tvDatos;
	@FXML private Button txtImprimir;

	AsignacionSupervisorDAO asignacionDAO = new AsignacionSupervisorDAO();

	public void initialize() {
		txtImprimir.setStyle("-fx-cursor: hand;");
		llenarDatos();
	}
	@SuppressWarnings("unchecked")
	void llenarDatos(){
		try{
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<AsignacionSupervisor> lista = new ArrayList<AsignacionSupervisor>();
			lista = asignacionDAO.buscarAsignaciones();
			ObservableList<AsignacionSupervisor> datos = FXCollections.observableArrayList();
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<AsignacionSupervisor, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(100);
			cedulaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getSupervisor().getNoIdentificacion());
				}
			});

			TableColumn<AsignacionSupervisor, String> supervisorColum = new TableColumn<>("Supervisor");
			supervisorColum.setMinWidth(10);
			supervisorColum.setPrefWidth(300);
			supervisorColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getSupervisor().getNombres() + " " + param.getValue().getSupervisor().getApellidos());
				}
			});

			TableColumn<AsignacionSupervisor, String> telefonoColum = new TableColumn<>("Teléfono");
			telefonoColum.setMinWidth(10);
			telefonoColum.setPrefWidth(100);
			telefonoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getSupervisor().getTelefono());
				}
			});

			TableColumn<AsignacionSupervisor, String> juntaColum = new TableColumn<>("Junta");
			juntaColum.setMinWidth(10);
			juntaColum.setPrefWidth(150);
			juntaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getJuntaVoto().getGenero() + " - " + param.getValue().getJuntaVoto().getNumero());
				}
			});

			TableColumn<AsignacionSupervisor, String> recintoColum = new TableColumn<>("Recinto");
			recintoColum.setMinWidth(10);
			recintoColum.setPrefWidth(250);
			recintoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {

					return new SimpleObjectProperty<String>(param.getValue().getJuntaVoto().getRecinto().getRecinto());
				}
			});
			TableColumn<AsignacionSupervisor, String> parroquiaColum = new TableColumn<>("Parroquia");
			parroquiaColum.setMinWidth(10);
			parroquiaColum.setPrefWidth(200);
			parroquiaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {

					return new SimpleObjectProperty<String>(param.getValue().getJuntaVoto().getRecinto().getZonaRural().getParroquia().getParroquia());
				}
			});

			TableColumn<AsignacionSupervisor, String> zonaColum = new TableColumn<>("Zona");
			zonaColum.setMinWidth(10);
			zonaColum.setPrefWidth(150);
			zonaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {

					return new SimpleObjectProperty<String>(param.getValue().getJuntaVoto().getRecinto().getZonaRural().getParroquia().getZona().getZona());
				}
			});

			tvDatos.getColumns().addAll(cedulaColum,supervisorColum,telefonoColum,juntaColum,recintoColum,parroquiaColum,zonaColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	public void imprimir() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO", "REPORTE DE SUPERVISORES DE JUNTAS RECEPTORAS DE VOTO");
			PrintReport reporte = new PrintReport();
			reporte.crearReporte("/recursos/rpt/rptSupervisoresJunta.jasper", asignacionDAO, param);
			reporte.showReport("Reporte de delegados");

		}catch(Exception ex) {

		}
	}
}
