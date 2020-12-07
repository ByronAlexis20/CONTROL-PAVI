package com.control.pavi.reportes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.control.pavi.model.PartidoPolitico;
import com.control.pavi.model.dao.PartidoPoliticoDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;
import com.control.pavi.util.PrintReport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class DelegadosPorPartidoController {
	@FXML private ComboBox<PartidoPolitico> cboPartido;
	@FXML private Button txtImprimir;

	PartidoPoliticoDAO partidoDAO = new PartidoPoliticoDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			llenarComboPartido();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void llenarComboPartido(){
		try{
			cboPartido.setPromptText("Seleccionar partido politico");
			List<PartidoPolitico> lista;
			lista = partidoDAO.buscarPorPatron("");
			ObservableList<PartidoPolitico> datos = FXCollections.observableArrayList();
			datos.addAll(lista);
			cboPartido.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void imprimir() {
		try {
			if(cboPartido.getSelectionModel().getSelectedItem() == null) {
    			helper.mostrarAlertaAdvertencia("Debe seleccionar el partido", Context.getInstance().getStage());
    			return;
    		}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ID_PARTIDO", cboPartido.getSelectionModel().getSelectedItem().getIdPartido());
			PrintReport reporte = new PrintReport();
			reporte.crearReporte("/recursos/rpt/rptDelegadosPorPartido.jasper", partidoDAO, param);
			reporte.showReport("Reporte de delegados");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
