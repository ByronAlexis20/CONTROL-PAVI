package com.control.pavi.seguridad.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Menu;
import com.control.pavi.model.Perfil;
import com.control.pavi.model.Permiso;
import com.control.pavi.model.dao.MenuDAO;
import com.control.pavi.model.dao.PerfilDAO;
import com.control.pavi.model.dao.PermisoDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class PermisosController {
	@FXML private TableView<Permiso> tvPermiso;
	@FXML private Button btnGuardar;
	@FXML private TableView<Menu> tvMenu;
	@FXML private ComboBox<Perfil> cboPerfil;
	@FXML private Button btnAnadir;
	@FXML private Button btnQuitar;

	PerfilDAO perfilDAO = new PerfilDAO();
	MenuDAO menuDAO = new MenuDAO();
	PermisoDAO permisoDAO = new PermisoDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			llenarComboPerfil();
			llenar_Datos();
		}catch(Exception ex) {
			
		}
	}
	private void llenarComboPerfil(){
		try{
			cboPerfil.setPromptText("Seleccionar perfil");
			List<Perfil> listaPerfiles;
			listaPerfiles = perfilDAO.buscarPorPatron("");
			ObservableList<Perfil> datos = FXCollections.observableArrayList();
			datos.addAll(listaPerfiles);
			cboPerfil.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	void llenar_Datos(){
		try{
			tvMenu.getItems().clear();
			tvMenu.getColumns().clear();
			List<Menu> ListaMenu = new ArrayList<Menu>();
			ListaMenu = menuDAO.getListaMenuAccesos();

			ObservableList<Menu> datos = FXCollections.observableArrayList();
			datos.setAll(ListaMenu);

			//llenar los datos en la tabla
			TableColumn<Menu, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(72);
			idColum.setCellValueFactory(new PropertyValueFactory<Menu, String>("idMenu"));

			TableColumn<Menu, String> nombreColum = new TableColumn<>("Nombre del Formulario");
			nombreColum.setMinWidth(10);
			nombreColum.setPrefWidth(400);
			nombreColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Menu,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Menu, String> param) {
					String dato = param.getValue().getDescripcion();
					List<Menu> listaMenuAll = new ArrayList<Menu>();
					listaMenuAll = menuDAO.getListaMenu();
					for(int j = 0 ; j < listaMenuAll.size() ; j ++) {
						if(param.getValue().getIdMenuPadre().equals(listaMenuAll.get(j).getIdMenu()))
							dato = listaMenuAll.get(j).getDescripcion() +  "/" + dato;
					}
					return new SimpleObjectProperty<String>(dato);
				}
			});
			tvMenu.getColumns().addAll(idColum,nombreColum);
			tvMenu.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public void cargarAccesos() {
		if(cboPerfil.getSelectionModel().getSelectedItem() != null) {
			cargarPermisosPerfil(cboPerfil.getSelectionModel().getSelectedItem().getIdPerfil());
		}
	}
	@SuppressWarnings("unchecked")
	public void cargarPermisosPerfil(int idPerfil) {
		try {
			List<Permiso> resultado = permisoDAO.getPermiso(idPerfil);
			if(resultado.size() > 0) {
				boolean bandera = false;
				List<Menu> listaMenus = menuDAO.getListaMenuAccesos();
				
				ObservableList<Menu> datos = FXCollections.observableArrayList();
				datos.setAll(listaMenus);
				ObservableList<Menu> datosMenu = FXCollections.observableArrayList();

				tvMenu.getColumns().clear();
				tvMenu.getItems().clear();

				//verificar si el menu esta asignado a un perfil
				for(int i = 0 ; i < datos.size() ; i ++) {
					bandera = false;
					for(int j = 0 ; j < resultado.size() ; j ++) {
						if(datos.get(i).getIdMenu().equals(resultado.get(j).getMenu().getIdMenu()))
							bandera = true;
					}
					if(bandera == false)
						datosMenu.add(datos.get(i));
				}

				//llenar los datos en la tabla
				TableColumn<Menu, String> idColum = new TableColumn<>("Código");
				idColum.setMinWidth(10);
				idColum.setPrefWidth(70);
				idColum.setCellValueFactory(new PropertyValueFactory<Menu, String>("idMenu"));

				TableColumn<Menu, String> nombreColum = new TableColumn<>("Nombre del Menu");
				nombreColum.setMinWidth(10);
				nombreColum.setPrefWidth(400);
				nombreColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Menu,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Menu, String> param) {
						String dato = param.getValue().getDescripcion();
						List<Menu> listaMenuAll = new ArrayList<Menu>();
						listaMenuAll = menuDAO.getListaMenu();
						for(int j = 0 ; j < listaMenuAll.size() ; j ++) {
							if(param.getValue().getIdMenuPadre().equals(listaMenuAll.get(j).getIdMenu()))
								dato = listaMenuAll.get(j).getDescripcion() +  "/" + dato;
						}
						return new SimpleObjectProperty<String>(dato);
					}
				});

				tvMenu.getColumns().addAll(idColum,nombreColum);
				tvMenu.setItems(datosMenu);

			}else {
				tvMenu.getColumns().clear();
				llenar_Datos();
			}

			//recupera los asignados a esa persona
			if(resultado.size() > 0) {
				//llenar los datos en la tabla
				tvPermiso.getColumns().clear();
				tvPermiso.getItems().clear();
				List<Permiso> listaPermisos = resultado;
				ObservableList<Permiso> datos = FXCollections.observableArrayList();
				datos.setAll(listaPermisos);
				TableColumn<Permiso, String> idColum = new TableColumn<>("Código");
				idColum.setMinWidth(10);
				idColum.setPrefWidth(70);
				idColum.setCellValueFactory(new PropertyValueFactory<Permiso, String>("idPermiso"));

				TableColumn<Permiso, String> nombreColum = new TableColumn<>("Nombre del Menu");
				nombreColum.setMinWidth(10);
				nombreColum.setPrefWidth(400);
				nombreColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Permiso,String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Permiso, String> param) {
						String dato = param.getValue().getMenu().getDescripcion();
						List<Menu> listaMenuAll = new ArrayList<Menu>();
						listaMenuAll = menuDAO.getListaMenu();
						for(int j = 0 ; j < listaMenuAll.size() ; j ++) {
							if(param.getValue().getMenu().getIdMenuPadre().equals(listaMenuAll.get(j).getIdMenu()))
								dato = listaMenuAll.get(j).getDescripcion() +  "/" + dato;
						}
						return new SimpleObjectProperty<String>(dato);
					}
				});
				tvPermiso.getColumns().addAll(idColum,nombreColum);
				tvPermiso.setItems(datos);				
			}else {
				tvPermiso.getColumns().clear();
				tvPermiso.getItems().clear();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void anadir() {
		try {
			if(tvMenu.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					permisoDAO.getEntityManager().getTransaction().begin();
					Permiso permiso = new Permiso();
					permiso.setIdPermiso(null);
					permiso.setEstado(true);
					permiso.setPerfil(cboPerfil.getSelectionModel().getSelectedItem());
					permiso.setMenu(tvMenu.getSelectionModel().getSelectedItem());
					
					permisoDAO.getEntityManager().persist(permiso);
					permisoDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
					cargarPermisosPerfil(cboPerfil.getSelectionModel().getSelectedItem().getIdPerfil());
				}
			}
		}catch(Exception ex) {
			permisoDAO.getEntityManager().getTransaction().rollback();
		}
	}

	public void quitar() {
		try {
			if(tvPermiso.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					permisoDAO.getEntityManager().getTransaction().begin();
					Permiso permiso = tvPermiso.getSelectionModel().getSelectedItem();
					permiso.setEstado(false);
					
					permisoDAO.getEntityManager().persist(permiso);
					permisoDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
					cargarPermisosPerfil(cboPerfil.getSelectionModel().getSelectedItem().getIdPerfil());
				}
			}
		}catch(Exception ex) {
			permisoDAO.getEntityManager().getTransaction().rollback();
		}
	}

}
