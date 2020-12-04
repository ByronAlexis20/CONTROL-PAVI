package com.control.pavi.junta.controller;

import java.util.ArrayList;
import java.util.List;

import com.control.pavi.model.AsignacionJunta;
import com.control.pavi.model.AsignacionSupervisor;
import com.control.pavi.model.JuntaVoto;
import com.control.pavi.model.Supervisor;
import com.control.pavi.model.dao.AsignacionSupervisorDAO;
import com.control.pavi.model.dao.JuntaVotoDAO;
import com.control.pavi.model.dao.SupervisorDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class SupervisorListaController {
	@FXML private TableView<Supervisor> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;
	@FXML private RadioButton rbParroquia;
	@FXML private RadioButton rbProvincia;
	@FXML private RadioButton rbRecinto;
	@FXML private RadioButton rbCanton;
	@FXML private TextField txtBuscarJunta;
	@FXML private TableView<JuntaVoto> tvJunta;
	
	JuntaVotoDAO juntaDAO = new JuntaVotoDAO();
	AsignacionSupervisorDAO asignacionDAO = new AsignacionSupervisorDAO();
	SupervisorDAO supervisorDAO = new SupervisorDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnEditar.setStyle("-fx-cursor: hand;");
			btnEliminar.setStyle("-fx-cursor: hand;");
			btnNuevo.setStyle("-fx-cursor: hand;");
			
			btnEditar.getStyleClass().add("botonEditar");
			btnNuevo.getStyleClass().add("botonNuevo");
			btnEliminar.getStyleClass().add("botonEliminar");
			rbProvincia.setSelected(true);
			llenarDatosJuntaFaltantesDeAsignar();
			cargarSupervisoresAsignador();
			txtBuscarJunta.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarJuntasFaltantes();
					} 
				} 
			}); 
			txtBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarSupervisor();
					} 
				} 
			}); 
		}catch(Exception ex) {
			
		}
	}
	
	private void buscarSupervisor() {
		try {
			List<Supervisor> lista;
			ObservableList<Supervisor> datos = FXCollections.observableArrayList();
			lista = supervisorDAO.buscarPorPatron(txtBuscar.getText().toString());
			datos.setAll(lista);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void cargarSupervisoresAsignador() {
		try {
			List<Supervisor> lista = supervisorDAO.buscarPorPatron("");
			ObservableList<Supervisor> datos = FXCollections.observableArrayList();
			datos.setAll(lista);
			TableColumn<Supervisor, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(100);
			cedulaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Supervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Supervisor, String> param) {
					
					return new SimpleObjectProperty<String>(param.getValue().getNoIdentificacion());
				}
			});
			
			TableColumn<Supervisor, String> nombreColum = new TableColumn<>("Supervisor");
			nombreColum.setMinWidth(10);
			nombreColum.setPrefWidth(200);
			nombreColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Supervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Supervisor, String> param) {
					String supervisor = "";
					supervisor = param.getValue().getNombres() + " " + param.getValue().getApellidos();
					return new SimpleObjectProperty<String>(supervisor);
				}
			});
			
			TableColumn<Supervisor, String> telefonoColum = new TableColumn<>("Telefono");
			telefonoColum.setMinWidth(10);
			telefonoColum.setPrefWidth(150);
			telefonoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Supervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Supervisor, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getTelefono());
				}
			});
			
			TableColumn<Supervisor, String> asignacionColum = new TableColumn<>("Juntas Asignadas");
			asignacionColum.setMinWidth(10);
			asignacionColum.setPrefWidth(150);
			asignacionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Supervisor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Supervisor, String> param) {
					int cont = 0;
					for(AsignacionSupervisor asig : param.getValue().getAsignacionSupervisors()) {
						if(asig.getEstado() == true)
							cont ++;
					}
					return new SimpleObjectProperty<String>(String.valueOf(cont));
				}
			});
			
			tvDatos.getColumns().addAll(cedulaColum,nombreColum,telefonoColum,asignacionColum);
			tvDatos.setItems(datos);
		}catch(Exception ex) {
			
		}
	}
	
	private void buscarJuntasFaltantes() {
		try {
			boolean bandera = false;
			List<JuntaVoto> lista;
			List<JuntaVoto> juntasLibres = new ArrayList<>();
			ObservableList<JuntaVoto> datos = FXCollections.observableArrayList();
			List<AsignacionSupervisor> asignaciones = asignacionDAO.buscarAsignaciones();
			
			if(rbProvincia.isSelected()) {
				lista = juntaDAO.buscarTodosActivoProvincia(txtBuscarJunta.getText());
				for(JuntaVoto jun : lista) {
					bandera = false;
					for(AsignacionSupervisor asig : asignaciones) {
						if(jun.getIdJunta() == asig.getJuntaVoto().getIdJunta())
							bandera = true;
					}
					if(bandera == false)
						juntasLibres.add(jun);
				}
			}
			else if(rbCanton.isSelected()) {
				lista = juntaDAO.buscarTodosActivoCanton(txtBuscarJunta.getText());
				for(JuntaVoto jun : lista) {
					bandera = false;
					for(AsignacionSupervisor asig : asignaciones) {
						if(jun.getIdJunta() == asig.getJuntaVoto().getIdJunta())
							bandera = true;
					}
					if(bandera == false)
						juntasLibres.add(jun);
				}
			}
			else if(rbParroquia.isSelected()) {
				lista = juntaDAO.buscarTodosActivoParroquia(txtBuscarJunta.getText());
				for(JuntaVoto jun : lista) {
					bandera = false;
					for(AsignacionSupervisor asig : asignaciones) {
						if(jun.getIdJunta() == asig.getJuntaVoto().getIdJunta())
							bandera = true;
					}
					if(bandera == false)
						juntasLibres.add(jun);
				}
			}
			else if(rbRecinto.isSelected()) {
				lista = juntaDAO.buscarTodosActivoRecinto(txtBuscarJunta.getText());
				for(JuntaVoto jun : lista) {
					bandera = false;
					for(AsignacionSupervisor asig : asignaciones) {
						if(jun.getIdJunta() == asig.getJuntaVoto().getIdJunta())
							bandera = true;
					}
					if(bandera == false)
						juntasLibres.add(jun);
				}
			}
			datos.setAll(juntasLibres);
			tvJunta.setItems(datos);
			tvJunta.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void llenarDatosJuntaFaltantesDeAsignar() {
		try {
			boolean bandera = false;
			tvJunta.getColumns().clear();
			tvJunta.getItems().clear();
			List<JuntaVoto> lista;
			List<JuntaVoto> juntasLibres = new ArrayList<>();
			ObservableList<JuntaVoto> datos = FXCollections.observableArrayList();
			lista = juntaDAO.buscarTodosActivo();
			List<AsignacionSupervisor> asignaciones = asignacionDAO.buscarAsignaciones();
			for(JuntaVoto jun : lista) {
				bandera = false;
				for(AsignacionSupervisor asig : asignaciones) {
					if(jun.getIdJunta() == asig.getJuntaVoto().getIdJunta())
						bandera = true;
				}
				if(bandera == false)
					juntasLibres.add(jun);
			}
			
			
			datos.setAll(juntasLibres);

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
			
			TableColumn<JuntaVoto, String> generoColum = new TableColumn<>("Género");
			generoColum.setMinWidth(10);
			generoColum.setPrefWidth(80);
			generoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JuntaVoto,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<JuntaVoto, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getGenero());
				}
			});
			
			TableColumn<JuntaVoto, String> numeroColum = new TableColumn<>("Número");
			numeroColum.setMinWidth(10);
			numeroColum.setPrefWidth(80);
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
			
			tvJunta.getColumns().addAll(provinciaColum,parroquiaColum,recintoColum,generoColum,numeroColum,delegadoColum,partidoPoliticoColum);
			tvJunta.setItems(datos);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevo() {
		try {
			Context.getInstance().setSupervisor(null);
			helper.abrirPantallaModal("/junta/SupervisorEditar.fxml","Supervisor", Context.getInstance().getStage());
			cargarSupervisoresAsignador();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	public void editar() {
		try {
			Context.getInstance().setSupervisor(tvDatos.getSelectionModel().getSelectedItem());
			helper.abrirPantallaModal("/junta/SupervisorEditar.fxml","Supervisor", Context.getInstance().getStage());
			cargarSupervisoresAsignador();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	public void eliminar() {
		
	}

	public void cambiaProvincia() {
    	if(rbProvincia.isSelected()) {
    		rbProvincia.setSelected(true);
    		rbCanton.setSelected(false);
    		rbParroquia.setSelected(false);
    		rbRecinto.setSelected(false);
    	}
    }

    
    public void cambiaCanton() {
    	if(rbCanton.isSelected()) {
    		rbProvincia.setSelected(false);
    		rbCanton.setSelected(true);
    		rbParroquia.setSelected(false);
    		rbRecinto.setSelected(false);
    	}
    }

    
    public void cambiaParroquia() {
    	if(rbParroquia.isSelected()) {
    		rbProvincia.setSelected(false);
    		rbCanton.setSelected(false);
    		rbParroquia.setSelected(true);
    		rbRecinto.setSelected(false);
    	}
    }

    
    public void cambiaRecinto() {
    	if(rbRecinto.isSelected()) {
    		rbProvincia.setSelected(false);
    		rbCanton.setSelected(false);
    		rbParroquia.setSelected(false);
    		rbRecinto.setSelected(true);
    	}
    }
}
