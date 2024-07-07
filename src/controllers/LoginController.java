package controllers;

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

import static models.UnoGame.customFont20;
import static models.UnoGame.customFont80;


public class LoginController {
    public static Juego juego;

    @FXML
    public Label textoTitulo;
    @FXML
    public Label textoIntroduceUsuario;
    @FXML
    private Label textoBienvenida;

    @FXML
    public TextField campoUsuario;
    @FXML
    private Button btnPartidaNueva;
    @FXML
    private Button btnCargarPartida;

    @FXML
    public void initialize(){
        textoBienvenida.setFont(customFont20);
        textoIntroduceUsuario.setFont(customFont20);
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
    protected void getUsuario() throws IOException {
        String usuario = campoUsuario.getText();

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        boolean usuarioExiste = false;
//
//        //FileReader reader = new FileReader("usuarios.json");
//        LinkedList<String> u = null;
//        //u = gson.fromJson(reader, List.class);
//        //System.out.println(u);
//        //reader.close();
//
//        try(FileWriter writer = new FileWriter("usuarios.json")){
//            //u.add(usuario);
//            //System.out.println(u);
//            gson.toJson(u, writer);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        try (FileReader reader = new FileReader("usuarios.json")) {
//            List<String> usuarios = gson.fromJson(reader, List.class);
//
//            if (usuarios != null) {
//                for(String u : usuarios) {
//                    if (usuario.equals(u)) {
//                        usuarioExiste = true;
//                    }
//                }
//            }
//        } catch (FileNotFoundException e) {
//            // File not found, will create a new one
//        }
//
//        if(usuarioExiste == true){
//            texto2.setText("CONOCIDO");
//        }
//        else{
//            texto2.setText("NO CONOCIDO");
//        }
    }

    @FXML
    protected void onBtnCargarPartidaClick() {

        textoBienvenida.setText("Cargando partida anterior..." +
                "\nBienvenido de nuevo, " + campoUsuario.getText());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GameView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
            Stage stage = new Stage();

//            Image icon = new Image(GameController.class.getResourceAsStream("/views/recursos/cartaUno.png"));
//            stage.getIcons().add(icon);

            stage.setTitle("PARTIDA");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onBtnPartidaNuevaClick(){
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
        textoBienvenida.setText("Creando partida nueva...\n" +
                "Bienvenido, " + campoUsuario.getText());
        //usuario.setText(campoUsuario.getText());

        Mazo pila = new Mazo();
        pila.crear(); pila.barajear();

        Mazo descarte = new Mazo();

        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
        jugadores.add(new Jugador(campoUsuario.getText())); jugadores.add(new CPU());

        juego = new Juego(descarte, pila, jugadores);
        juego.iniciarJuego();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GameView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("PARTIDA");
            stage.setScene(new Scene(root));
            //stage.setResizable(false);
            stage.maximizedProperty();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}