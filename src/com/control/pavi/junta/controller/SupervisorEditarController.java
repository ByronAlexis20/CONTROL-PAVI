package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.control.pavi.model.AsignacionJunta;
import com.control.pavi.model.AsignacionSupervisor;
import com.control.pavi.model.JuntaVoto;
import com.control.pavi.model.Supervisor;
import com.control.pavi.model.dao.AsignacionSupervisorDAO;
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

public class SupervisorEditarController {
	@FXML
	private Button btnSalir;
	@FXML
	private TextField txtApellidos;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtTelefono;
	@FXML
	private TextField txtCodigo;
	@FXML
	private TextField txtNombres;
	@FXML
	private TableView<AsignacionSupervisor> tvDatosJunta;
	@FXML
	private Button btnAgregar;
	@FXML
	private TextField txtCedula;
	@FXML
	private Button btnGrabar;
	@FXML
	private Button btnQuitar;

	Supervisor supervisor;
	ControllerHelper helper = new ControllerHelper();
	AsignacionSupervisorDAO asignacionDAO = new AsignacionSupervisorDAO();

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
			cargarDatosJunta();
			if (Context.getInstance().getSupervisor() != null) {
				supervisor = Context.getInstance().getSupervisor();
				cargarDatos();
				Context.getInstance().setCanton(null);
			} else {
				supervisor = new Supervisor();
			}
		} catch (Exception ex) {

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
			if (supervisor.getAsignacionSupervisors().size() > 0) {
				cargarJuntasAsignadas();
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void cargarDatosJunta() {
		try {
			tvDatosJunta.getColumns().clear();
			tvDatosJunta.getItems().clear();
			List<AsignacionSupervisor> lista = new ArrayList<>();
			ObservableList<AsignacionSupervisor> datos = FXCollections.observableArrayList();
			datos.setAll(lista);

			// llenar los datos en la tabla
			TableColumn<AsignacionSupervisor, String> provinciaColum = new TableColumn<>("Provincia");
			provinciaColum.setMinWidth(10);
			provinciaColum.setPrefWidth(150);
			provinciaColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
							return new SimpleObjectProperty<String>(param.getValue().getJuntaVoto().getRecinto()
									.getParroquia().getCanton().getProvincia().getProvincia());
						}
					});

			TableColumn<AsignacionSupervisor, String> parroquiaColum = new TableColumn<>("Parroquia");
			parroquiaColum.setMinWidth(10);
			parroquiaColum.setPrefWidth(150);
			parroquiaColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
							return new SimpleObjectProperty<String>(
									param.getValue().getJuntaVoto().getRecinto().getParroquia().getParroquia());
						}
					});

			TableColumn<AsignacionSupervisor, String> recintoColum = new TableColumn<>("Recinto");
			recintoColum.setMinWidth(10);
			recintoColum.setPrefWidth(220);
			recintoColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
							return new SimpleObjectProperty<String>(
									param.getValue().getJuntaVoto().getRecinto().getRecinto());
						}
					});

			TableColumn<AsignacionSupervisor, String> juntaColum = new TableColumn<>("Junta");
			juntaColum.setMinWidth(10);
			juntaColum.setPrefWidth(80);
			juntaColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
							return new SimpleObjectProperty<String>(param.getValue().getJuntaVoto().getGenero() + " "
									+ param.getValue().getJuntaVoto().getNumero());
						}
					});

			TableColumn<AsignacionSupervisor, String> delegadoColum = new TableColumn<>("Delegado");
			delegadoColum.setMinWidth(10);
			delegadoColum.setPrefWidth(200);
			delegadoColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
							String nombreDelegado = "SIN DELEGADO";
							if (param.getValue().getJuntaVoto().getAsignacionJuntas().size() > 0) {
								for (AsignacionJunta a : param.getValue().getJuntaVoto().getAsignacionJuntas()) {
									if (a.getEstado() == true)
										nombreDelegado = a.getRepresentante().getNombre() + " "
												+ a.getRepresentante().getApellidos();
								}
							}
							return new SimpleObjectProperty<String>(nombreDelegado);
						}
					});

			TableColumn<AsignacionSupervisor, String> partidoPoliticoColum = new TableColumn<>("Partido Politico");
			partidoPoliticoColum.setMinWidth(10);
			partidoPoliticoColum.setPrefWidth(200);
			partidoPoliticoColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisor, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisor, String> param) {
							String partido = "SIN PARTIDO POLITICO";
							if (param.getValue().getJuntaVoto().getAsignacionJuntas().size() > 0) {
								for (AsignacionJunta a : param.getValue().getJuntaVoto().getAsignacionJuntas()) {
									if (a.getEstado() == true)
										partido = a.getRepresentante().getPartidoPolitico().getLista();
								}
							}
							return new SimpleObjectProperty<String>(partido);
						}
					});

			tvDatosJunta.getColumns().addAll(provinciaColum, parroquiaColum, recintoColum, juntaColum, delegadoColum,
					partidoPoliticoColum);
			tvDatosJunta.setItems(datos);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void cargarJuntasAsignadas() {
		try {
			tvDatosJunta.getColumns().clear();
			tvDatosJunta.getItems().clear();
			List<AsignacionSupervisor> lista = new ArrayList<>();
			for (AsignacionSupervisor asig : supervisor.getAsignacionSupervisors()) {
				if (asig.getEstado() == true)
					lista.add(asig);
			}
			ObservableList<AsignacionSupervisor> datos = FXCollections.observableArrayList();
			datos.setAll(lista);
			tvDatosJunta.setItems(datos);
			tvDatosJunta.refresh();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void agregarJunta() {
		try {
			Context.getInstance().setJuntaVoto(null);
			// obtener los que se han agregado temporalmente
			List<JuntaVoto> listaAsignadosTemporal = new ArrayList<>();
			if (tvDatosJunta.getItems().size() > 0) {
				for (AsignacionSupervisor asig : tvDatosJunta.getItems()) {
					if (asig.getIdAsignacionSupervisor() == null)
						listaAsignadosTemporal.add(asig.getJuntaVoto());
				}
			}
			Context.getInstance().setListaJuntaVoto(listaAsignadosTemporal);

			helper.abrirPantallaModal("/junta/SupervisorSeleccionarJunta.fxml", "Seleccionar Junta",
					Context.getInstance().getStage());
			if (Context.getInstance().getJuntaVoto() != null) {
				JuntaVoto juntaSeleccionada = Context.getInstance().getJuntaVoto();
				AsignacionSupervisor agregar = new AsignacionSupervisor();
				agregar.setIdAsignacionSupervisor(null);
				agregar.setEstado(true);
				agregar.setJuntaVoto(juntaSeleccionada);

				if (tvDatosJunta.getItems().size() > 0)
					tvDatosJunta.getItems().add(agregar);
				else {
					List<AsignacionSupervisor> listadoAsignar = new ArrayList<>();
					ObservableList<AsignacionSupervisor> datos = FXCollections.observableArrayList();
					listadoAsignar.add(agregar);
					datos.setAll(listadoAsignar);
					tvDatosJunta.setItems(datos);
				}
				tvDatosJunta.refresh();

			}
			Context.getInstance().setJuntaVoto(null);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void quitarJunta() {
		try {
			if (tvDatosJunta.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion(
						"Se procederá a quitar la Junta .. Desea Continuar?", Context.getInstance().getStage());
				if (result.get() == ButtonType.OK) {
					AsignacionSupervisor seleccion = new AsignacionSupervisor();
					seleccion = tvDatosJunta.getSelectionModel().getSelectedItem();
					// cuando es una junta que tiene el id diferente de null.. se le da de baja
					// directamente
					if (seleccion.getIdAsignacionSupervisor() != null) {
						seleccion.setEstado(false);
						asignacionDAO.getEntityManager().getTransaction().begin();
						seleccion = asignacionDAO.getEntityManager().merge(seleccion);
						asignacionDAO.getEntityManager().getTransaction().commit();
						helper.mostrarAlertaInformacion("Junta quitada", Context.getInstance().getStage());
						tvDatosJunta.getItems().remove(seleccion);
					} else {// cuando es nuevo.. no es necesario ir a la base de datos.. se la elimina
							// directamente de la tabla
						tvDatosJunta.getItems().remove(seleccion);
					}
				}
			} else {
				helper.mostrarAlertaError("Debe Seleccionar una Junta a Quitar", Context.getInstance().getStage());
			}
		} catch (Exception ex) {

		}
	}

	public void grabar() {
		try {
			if(txtCedula.getText().toString().isEmpty()) {
    			helper.mostrarAlertaAdvertencia("Ingresar Cédula", Context.getInstance().getStage());
    			txtCedula.requestFocus();
    			return;
    		}
			if(txtNombres.getText().toString().isEmpty()) {
    			helper.mostrarAlertaAdvertencia("Ingresar Nombres", Context.getInstance().getStage());
    			txtCedula.requestFocus();
    			return;
    		}
			if(txtApellidos.getText().toString().isEmpty()) {
    			helper.mostrarAlertaAdvertencia("Ingresar Apellidos", Context.getInstance().getStage());
    			txtCedula.requestFocus();
    			return;
    		}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				//cuando el supervisor es nuevo, se graba todo
				if(supervisor.getIdSupervisor() == null) {
					
				}else {
					
				}
				
				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			
		}
	}

	public void salir() {
		Context.getInstance().getStageModal().close();
	}

}
