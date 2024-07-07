package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.ContenedorCartasC;
import models.ContenedorCartasJ;
import models.game.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

import static controllers.LoginController.juego;
import static models.UnoGame.*;

public class GameController {

    @FXML
    public Label textoEnJuegoAbajo;
    @FXML
    public Label textoEnJuegoArriba;
    @FXML
    public Label textoUsuario;
    @FXML
    public Label textoAgarrarCarta;
    @FXML
    public Label textoCPU;
    @FXML
    public Button btnSalir;
    @FXML
    public ChoiceBox<String> boxEscogerColor;
    public Button btnColorEscogido;
    @FXML
    public Button botonAgarrarCarta;
    @FXML
    private ContenedorCartasJ contenedorJ = new ContenedorCartasJ();
    @FXML
    public ContenedorCartasC contenedorC = new ContenedorCartasC();
    @FXML
    public static Carta cartaActual;
    @FXML
    private static Button boton;
    @FXML
    private Label usuario;
    @FXML
    protected Button botonCartaActual;

    @FXML
    public void initialize(){
        cartaActual = juego.getMazoJuego().getTope();
        this.setImage(botonCartaActual);

        boxEscogerColor.setItems(FXCollections.observableArrayList("R", "B", "G", "Y"));
        boxEscogerColor.setStyle("-fx-font: 20px \""+fontURL+"\";");
        //boxEscogerColor.setOnAction(e -> escogerColor());

        btnColorEscogido.setFont(customFont20);

        textoEnJuegoAbajo.setFont(customFont20);
        textoEnJuegoArriba.setFont(customFont20);

        textoUsuario.setFont(customFont40);
        textoCPU.setFont(customFont40);

        textoAgarrarCarta.setFont(customFont30);

        btnSalir.setFont(customFont20);



    }

    public void onBtnAgarrarCartaClick(){

        textoEnJuegoAbajo.setText("");
        juego.getJugadores().getFirst().agarrarCarta(juego);

        contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));

        Platform.runLater(() -> jugadaCPU(juego.getJugadores().getLast()));
    }

    public static void onBtnCartaClick(Button b){
        String id = b.getId();
        cartaActual = juego.getJugadores().getFirst().buscarCarta(id);
        boton = b;
    }

    public void onBtnJugarCartaClick() throws InterruptedException {

        if(cartaActual.esJugable(juego)){
            textoEnJuegoArriba.setText("");
            textoEnJuegoAbajo.setText("");
            juego.getJugadores().getFirst().jugar(juego, cartaActual);

            contenedorJ.eliminarBoton(boton.getId());

            botonCartaActual.setGraphic(boton.getGraphic());

            if(cartaActual instanceof CartaComodin){
                boxEscogerColor.setDisable(false);
                btnColorEscogido.setDisable(false);
                return;
            }
            if(cartaActual.getTipo().equals("T4")){
                juego.getJugadores().getLast().agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                juego.getJugadores().getLast().agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                juego.getJugadores().getLast().agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                juego.getJugadores().getLast().agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());
            }
            else{
                if(cartaActual.getTipo().equals("T2")) {
                    juego.getJugadores().getLast().agarrarCarta(juego);
                    contenedorC.agregarBoton(contenedorC.crearBoton());

                    juego.getJugadores().getLast().agarrarCarta(juego);
                    contenedorC.agregarBoton(contenedorC.crearBoton());
                }
                else{
                    if(cartaActual.getTipo().equals("S") || cartaActual.getTipo().equals("R")){
                        textoEnJuegoAbajo.setText("¡Repites el turno!");
                    }
                    else{

                        if(juego.getJugadores().getFirst().getCartas().getMazo().size() == 1){
                            textoEnJuegoAbajo.setText("¡USUARIO ha cantado UNO!");
                        }
                        else{
                            if(juego.getJugadores().getFirst().getCartas().getMazo().isEmpty()){
                                contenedorJ.setDisable(true);
                                contenedorC.setDisable(true);
                                botonCartaActual.setDisable(true);
                                botonAgarrarCarta.setDisable(true);
                                textoAgarrarCarta.setDisable(true);
                                textoEnJuegoArriba.setText("¡USUARIO ha ganado!");
                                textoEnJuegoArriba.setFont(customFont80);
                            }
                            else{
                                Platform.runLater(() -> jugadaCPU(juego.getJugadores().getLast()));
                            }
                        }


                    }
                }
            }

        }
        else{
            cartaActual = null;
            textoEnJuegoAbajo.setText("No puedes jugar esta carta");
        }

        cartaActual = null;

    }

    public void jugadaCPU(Jugador cpu){
        Carta carta = null;

        if(cpu.puedeJugar(juego)){
            carta = ((CPU) cpu).escogerCarta(juego);

            cpu.jugar(juego, carta);
            contenedorC.eliminarBoton();
            cartaActual = carta;

            botonCartaActual.setId(cartaActual.getColor() + "-" + cartaActual.getTipo());

            if(carta instanceof CartaComodin){
                char color = ((CPU) cpu).escogerColor();
                juego.setColorActual(color);
                textoEnJuegoArriba.setText("Color escogido: " + color);
            }
            if(carta.getTipo().equals("T4")){
                juego.getJugadores().getFirst().agarrarCarta(juego);
                contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));

                juego.getJugadores().getFirst().agarrarCarta(juego);
                contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));

                juego.getJugadores().getFirst().agarrarCarta(juego);
                contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));

                juego.getJugadores().getFirst().agarrarCarta(juego);
                contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));
            }
            else{
                if(carta.getTipo().equals("T2")){
                    juego.getJugadores().getFirst().agarrarCarta(juego);
                    contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));

                    juego.getJugadores().getFirst().agarrarCarta(juego);
                    contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));
                }
                else{
                    if(carta.getTipo().equals("R") || carta.getTipo().equals("S")){
                        textoEnJuegoArriba.setText("CPU repitió el turno");
                        jugadaCPU(juego.getJugadores().getLast());
                    }
                }
            }




            if(juego.getJugadores().getLast().getCartas().getMazo().size() == 1){
                textoEnJuegoAbajo.setText("¡CPU ha cantado UNO!");
            }
            else{
                if(juego.getJugadores().getLast().getCartas().getMazo().isEmpty()){
                    contenedorJ.setDisable(true);
                    contenedorC.setDisable(true);
                    botonCartaActual.setDisable(true);
                    botonAgarrarCarta.setDisable(true);
                    textoAgarrarCarta.setDisable(true);
                    textoEnJuegoArriba.setText("¡CPU ha ganado!");
                    textoEnJuegoArriba.setFont(customFont80);
                }
            }

            setImage(botonCartaActual);
        }
        else {

            cpu.agarrarCarta(juego);
            contenedorC.agregarBoton(contenedorC.crearBoton());

        }
    }

    private void setImage(Button btn){
        String url = cartaActual.getUrlImagen();
        Image image = new Image(Objects.requireNonNull(ContenedorCartasJ.class.getResourceAsStream(url)));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(132);
        imageView.setFitWidth(85);

        btn.setGraphic(imageView);
    }

    public void onBtnSalirClick() {
        notifyAll();
    }

    public void onBtnColorEscogidoClick() {
        if(boxEscogerColor.getValue() != null){
            boxEscogerColor.setDisable(true);
            btnColorEscogido.setDisable(true);
            juego.setColorActual(boxEscogerColor.getValue().charAt(0));

            if(cartaActual.getTipo().equals("CC")){
                jugadaCPU(juego.getJugadores().getLast());
                setImage(botonCartaActual);
            }

        }

    }
}