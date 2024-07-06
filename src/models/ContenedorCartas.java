package models;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.game.Carta;
import models.game.Jugador;

import java.util.ArrayList;
import java.util.Objects;

import static controllers.LoginController.juego;

public class ContenedorCartas extends HBox {

    public ContenedorCartas(){
//        for(Jugador j : juego.getJugadores()){
//            for(Carta c : j.getCartas().getMazo()){
//                agregar(c);
//            }
//        }

        for(Carta c : juego.getJugadores().getFirst().getCartas().getMazo()){
            agregar(c);
        }
    }

    public void agregar(Carta carta){
        Button button = new Button();

        Image image = new Image(Objects.requireNonNull(ContenedorCartas.class.getResourceAsStream("/views/cartas/carta_volteada.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        button.setGraphic(imageView);

        getChildren().add(button);
    }
}
