package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.ContenedorCartasC;
import models.ContenedorCartasJ;
import models.UnoGame;
import models.game.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static controllers.LoginController.juego;
import static models.UnoGame.*;

public class GameController {

    private String nombreUsuario;

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
    private Button btnSalir;
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

            delay(event -> { jugadaCPU(); }, 1);
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
        botonCartaEscogida = b;
    }

    public void onBtnJugarCartaClick() {

        if((cartaActual != null) && (cartaActual.esJugable(juego))){
            textoEnJuegoArriba.setText("");
            textoEnJuegoAbajo.setText("");
            jugador.jugar(juego, cartaActual);

            contenedorJ.eliminarBoton(botonCartaEscogida.getId());

            botonCartaActual.setGraphic(botonCartaEscogida.getGraphic());

            if(cartaActual.getTipo().equals("T4")){


                cpu.agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                cpu.agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                cpu.agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                cpu.agarrarCarta(juego);
                contenedorC.agregarBoton(contenedorC.crearBoton());

                ventanaModalEscogerColor();
                textoEnJuegoArriba.setText("Color escogido: " + juego.getColorActual());
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
                            ventanaModalEscogerColor();
                            textoEnJuegoArriba.setText("Color escogido: " + juego.getColorActual());
                            delay(event -> { jugadaCPU(); }, 1);
                        }
                        else{
                            if(jugador.getCartas().getMazo().size() == 1){
                                textoEnJuegoAbajo.setText("¡" + nombreUsuario+" ha cantado UNO!");

                                delay(event -> { jugadaCPU(); }, 1);
                            }
                            else {
                                if (jugador.getCartas().getMazo().isEmpty()) {

                                    contenedorC.setDisable(true);
                                    contenedorJ.setDisable(true);
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
                                    delay(event -> { jugadaCPU(); }, 1);
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

                delay(event -> { jugadaCPU(); }, 1);
            }
            else{
                if(carta.getTipo().equals("T2")){
                    jugador.agarrarCarta(juego);
                    contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));

                    jugador.agarrarCarta(juego);
                    contenedorJ.agregarBoton(contenedorJ.crearBoton(jugador.getCartas().getTope()));

                }
                else{
                    if(carta.getTipo().equals("R") || carta.getTipo().equals("S")){
                        textoEnJuegoAbajo.setText("CPU repitió el turno");

                        delay(event -> { jugadaCPU(); }, 1);
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

    public void onBtnSalirClick() throws IOException {
        guardarPartida();
        ((Stage) btnSalir.getScene().getWindow()).close();

        FXMLLoader fxmlLoader = new FXMLLoader(UnoGame.class.getResource("/views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);

        Image icon = new Image(UnoGame.class.getResourceAsStream("/views/recursos/cartaUno.png"));

        Stage stage = new Stage();

        stage.setTitle("uno-game-login");
        stage.getIcons().add(icon);
        stage.setScene(scene);

        stage.show();

    }

//    public void onBtnColorEscogidoClick() throws IOException {
//        ventanaModalEscogerColor();
//
////        if(boxEscogerColor.getValue() != null){
////            boxEscogerColor.setDisable(true);
////            btnColorEscogido.setDisable(true);
////            juego.setColorActual(boxEscogerColor.getValue().charAt(0));
////            textoEnJuegoArriba.setText("Color escogido: " + juego.getColorActual());
////
////            if(cartaActual.getTipo().equals("CC")){
////                jugadaCPU();
////            }
////
////        }
//
//    }

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

    public void ventanaModalEscogerColor(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ChooseColorView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("uno-game-choose-color");
            stage.initModality(Modality.APPLICATION_MODAL); // Set the modality
            stage.setScene(new Scene(root));

            stage.initStyle(StageStyle.UNDECORATED);

            stage.showAndWait(); // Show the modal window and wait for it to be closed
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

    private void guardarUsuario(Jugador j) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ArrayList<Jugador> usuarios = new ArrayList<>();

        try(FileReader reader = new FileReader("usuarios.json")){
            usuarios = gson.fromJson(reader, ArrayList.class);                  //  carga la lista de usuarios
        }
        catch (FileNotFoundException e){

        }

        if(usuarios != null){               //si es null el archivo está vacío
            for(Jugador aux : usuarios){
                if(Objects.equals(j.getNombre(), aux.getNombre())){
                    usuarios.remove(aux);           //si consigue uno con el mismo nombre lo elimina
                }
            }
        }

        usuarios.add(j);        //añade el jugador con los puntos a la lista

        try(FileWriter writer = new FileWriter("usuarios.json")){
            gson.toJson(usuarios, writer);      //carga otra vez la lista en el archivo
        }
    }


}
