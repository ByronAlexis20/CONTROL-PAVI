package com.control.pavi.seguridad.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Perfil;
import com.control.pavi.model.Usuario;
import com.control.pavi.model.dao.PerfilDAO;
import com.control.pavi.model.dao.UsuarioDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;
import com.control.pavi.util.Encriptado;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UsuarioEditarController {
	@FXML private TextField txtApellidos;
	@FXML private TextField txtDireccion;
	@FXML private Button btnSalir;
	@FXML private TextField txtEmail;
	@FXML private TextField txtCedula;
	@FXML private Label lbCodigo;
	@FXML private TextField txtUsuario;
	@FXML private TextField txtTelefono;
	@FXML private CheckBox chkEstado;
	@FXML private ComboBox<Perfil> cboPerfil;
	@FXML private TextField txtNombres;
	@FXML private Button btnGrabar;
	@FXML private PasswordField txtClave;
	@FXML private Label lblMensajeCedula;

	PerfilDAO perfilDAO = new PerfilDAO();
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	ControllerHelper helper = new ControllerHelper();
	Usuario usuario;
	
	public void initialize() {
		try {
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnSalir.setStyle("-fx-cursor: hand;");
			
			btnGrabar.getStyleClass().add("botonGrabar");
			btnSalir.getStyleClass().add("botonSalir");
			
			llenarComboPerfil();
			//validar solo numeros
			txtCedula.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
						String newValue) {
					if (newValue.matches("\\d*")) {
						//int value = Integer.parseInt(newValue);
					} else {
						txtCedula.setText(oldValue);
					}
				}
			});
			txtTelefono.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
						String newValue) {
					if (newValue.matches("\\d*")) {
						//int value = Integer.parseInt(newValue);
					} else {
						txtTelefono.setText(oldValue);
					}
				}
			});
			
			txtCedula.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
					if (txtCedula.getText().length() > 10) {
						String s = txtCedula.getText().substring(0, 10);
						txtCedula.setText(s);
					}
				}
			});
			txtTelefono.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
					if (txtTelefono.getText().length() > 10) {
						String s = txtTelefono.getText().substring(0, 10);
						txtTelefono.setText(s);
					}
				}
			});
			
			if(Context.getInstance().getUsuarioEditar() != null) {
				usuario = Context.getInstance().getUsuarioEditar();
				recuperarDatos();
				Context.getInstance().setUsuarioEditar(null);
			}else {
				usuario = new Usuario();
			}
		}catch(Exception ex) {
			
		}
	}
	public void recuperarDatos(){
		try{
			txtCedula.setText(usuario.getCedula());
			txtNombres.setText(usuario.getNombres());
			txtApellidos.setText(usuario.getApellidos());
			//cboPerfil.setValue(listaUsuario.get(i).getSegPerfil());
			if(usuario.getPerfil() != null) {
				cboPerfil.getSelectionModel().select(usuario.getPerfil());
			}
			txtTelefono.setText(usuario.getTelefono());
			txtDireccion.setText(usuario.getDireccion());
			txtUsuario.setText(Encriptado.Desencriptar(usuario.getUsuario()));
			chkEstado.setSelected(usuario.getEstado());
			
			txtEmail.setText(usuario.getEmail());
		
		}catch(Exception e) {
			e.printStackTrace();
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
	
	public void grabar() {
		try {
			if(validarDatos() == false)
				return;

			usuario.setEstado(chkEstado.isSelected());
			//usuario.setSegPerfil(cboPerfil.getSelectionModel().getSelectedItem());
			usuario.setCedula(txtCedula.getText());
			usuario.setNombres(txtNombres.getText());
			usuario.setApellidos(txtApellidos.getText());
			usuario.setDireccion(txtDireccion.getText());
			usuario.setTelefono(txtTelefono.getText());
			usuario.setPerfil(cboPerfil.getSelectionModel().getSelectedItem());
			usuario.setEmail(txtEmail.getText());
			
			usuario.setUsuario(Encriptado.Encriptar(txtUsuario.getText()));
			usuario.setClave(Encriptado.Encriptar(txtClave.getText()));
			
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				usuarioDAO.getEntityManager().getTransaction().begin();
				if(usuario.getIdUsuario() == null) {//inserta
					usuarioDAO.getEntityManager().persist(usuario);
				}else {//modifica
					usuarioDAO.getEntityManager().merge(usuario);
				}
				usuarioDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				salir();
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			usuarioDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
	}
	boolean validarDatos() {
		try {
			if(txtCedula.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Debe ingresar cedula del Usuario", Context.getInstance().getStage());
				txtCedula.requestFocus();
				return false;
			}
			if(helper.validarDeCedula(txtCedula.getText()) == false) {
				helper.mostrarAlertaAdvertencia("Cédula Ingresada Incorrecta", Context.getInstance().getStage());
				txtCedula.requestFocus();
				return false;
			}
			if(txtNombres.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del Usuario", Context.getInstance().getStage());
				txtNombres.requestFocus();
				return false;
			}
			if(txtApellidos.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el apellidos del Usuario", Context.getInstance().getStage());
				txtApellidos.requestFocus();
				return false;
			}
			if(cboPerfil.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar el perfil del usuario", Context.getInstance().getStage());
				return false;
			}
			
			if(!txtEmail.getText().toString().isEmpty()) {
				if(helper.validarEmail(txtEmail.getText().toString()) == false) {
					helper.mostrarAlertaAdvertencia("Correo ingresado es incorrecto", Context.getInstance().getStage());
					txtEmail.requestFocus();
					return false;
				}
			}
			
			
			if(txtUsuario.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Debe ingresar un Usuario", Context.getInstance().getStage());
				txtUsuario.requestFocus();
				return false;	
			}
			if(txtClave.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Debe ingresar una clave para el usuario", Context.getInstance().getStage());
				txtClave.requestFocus();
				return false;	
			}
			if(validarUsuario() == true) {
				helper.mostrarAlertaAdvertencia("El usuario ya existe!!", Context.getInstance().getStage());
				txtUsuario.requestFocus();
				return false;	
			}
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	boolean validarUsuario() {
		try {
			List<Usuario> listaUsuario;
			listaUsuario = usuarioDAO.getValidarUsuario(Encriptado.Encriptar(txtUsuario.getText()), usuario.getIdUsuario());
			if(listaUsuario.size() != 0)
				return true;
			else
				return false;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public void salir() {
		Context.getInstance().getStageModal().close();
	}
}
