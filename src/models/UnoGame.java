package models;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class UnoGame extends Application {

    public static final String fontURL = "/views/recursos/MinecraftRegular-Bmg3.otf";
    public static final Font customFont20 = Font.loadFont(UnoGame.class.getResourceAsStream("/views/recursos/MinecraftRegular-Bmg3.otf"), 20);
    public static final Font customFont30 = Font.loadFont(UnoGame.class.getResourceAsStream("/views/recursos/MinecraftRegular-Bmg3.otf"), 30);
    public static final Font customFont40 = Font.loadFont(UnoGame.class.getResourceAsStream("/views/recursos/MinecraftRegular-Bmg3.otf"), 40);
    public static final Font customFont80 = Font.loadFont(UnoGame.class.getResourceAsStream("/views/recursos/MinecraftRegular-Bmg3.otf"), 80);

    @Override
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(UnoGame.class.getResource("/views/LoginView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 700);

            Image icon = new Image(UnoGame.class.getResourceAsStream("/views/recursos/cartaUno.png"));

            stage.setTitle("uno-game-login");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        launch();
    }

    public static void delay(EventHandler<ActionEvent> var1, double seconds){
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(var1);
        pause.play();
    }
}
