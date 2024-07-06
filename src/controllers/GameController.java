package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.game.Juego;

import java.io.IOException;

public class GameController {
    private Juego juego;

    public GameController(Juego juego) {
        this.juego = juego;
    }

    public void onSalirClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VentanaModal.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Ventana modal");
            stage.initModality(Modality.APPLICATION_MODAL); // Set the modality
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Show the modal window and wait for it to be closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
