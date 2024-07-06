package models;

import javafx.application.Application;
import models.game.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static controllers.LoginController.juego;

public class GameModel {

    public static void jugadaCPU(Jugador cpu){
        if(cpu.puedeJugar(juego)){
            cpu.jugar(juego, null);
        }
    }

    public void informacion(){
    }
}
