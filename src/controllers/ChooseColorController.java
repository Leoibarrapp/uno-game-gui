package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static controllers.LoginController.juego;
import static models.UnoGame.customFont30;

public class ChooseColorController {
    public Label textoSeleccionaColor;
    @FXML
    private Button R;
    @FXML
    private Button B;
    @FXML
    private Button G;
    @FXML
    private Button Y;

    public void initialize(){
        textoSeleccionaColor.setFont(customFont30);
        R.setOnAction(event -> onBotonEscogerColorClick(R));
        B.setOnAction(event -> onBotonEscogerColorClick(B));
        G.setOnAction(event -> onBotonEscogerColorClick(G));
        Y.setOnAction(event -> onBotonEscogerColorClick(Y));
    }

    public void onBotonEscogerColorClick(Button boton) {
        juego.setColorActual(boton.getId().charAt(0));

        ((Stage) R.getScene().getWindow()).close();
    }
}
