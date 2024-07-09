package models;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.game.Carta;

import java.util.Objects;

import static controllers.LoginController.juego;

public class ContenedorCartasC extends HBox {

    public ContenedorCartasC(){
        for(Carta c : juego.getJugadores().getLast().getCartas().getMazo()){
            agregarBoton(crearBoton());
        }
    }

    public Button crearBoton() {
        Button boton = new Button();

        Image image = new Image(Objects.requireNonNull(ContenedorCartasC.class.getResourceAsStream("/views/recursos/cartaUno.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(135);
        imageView.setFitWidth(87);

        boton.setGraphic(imageView);

        return boton;
    }

    public void agregarBoton(Button boton){
        getChildren().add(boton);
    }

    public void eliminarBoton(){
        Button button = (Button) this.getChildren().getFirst();
            this.getChildren().remove(button);
    }
}
