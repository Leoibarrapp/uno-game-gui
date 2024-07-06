package models;

import controllers.GameController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.game.Carta;

import java.util.Objects;

import static controllers.GameController.onBtnCartaClick;
import static controllers.LoginController.juego;

public class ContenedorCartas extends HBox {

    public ContenedorCartas(){
//        for(Jugador j : juego.getJugadores()){
//            for(Carta c : j.getCartas().getMazo()){
//                agregar(c);
//            }
//        }

        for(Carta c : juego.getJugadores().getFirst().getCartas().getMazo()){
            agregarBoton(crearBoton(c));
        }
    }

    public Button crearBoton(Carta carta) {
        Button boton = new Button();

        Image image = new Image(Objects.requireNonNull(ContenedorCartas.class.getResourceAsStream("/views/cartas/carta_volteada.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(132);
        imageView.setFitWidth(85);

        boton.setGraphic(imageView);
        boton.setId(carta.getColor() + "-" + carta.getTipo());
        boton.setOnAction(event -> onBtnCartaClick(boton));

        return boton;
    }

    public void agregarBoton(Button boton){
        getChildren().add(boton);
    }

    public void eliminarBoton(Button boton){
        getChildren().remove(boton);
    }
}
