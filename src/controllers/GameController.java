package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import models.ContenedorCartas;
import models.game.Carta;

import java.util.ArrayList;

import static controllers.LoginController.juego;

public class GameController {

    @FXML
    private ContenedorCartas cartasJ = new ContenedorCartas();
    @FXML
    private ContenedorCartas cartasC = new ContenedorCartas();

    public void onSalirClick(){
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VentanaModal.fxml"));
//            Parent root = fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setTitle("Ventana modal");
//            stage.initModality(Modality.APPLICATION_MODAL); // Set the modality
//            stage.setScene(new Scene(root));
//            stage.showAndWait(); // Show the modal window and wait for it to be closed
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void onBtnAgarrarCartaClick(){
        juego.getJugadores().getFirst().agarrarCarta(juego);
        System.out.println(juego.getJugadores().getFirst() + "ha agarrado carta");

        cartasJ.agregar(juego.getJugadores().getFirst().getCartas().getTope());
    }

    public void onBtnJugarCartaClick(){
        if(juego.getJugadores().getFirst().getCartas().getTope().esJugable(juego)){
            juego.getJugadores().getFirst().jugar(juego, juego.getJugadores().getFirst().getCartas().getTope());
        }
        System.out.println(juego.getJugadores().getFirst());

        System.out.println(juego.getMazoJuego().getTope());
    }

    private void eliminarBotonCarta(){
    }

}
