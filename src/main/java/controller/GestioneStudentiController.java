package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.unisa.diem.is.gruppo25.BibliotecaService;
import it.unisa.diem.is.gruppo25.Studente;
import java.net.URL;
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
public class GestioneStudentiController implements Initializable {

    @FXML 
    private Button homeBtn;
    @FXML 
    private TextField cercaTxt;
    @FXML 
    private Button cercaBtn;
    @FXML 
    private TableView<Studente> tableView; 
    @FXML 
    private TableColumn<Studente, String> nomeClm; 
    @FXML 
    private TableColumn<Studente, String> cognomeClm; 
    @FXML 
    private TableColumn<Studente, String> matricolaClm; 
    @FXML 
    private TableColumn<Studente, String> emailClm; 
    @FXML 
    private TableColumn<Studente, String> libriClm; 
    @FXML 
    private TableColumn<Studente, String> scadenzaClm; 
    @FXML 
    private TextField nomeTxt;
    @FXML 
    private TextField cognomeTxt;
    @FXML 
    private TextField matricolaTxt;
    @FXML 
    private TextField emailTxt;
    @FXML 
    private Button aggiungiBtn;
    @FXML 
    private Button modificaBtn;
    @FXML 
    private Button eliminaBtn;

    private ObservableList<Studente> studentiList;
    private BibliotecaService service;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void inizializzaBinding() {

    }

    private void inizializzaListenerSelezione() {

    }

    private void aggiornaTabella() {

    }

    private void pulisciCampi() {

    }

    private void mostraErrore(String messaggio) {
    }

    @FXML
    private void homeAct(ActionEvent event) {
    }

    @FXML
    private void cercaAct(ActionEvent event) {
    }

    @FXML
    private void aggiungiAct(ActionEvent event) {
    }

    @FXML
    private void modificaAct(ActionEvent event) {
    }

    @FXML
    private void eliminaAct(ActionEvent event) {
    }
    
}
