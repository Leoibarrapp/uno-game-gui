package controllers;

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

public class StatisticsController {
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

        //setEstadisticasTabla();

    }
    @FXML
    public void setEstadisticasTabla(Scoreboard scoreboard) {
         for (Jugador jugador : scoreboard.getJugadores()) {

             estadisticas.add(jugador);
             this.estadisticasTabla.setItems(estadisticas);
         }

    }
}