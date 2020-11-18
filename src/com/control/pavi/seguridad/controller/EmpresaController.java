package com.control.pavi.seguridad.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.control.pavi.model.Empresa;
import com.control.pavi.model.dao.EmpresaDAO;
import com.control.pavi.util.Constantes;
import com.control.pavi.util.Context;
import com.control.pavi.util.ControllerHelper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class EmpresaController {
	@FXML private TextField txtDireccion;
	@FXML private TextField txtNombreSistema;
	@FXML private TextField txtDescripcionSistema;
	@FXML private TextField txtCodigo;
	@FXML private TextField txtRepresentante;
	@FXML private TextField txtRuc;
	@FXML private TextField txtTelefono;
	@FXML private ImageView ivLogo;
	@FXML private CheckBox chkEstado;
	@FXML private Button btnGrabar;
	@FXML private Button btnExaminar;
	@FXML private TextField txtRazonSocial;
	@FXML private Button btnQuitar;

	EmpresaDAO empresaDAO = new EmpresaDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			btnExaminar.setStyle("-fx-cursor: hand;");
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnQuitar.setStyle("-fx-cursor: hand;");

			txtCodigo.setText("0");
			txtCodigo.setEditable(false);
			txtCodigo.setVisible(false);
			int maxLength = 13;
			int maxLengthTelf = 10;
			recuperarDatos();

			//validar solo numeros
			txtRuc.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.matches("\\d*")) {
						//int value = Integer.parseInt(newValue);
					} else {
						txtRuc.setText(oldValue);
					}
				}
			});
			//validar solo numeros
			txtTelefono.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.matches("\\d*")) {
						//int value = Integer.parseInt(newValue);
					} else {
						txtTelefono.setText(oldValue);
					}
				}
			});

			//solo letras mayusculas
			txtRazonSocial.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtRazonSocial.getText().toUpperCase();
					txtRazonSocial.setText(cadena);
				}
			});

			//solo letras mayusculas
			txtNombreSistema.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtRazonSocial.getText().toUpperCase();
					txtNombreSistema.setText(cadena);
				}
			});
			
			//solo letras mayusculas
			txtDescripcionSistema.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtRazonSocial.getText().toUpperCase();
					txtDescripcionSistema.setText(cadena);
				}
			});
			
			//validar solo 13 valores
			txtRuc.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
					if (txtRuc.getText().length() > maxLength) {
						String s = txtRuc.getText().substring(0, maxLength);
						txtRuc.setText(s);
					}
				}
			});

			//validar solo 10 valores
			txtTelefono.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
					if (txtTelefono.getText().length() > maxLengthTelf) {
						String s = txtTelefono.getText().substring(0, maxLengthTelf);
						txtTelefono.setText(s);
					}
				}
			});

			//solo letras mayusculas
			txtRepresentante.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtRepresentante.getText().toUpperCase();
					txtRepresentante.setText(cadena);
				}
			});

			//validar solo letras.... igual se va con puntuaciones
			txtRepresentante.textProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\D*")) {
						txtRepresentante.setText(newValue.replaceAll("[^\\D]", ""));
					}
				}
			});

			//solo letras mayusculas
			txtDireccion.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtDireccion.getText().toUpperCase();
					txtDireccion.setText(cadena);
				}
			});
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void recuperarDatos(){
		try{
			List<Empresa> listaEmpresa = new ArrayList<Empresa>();
			listaEmpresa = empresaDAO.getListaEmpresa();
			for(int i = 0 ; i < listaEmpresa.size() ; i ++) {
				txtCodigo.setText(Integer.toString(listaEmpresa.get(i).getIdEmpresa()));
				txtRuc.setText(listaEmpresa.get(i).getRuc());
				txtRazonSocial.setText(listaEmpresa.get(i).getRazonSocial());
				txtRepresentante.setText(listaEmpresa.get(i).getRepresentante());
				txtTelefono.setText(listaEmpresa.get(i).getTelefono());
				txtDireccion.setText(listaEmpresa.get(i).getDireccion());
				txtNombreSistema.setText(listaEmpresa.get(i).getNombreSistema());
				txtDescripcionSistema.setText(listaEmpresa.get(i).getDescripcionSistema());
				chkEstado.setSelected(listaEmpresa.get(i).getEstado());
				
				if(listaEmpresa.get(i).getLogo() != null) {
					String imgString = new String(listaEmpresa.get(i).getLogo(), "UTF-8");
					ivLogo.setImage(helper.getImageFromBase64String(imgString).getImage());
				}
				else {
					Image img = new Image("/usuario.jpg");
					ivLogo.setImage(img);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void grabar() {
		try {
			boolean estado;
			if(validarDatos() == false)
				return;
			if(chkEstado.isSelected() == true)
				estado = Constantes.ESTADO_ACTIVO;
			else
				estado = Constantes.ESTADO_INACTIVO;

			Empresa empresa = new Empresa();
			empresa.setRuc(txtRuc.getText());
			empresa.setRazonSocial(txtRazonSocial.getText());
			empresa.setRepresentante(txtRepresentante.getText());
			empresa.setTelefono(txtTelefono.getText());
			empresa.setDireccion(txtDireccion.getText());
			empresa.setNombreSistema(txtNombreSistema.getText());
			empresa.setDescripcionSistema(txtDescripcionSistema.getText());
			empresa.setEstado(estado);
			empresa.setLogo(helper.encodeFileToBase64Binary(ivLogo.getImage()).getBytes());

			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				empresaDAO.getEntityManager().getTransaction().begin();
				if(txtCodigo.getText().equals("0")) {//inserta
					empresa.setIdEmpresa(null);
					empresaDAO.getEntityManager().persist(empresa);
				}else {//modifica
					empresa.setIdEmpresa(Integer.parseInt(txtCodigo.getText()));
					empresaDAO.getEntityManager().merge(empresa);
				}
				empresaDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				recuperarDatos();
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			empresaDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
	}
	boolean validarDatos() {
		try {
			if(txtRuc.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Ingresar RUC de la empresa", Context.getInstance().getStage());
				txtRuc.requestFocus();
				return false;
			}

			if(txtRazonSocial.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Ingresar Razón Social", Context.getInstance().getStage());
				txtRazonSocial.requestFocus();
				return false;
			}

			if(txtRepresentante.toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Ingresar Representante Legal de la Empresa", Context.getInstance().getStage());
				txtRepresentante.requestFocus();
				return false;	
			}

			if(txtTelefono.toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Ingresar teléfono de contacto", Context.getInstance().getStage());
				txtTelefono.requestFocus();
				return false;	
			}

			if(txtDireccion.getText().toString().isEmpty()) {
				helper.mostrarAlertaAdvertencia("Ingresar dirección de la empresa", Context.getInstance().getStage());
				txtDireccion.requestFocus();
				return false;	
			}
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public void examinar() {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Buscar Imagen");
			// Agregar filtros para facilitar la busqueda
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Imagen jpg", "*.jpg"),
					new FileChooser.ExtensionFilter("Imagen png", "*.png")
					);
			// Obtener la imagen seleccionada
			File imgFile = fileChooser.showOpenDialog(Context.getInstance().getStage());
			// Mostar la imagen
			if (imgFile != null) {
				Image image = new Image("file:" + imgFile.getAbsolutePath());
				ivLogo.setImage(image);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void quitar() {
		try {
			Image img = new Image("/usuario.jpg");
			ivLogo.setImage(img);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
