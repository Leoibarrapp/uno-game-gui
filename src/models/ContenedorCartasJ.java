package models;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.game.Carta;
import models.game.Jugador;

import java.util.Objects;

import static controllers.GameController.onBtnCartaClick;
import static controllers.LoginController.juego;

public class ContenedorCartasJ extends HBox {

    public ContenedorCartasJ(){
        for(Carta c : juego.getJugadores().getFirst().getCartas().getMazo()){
            agregarBoton(crearBoton(c));
        }
    }

    public Button crearBoton(Carta carta) {
        Button boton = new Button();

        Image image = new Image(Objects.requireNonNull(ContenedorCartasJ.class.getResourceAsStream(carta.getUrlImagen())));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(135);
        imageView.setFitWidth(87);

        boton.setGraphic(imageView);
        boton.setId(carta.getColor() + "-" + carta.getTipo());
        boton.setOnAction(event -> onBtnCartaClick(boton));
        //boton.setCursor(Cursor.HAND);

        return boton;
    }

    public void agregarBoton(Button boton){
        getChildren().add(boton);
    }

    public void eliminarBoton(String id){
        Button button = (Button) this.lookup("#"+id);
        if (button != null) {
            this.getChildren().remove(button);
        }
    }
}
