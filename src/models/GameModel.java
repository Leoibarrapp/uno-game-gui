package models;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.game.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static controllers.GameController.*;
import static controllers.LoginController.juego;

public class GameModel {

//    public static void jugadaCPU(Jugador cpu){
//        Carta carta = null;
//        System.out.println((Jugador) cpu);
//        if(cpu.puedeJugar(juego)){
//            System.out.println("si tiene");
//            carta = ((CPU) cpu).escogerCarta(juego);
//            System.out.println(cpu);
//            System.out.println("carta " + carta);
//            cpu.jugar(juego, carta);
//            System.out.println(cpu);
//            contenedorC.eliminarBoton();
//            cartaActual = carta;
//        }
//        else{
//            cpu.agarrarCarta(juego);
//            contenedorC.agregarBoton(contenedorC.crearBoton());
//            System.out.println("no tiene");
//        }
//    }

}
