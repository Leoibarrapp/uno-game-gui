package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import javafx.scene.paint.Color;
import models.ContenedorCartasC;
import models.ContenedorCartasJ;
import models.UnoGame;
import models.game.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static controllers.LoginController.juego;
import static models.UnoGame.*;

public class GameController {

    private String nombreUsuario;

    @FXML
    public Label textoEnJuegoAbajo;
    @FXML
    public Label textoEnJuegoArriba;
    @FXML
    public Label textoUsuario;
    @FXML
    public Label textoCPU;
    @FXML
    public Label textoAgarrarCarta;
    @FXML
    public Button btnSalir;
    @FXML
    public ChoiceBox<String> boxEscogerColor;
    public Button btnColorEscogido;
    @FXML
    public Button botonAgarrarCarta;
    @FXML
    public Label textoEscogerCarta;
    @FXML
    private ContenedorCartasJ contenedorJ = new ContenedorCartasJ();
    @FXML
    public ContenedorCartasC contenedorC = new ContenedorCartasC();
    @FXML
    public static Carta cartaActual;
    @FXML
    private static Button boton;
    @FXML
    protected Button botonCartaActual;

    private Jugador jugador = juego.getJugadores().getFirst();
    private Jugador cpu = juego.getJugadores().getLast();

    @FXML
    public void initialize(){
        nombreUsuario = jugador.getNombre();

        cartaActual = juego.getMazoJuego().getTope();
        this.setImage(botonCartaActual);

        boxEscogerColor.setItems(FXCollections.observableArrayList("R", "B", "G", "Y"));
        boxEscogerColor.setStyle("-fx-font: 20px \""+fontURL+"\";");

        btnColorEscogido.setFont(customFont20);

        textoEnJuegoAbajo.setFont(customFont30);
        textoEnJuegoArriba.setFont(customFont30);

        textoUsuario.setFont(customFont40);
        textoUsuario.setText(jugador.getNombre());

        textoCPU.setFont(customFont40);

        textoAgarrarCarta.setFont(customFont20);
        textoEscogerCarta.setFont(customFont20);

        btnSalir.setFont(customFont20);

    }

    public void onBtnAgarrarCartaClick() throws IOException {
        if(juego.getMazoPila().getMazo().isEmpty()){
            juego.reBarajear();
        }
        if(jugador.puedeJugar(juego) == false){
            textoEnJuegoAbajo.setText("");
            jugador.agarrarCarta(juego);

            contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));

