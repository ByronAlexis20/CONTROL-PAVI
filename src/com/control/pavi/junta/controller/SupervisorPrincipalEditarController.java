package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.control.pavi.model.AsignacionSupervisor;
import com.control.pavi.model.AsignacionSupervisorPrincipal;
import com.control.pavi.model.Supervisor;
import com.control.pavi.model.SupervisorPrincipal;
import com.control.pavi.model.dao.AsignacionSupervisorPrincipalDAO;
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

public class SupervisorPrincipalEditarController {
	@FXML private Button btnSalir;
	@FXML private TextField txtApellidos;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtTelefono;
	@FXML private TextField txtCodigo;
	@FXML private TextField txtNombres;
	@FXML private TableView<AsignacionSupervisorPrincipal> tvDatosJunta;
	@FXML private Button btnAgregar;
	@FXML private TextField txtCedula;
	@FXML private Button btnGrabar;
	@FXML private Button btnQuitar;

	SupervisorPrincipal supervisor;
	ControllerHelper helper = new ControllerHelper();
	AsignacionSupervisorPrincipalDAO asignacionDAO = new AsignacionSupervisorPrincipalDAO();

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
			cargarDatosSupervisoresSecundariosAsignados();
			if (Context.getInstance().getSupervisorPrincipal() != null) {
				supervisor = Context.getInstance().getSupervisorPrincipal();
				cargarDatos();
				Context.getInstance().setSupervisorPrincipal(null);
			} else {
				supervisor = new SupervisorPrincipal();
			}
		} catch (Exception ex) {

		}
	}

	private void cargarDatos() {
		try {
			txtCodigo.setText(String.valueOf(supervisor.getIdSupervisor()));
			txtApellidos.setText(supervisor.getApellidos());
			txtCedula.setText(supervisor.getCedula());
			txtDireccion.setText(supervisor.getDireccion());
			txtNombres.setText(supervisor.getNombres());
			txtTelefono.setText(supervisor.getTelefono());
			if (supervisor.getAsignacionSupervisorPrincipals().size() > 0) {
				cargarSupervisoresAsignados();
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void cargarDatosSupervisoresSecundariosAsignados() {
		try {
			tvDatosJunta.getColumns().clear();
			tvDatosJunta.getItems().clear();
			List<AsignacionSupervisorPrincipal> lista = new ArrayList<>();
			ObservableList<AsignacionSupervisorPrincipal> datos = FXCollections.observableArrayList();
			datos.setAll(lista);

			// llenar los datos en la tabla
			TableColumn<AsignacionSupervisorPrincipal, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(100);
			cedulaColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisorPrincipal, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisorPrincipal, String> param) {
							return new SimpleObjectProperty<String>(param.getValue().getSupervisor().getNoIdentificacion());
						}
					});

			TableColumn<AsignacionSupervisorPrincipal, String> supervisorColum = new TableColumn<>("Supervisor");
			supervisorColum.setMinWidth(10);
			supervisorColum.setPrefWidth(200);
			supervisorColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisorPrincipal, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisorPrincipal, String> param) {
							return new SimpleObjectProperty<String>(param.getValue().getSupervisor().getNombres() + " " + param.getValue().getSupervisor().getApellidos());
						}
					});

			TableColumn<AsignacionSupervisorPrincipal, String> telefonoColum = new TableColumn<>("Teléfono");
			telefonoColum.setMinWidth(10);
			telefonoColum.setPrefWidth(220);
			telefonoColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisorPrincipal, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisorPrincipal, String> param) {
							return new SimpleObjectProperty<String>(
									param.getValue().getSupervisor().getTelefono());
						}
					});

			TableColumn<AsignacionSupervisorPrincipal, String> juntaColum = new TableColumn<>("Juntas Asignadas");
			juntaColum.setMinWidth(10);
			juntaColum.setPrefWidth(200);
			juntaColum.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<AsignacionSupervisorPrincipal, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<AsignacionSupervisorPrincipal, String> param) {
							int cont = 0;
							for(AsignacionSupervisor asig : param.getValue().getSupervisor().getAsignacionSupervisors()) {
								if(asig.getEstado() == true)
									cont ++;
							}
							return new SimpleObjectProperty<String>(String.valueOf(cont));
						}
					});

			tvDatosJunta.getColumns().addAll(cedulaColum, supervisorColum, telefonoColum, juntaColum);
			tvDatosJunta.setItems(datos);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void cargarSupervisoresAsignados() {
		try {
			tvDatosJunta.getItems().clear();
			List<AsignacionSupervisorPrincipal> lista = new ArrayList<>();
			for (AsignacionSupervisorPrincipal asig : supervisor.getAsignacionSupervisorPrincipals()) {
				if (asig.getEstado() == true)
					lista.add(asig);
			}
			ObservableList<AsignacionSupervisorPrincipal> datos = FXCollections.observableArrayList();
			datos.setAll(lista);
			tvDatosJunta.setItems(datos);
			tvDatosJunta.refresh();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void agregarJunta() {
		try {
			Context.getInstance().setSupervisor(null);
			// obtener los que se han agregado temporalmente
			List<Supervisor> listaAsignadosTemporal = new ArrayList<>();
			if (tvDatosJunta.getItems().size() > 0) {
				for (AsignacionSupervisorPrincipal asig : tvDatosJunta.getItems()) {
					if (asig.getIdAsignacion() == null)
						listaAsignadosTemporal.add(asig.getSupervisor());
				}
			}
			Context.getInstance().setListaSupervisor(listaAsignadosTemporal);
			Context.getInstance().setListaSupervisor(listaAsignadosTemporal);
			helper.abrirPantallaModalSecundario("/junta/SupervisorSeleccionarSupervisor.fxml", "Seleccionar Supervisor Secundario", Context.getInstance().getStageModal());
			if (Context.getInstance().getSupervisor() != null) {
				Supervisor supervisorSeleccionada = Context.getInstance().getSupervisor();
				AsignacionSupervisorPrincipal agregar = new AsignacionSupervisorPrincipal();
				agregar.setIdAsignacion(null);
				agregar.setEstado(true);
				agregar.setSupervisor(supervisorSeleccionada);

				if (tvDatosJunta.getItems().size() > 0)
					tvDatosJunta.getItems().add(agregar);
				else {
					List<AsignacionSupervisorPrincipal> listadoAsignar = new ArrayList<>();
					ObservableList<AsignacionSupervisorPrincipal> datos = FXCollections.observableArrayList();
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
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion( "Se procederá a quitar el supervisor .. Desea Continuar?", Context.getInstance().getStage());
				if (result.get() == ButtonType.OK) {
					AsignacionSupervisorPrincipal seleccion = new AsignacionSupervisorPrincipal();
					seleccion = tvDatosJunta.getSelectionModel().getSelectedItem();
					// cuando es una junta que tiene el id diferente de null.. se le da de baja
					// directamente
					if (seleccion.getIdAsignacion() != null) {
						tvDatosJunta.getItems().remove(seleccion);
						seleccion.setEstado(false);
						asignacionDAO.getEntityManager().getTransaction().begin();
						seleccion = asignacionDAO.getEntityManager().merge(seleccion);
						asignacionDAO.getEntityManager().getTransaction().commit();
						helper.mostrarAlertaInformacion("Supervisor quitado", Context.getInstance().getStage());
					} else {// cuando es nuevo.. no es necesario ir a la base de datos.. se la elimina
							// directamente de la tabla
						tvDatosJunta.getItems().remove(seleccion);
					}
				}
			} else {
				helper.mostrarAlertaError("Debe Seleccionar un Supervisor a Quitar", Context.getInstance().getStage());
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
			if(tvDatosJunta.getItems().size() <= 0) {
				helper.mostrarAlertaAdvertencia("No ha realizado la asignación de supervisores", Context.getInstance().getStage());
    			txtCedula.requestFocus();
    			return;
			}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				supervisor.setApellidos(txtApellidos.getText());
				supervisor.setDireccion(txtDireccion.getText());
				supervisor.setEstado(true);
				supervisor.setCedula(txtCedula.getText());
				supervisor.setNombres(txtNombres.getText());
				supervisor.setTelefono(txtTelefono.getText());
				
				asignacionDAO.getEntityManager().getTransaction().begin();
				//cuando el supervisor es nuevo, se graba todo
				if(supervisor.getIdSupervisor() == null) {
					supervisor.setIdSupervisor(null);
					//es un nuevo entonces no tiene asignaciones aun
					List<AsignacionSupervisorPrincipal> listadoAsignaciones = new ArrayList<>();
					for(AsignacionSupervisorPrincipal asig : tvDatosJunta.getItems()) {
						asig.setSupervisorPrincipal(supervisor);
						listadoAsignaciones.add(asig);
					}
					supervisor.setAsignacionSupervisorPrincipals(listadoAsignaciones);
					asignacionDAO.getEntityManager().persist(supervisor);
				}else {

					if(supervisor.getAsignacionSupervisorPrincipals().size() > 0) {
						for(AsignacionSupervisorPrincipal asig : tvDatosJunta.getItems()) {
							if(asig.getIdAsignacion() == null) {
								asig.setSupervisorPrincipal(supervisor);
								supervisor.getAsignacionSupervisorPrincipals().add(asig);
							}
						}
					}else {
						List<AsignacionSupervisorPrincipal> listadoAsignaciones = new ArrayList<>();
						for(AsignacionSupervisorPrincipal asig : tvDatosJunta.getItems()) {
							if(asig.getIdAsignacion() == null) {
								asig.setSupervisorPrincipal(supervisor);
								listadoAsignaciones.add(asig);
							}
						}
						supervisor.setAsignacionSupervisorPrincipals(listadoAsignaciones);
					}
					asignacionDAO.getEntityManager().merge(supervisor);
				}
				asignacionDAO.getEntityManager().getTransaction().commit();
				Context.getInstance().getStageModal().close();
				helper.mostrarAlertaInformacion("Datos Grabados", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			asignacionDAO.getEntityManager().getTransaction().rollback();
		}
	}

	public void salir() {
		Context.getInstance().getStageModal().close();
	}
}
