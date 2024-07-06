package models.game;

public class CPU extends Jugador{

    public CPU() {
        super("CPU");
    }

    /**
     * El models.game.CPU escoge una carta de manera aleatoria
     * @param juego
     * @return la carta escogida por el models.game.CPU
     */
    public Carta escogerCarta(Juego juego){
        Carta escogida = null;
        for(Carta carta : this.getCartas().getMazo()){
            if(carta.esJugable(juego)){
                escogida = carta;
                break;
            }
        }
        return escogida;
    }

    /**
     * EL models.game.CPU escoge un color de manera aleatoria para cuando corresponde un cambio de color
     * @return el color escogido por el models.game.CPU
     */
    public char escogerColor(){
        int numero = (int) (Math.random()*4);
        char color = ' ';
        switch(numero){
            case 0: color = 'R';
                break;
            case 1: color = 'G';
                break;
            case 2: color = 'B';
                break;
            case 3: color = 'Y';
                break;
        }
        return color;
    }

    /**
     * Es parecido al metodo toString de jugador
     * @return devuelve el nombre del jugador y la cantidad de cartas restantes
     */
    public String toString(){
        String s = "models.game.CPU [...] \u001B[37m " + this.getCartas().getMazo().size() + " cartas restantes";

        if(this.getCartas().getMazo().size() == 1){
            s = s + "\u001B[33m UNO!";
        }

        return s + "\u001B[0m";
    }

}