            jugadaCPU();
        }
        else{
            textoEnJuegoAbajo.setText("¡Tienes cartas que puedes jugar!");
        }

        guardarPartida();

        //guardarUsuario(jugador);
    }

    public static void onBtnCartaClick(Button b){
        String id = b.getId();
        cartaActual = juego.getJugadores().getFirst().buscarCarta(id);
        boton = b;
    }

    public void onBtnJugarCartaClick() throws InterruptedException, IOException {

        if((cartaActual != null) && (cartaActual.esJugable(juego))){
            textoEnJuegoArriba.setText("");
            textoEnJuegoAbajo.setText("");
            jugador.jugar(juego, cartaActual);

            contenedorJ.eliminarBoton(boton.getId());

            botonCartaActual.setGraphic(boton.getGraphic());

            if(cartaActual.getTipo().equals("T4")){
                boxEscogerColor.setDisable(false);
                btnColorEscogido.setDisable(false);

                cpu.agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                cpu.agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                cpu.agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                cpu.agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());
            }
            else{
                if(cartaActual.getTipo().equals("T2")) {
                    cpu.agarrarCarta(juego);
                    contenedorC.agregarBoton(contenedorC.crearBoton());

                    cpu.agarrarCarta(juego);
                    contenedorC.agregarBoton(contenedorC.crearBoton());
                }
                else{
                    if(cartaActual.getTipo().equals("S") || cartaActual.getTipo().equals("R")){
                        textoEnJuegoAbajo.setText("¡Repites el turno!");
                    }
                    else{
                        if(cartaActual.getTipo().equals("CC")){
                            boxEscogerColor.setDisable(false);
                            btnColorEscogido.setDisable(false);
                            jugadaCPU();
                        }
                        else{
                            if(jugador.getCartas().getMazo().size() == 1){
                                textoEnJuegoAbajo.setText("¡" + nombreUsuario+" ha cantado UNO!");
                                jugadaCPU();
                            }
                            else {
                                if (jugador.getCartas().getMazo().isEmpty()) {
                                    contenedorJ.setDisable(true);
                                    contenedorC.setDisable(true);
                                    botonCartaActual.setDisable(true);
                                    botonAgarrarCarta.setDisable(true);
                                    textoAgarrarCarta.setDisable(true);
                                    textoEnJuegoArriba.setText("¡" + nombreUsuario + " ha ganado!");
                                    textoEnJuegoArriba.setFont(customFont80);

                                    int puntos = obtenerPuntos(cpu);

                                    textoEnJuegoAbajo.setText(puntos + " puntos");
                                    textoEnJuegoAbajo.setFont(customFont80);

                                    puntos += jugador.getPuntos();

                                    juego.setGanador(jugador);
                                    jugador.setPuntos(puntos);

                                    //guardarUsuario(jugador);
                                    return;
                                }
                                else {
                                    jugadaCPU();
                                }
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

        guardarPartida();
    }

    public void jugadaCPU() {
        Carta carta = null;

        if(cpu.puedeJugar(juego)){
            carta = cpu.escogerCarta(juego);

            cpu.jugar(juego, carta);
            contenedorC.eliminarBoton();
            cartaActual = carta;

            botonCartaActual.setId(cartaActual.getColor() + "-" + cartaActual.getTipo());

            if(carta.getColor() == 'W'){
                char color = cpu.escogerColor();
                juego.setColorActual(color);
                textoEnJuegoArriba.setText("Color escogido: " + color);
            }
            if(carta.getTipo().equals("T4")){

                for(int i = 0; i < 4; i++){
                    jugador.agarrarCarta(juego);
                    contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));
                }
                jugadaCPU();
            }
            else{
                if(carta.getTipo().equals("T2")){
                    jugador.agarrarCarta(juego);
                    contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));

                    jugador.agarrarCarta(juego);
                    contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));

                    jugadaCPU();
                }
                else{
                    if(carta.getTipo().equals("R") || carta.getTipo().equals("S")){
                        textoEnJuegoAbajo.setText("CPU repitió el turno");
                        jugadaCPU();
                    }
                    else{
                        if(cpu.getCartas().getMazo().size() == 1){
                            textoEnJuegoAbajo.setText("¡CPU ha cantado UNO!");
                        }
                        else{
                            if(cpu.getCartas().getMazo().isEmpty()){
                                contenedorJ.setDisable(true);
                                contenedorC.setDisable(true);
                                botonCartaActual.setDisable(true);
                                botonAgarrarCarta.setDisable(true);
                                textoAgarrarCarta.setDisable(true);

                                textoEnJuegoArriba.setText("¡CPU ha ganado!");
                                textoEnJuegoArriba.setFont(customFont80);

                                int puntos = obtenerPuntos(jugador);

                                textoEnJuegoAbajo.setText(puntos + " puntos");
                                textoEnJuegoAbajo.setFont(customFont80);

                                puntos += cpu.getPuntos();

                                juego.setGanador(cpu);
                                cpu.setPuntos(puntos);

                                //guardarUsuario(cpu);
                                return;
                            }
                        }
                    }
                }
            }
        }
        else {
            cpu.agarrarCarta(juego);
            contenedorC.agregarBoton(contenedorC.crearBoton());
        }


        setImage(botonCartaActual);
        cartaActual = null;

        guardarPartida();
    }

    private void setImage(Button btn){
        if(cartaActual != null){
            String url = cartaActual.getUrlImagen();
            Image image = new Image(Objects.requireNonNull(GameController.class.getResourceAsStream(url)));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(132);
            imageView.setFitWidth(85);

            btn.setGraphic(imageView);
        }

    }

    public void onBtnSalirClick() throws InterruptedException {
        guardarPartida();
        btnSalir.getScene().getWindow().hide();
    }

    public void onBtnColorEscogidoClick() throws IOException {
        if(boxEscogerColor.getValue() != null){
            boxEscogerColor.setDisable(true);
            btnColorEscogido.setDisable(true);
            juego.setColorActual(boxEscogerColor.getValue().charAt(0));
            textoEnJuegoArriba.setText("Color escogido: " + juego.getColorActual());

            if(cartaActual.getTipo().equals("CC")){
                setImage(botonCartaActual);

            }

        }

    }
    private int obtenerPuntos(Jugador j){
        Mazo cartas = j.getCartas();

        int puntos = 0;

        for(Carta c : cartas.getMazo()){
            switch (c.getTipo()){
                case "T2", "R", "S":
                    puntos += 20;
                    break;
                case "T4", "CC":
                    puntos += 50;
                    break;
                default:
                    puntos += Integer.parseInt(c.getTipo());
            }
        }

        return puntos;
    }

    private void guardarPartida(){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter writer = new FileWriter("partida.json")) {
            gson.toJson(juego, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void guardarUsuario(Jugador j) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ArrayList<Jugador> usuarios = null;

        try(FileReader reader = new FileReader("usuarios.json")){
            usuarios = gson.fromJson(reader, ArrayList.class);
        }
        catch (FileNotFoundException e){

        }

        if(usuarios != null){
            for(Jugador aux : usuarios){
                if(Objects.equals(j.getNombre(), aux.getNombre())){
                    usuarios.remove(aux);
                }
            }
        }

        usuarios.add(j);

        try(FileWriter writer = new FileWriter("usuarios.json")){
            gson.toJson(usuarios, writer);
        }
    }
}
