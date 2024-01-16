package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class SeriesController {
	
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
    void initialize() {
        assert GeneroAccion != null : "fx:id=\"GeneroAccion\" was not injected: check your FXML file 'Series.fxml'.";
        assert GeneroAnimacion != null : "fx:id=\"GeneroAnimacion\" was not injected: check your FXML file 'Series.fxml'.";
        assert GeneroAventura != null : "fx:id=\"GeneroAventura\" was not injected: check your FXML file 'Series.fxml'.";
        assert GeneroCienciaFiccion != null : "fx:id=\"GeneroCienciaFiccion\" was not injected: check your FXML file 'Series.fxml'.";
        assert GeneroComedia != null : "fx:id=\"GeneroComedia\" was not injected: check your FXML file 'Series.fxml'.";
        assert GeneroDrama != null : "fx:id=\"GeneroDrama\" was not injected: check your FXML file 'Series.fxml'.";
        assert GeneroMisterio != null : "fx:id=\"GeneroMisterio\" was not injected: check your FXML file 'Series.fxml'.";
        assert GeneroMusical != null : "fx:id=\"GeneroMusical\" was not injected: check your FXML file 'Series.fxml'.";
        assert GeneroSuspenso != null : "fx:id=\"GeneroSuspenso\" was not injected: check your FXML file 'Series.fxml'.";
        assert GeneroTerror != null : "fx:id=\"GeneroTerror\" was not injected: check your FXML file 'Series.fxml'.";

    }

}
