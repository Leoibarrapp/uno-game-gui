package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import models.UnoGame;
import models.game.*;

import java.io.*;
import java.util.ArrayList;

import static models.UnoGame.*;


public class LoginController {
    public static Juego juego;
    public static String nombreUsuario;

    @FXML
    public Label textoTitulo;
    @FXML
    public Label textoIntroduceUsuario;
    @FXML
    public Label textoBienvenida1;
    @FXML
    private Label textoBienvenida;

    @FXML
    public  TextField campoUsuario;
    @FXML
    private Button btnPartidaNueva;
    @FXML
    private Button btnCargarPartida;

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
    }



    @FXML
    protected void onBtnCargarPartidaClick() {

        nombreUsuario = campoUsuario.getText();
        textoBienvenida.setText("Cargando partida anterior...");
        textoBienvenida1.setText("Bienvenido de nuevo, " + nombreUsuario);

        mostrarVentanaJuego();
    }

    @FXML
    protected void onBtnPartidaNuevaClick(){
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
        nombreUsuario = campoUsuario.getText();

        textoBienvenida.setText("Creando partida nueva...");
        textoBienvenida1.setText("Bienvenido, " + nombreUsuario);
        //usuario.setText(campoUsuario.getText());

        Mazo pila = new Mazo();
        pila.crear(); pila.barajear();

        Mazo descarte = new Mazo();

        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
        jugadores.add(new Jugador(campoUsuario.getText())); jugadores.add(new CPU());

        juego = new Juego(descarte, pila, jugadores);
        juego.iniciarJuego();

        mostrarVentanaJuego();
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
            stage.setFullScreen(true);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}