package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import it.unisa.diem.is.gruppo25.App;

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
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    } 
    
    @FXML
    private void gestioneLibriAct(ActionEvent event) {
        openScene("/GestioneLibri.fxml", "Gestione Libri");
    }

    @FXML
    private void gestioneStudentiAct(ActionEvent event) {
        openScene("/GestioneStudenti.fxml", "Gestione Studenti");

    }

    @FXML
    private void gestionePrestitiAct(ActionEvent event) {
        openScene("/GestionePrestiti.fxml", "Gestione Prestiti");
    }
    
    private void openScene(String resourcePath, String title) {
        try {
            Stage stage = (Stage) gestioneLibriBtn.getScene().getWindow(); 
            
            URL fxml = getClass().getResource(resourcePath);
            if (fxml == null) {
                 fxml = getClass().getResource("/it/unisa/diem/is/gruppo25" + resourcePath);
                 if (fxml == null) {
                     throw new IOException("FXML non trovato: " + resourcePath);
                 }
            }

            Parent root = FXMLLoader.load(fxml);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di caricamento");
            alert.setHeaderText("Impossibile aprire la schermata");
            alert.setContentText("Verifica che il file FXML sia presente nel percorso corretto. Errore: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
