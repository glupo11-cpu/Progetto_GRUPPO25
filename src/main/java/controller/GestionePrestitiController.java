package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.unisa.diem.is.gruppo25.BibliotecaService;
import it.unisa.diem.is.gruppo25.Prestito;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * 
 */
public class GestionePrestitiController implements Initializable {


    @FXML 
    private Button homeBtn;
    @FXML 
    private TextField cercaTxt;
    @FXML 
    private Button cercaBtn;
    @FXML 
    private TableView<Prestito> tableView; 
    @FXML 
    private TableColumn<Prestito, String> matricolaClm;
    @FXML 
    private TableColumn<Prestito, String> libroClm; 
    @FXML 
    private TableColumn<Prestito, LocalDate> dataPrestitoClm; 
    @FXML 
    private TableColumn<Prestito, LocalDate> scadenzaClm; 
    @FXML 
    private TextField matricolaTxt;
    @FXML 
    private TextField isbnTxt;
    @FXML 
    private TextField scadenzaTxt;
    @FXML 
    private Button registraBtn;
    @FXML 
    private Button restituzioneBtn;

    private ObservableList<Prestito> prestitiList;
    private BibliotecaService service;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void homeAct(ActionEvent event) {
    }

    @FXML
    private void cercaAct(ActionEvent event) {
    }

    @FXML
    private void registraAct(ActionEvent event) {
    }

    @FXML
    private void restituzioneAct(ActionEvent event) {
    }
    
}
