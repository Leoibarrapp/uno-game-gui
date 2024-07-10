package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.ContenedorCartasC;
import models.ContenedorCartasJ;
import models.UnoGame;
import models.game.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import static controllers.LoginController.juego;
import static models.UnoGame.*;

public class GameController {

    private String nombreUsuario;

    @FXML
    public Button btnCantarUno;
    @FXML
    private Label textoEnJuegoAbajo;
    @FXML
    private Label textoEnJuegoArriba;
    @FXML
    private Label textoUsuario;
    @FXML
    private Label textoCPU;
    @FXML
    private Label textoAgarrarCarta;
    @FXML
    private Button btnVolver;
    @FXML
    private Button botonAgarrarCarta;
    @FXML
    private ContenedorCartasJ contenedorJ = new ContenedorCartasJ();
    @FXML
    private ContenedorCartasC contenedorC = new ContenedorCartasC();
    @FXML
    private static Carta cartaActual;
    @FXML
    private static Button botonCartaEscogida;
    @FXML
    private Button botonCartaActual;

    private Jugador jugador = juego.getJugadores().getFirst();
    private Jugador cpu = juego.getJugadores().getLast();

    @FXML
    public void initialize(){
        nombreUsuario = jugador.getNombre();

        cartaActual = juego.getMazoJuego().getTope();
        this.setImage(botonCartaActual);

        textoEnJuegoAbajo.setFont(customFont30);
        textoEnJuegoArriba.setFont(customFont30);

        textoUsuario.setFont(customFont40);
        textoUsuario.setText(jugador.getNombre());

        textoCPU.setFont(customFont40);

        textoAgarrarCarta.setFont(customFont20);

        btnVolver.setFont(customFont20);
        btnVolver.setPrefWidth(Region.USE_COMPUTED_SIZE);

        btnCantarUno.setFont(customFont30);
        btnCantarUno.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btnCantarUno.setPrefHeight(Region.USE_COMPUTED_SIZE);
    }

    public void onBtnAgarrarCartaClick() throws IOException {
        if(juego.getMazoPila().getMazo().isEmpty()){
            juego.reBarajear();
        }
        if(jugador.puedeJugar(juego) == false){
            textoEnJuegoAbajo.setText("");
            jugador.agarrarCarta(juego);

            contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));

