package models.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * los 4 colores disponibles para crear las CartaColor
 */
enum ColorCarta{ R, G, B, Y };

public class Mazo {
    private LinkedList<Carta> mazo;

    public Mazo() {
        this.mazo = new LinkedList<>();
    }

    public LinkedList<Carta> getMazo() {
        return mazo;
    }

    public void setMazo(LinkedList<Carta> mazo) {
        this.mazo = mazo;
    }

    public Carta getTope() {
        return mazo.getFirst();
    }

    /**
     * Crea las 108 cartas del juego
     * @return el mazo con las 108 cartas
     */
    public LinkedList<Carta> crear(){

        for (ColorCarta color : ColorCarta.values()) {
            mazo.add(new Carta(color.name().charAt(0), "0"));

            for (int numero = 1; numero <= 9; numero++) {
                mazo.add(new Carta(color.name().charAt(0), String.valueOf(numero)));
                mazo.add(new Carta(color.name().charAt(0), String.valueOf(numero)));
            }

            mazo.add(new Carta(color.name().charAt(0), "T2")); mazo.add(new Carta(color.name().charAt(0), "T2"));
            mazo.add(new Carta(color.name().charAt(0), "R")); mazo.add(new Carta(color.name().charAt(0), "R"));
            mazo.add(new Carta(color.name().charAt(0), "S")); mazo.add(new Carta(color.name().charAt(0), "S"));
        }

        for(int i = 0; i < 4; i ++){
            mazo.add(new Carta('W', "T4"));
            mazo.add(new Carta('W', "CC"));
        }

        return mazo;
    }

    /**
     * barajea las cartas de un mazo
     */
    public void barajear(){
        Collections.shuffle(this.mazo);
    }

    /**
     * agrega una carta al mazo
     * @param carta la carta a agregar
     */
    public void agregarCarta(Carta carta) {
        mazo.addFirst(carta);
    }

    /**
     * elimina una carta del mazo
     * @param carta la carta a eliminar
     */
    public void eliminarCarta(Carta carta) {
        mazo.remove(carta);
    }

    /**
     * elimina una carta del mazo
     * @param index la posicion de la carta a eliminar
     */
    public void eliminarCarta(int index) {
        mazo.remove(index);
    }

    /**
     * sobreescribe toString
     * @return un string con todas las cartas contenidas en el mazo
     */
    public String toString() {
        String s = "[ ";

        for(int i = 0; i < mazo.size()-1; i++){
            s = s + mazo.get(i) + ", ";
        }

        return s + mazo.getLast() + " ]";
    }
}