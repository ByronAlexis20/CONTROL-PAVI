package com.control.pavi.seguridad.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Usuario;
import com.control.pavi.model.dao.UsuarioDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;
import com.control.pavi.util.Encriptado;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class UsuarioListaController {
	@FXML private TableView<Usuario> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;

	UsuarioDAO usuarioDAO = new UsuarioDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnEditar.setStyle("-fx-cursor: hand;");
			btnEliminar.setStyle("-fx-cursor: hand;");
			btnNuevo.setStyle("-fx-cursor: hand;");
			
			btnEditar.getStyleClass().add("botonEditar");
			btnNuevo.getStyleClass().add("botonNuevo");
			btnEliminar.getStyleClass().add("botonEliminar");
			txtBuscar.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtBuscar.getText().toUpperCase();
					txtBuscar.setText(cadena);
				}
			});
			txtBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarUsuario();
					} 
				} 
			}); 
			llenarDatos("");
		}catch(Exception ex) {
			
		}
	}
	@SuppressWarnings("unchecked")
	void llenarDatos(String busqueda){
		try{
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<Usuario> listaPerfil;
			ObservableList<Usuario> datos = FXCollections.observableArrayList();
			listaPerfil = usuarioDAO.buscarPorNombreApellido(busqueda);
			datos.setAll(listaPerfil);

			//llenar los datos en la tabla
			TableColumn<Usuario, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(90);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdUsuario()));
				}
			});
			TableColumn<Usuario, String> nombresColum = new TableColumn<>("Nombres y Apellidos");
			nombresColum.setMinWidth(10);
			nombresColum.setPrefWidth(260);
			nombresColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getNombres() + " " + param.getValue().getApellidos());
				}
			});
			TableColumn<Usuario, String> emailColum = new TableColumn<>("Email");
			emailColum.setMinWidth(10);
			emailColum.setPrefWidth(150);
			emailColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getEmail());
				}
			});
			
			TableColumn<Usuario, String> telefonoColum = new TableColumn<>("Teléfono");
			telefonoColum.setMinWidth(10);
			telefonoColum.setPrefWidth(100);
			telefonoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getTelefono());
				}
			});
			
			TableColumn<Usuario, String> usuarioColum = new TableColumn<>("Usuario");
			usuarioColum.setMinWidth(10);
			usuarioColum.setPrefWidth(100);
			usuarioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
					return new SimpleObjectProperty<String>(Encriptado.Desencriptar(param.getValue().getUsuario()));
				}
			});
			
			TableColumn<Usuario, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(90);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
					String estado = "";
					if(param.getValue().getEstado() == true)
						estado = "Activo";
					else
						estado = "Inactivo";
					return new SimpleObjectProperty<String>(estado);
				}
			});

			
			tvDatos.getColumns().addAll(idColum,nombresColum,telefonoColum,usuarioColum,estadoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void buscarUsuario() {
		try {
			List<Usuario> listaUsuario;
			ObservableList<Usuario> datos = FXCollections.observableArrayList();
			listaUsuario = usuarioDAO.buscarPorNombreApellido(txtBuscar.getText().toString());
			datos.setAll(listaUsuario);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevoUsuario() {
		try {
			Context.getInstance().setUsuarioEditar(null);
			helper.abrirPantallaModal("/seguridad/UsuarioEditar.fxml","Datos del Usuario", Context.getInstance().getStage());
			llenarDatos("");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void editarUsuario() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Context.getInstance().setUsuarioEditar(tvDatos.getSelectionModel().getSelectedItem());
				helper.abrirPantallaModal("/seguridad/UsuarioEditar.fxml","Datos del Usuario", Context.getInstance().getStage());
				llenarDatos("");
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Usuario a Editar", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void eliminarUsuario() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a dar de baja al Usuario.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					Usuario seleccion = new Usuario();
					seleccion = tvDatos.getSelectionModel().getSelectedItem();
					seleccion.setEstado(false);
					usuarioDAO.getEntityManager().getTransaction().begin();
					usuarioDAO.getEntityManager().merge(seleccion);
					usuarioDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Usuario Dado de Baja", Context.getInstance().getStage());
					llenarDatos("");
				}
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Usuario a Dar de Baja", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
