package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class UnoGame extends Application {

    public static final Font customFont20 = Font.loadFont(UnoGame.class.getResourceAsStream("/views/MinecraftRegular-Bmg3.otf"), 20);
    public static final Font customFont30 = Font.loadFont(UnoGame.class.getResourceAsStream("/views/MinecraftRegular-Bmg3.otf"), 30);
    public static final Font customFont40 = Font.loadFont(UnoGame.class.getResourceAsStream("/views/MinecraftRegular-Bmg3.otf"), 40);
    public static final Font customFont80 = Font.loadFont(UnoGame.class.getResourceAsStream("/views/MinecraftRegular-Bmg3.otf"), 80);


    @Override
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(UnoGame.class.getResource("/views/LoginView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            stage.setTitle("uno-game-gui");
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
}
