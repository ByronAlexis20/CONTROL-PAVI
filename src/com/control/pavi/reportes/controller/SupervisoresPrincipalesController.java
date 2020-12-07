package com.control.pavi.reportes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.control.pavi.model.AsignacionSupervisorPrincipal;
import com.control.pavi.model.dao.AsignacionSupervisorPrincipalDAO;
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

public class SupervisoresPrincipalesController {
	@FXML private Button txtImprimir;
	@FXML private TableView<AsignacionSupervisorPrincipal> tvDatos;
	AsignacionSupervisorPrincipalDAO asignacionDAO = new AsignacionSupervisorPrincipalDAO();
	
	public void initialize() {
		txtImprimir.setStyle("-fx-cursor: hand;");
		llenarDatos();
	}
	@SuppressWarnings("unchecked")
	void llenarDatos(){
		try{
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<AsignacionSupervisorPrincipal> lista = new ArrayList<AsignacionSupervisorPrincipal>();
			lista = asignacionDAO.buscarAsignaciones();
			ObservableList<AsignacionSupervisorPrincipal> datos = FXCollections.observableArrayList();
			datos.setAll(lista);

			//llenar los datos en la tabla
			TableColumn<AsignacionSupervisorPrincipal, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(100);
			cedulaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisorPrincipal,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisorPrincipal, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getSupervisor().getNoIdentificacion());
				}
			});

			TableColumn<AsignacionSupervisorPrincipal, String> supervisorColum = new TableColumn<>("Supervisor Principal");
			supervisorColum.setMinWidth(10);
			supervisorColum.setPrefWidth(300);
			supervisorColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisorPrincipal,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisorPrincipal, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getSupervisorPrincipal().getNombres() + " " + param.getValue().getSupervisorPrincipal().getApellidos());
				}
			});

			TableColumn<AsignacionSupervisorPrincipal, String> supervisorCurstodioColum = new TableColumn<>("Supervisor a custodiar");
			supervisorCurstodioColum.setMinWidth(10);
			supervisorCurstodioColum.setPrefWidth(300);
			supervisorCurstodioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsignacionSupervisorPrincipal,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisorPrincipal, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getSupervisor().getNombres() + " " + param.getValue().getSupervisor().getApellidos());
				}
			});

			tvDatos.getColumns().addAll(cedulaColum,supervisorColum,supervisorCurstodioColum);
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
			reporte.crearReporte("/recursos/rpt/rptSupervisorPrincipal.jasper", asignacionDAO, param);
			reporte.showReport("Reporte de supervisores");

		}catch(Exception ex) {

		}
	}
}
