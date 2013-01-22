/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import sovelluslogiikka.Lauta;

/**
 *
 * @author pcmakine
 */
import sovelluslogiikka.*;

public class BattleShip {
    private static Laiva laiva;
    private static Laiva[] laivat;

 public static boolean teeLaiva(){
     int [] xkoord = {5,6,7};
     int [] ykoord = {5,6,7};
     Laiva laiva = new Laiva(xkoord, ykoord);
     laivat[0]=laiva;
     return true;
 }
    public static void main(String[] args) {
        Lauta lauta = new Lauta(10);

        int[] alku = {4, 4};
        boolean onnistuuko;
        String suunta = "alas";
    
        onnistuuko = lauta.teeLaiva(alku, suunta, 2);
        System.out.println(onnistuuko);
    }
}
