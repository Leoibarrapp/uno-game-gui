package models.game;

public class CartaColor extends Carta{

    /**
     * Constructor de models.game.CartaColor
     * @param color el color de la carta
     * @param tipo el tipo de la carta, ejemplo: 0; 6; R (reversa)
     */
    public CartaColor(char color, String tipo) {
        super(color, tipo);
    }

    /**
     * Se compara la carta con la carta tope del juego para validar si se puede jugar
     * Si solo queda una carta y es comodin, entonces no se puede jugar
     * @param juego Se le pasa el juego para verificar el tope actual
     * @return true si es jugable y false si no
     */
    public boolean esJugable(Juego juego){
        Carta tope = juego.getMazoJuego().getTope();
        switch (this.getTipo()){
            case "R","S","T2":
                Jugador jugador = juego.getJugadores().get(juego.getTurno());
                if(jugador.cartasRestantes() == 1){
                    return false;
                } else
                    if ((this.getTipo().equals(tope.getTipo())) || (this.getColor() == juego.getColorActual()) ) {
                        return true;
                }
            default:
        if( (this.getTipo().equals(tope.getTipo())) || (this.getColor() == juego.getColorActual()) ) {
            return true;
        }else
            return false;

    }}

    /**
     * Realiza la accion de la carta segun su tipo
     * Las cartas Reversa [R] y Saltar [S] hacen que repitas el turno
     * En el T2 ambos jugadores sueltan las cartas T2 que tengan y se van sumando hasta que se acaben
     * @param juego la funcion necesita acceso a los jugadores y sus cartas
     */
    public void usar(Juego juego){
        Jugador jugador = juego.getJugadores().get(juego.getTurno());

        switch(this.getTipo()){
            case "R","S":
                System.out.println("\tRepite el turno");
                break;
            case "T2":
                juego.cambiarTurno();
                int cant = 2;

                Carta carta = jugador.buscarCartaSegunTipo("T2");

                while(carta != null){
                    System.out.println( "\t" + TextColor.YELLOW + jugador.getNombre() + TextColor.RESET + " ha respondido con otra carta " + TextColor.GREEN + "T2!" + TextColor.RESET );

                    cant = cant + 2;
                    juego.cambiarTurno();
                    jugador.getCartas().eliminarCarta(carta);
                    juego.getMazoJuego().agregarCarta(carta);

                    jugador = juego.getJugadores().get(juego.getTurno());
                    carta = jugador.buscarCartaSegunTipo("T2");
                }

                for(int i = 0; i < cant; i++){
                    jugador.agarrarCarta(juego);
                }

                System.out.println();
                System.out.println("\t"+ TextColor.YELLOW + jugador.getNombre() + TextColor.RESET +" robó " + TextColor.YELLOW + cant + " cartas" + TextColor.RESET + " de la pila" + TextColor.RESET);
            default:
                juego.cambiarTurno();
        }

    }
}