package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.ContenedorCartas;
import models.GameModel;
import models.game.Carta;

import static controllers.LoginController.juego;

public class GameController {

    @FXML
    private ContenedorCartas contenedorJ = new ContenedorCartas();
    @FXML
    private ContenedorCartas cartasC = new ContenedorCartas();
    @FXML
    private static Carta carta;
    @FXML
    private static Button boton;

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

        contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));

        GameModel.jugadaCPU(juego.getJugadores().getLast());
        System.out.println(juego.getMazoJuego().getTope());

        System.out.println(juego.getJugadores().getLast());
    }

    public static void onBtnCartaClick(Button b){
        String id = b.getId();
        carta = juego.getJugadores().getFirst().buscarCarta(id);
        boton = b;
    }

    public void onBtnJugarCartaClick(){
        if(carta.esJugable(juego)){
            juego.getJugadores().getFirst().jugar(juego, carta);
            contenedorJ.eliminarBoton(boton);
            System.out.println("eliminado");
            System.out.println(boton.getId());


        }

    }

}
