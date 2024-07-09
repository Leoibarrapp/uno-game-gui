package models.game;

import java.util.ArrayList;

public class Juego {
    private Mazo mazoJuego;
    private Mazo mazoPila;
    private ArrayList<Jugador> jugadores;
    private Carta cartaTope;
    private Jugador ganador = null;
    private int turno;
    private char colorActual;


    /**
     * Constructor de Juego
     * @param mazoJuego es el mazo sobre el cual se estara jugando
     * @param mazoPila es el mazo del cual se agarraran cartas
     * @param jugadores son los jugadores del juego
     */
    public Juego(Mazo mazoJuego, Mazo mazoPila, ArrayList<Jugador> jugadores) {
        this.mazoJuego = mazoJuego;
        this.mazoPila = mazoPila;
        this.jugadores = jugadores;
    }

    public Juego() {
    }

    public Mazo getMazoJuego() {
        return mazoJuego;
    }

    public Mazo getMazoPila() {
        return mazoPila;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    public Carta setCartaTope(Mazo mazoPila) {
        return  mazoPila.getTope();
    }

    public Carta getCartaActual() {
        return cartaTope;
    }
    /**
     * El turno pasa al siguiente jugador
     */
    public void cambiarTurno(){
        if(turno == 0){
            turno = 1;
        }
        else{
            turno = 0;
        }
    }

    /**
     * Se reparten las cartas a los jugadores y se coloca una inicial, si es un comodin se barajea nuevamente
     */
    public void iniciarJuego(){
        for(Jugador jugador : jugadores){
            Mazo mazo = new Mazo();
            for(int i = 0; i < 7; i++){
                mazo.agregarCarta(mazoPila.getTope());
                mazoPila.eliminarCarta(mazoPila.getTope());
            }
            jugador.setCartas(mazo);
        }
        Carta carta;
        int num;

        while(true){
            carta = mazoPila.getMazo().getFirst();
            try{
                num = Integer.parseInt(carta.getTipo());
                break;
            }
            catch(NumberFormatException e){
                mazoPila.barajear();
            }
        }

        mazoPila.eliminarCarta(carta);
        mazoJuego.agregarCarta(carta);

        colorActual = mazoJuego.getTope().getColor();
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }

    public Jugador getGanador() {
        return this.ganador;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    /**
     * agarra todas las del mazo juego menos la primera y se vuelven a barajear
     * se usa cuando se acaban las cartas de la pila
     */
    public void reBarajear(){
        Carta carta;
        int tamano = mazoJuego.getMazo().size();
        for(int i = 1; i < tamano; i++){
            carta = mazoJuego.getMazo().getLast();
            mazoPila.agregarCarta(carta);
            mazoJuego.getMazo().removeLast();
        }

        mazoPila.barajear();
    }

    public char getColorActual() {
        return colorActual;
    }

    public void setColorActual(char colorActual) {
        this.colorActual = colorActual;
    }
}