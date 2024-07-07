package models.game;

import models.ContenedorCartasC;

public class Jugador{
    private String nombre;
    private Mazo cartas;
    private int puntos;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Mazo getCartas() {
        return cartas;
    }

    public void setCartas(Mazo cartas) {
        this.cartas = cartas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Verifica si el jugador tiene alguna carta que pueda colocar encima del mazo de models.game.Juego
     * @param juego necesita acceder al tope del mazo juego actual
     * @return si retorna falso entonces el jugador pierde el turno y agarra una carta
     */
    public boolean puedeJugar(Juego juego){
        Carta tope = juego.getMazoJuego().getTope();
        for(Carta carta : cartas.getMazo()) {
            if(carta.esJugable(juego)){
                return true;
            }
        }
        return false;
    }

    /**
     * Si el jugador no puede jugar ninguna carta (puedeJugar retorna falso) entonces se aplica este metodo
     * Al mazo del jugador se le agrega una carta de la baraja
     * @param juego necesita acceso a la baraja
     */
    public void agarrarCarta(Juego juego){
        Carta carta =  juego.getMazoPila().getTope();
        this.cartas.agregarCarta(carta);
        juego.getMazoPila().eliminarCarta(carta);
    }

    /**
     * Busca si la carta colocada por el jugador se encuentra en su mazo
     * @param idCarta es el identificador de una carta, ejemplo: B-7
     * @return la carta, si no la encuentra devuelve null
     */
    public Carta buscarCarta(String idCarta){
        if(!idCarta.isEmpty()){
            char color = idCarta.charAt(0);
            //System.out.println(color);
            if(idCarta.length() > 1){
                String tipo = idCarta.substring(2);
                //System.out.println(tipo);

                for(Carta carta : cartas.getMazo()){
                    if((color == carta.getColor()) && (tipo.equals(carta.getTipo()))){
                        return carta;
                    }
                }
            }

        }
        return null;
    }

    /**
     * Busca una carta de un tipo especifico en lascartas del jugador
     * @param tipoCarta es el tipo de la carta, ejemplo: T4
     * @return la carta, si no la encuentra devuelve null
     */
    public Carta buscarCartaSegunTipo(String tipoCarta){
        Carta c = null;

        for(Carta carta : cartas.getMazo()){
            if(carta.getTipo().equals(tipoCarta)){
                c = carta;
            }
        }

        return c;
    }

    /**
     * Cuando a un jugador le queda una sola carta, canta UNO
     */
    public void cantarUno(){
        System.out.println("\t" + TextColor.GREEN + this.getNombre() + " ha cantado UNO!" + TextColor.RESET);
    }

    /**
     * Cuando la carta esJugable entonces se usa esta funcion
     * El jugador se coloca en el mazo del models.game.Juego y luego se realiza la accion correspondiente con usar()
     * @param juego
     * @param carta es la carta a jugar
     */
    public void jugar(Juego juego, Carta carta){
      
        juego.getMazoJuego().agregarCarta(carta);
        this.cartas.eliminarCarta(carta);

        if (cartas.getMazo().size() == 1) {
            this.cantarUno();
        }

        juego.setColorActual(carta.getColor());
        carta.usar(juego);

        if (cartas.getMazo().isEmpty()) {
            juego.setGanador(this);
        }
    }

    /**
     *@return la cantidad de cartas que le quedan al jugador
     */
    public int cartasRestantes(){
        return cartas.getMazo().size();
    }


    /**
     * Sobreescribe toString() para imprimir la informacion de un jugador
     * @return el nombre del jugador + su mazo + la cantidad de cartas que le quedan
     */

    public String toString(){
        String s = nombre + " " + cartas + "\u001B[37m " + cartas.getMazo().size() + " cartas restantes";

        if(cartas.getMazo().size() == 1){
            s = s + "\u001B[33m UNO!";
        }

        return s + "\u001B[0m";
    }

}