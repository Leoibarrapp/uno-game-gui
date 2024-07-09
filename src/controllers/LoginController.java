package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import models.UnoGame;
import models.game.*;

import java.io.*;
import java.util.ArrayList;

import static models.UnoGame.*;


public class LoginController {
    public static Juego juego;

    @FXML
    private Label textoTitulo;
    @FXML
    private Label textoIntroduceUsuario;
    @FXML
    private Label textoBienvenida1;
    @FXML
    private ProgressBar barraProgreso;
    @FXML
    private Button btnEstadisticas;
    @FXML
    private Label textoBienvenida;
    @FXML
    private  TextField campoUsuario;
    @FXML
    private Button btnPartidaNueva;
    @FXML
    private Button btnCargarPartida;
    @FXML
    private Button btnSalir;

    @FXML
    public void initialize(){
        textoBienvenida.setFont(customFont20);
        textoBienvenida1.setFont(customFont20);
        textoIntroduceUsuario.setFont(customFont30);
        textoTitulo.setFont(customFont80);
        campoUsuario.setFont(customFont20);

        btnPartidaNueva.setFont(customFont20);
        btnPartidaNueva.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btnPartidaNueva.setPrefHeight(Region.USE_COMPUTED_SIZE);

        btnCargarPartida.setFont(customFont20);
        btnCargarPartida.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btnCargarPartida.setPrefHeight(Region.USE_COMPUTED_SIZE);

        btnSalir.setFont(customFont20);
        btnSalir.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btnSalir.setPrefHeight(Region.USE_COMPUTED_SIZE);

        btnEstadisticas.setFont(customFont20);
        btnEstadisticas.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btnEstadisticas.setPrefHeight(Region.USE_COMPUTED_SIZE);
    }



    @FXML
    protected void onBtnCargarPartidaClick() {

        cargarPartida();

        if(juego.getGanador() == null){
            String nombreUsuario = campoUsuario.getText();
            textoBienvenida.setText("Cargando partida anterior...");
            textoBienvenida1.setText("Bienvenido de nuevo, " + juego.getJugadores().getFirst().getNombre());

            delay(event -> { mostrarBarraDeCarga(); }, 1);
            delay(event -> { mostrarVentanaJuego(); }, 6);
        }
        else{
            textoBienvenida1.setTextFill(Color.YELLOW);
            textoBienvenida1.setText( "La partida que está intentando cargar ya ha concluido.");
            juego = null;
        }
    }

    @FXML
    protected void onBtnPartidaNuevaClick() throws InterruptedException {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
        String nombreUsuario = campoUsuario.getText();

        textoBienvenida.setText("Creando partida nueva...");
        textoBienvenida1.setText("Bienvenido, " + nombreUsuario);
        //usuario.setText(campoUsuario.getText());

        Mazo pila = new Mazo();
        pila.crear(); pila.barajear();

        Mazo descarte = new Mazo();

        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
        jugadores.add(new Jugador(campoUsuario.getText()));
        jugadores.add(new Jugador("CPU"));

        juego = new Juego(descarte, pila, jugadores);
        juego.iniciarJuego();

        delay(event -> { mostrarBarraDeCarga(); }, 1);
        delay(event -> { mostrarVentanaJuego(); }, 6);

    }

    private void mostrarVentanaJuego(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();

            Image icon = new Image(UnoGame.class.getResourceAsStream("/views/recursos/cartaUno.png"));

            stage.setTitle("uno-game");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            //stage.setFullScreen(true);

            ((Stage) btnPartidaNueva.getScene().getWindow()).close();

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarBarraDeCarga(){
        barraProgreso.setVisible(true);

        delay(event -> barraProgreso.setProgress(0.2), 2);
        delay(event -> barraProgreso.setProgress(0.4), 2.5);
        delay(event -> barraProgreso.setProgress(0.6), 3);
        delay(event -> barraProgreso.setProgress(0.8), 3.5);
        delay(event -> barraProgreso.setProgress(1.0), 4);
    }

    public void onBtnSalirClick(){
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

    private void cargarPartida(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileReader reader = new FileReader("partida.json");
            //juego = new Juego();
            juego = gson.fromJson(reader, Juego.class);

//            ArrayList<Jugador> jugadores = (ArrayList<Jugador>) juego.getJugadores();
//            Mazo baraja = (Mazo) juego.getMazoPila();
//            Mazo pila = (Mazo) juego.getMazoJuego();
//
//            Jugador jugador = jugadores.getFirst();
//            Jugador cpu = jugadores.getLast();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBtnEstadisticasClick() {

    }
}