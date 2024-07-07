package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.ContenedorCartasC;
import models.ContenedorCartasJ;
import models.game.*;

import java.util.Objects;

import static controllers.LoginController.juego;
import static models.UnoGame.*;

public class GameController {

    @FXML
    public Label textoEnJuegoAbajo;
    @FXML
    public Label textoEnJuegoArriba;

    public Label textoUsuario;
    public Label textoAgarrarCarta;
    public Label textoCPU;
    public Button btnSalir;

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
        System.out.println(juego.getJugadores().getFirst() + "ha agarrado cartaActual");

        contenedorJ.agregarBoton(contenedorJ.crearBoton(juego.getJugadores().getFirst().getCartas().getTope()));

        jugadaCPU(juego.getJugadores().getLast());
        setImage(botonCartaActual);
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
            System.out.println(cartaActual);

            contenedorJ.eliminarBoton(boton.getId());

            botonCartaActual.setGraphic(boton.getGraphic());

            jugadaCPU(juego.getJugadores().getLast());
            setImage(botonCartaActual);
        }
        else{
            cartaActual = null;
            textoEnJuegoAbajo.setText("No puedes jugar esta carta");
        }

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
        }
        else {
            cpu.agarrarCarta(juego);
            contenedorC.agregarBoton(contenedorC.crearBoton());

        }
    }

    private void setImage(Button btn){
        Image image = new Image(Objects.requireNonNull(ContenedorCartasJ.class.getResourceAsStream(cartaActual.getUrlImagen())));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(132);
        imageView.setFitWidth(85);

        btn.setGraphic(imageView);
    }

    public void onBtnSalirClick() {
        notifyAll();
    }
}
