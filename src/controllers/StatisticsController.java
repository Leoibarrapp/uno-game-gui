package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.UnoGame;
import models.game.Jugador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;

import static models.UnoGame.customFont20;
import static models.UnoGame.customFont30;

public class StatisticsController {
    @FXML
    public Button btnCargar;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @FXML
    private Button btnVolver;
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
        btnVolver.setFont(customFont20);
        btnVolver.setPrefWidth(Region.USE_COMPUTED_SIZE);

        estadisticasTxt.setFont(customFont30);

        estadisticas = FXCollections.observableArrayList();

        this.colJugador.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.colPuntaje.setCellValueFactory(new PropertyValueFactory<>("puntaje"));

        ArrayList<Jugador> usuarios = cargarEstadisticas();

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

    public void onBtnVolverClick() throws IOException {
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
}