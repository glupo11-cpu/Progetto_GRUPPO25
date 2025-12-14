package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.NoSuchElementException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
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
import it.unisa.diem.is.gruppo25.App;
import it.unisa.diem.is.gruppo25.BibliotecaService;
import it.unisa.diem.is.gruppo25.Studente;
import it.unisa.diem.is.gruppo25.Prestito;
import java.util.stream.Collectors;

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
        service = App.getBibliotecaService();
        studentiList = FXCollections.observableArrayList();

        nomeClm.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeClm.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        matricolaClm.setCellValueFactory(new PropertyValueFactory<>("matricola"));
        emailClm.setCellValueFactory(new PropertyValueFactory<>("emailIstituzionale"));

        libriClm.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPrestitiAttivi().size()))
        );

        scadenzaClm.setCellValueFactory(cellData -> {
            String scadenze = cellData.getValue().getPrestitiAttivi().stream()
                    .map(Prestito::getDataPrevistaRestituzione)
                    .sorted()
                    .map(d -> d.toString()) 
                    .collect(Collectors.joining(", "));
            
            return new SimpleStringProperty(scadenze.isEmpty() ? "-" : scadenze);
        });


        tableView.setItems(studentiList);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        inizializzaBinding();
        inizializzaListenerSelezione();
        aggiornaTabella();
    }    
    
    private void inizializzaBinding() {
        aggiungiBtn.disableProperty().bind(
                nomeTxt.textProperty().isEmpty()
                        .or(cognomeTxt.textProperty().isEmpty())
                        .or(matricolaTxt.textProperty().isEmpty())
                        .or(emailTxt.textProperty().isEmpty())
        );

        modificaBtn.disableProperty().bind(
                tableView.getSelectionModel().selectedItemProperty().isNull()
                        .or(nomeTxt.textProperty().isEmpty())
                        .or(cognomeTxt.textProperty().isEmpty())
                        .or(matricolaTxt.textProperty().isEmpty())
                        .or(emailTxt.textProperty().isEmpty())
        );

        eliminaBtn.disableProperty().bind(
                Bindings.isNull(tableView.getSelectionModel().selectedItemProperty())
        );
    }

    private void inizializzaListenerSelezione() {
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldStud, newStud) -> {
                    if (newStud != null) {
                        nomeTxt.setText(newStud.getNome());
                        cognomeTxt.setText(newStud.getCognome());
                        matricolaTxt.setText(newStud.getMatricola());
                        emailTxt.setText(newStud.getEmailIstituzionale());
                    } else {
                         pulisciCampi(); 
                    }
                }
        );
    }

    private void aggiornaTabella() {
        studentiList.setAll(service.elencoStudentiOrdinati());
    }

    private void pulisciCampi() {
        nomeTxt.clear();
        cognomeTxt.clear();
        matricolaTxt.clear();
        emailTxt.clear();
        tableView.getSelectionModel().clearSelection();
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    @FXML
    private void homeAct(ActionEvent event) {
        try {
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/SchermataIniziale.fxml")); 
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Schermata iniziale");
            stage.show();
        } catch (IOException e) {
            mostraErrore("Impossibile tornare alla home. Errore I/O.");
        }
    }

    @FXML
    private void cercaAct(ActionEvent event) {
        String input = cercaTxt.getText();
        if (input == null || input.trim().isEmpty()) {
            aggiornaTabella();
            return;
        }
        String testo = input.trim();

        studentiList.setAll(service.cercaStudente(testo, testo)); 
    }

    @FXML
    private void aggiungiAct(ActionEvent event) {
        try {
            String nome = nomeTxt.getText();
            String cognome = cognomeTxt.getText();
            String matricola = matricolaTxt.getText();
            String email = emailTxt.getText();

            Studente s = new Studente(nome, cognome, matricola, email); 
            service.inserisciStudente(s);
            aggiornaTabella();
            pulisciCampi();
        } catch (IllegalArgumentException e) {
            mostraErrore("Dati non validi: " + e.getMessage());
        } catch (Exception e) {
            mostraErrore("Errore generico durante l'aggiunta: " + e.getMessage());
        }
    }

    @FXML
    private void modificaAct(ActionEvent event) {
        Studente selezionato = tableView.getSelectionModel().getSelectedItem();
        if (selezionato == null) {
            return;
        }
        try {
            String nuovoNome = nomeTxt.getText();
            String nuovoCognome = cognomeTxt.getText();
            String nuovaEmail = emailTxt.getText();
            String matricolaOriginale = selezionato.getMatricola();

            service.modificaStudente(
                    matricolaOriginale,
                    nuovoNome,
                    nuovoCognome,
                    nuovaEmail
            ); 
            aggiornaTabella();

        } catch (NoSuchElementException e) {
            mostraErrore("Studente non trovato o selezionato. Riprova.");
        } catch (Exception e) {
            mostraErrore("Errore generico durante la modifica: " + e.getMessage());
        }
    }

    @FXML
    private void eliminaAct(ActionEvent event) {
Studente selezionato = tableView.getSelectionModel().getSelectedItem();
        if (selezionato == null) {
            return;
        }

        String nome = selezionato.getNome();
        String cognome = selezionato.getCognome();
        String matricola = selezionato.getMatricola();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma eliminazione");
        alert.setHeaderText("Eliminare lo studente selezionato?");
        alert.setContentText(
                "Nome: " + nome + " " + cognome + "\n" +
                "Matricola: " + matricola
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                service.eliminaStudente(selezionato.getMatricola());
                aggiornaTabella();
                pulisciCampi();
            } catch (IllegalStateException e) {
                mostraErrore("Impossibile eliminare: " + e.getMessage());
            } catch (Exception e) {
                mostraErrore("Errore generico: " + e.getMessage());
            }
        }
    
}
}
