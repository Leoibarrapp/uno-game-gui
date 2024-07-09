package models.game;

public class Carta {
    private final char color;
    private final String tipo;
    private final String urlImagen;

    /**
     * constructor de la carta, es abstracta, solo sirve para instanciar sus hijos
     * @param color el color de la carta
     * @param tipo el tipo de la carta
     */
    public Carta(char color, String tipo) {
        this.tipo = tipo;
        this.color = color;
        this.urlImagen = "/views/recursos/" + color+"-"+tipo + ".png";
    }

    public char getColor() {
        return color;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean esJugable(Juego juego) {
        Carta tope = juego.getMazoJuego().getTope();
        Jugador jugador = juego.getJugadores().get(juego.getTurno());
        
        boolean valor = false;
        
        switch (this.getTipo()) {

            case "R", "S", "T2":
                if (jugador.cartasRestantes() == 1) {
                    valor = false;
                }
                else{
                    if ((this.getTipo().equals(tope.getTipo())) || (this.getColor() == juego.getColorActual())) {
                        valor = true;
                    }
                    else {
                        valor = false;
                    }
                }

                break;

            case "T4", "CC":
                if (jugador.cartasRestantes() == 1) {
                    valor = false;
                }
                else{
                    if (jugador.cartasRestantes() >= 2) {
                        valor = true;
                    }
                }
                
                break;

            default:
                //case "0","1","2","3","4","5","6","7","8","9":
                if ((this.getTipo().equals(tope.getTipo())) || (this.getColor() == juego.getColorActual())) {
                    valor = true;
                }
                else {
                    valor = false;
                }
        }
        
        return valor;
    }

//    public void usar(Juego juego) {
//        int cant;
//        switch (this.getTipo()) {
//            case "CC":
//
//                break;
//            case "T4":
//                juego.cambiarTurno();
//                cant = 4;
//
//                Jugador jugador = juego.getJugadores().get(juego.getTurno());
//                Carta carta = jugador.buscarCartaSegunTipo("T4");
//
//                while (carta != null) {
//                    System.out.println("\t" + TextColor.YELLOW + jugador.getNombre() + TextColor.RESET + " ha respondido con otra carta " + TextColor.GREEN + "T4!" + TextColor.RESET);
//                    cant = cant + 4;
//                    juego.cambiarTurno();
//                    jugador = juego.getJugadores().get(juego.getTurno());
//                    carta = jugador.buscarCartaSegunTipo("T4");
//                }
//
//                for (int i = 0; i < cant; i++) {
//                    jugador.agarrarCarta(juego);
//                }
//
//                System.out.println();
//                System.out.println("\t" + TextColor.YELLOW + jugador.getNombre() + TextColor.RESET + " robó " + TextColor.YELLOW + cant + " cartas " + TextColor.RESET + "de la pila");
//                break;
//            case "R", "S":
//                System.out.println("\tRepite el turno");
//                break;
//            case "T2":
//                juego.cambiarTurno();
//                cant = 2;
//
//                jugador = juego.getJugadores().get(juego.getTurno());
//                carta = jugador.buscarCartaSegunTipo("T2");
//
//                while (carta != null) {
//                    System.out.println("\t" + TextColor.YELLOW + jugador.getNombre() + TextColor.RESET + " ha respondido con otra carta " + TextColor.GREEN + "T2!" + TextColor.RESET);
//
//                    cant = cant + 2;
//                    juego.cambiarTurno();
//                    jugador.getCartas().eliminarCarta(carta);
//                    juego.getMazoJuego().agregarCarta(carta);
//
//                    jugador = juego.getJugadores().get(juego.getTurno());
//                    carta = jugador.buscarCartaSegunTipo("T2");
//                }
//
//                for (int i = 0; i < cant; i++) {
//                    jugador.agarrarCarta(juego);
//                }
//
//                System.out.println();
//                System.out.println("\t" + TextColor.YELLOW + jugador.getNombre() + TextColor.RESET + " robó " + TextColor.YELLOW + cant + " cartas" + TextColor.RESET + " de la pila" + TextColor.RESET);
//            default:
//                juego.cambiarTurno();
//        }
//    }


    /**
     * Sobreescribe toString para imprimir una Carta
     * @return el identificador de la carta con los colores correspondientes
     */
    public String toString() {
        String c = "";
        switch(color){
            case 'R':
                c = TextColor.RED;
                break;
            case 'G':
                c = TextColor.GREEN;
                break;
            case 'B':
                c = TextColor.BLUE;
                break;
            case 'Y':
                c = TextColor.YELLOW;
                break;
            case 'W':
                c = TextColor.GRAY;
                break;
        }

        return c + color + "-" + tipo + TextColor.RESET;
    }

    public String getUrlImagen() {
        return urlImagen;
    }
}