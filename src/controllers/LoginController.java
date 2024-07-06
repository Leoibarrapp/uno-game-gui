package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;

import models.game.*;

import java.io.*;

public class LoginController {

    @FXML
    private Label texto1;
    @FXML
    private Label texto2;
    @FXML
    private TextField campoUsuario;
    @FXML
    private Button btnPartidaNueva;
    @FXML
    private Button btnCargarPartida;

    @FXML
    protected static Juego juego;

    public LoginController() throws IOException {
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
        texto1.setText("Cargando partida anterior...");
        texto2.setText("Bienvenido de nuevo, " + campoUsuario.getText());

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GameView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("PARTIDA");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onBtnPartidaNuevaClick(){
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
        texto1.setText("Creando partida nueva...");
        texto2.setText("Bienvenido, " + campoUsuario.getText());
//
//        Mazo pila = new Mazo();
//        pila.crear(); pila.barajear();
//
//        Mazo descarte = new Mazo();
//
//        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
//        jugadores.add(new Jugador(campoUsuario.getText())); jugadores.add(new CPU());
//
//        juego = new Juego(descarte, pila, jugadores);
//        juego.iniciarJuego();


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GameView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("PARTIDA");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}