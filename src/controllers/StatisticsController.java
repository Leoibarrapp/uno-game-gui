package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import models.game.Jugador;
import models.game.Scoreboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;

public class StatisticsController {
    @FXML
    public Button BtnCargar;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Jugador> estadisticasTabla;
    @FXML
    private Text estadisticasTxt;
    @FXML
    private TableColumn colJugador;
    @FXML
    private TableColumn colPuntaje;
    @FXML
    private ObservableList<Jugador> estadisticas;

    @FXML
    public void initialize() {
       estadisticas = FXCollections.observableArrayList();

       this.colJugador.setCellValueFactory(new PropertyValueFactory<>("nombre"));
       this.colPuntaje.setCellValueFactory(new PropertyValueFactory<>("puntaje"));

        ArrayList<Jugador> usuarios = cargarEstadisticas();

        System.out.println(usuarios);

        setEstadisticasTabla(usuarios);

    }
    @FXML
    public void setEstadisticasTabla(ArrayList<Jugador> usuarios) {

        estadisticas.setAll(usuarios);
        estadisticasTabla.setItems(estadisticas);

    }
    @FXML
    public ArrayList<Jugador> cargarEstadisticas() {
        ArrayList<Jugador> usuarios = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader("usuarios.json")) {
            Type listType = new TypeToken<ArrayList<Jugador>>() {}.getType();
            usuarios = gson.fromJson(reader, listType);
        } catch (IOException e) {
        }
        return usuarios;
    }
}