package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;

import com.control.pavi.model.AsignacionJunta;
import com.control.pavi.model.AsignacionSupervisor;
import com.control.pavi.model.JuntaVoto;
import com.control.pavi.model.Supervisor;
import com.control.pavi.util.Context;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class SupervisorEditarController {
	@FXML private Button btnSalir;
	@FXML private TextField txtApellidos;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtTelefono;
	@FXML private TextField txtCodigo;
	@FXML private TextField txtNombres;
	@FXML private TableView<JuntaVoto> tvDatosJunta;
	@FXML private Button btnAgregar;
	@FXML private TextField txtCedula;
	@FXML private Button btnGrabar;
	@FXML private Button btnQuitar;
	
	Supervisor supervisor;
	
	public void initialize() {
		try {
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			btnAgregar.setStyle("-fx-cursor: hand;");
			btnQuitar.setStyle("-fx-cursor: hand;");
			
			btnGrabar.getStyleClass().add("botonGrabar");
			btnSalir.getStyleClass().add("botonSalir");
			
			txtCodigo.setText("0");
			txtCodigo.setDisable(true);
			txtCedula.requestFocus();
			
			if(Context.getInstance().getSupervisor() != null) {
				supervisor = Context.getInstance().getSupervisor();
				cargarDatos();
				Context.getInstance().setCanton(null);
			}else {
				supervisor = new Supervisor();
			}
		}catch(Exception ex) {
			
		}
	}
	
	private void cargarDatos() {
		try {
			txtCodigo.setText(String.valueOf(supervisor.getIdSupervisor()));
			txtApellidos.setText(supervisor.getApellidos());
			txtCedula.setText(supervisor.getNoIdentificacion());
			txtDireccion.setText(supervisor.getDireccion());
			txtNombres.setText(supervisor.getNombres());
			txtTelefono.setText(supervisor.getTelefono());
			if(supervisor.getAsignacionSupervisors().size() > 0) {
				cargarJuntasAsignadas();
			}
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	private void cargarJuntasAsignadas() {
		try {
			tvDatosJunta.getColumns().clear();
			tvDatosJunta.getItems().clear();
			List<JuntaVoto> lista = new ArrayList<>();
			for(AsignacionSupervisor asig : supervisor.getAsignacionSupervisors()) {
				if(asig.getEstado() == true)
					lista.add(asig.getJuntaVoto());
			}
			ObservableList<JuntaVoto> datos = FXCollections.observableArrayList();
			datos.setAll(lista);

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
			
			TableColumn<JuntaVoto, String> parroquiaColum = new TableColumn<>("Parroquia");
			parroquiaColum.setMinWidth(10);
			parroquiaColum.setPrefWidth(150);
			parroquiaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRecinto().getParroquia().getParroquia());
				}
			});
			
			TableColumn<JuntaVoto, String> recintoColum = new TableColumn<>("Recinto");
			recintoColum.setMinWidth(10);
			recintoColum.setPrefWidth(220);
			recintoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getRecinto().getRecinto());
				}
			});
			
			TableColumn<JuntaVoto, String> juntaColum = new TableColumn<>("Junta");
			juntaColum.setMinWidth(10);
			juntaColum.setPrefWidth(80);
			juntaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getGenero() + " " + param.getValue().getNumero());
				}
			});
			
			TableColumn<JuntaVoto, String> delegadoColum = new TableColumn<>("Delegado");
			delegadoColum.setMinWidth(10);
			delegadoColum.setPrefWidth(200);
			delegadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					String nombreDelegado = "SIN DELEGADO";
					if(param.getValue().getAsignacionJuntas().size() > 0) {
						for(AsignacionJunta a : param.getValue().getAsignacionJuntas()) {
							if(a.getEstado() == true)
								nombreDelegado = a.getRepresentante().getNombre() + " " + a.getRepresentante().getApellidos();
						}
					}
					return new SimpleObjectProperty<String>(nombreDelegado);
				}
			});
			
			TableColumn<JuntaVoto, String> partidoPoliticoColum = new TableColumn<>("Partido Politico");
			partidoPoliticoColum.setMinWidth(10);
			partidoPoliticoColum.setPrefWidth(200);
			partidoPoliticoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					String partido = "SIN PARTIDO POLITICO";
					if(param.getValue().getAsignacionJuntas().size() > 0) {
						for(AsignacionJunta a : param.getValue().getAsignacionJuntas()) {
							if(a.getEstado() == true)
								partido = a.getRepresentante().getPartidoPolitico().getLista();
						}
					}
					return new SimpleObjectProperty<String>(partido);
				}
			});
			
			tvDatosJunta.getColumns().addAll(provinciaColum,parroquiaColum,recintoColum,juntaColum,delegadoColum,partidoPoliticoColum);
			tvDatosJunta.setItems(datos);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void agregarJunta() {
		
	}

	
	public void quitarJunta() {

	}

	
	public void grabar() {

	}

	
	public void salir() {
		Context.getInstance().getStageModal().close();
	}

}
