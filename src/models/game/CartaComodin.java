package models.game;

public class CartaComodin extends Carta{

    /**
     * Constructor de models.game.CartaComodin
     * @param color el color de la carta (en este caso es C)
     * @param tipo el tipo de la carta, ejemplo: CC, T4
     */
    public CartaComodin(char color, String tipo) {
        super(color, tipo);
    }

    /**
     * @param juego el juego
     * @return los comodines siempre son jugables excepto que sea la ultima carta de mazo
     */
    public boolean esJugable(Juego juego) {
        Jugador jugador = juego.getJugadores().get(juego.getTurno());
            if(jugador.cartasRestantes() == 1){
                return false;
            }
        return true;
    }

    /**
     * Realiza la accion de la carta segun su tipo
     * En el T4 ambos jugadores sueltan las cartas T4 que tengan y se van sumando hasta que se acaben
     * @param juego la funcion necesita acceso a los jugadores y sus cartas
     */
    public void usar(Juego juego) {
        switch(this.getTipo()){
            case "CC":

                break;
            case "T4":
//                juego.cambiarTurno();
//                int cant = 4;
//
//                Jugador jugador = juego.getJugadores().get(juego.getTurno());
//                Carta carta = jugador.buscarCartaSegunTipo("T4");
//
//                while(carta != null){
//                    System.out.println( "\t" + TextColor.YELLOW + jugador.getNombre() + TextColor.RESET + " ha respondido con otra carta " + TextColor.GREEN + "T4!" + TextColor.RESET );
//                    cant = cant + 4;
//                    juego.cambiarTurno();
//                    jugador = juego.getJugadores().get(juego.getTurno());
//                    carta = jugador.buscarCartaSegunTipo("T4");
//                }
//
//                for(int i = 0; i < cant; i++){
//                    jugador.agarrarCarta(juego);
//                }
//
//                System.out.println();
//                System.out.println("\t"+ TextColor.YELLOW + jugador.getNombre() + TextColor.RESET +" robó " + TextColor.YELLOW + cant + " cartas " + TextColor.RESET + "de la pila");
//                break;
        }
        juego.cambiarTurno();
    }

}