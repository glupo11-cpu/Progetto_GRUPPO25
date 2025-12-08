package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * 
 */
public class SchermataInizialeController implements Initializable {

    @FXML
    private Button gestioneLibriBtn;
    @FXML
    private Button gestioneStudentiBtn;
    @FXML
    private Button gestionePrestitiBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void gestioneLibriAct(ActionEvent event) {
    }

    @FXML
    private void gestioneStudentiAct(ActionEvent event) {
    }

    @FXML
    private void gestionePrestitiAct(ActionEvent event) {
    }
    
    private void openScene(String resourcePath, String title) {
        
    }
}
