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
public class App extends Application {

    private static BibliotecaService bibliotecaService;

    static {
        bibliotecaService = new BibliotecaService();
        bibliotecaService.caricaDati();
    }

    public static BibliotecaService getBibliotecaService() { return bibliotecaService; }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SchermataIniziale.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Schermata iniziale");
        stage.show();
        
        stage.setOnCloseRequest(event -> bibliotecaService.salvaDati());
    }
    public static void main(String[] args) { launch(); }
}
