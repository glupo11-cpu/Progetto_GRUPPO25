package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.unisa.diem.is.gruppo25.App;
import it.unisa.diem.is.gruppo25.BibliotecaService;
import it.unisa.diem.is.gruppo25.Prestito;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * 
 */
public class GestionePrestitiController implements Initializable {

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
        service = App.getBibliotecaService();
        prestitiList = FXCollections.observableArrayList();

        matricolaClm.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudente().getMatricola()));
        libroClm.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLibro().getCodiceIsbn()));
        dataPrestitoClm.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDataInizio()));
        scadenzaClm.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDataPrevistaRestituzione()));
        
        dataPrestitoClm.setCellFactory(column -> createDateCell(DATE_FORMATTER));
        scadenzaClm.setCellFactory(column -> createDateCell(DATE_FORMATTER));

        tableView.setItems(prestitiList);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        inizializzaBinding();
        inizializzaRigaEvidenziata();
        inizializzaListenerSelezione();
        aggiornaTabella();
    }
    
        private TableCell<Prestito, LocalDate> createDateCell(DateTimeFormatter formatter) {
        return new TableCell<Prestito, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        };
    }

    private void inizializzaBinding() {
        registraBtn.disableProperty().bind(
                matricolaTxt.textProperty().isEmpty()
                        .or(isbnTxt.textProperty().isEmpty())
                        .or(scadenzaTxt.textProperty().isEmpty())
        );

        restituzioneBtn.disableProperty().bind(
                Bindings.isNull(tableView.getSelectionModel().selectedItemProperty())
        );
    }

    private void inizializzaRigaEvidenziata() {
        tableView.setRowFactory(tv -> new TableRow<Prestito>() {
            @Override
            protected void updateItem(Prestito p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null) {
                    setStyle("");
                } else {
                    if (p.isAttivo() && p.isInRitardo()) {
                        setStyle("-fx-background-color: #ffcccc;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    private void inizializzaListenerSelezione() {
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldPrestito, newPrestito) -> {
                    if (newPrestito != null) {
                        matricolaTxt.setText(newPrestito.getStudente().getMatricola());
                        isbnTxt.setText(newPrestito.getLibro().getCodiceIsbn());
                        scadenzaTxt.setText(newPrestito.getDataPrevistaRestituzione().format(DATE_FORMATTER));
                    } else {
                        matricolaTxt.clear();
                        isbnTxt.clear();
                        scadenzaTxt.clear();
                    }
                }
        );
    }

    private void aggiornaTabella() {
        prestitiList.setAll(service.elencoPrestitiAttiviOrdinatiPerDataPrevista());
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
        prestitiList.setAll(service.cercaPrestiti(testo, testo));
    }

    @FXML
    private void registraAct(ActionEvent event) {
            try {
            String matricola = matricolaTxt.getText();
            String isbn = isbnTxt.getText();
            String scadenzaStr = scadenzaTxt.getText();

            if (matricola.trim().isEmpty() || isbn.trim().isEmpty() || scadenzaStr.trim().isEmpty()) {
                mostraErrore("Compila tutti i campi.");
                return;
            }

            LocalDate dataScadenza;
            try {
                dataScadenza = LocalDate.parse(scadenzaStr.trim(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                mostraErrore("Data di scadenza non valida. Usa il formato GG-MM-AAAA.");
                return;
            }

            service.registraPrestito(matricola, isbn, dataScadenza);
            aggiornaTabella();

        } catch (NoSuchElementException e) {
            mostraErrore("Studente o libro non trovato: " + e.getMessage());
        } catch (IllegalStateException e) {
            mostraErrore("Impossibile registrare il prestito: " + e.getMessage());
        } catch (Exception e) {
            mostraErrore("Errore generico: " + e.getMessage());
        }
    }

    @FXML
    private void restituzioneAct(ActionEvent event) {
Prestito selezionato = tableView.getSelectionModel().getSelectedItem();
        if (selezionato == null) {
            return;
        }

        String matricola = selezionato.getStudente().getMatricola();
        String isbn = selezionato.getLibro().getCodiceIsbn();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma restituzione");
        alert.setHeaderText("Registrare la restituzione del libro?");

        alert.setContentText(
                "Matricola: " + matricola + "\n" +
                "Libro (ISBN): " + isbn
        );

        if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            LocalDate dataRestituzione = LocalDate.now();
            service.registraRestituzione(selezionato, dataRestituzione);
            aggiornaTabella();
        } catch (IllegalStateException e) {
            mostraErrore("Errore di stato: " + e.getMessage());
        } catch (Exception e) {
            mostraErrore("Errore generico: " + e.getMessage());
        }
    
    
}
}