            delay(event -> { jugadaCPU(); }, 1);
        }
        else{
            textoEnJuegoAbajo.setText("¡Tienes cartas que puedes jugar!");
        }

        guardarPartida();

        guardarUsuario(jugador);
    }

    public static void onBtnCartaClick(Button b){
        String id = b.getId();
        cartaActual = juego.getJugadores().getFirst().buscarCarta(id);
        botonCartaEscogida = b;
    }

    public void onBtnJugarCartaClick() {

        if((cartaActual != null) && (cartaActual.esJugable(juego))){
            textoEnJuegoArriba.setText("");
            textoEnJuegoAbajo.setText("");
            jugador.jugar(juego, cartaActual);

            contenedorJ.eliminarBoton(botonCartaEscogida.getId());

            botonCartaActual.setGraphic(botonCartaEscogida.getGraphic());

            switch (cartaActual.getTipo()){
                case "T4":

                    for(int i = 0; i < 4; i++){
                        cpu.agarrarCarta(juego);
                        contenedorC.agregarBoton(contenedorC.crearBoton());
                    }

                    ventanaModalEscogerColor();
                    textoEnJuegoArriba.setText("Color escogido: " + juego.getColorActual());

                    textoEnJuegoAbajo.setText("¡Repites turno!");
                    cantarUno();
                    return;
                case "T2":
                    cpu.agarrarCarta(juego);
                    contenedorC.agregarBoton(contenedorC.crearBoton());

                    cpu.agarrarCarta(juego);
                    contenedorC.agregarBoton(contenedorC.crearBoton());

                    textoEnJuegoAbajo.setText("¡Repites turno!");
                    cantarUno();
                    return;
                case "S", "R":
                    textoEnJuegoAbajo.setText("¡Repites turno!");
                    cantarUno();
                    return;
                case "CC":
                    ventanaModalEscogerColor();
                    textoEnJuegoArriba.setText("Color escogido: " + juego.getColorActual());
                default:
                    if(jugador.getCartas().getMazo().size() == 1){
                        btnCantarUno.setVisible(true);
                        btnCantarUno.setDisable(false);

                        delay( event -> { btnCantarUno.setVisible(false); }, 2);

                        delay(event -> {
                            if(btnCantarUno.isDisabled() == false){
                                textoEnJuegoArriba.setTextFill(Color.ORANGE); textoEnJuegoArriba.setText("¡No has cantado uno!");
                                textoEnJuegoAbajo.setTextFill(Color.ORANGE); textoEnJuegoAbajo.setText("Fuiste penalizado con +2 cartas");

                                for(int i = 0; i < 2; i++){
                                    jugador.agarrarCarta(juego);
                                    contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));
                                }
                            }
                        }, 2);

                        delay(event -> { btnCantarUno.setVisible(false); }, 2 );
                        delay(event -> {
                            textoEnJuegoArriba.setText(""); textoEnJuegoArriba.setTextFill(Color.WHITE);
                            textoEnJuegoAbajo.setText(""); textoEnJuegoAbajo.setTextFill(Color.WHITE);
                        }, 6);

                    }
                    else {
                        if (jugador.getCartas().getMazo().isEmpty()) {

                            contenedorC.setDisable(true);
                            contenedorJ.setDisable(true);
                            botonAgarrarCarta.setDisable(true);
                            textoAgarrarCarta.setDisable(true);
                            textoEnJuegoArriba.setText("¡" + nombreUsuario + " ha ganado!");
                            textoEnJuegoArriba.setFont(customFont80);

                            int puntos = obtenerPuntosGanador(cpu);

                            textoEnJuegoAbajo.setText(puntos + " puntos");
                            textoEnJuegoAbajo.setFont(customFont80);

                            puntos += jugador.getPuntaje();

                            juego.setGanador(jugador);
                            jugador.setPuntaje(puntos);
                            return;
                        }
                        else{
                            delay(event -> { jugadaCPU(); }, 1);
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

            switch (cartaActual.getTipo()){
                case "CC":

                    juego.setColorActual(cpu.escogerColor());
                    textoEnJuegoArriba.setText("Color escogido: " + juego.getColorActual());

                    break;
                case "T4":
                    for(int i = 0; i < 4; i++){
                        jugador.agarrarCarta(juego);
                        contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));
                    }

                    juego.setColorActual(cpu.escogerColor());
                    textoEnJuegoArriba.setText("Color escogido: " + juego.getColorActual());

                    textoEnJuegoAbajo.setText("¡CPU repite turno!");
                    delay(event -> { jugadaCPU(); }, 1);

                    break;
                case "T2":
                    jugador.agarrarCarta(juego);
                    contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));

                    jugador.agarrarCarta(juego);
                    contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));

                    textoEnJuegoAbajo.setText("¡CPU repite turno!");
                    delay(event -> { jugadaCPU(); }, 1);

                    break;
                case "S", "R":
                    textoEnJuegoAbajo.setText("¡CPU repite turno!");
                    delay(event -> { jugadaCPU(); }, 1);
                    break;
            }

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

                    int puntos = obtenerPuntosGanador(jugador);

                    textoEnJuegoAbajo.setText(puntos + " puntos");
                    textoEnJuegoAbajo.setFont(customFont80);

                    puntos += cpu.getPuntaje();

                    juego.setGanador(cpu);
                    cpu.setPuntaje(puntos);

                    return;
                }
            }

        }
        else {
            cpu.agarrarCarta(juego);
            contenedorC.agregarBoton(contenedorC.crearBoton());
            textoEnJuegoAbajo.setText("CPU ha agarrado una carta");
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


    public void onBtnVolverClick() throws IOException {

        if(juego.getGanador() != null){
            guardarUsuario(juego.getGanador());
        }

        guardarPartida();
        ((Stage) btnVolver.getScene().getWindow()).close();

        FXMLLoader fxmlLoader = new FXMLLoader(UnoGame.class.getResource("/views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);

        Image icon = new Image(UnoGame.class.getResourceAsStream("/views/recursos/cartaUno.png"));

        Stage stage = new Stage();

        stage.setTitle("uno-game-login");
        stage.getIcons().add(icon);
        stage.setScene(scene);

        stage.show();

    }

    private int obtenerPuntosGanador(Jugador j){
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

    public void ventanaModalEscogerColor(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ChooseColorView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("uno-game-choose-color");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.initStyle(StageStyle.UNDECORATED);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarPartida(){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter writer = new FileWriter("partida.json")) {
            gson.toJson(juego, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void guardarUsuario(Jugador j) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Jugador> usuarios = new ArrayList<>();

        try (FileReader reader = new FileReader("usuarios.json")) {
            Type listType = new TypeToken<ArrayList<Jugador>>() {}.getType();
            usuarios = gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
        }

        if (usuarios != null) {
            usuarios.removeIf(aux -> Objects.equals(j.getNombre(), aux.getNombre()));
        } else {
            usuarios = new ArrayList<>();
        }

        usuarios.add(j);

        try (FileWriter writer = new FileWriter("usuarios.json")) {
            gson.toJson(usuarios, writer);
        }
    }

    public void onBtnCantarUnoClick() {
        textoEnJuegoAbajo.setText("¡" + nombreUsuario + " ha cantado uno!");
        btnCantarUno.setVisible(false);
        btnCantarUno.setDisable(true);
    }

    public void cantarUno(){
        if(jugador.getCartas().getMazo().size() == 1){
            btnCantarUno.setVisible(true);
            btnCantarUno.setDisable(false);

            delay( event -> { btnCantarUno.setVisible(false); }, 2);

            delay(event -> {
                if(btnCantarUno.isDisabled() == false){
                    textoEnJuegoArriba.setTextFill(Color.ORANGE); textoEnJuegoArriba.setText("¡No has cantado uno!");
                    textoEnJuegoAbajo.setTextFill(Color.ORANGE); textoEnJuegoAbajo.setText("Fuiste penalizado con +2 cartas");

                    for(int i = 0; i < 2; i++){
                        jugador.agarrarCarta(juego);
                        contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));
                    }
                }
            }, 2);

            delay(event -> { btnCantarUno.setVisible(false); }, 2 );
            delay(event -> {
                textoEnJuegoArriba.setText(""); textoEnJuegoArriba.setTextFill(Color.WHITE);
                textoEnJuegoAbajo.setText(""); textoEnJuegoAbajo.setTextFill(Color.WHITE);
            }, 6);

        }
        else {
            if (jugador.getCartas().getMazo().isEmpty()) {

                contenedorC.setDisable(true);
                contenedorJ.setDisable(true);
                botonAgarrarCarta.setDisable(true);
                textoAgarrarCarta.setDisable(true);
                textoEnJuegoArriba.setText("¡" + nombreUsuario + " ha ganado!");
                textoEnJuegoArriba.setFont(customFont80);

                int puntos = obtenerPuntosGanador(cpu);

                textoEnJuegoAbajo.setText(puntos + " puntos");
                textoEnJuegoAbajo.setFont(customFont80);

                puntos += jugador.getPuntaje();

                juego.setGanador(jugador);
                jugador.setPuntaje(puntos);
                return;
            }
            else{

            }
        }
    }
}
