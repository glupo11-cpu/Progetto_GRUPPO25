package it.unisa.diem.is.gruppo25;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
/**
 * @brief Punto di ingresso dell'applicazione JavaFX.
 * La classe App inizializza il servizio e gestisce il ciclo di vita della UI (caricamento/salvataggio dati).
 */
public class App extends Application {

    private static BibliotecaService bibliotecaService;

    static {
        bibliotecaService = new BibliotecaService();
        bibliotecaService.caricaDati();
    }
/**
     * @brief Restituisce l'istanza del BibliotecaService.
     * @return l'istanza del servizio
     * @post il risultato è l'oggetto Singleton BibliotecaService
     */
    public static BibliotecaService getBibliotecaService() { return bibliotecaService; }
/**
     * @brief Metodo di avvio dell'applicazione JavaFX.
     * @param [in] stage lo stage principale per l'applicazione
     * @throws IOException se non è possibile caricare il file FXML
     * @pre stage != null
     * @post viene visualizzata la schermata iniziale
     * @post all'evento di chiusura viene invocato bibliotecaService.salvaDati()
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchermataIniziale.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Schermata iniziale");
        stage.show();
        
        stage.setOnCloseRequest(event -> bibliotecaService.salvaDati());
    }
    /**
     * @brief Metodo main per avviare l'applicazione.
     * @param [in] args argomenti della linea di comando
     * @post avvia l'applicazione JavaFX
     */
    public static void main(String[] args) { launch(); }
}
