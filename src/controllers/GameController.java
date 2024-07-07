package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.ContenedorCartasC;
import models.ContenedorCartasJ;
import models.GameModel;
import models.game.Carta;

import static controllers.LoginController.juego;

public class GameController {

    @FXML
    private ContenedorCartasJ contenedorJ = new ContenedorCartasJ();
    @FXML
    public static ContenedorCartasC contenedorC = new ContenedorCartasC();
    @FXML
    public static Carta cartaActual;
    @FXML
    private static Button boton;
    @FXML
    protected static Label usuario;
    @FXML
    protected Button botonCartaActual;

    public void onBtnAgarrarCartaClick(){

        juego.getJugadores().getFirst().agarrarCarta(juego);
        System.out.println(juego.getJugadores().getFirst() + "ha agarrado cartaActual");

        contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));

        GameModel.jugadaCPU(juego.getJugadores().getLast());
    }

    public static void onBtnCartaClick(Button b){
        String id = b.getId();
        cartaActual = juego.getJugadores().getFirst().buscarCarta(id);
        boton = b;
    }

    public void onBtnJugarCartaClick(){
        if(cartaActual.esJugable(juego)){
            juego.getJugadores().getFirst().jugar(juego, cartaActual);
            System.out.println(cartaActual);

            contenedorJ.eliminarBoton(boton.getId());

            botonCartaActual.setGraphic(boton.getGraphic());

            GameModel.jugadaCPU(juego.getJugadores().getLast());
        }

    }

}
