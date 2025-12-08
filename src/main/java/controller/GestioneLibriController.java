package controller; // Package corretto

import it.unisa.diem.is.gruppo25.App;
import it.unisa.diem.is.gruppo25.BibliotecaService;
import it.unisa.diem.is.gruppo25.Libro;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class GestioneLibriController implements Initializable {

    @FXML 
    private Button homeBtn;
    @FXML 
    private TextField cercaTxt;
    @FXML 
    private Button cercaBtn;
    @FXML 
    private TableView<Libro> tableView; 
    @FXML 
    private TableColumn<Libro, String> titoloClm; 
    @FXML 
    private TableColumn<Libro, String> autoreClm; 
    @FXML 
    private TableColumn<Libro, Integer> annoClm; 
    @FXML 
    private TableColumn<Libro, String> isbnClm; 
    @FXML 
    private TableColumn<Libro, Integer> copieClm; 
    @FXML 
    private TextField titoloTxt; 
    @FXML 
    private TextField autoreTxt; 
    @FXML 
    private TextField annoTxt; 
    @FXML 
    private TextField isbnTxt; 
    @FXML 
    private TextField copieTxt; 
    @FXML 
    private Button aggiungiBtn;
    @FXML 
    private Button modficaBtn; 
    @FXML 
    private Button eliminaBtn;
    
    
    private ObservableList<Libro> libroList;
    private BibliotecaService service;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
    private void modficaAct(ActionEvent event) { 
        
    }

    @FXML
    private void eliminaAct(ActionEvent event) {
        
    }
}
    