package com.control.pavi.seguridad.controller;

import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Empresa;
import com.control.pavi.model.Usuario;
import com.control.pavi.model.dao.EmpresaDAO;
import com.control.pavi.model.dao.UsuarioDAO;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;
import com.control.pavi.util.Encriptado;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class InicioSesionController {
	@FXML private ImageView ivLogin;
	@FXML private Button btnAceptar;
	@FXML private Button btnCancelar;
	@FXML private AnchorPane apContenido;
	@FXML private TextField txtUsuario;
	@FXML private PasswordField txtClave;
	@FXML private Label lblNombreSistema;
	@FXML private Label lblDescripcion;

	EmpresaDAO empresaDAO = new EmpresaDAO();
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	ControllerHelper helper = new ControllerHelper();
	String nombreSistema = "";
	public void initialize() {
		try {
			btnAceptar.setStyle("-fx-cursor: hand;");
			btnCancelar.setStyle("-fx-cursor: hand;");
			
			List<Empresa> empresaLista = empresaDAO.getListaEmpresa();
			for(Empresa emp : empresaLista) {
				nombreSistema = emp.getNombreSistema();
				lblNombreSistema.setText(emp.getNombreSistema());
				lblDescripcion.setText(emp.getDescripcionSistema());
			}
			Image image = new Image("usuario_login.png");
			ivLogin.setImage(image);
			
			txtUsuario.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent ke){
					if (ke.getCode().equals(KeyCode.ENTER)){
						aceptar();
					}
				}
			});
			txtClave.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent ke){
					if (ke.getCode().equals(KeyCode.ENTER)){
						aceptar();
					}
				}
			});
			
		}catch(Exception ex) {
		}
	}
	
	public void aceptar() {
		try {
			List<Usuario> usuario;
    		usuario = usuarioDAO.getUsuario(Encriptado.Encriptar(txtUsuario.getText()),Encriptado.Encriptar(txtClave.getText()));
    		System.out.println(usuario.size());
    		if(usuario.size() > 0){
    			System.out.println(usuario.size());
    			Context.getInstance().setUsuario(usuario.get(0));
    			helper.abrirPantallaPrincipal(nombreSistema,"/principal/Principal.fxml");
    		}else {
    			helper.mostrarAlertaError("Usuario o clave incorrecto", Context.getInstance().getStage());
    		}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void cancelar() {
		try {
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Salir?",Context.getInstance().getStagePrincipal());
			if(result.get() == ButtonType.OK)
				System.exit(0);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
