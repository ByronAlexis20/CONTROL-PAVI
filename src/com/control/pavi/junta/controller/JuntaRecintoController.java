package com.control.pavi.junta.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.AsignacionJunta;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class JuntaRecintoController {
	@FXML private TableView<JuntaVoto> tvDatos;
	@FXML private Button btnQuitar;
	@FXML private Button btnAsignar;
	@FXML private TextField txtRecinto;
	@FXML private TextField txtProvincia;
	@FXML private TextField txtCanton;
	@FXML private TextField txtParroquia;
	
	Recinto recinto;
	JuntaVotoDAO juntaDAO = new JuntaVotoDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnAsignar.setStyle("-fx-cursor: hand;");
			btnQuitar.setStyle("-fx-cursor: hand;");
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
			
			tvDatos.getColumns().addAll(generoColum,numeroColum,delegadoColum,partidoPoliticoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex) {
			
		}
	}
	
	public void asignar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar un registro", Context.getInstance().getStage());
				return;
			}
			
			boolean bandera = false;
			for(AsignacionJunta asig : tvDatos.getSelectionModel().getSelectedItem().getAsignacionJuntas()) {
				if(asig.getEstado() == true)
					bandera = true;
			}
			if(bandera == true) {
				helper.mostrarAlertaAdvertencia("Ya existe un delegado para la Junta", Context.getInstance().getStage());
				return;
			}
			Context.getInstance().setJuntaVoto(tvDatos.getSelectionModel().getSelectedItem());
			helper.abrirPantallaModal("/junta/AsignarDelegado.fxml","Datos Delegados", Context.getInstance().getStageModal());
			llenarDatos();
		}catch(Exception ex) {
			
		}
	}

	
	public void quitar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a quitar el delegado.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					JuntaVoto seleccion = new JuntaVoto();
					seleccion = tvDatos.getSelectionModel().getSelectedItem();
					for(AsignacionJunta a : seleccion.getAsignacionJuntas()) {
						if(a.getEstado() == true) {
							AsignacionJunta asig = a;
							asig.setEstado(false);
							
							juntaDAO.getEntityManager().getTransaction().begin();
							juntaDAO.getEntityManager().merge(asig);
							juntaDAO.getEntityManager().getTransaction().commit();
							helper.mostrarAlertaInformacion("Delegado quitado", Context.getInstance().getStage());
							llenarDatos();
						}						
					}
					
				}
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Delegado a Quitar", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
