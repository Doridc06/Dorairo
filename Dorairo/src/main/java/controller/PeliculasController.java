package controller;

import java.net.URL;
import java.util.ResourceBundle;

import constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PeliculasController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox GeneroAccion;

    @FXML
    private CheckBox GeneroAnimacion;

    @FXML
    private CheckBox GeneroAventura;

    @FXML
    private CheckBox GeneroCienciaFiccion;

    @FXML
    private CheckBox GeneroComedia;

    @FXML
    private CheckBox GeneroDrama;

    @FXML
    private CheckBox GeneroMisterio;

    @FXML
    private CheckBox GeneroMusical;

    @FXML
    private CheckBox GeneroSuspenso;

    @FXML
    private CheckBox GeneroTerror;
    
	@FXML
	private ImageView imgLogo;

    @FXML
    void initialize() {
    	
    	// Establece la imagen del logo
    			Image imagenLogo = new Image(getClass().getResourceAsStream(Constants.URL_LOGO_NORMAL));
    			imgLogo.setImage(imagenLogo);

    	
      

    }
}