package models.game;

public class Tiempo {
    public static void delay(int ms){
        try {
            //Ponemos a "Dormir" el programa durante los ms que queremos
            Thread.sleep(ms);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
