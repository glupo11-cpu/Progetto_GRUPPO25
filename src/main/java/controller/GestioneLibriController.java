package controller; 

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
    private Button modificaBtn; 
    @FXML 
    private Button eliminaBtn;
    
    
    private ObservableList<Libro> libroList;
    private BibliotecaService service;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = App.getBibliotecaService();
        libroList = FXCollections.observableArrayList();

        titoloClm.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        autoreClm.setCellValueFactory(new PropertyValueFactory<>("autore"));
        annoClm.setCellValueFactory(new PropertyValueFactory<>("annoPubblicazione"));
        isbnClm.setCellValueFactory(new PropertyValueFactory<>("codiceIsbn"));
        copieClm.setCellValueFactory(new PropertyValueFactory<>("copieDisponibili"));

        tableView.setItems(libroList);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        inizializzaBinding();
        inizializzaListenerSelezione();
        aggiornaTabella();
    }

    private void inizializzaBinding() {
        aggiungiBtn.disableProperty().bind(
                titoloTxt.textProperty().isEmpty()
                        .or(autoreTxt.textProperty().isEmpty())
                        .or(annoTxt.textProperty().isEmpty())
                        .or(isbnTxt.textProperty().isEmpty())
                        .or(copieTxt.textProperty().isEmpty())
        );

        modificaBtn.disableProperty().bind(
                tableView.getSelectionModel().selectedItemProperty().isNull()
                        .or(titoloTxt.textProperty().isEmpty())
                        .or(autoreTxt.textProperty().isEmpty())
                        .or(annoTxt.textProperty().isEmpty())
                        .or(isbnTxt.textProperty().isEmpty())
                        .or(copieTxt.textProperty().isEmpty())
        );

        eliminaBtn.disableProperty().bind(
                Bindings.isNull(tableView.getSelectionModel().selectedItemProperty())
        );
    }

    private void inizializzaListenerSelezione() {
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldLibro, newLibro) -> {
                    if (newLibro != null) {
                        titoloTxt.setText(newLibro.getTitolo());
                        autoreTxt.setText(newLibro.getAutore());
                        annoTxt.setText(String.valueOf(newLibro.getAnnoPubblicazione()));
                        isbnTxt.setText(newLibro.getCodiceIsbn());
                        copieTxt.setText(String.valueOf(newLibro.getCopieDisponibili()));
                    } else {
                        pulisciCampi();
                    }
                }
        );
    }

    private void aggiornaTabella() {
        libroList.setAll(service.elencoLibriOrdinatiPerTitolo());
    }

    private void pulisciCampi() {
        titoloTxt.clear();
        autoreTxt.clear();
        annoTxt.clear();
        isbnTxt.clear();
        copieTxt.clear();
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

        final String testo = input.trim().toLowerCase();

        libroList.setAll(service.cercaLibri(testo, testo, testo));
    }

    @FXML
    private void aggiungiAct(ActionEvent event) {
        try {
            String titolo = titoloTxt.getText();
            String autore = autoreTxt.getText();
            int anno = Integer.parseInt(annoTxt.getText());
            String isbn = isbnTxt.getText();
            int copie = Integer.parseInt(copieTxt.getText());

            Libro l = new Libro(titolo, autore, anno, isbn, copie);
            service.inserisciLibro(l);
            aggiornaTabella();
            pulisciCampi();
        } catch (NumberFormatException e) {
            mostraErrore("Anno e copie devono essere numeri interi.");
        } catch (IllegalArgumentException e) {
            mostraErrore("Dati non validi: " + e.getMessage());
        } catch (Exception e) {
            mostraErrore("Errore durante l'aggiunta: " + e.getMessage());
        }
    }

    @FXML
    private void modificaAct(ActionEvent event) { 
        Libro selezionato = tableView.getSelectionModel().getSelectedItem();
        if (selezionato == null) {
            return;
        }
        try {
            String nuovoTitolo = titoloTxt.getText();
            String nuovoAutore = autoreTxt.getText();
            int nuovoAnno = Integer.parseInt(annoTxt.getText());
            int nuoveCopie = Integer.parseInt(copieTxt.getText());
            String isbnOriginale = selezionato.getCodiceIsbn();

            service.modificaLibro(
                    isbnOriginale,
                    nuovoTitolo,
                    nuovoAutore,
                    nuovoAnno,
                    nuoveCopie
            );
            aggiornaTabella();
        } catch (NumberFormatException e) {
            mostraErrore("Anno e copie devono essere numeri interi.");
        } catch (Exception e) {
            mostraErrore("Errore durante la modifica: " + e.getMessage());
        }
    }

    @FXML
    private void eliminaAct(ActionEvent event) {
        Libro selezionato = tableView.getSelectionModel().getSelectedItem();
        if (selezionato == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma eliminazione");
        alert.setHeaderText("Eliminare il libro selezionato?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                service.eliminaLibro(selezionato.getCodiceIsbn());
                aggiornaTabella();
                pulisciCampi();
            } catch (NoSuchElementException | IllegalStateException e) {
                mostraErrore("Impossibile eliminare: " + e.getMessage());
            } catch (Exception e) {
                mostraErrore("Errore generico durante l'eliminazione: " + e.getMessage());
            }
        }
    
    }
}
    