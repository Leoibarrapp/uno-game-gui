package models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UnoGame extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(UnoGame.class.getResource("/views/LoginView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 500);
            stage.setTitle("UNO");
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
