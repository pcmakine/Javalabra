/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import javax.swing.SwingUtilities;
import sovelluslogiikka.Lauta;

/**
 *
 * @author pcmakine
 */
import sovelluslogiikka.*;

public class BattleShip {

    private static Laiva laiva;
    private static Laiva[] laivat;

    /* public static boolean teeLaiva(){
     int [] xkoord = {5,6,7};
     int [] ykoord = {5,6,7};
     Laiva laiva = new Laiva(xkoord, ykoord);
     laivat[0]=laiva;
     return true;
     } }*/
    public static void main(String[] args) {
        Lauta lauta = new Lauta(10, 5);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Grafiikka ikkuna = new Grafiikka();
                ikkuna.setVisible(true);
            }
        });

        int[] alku = {4, 7};
        int[] kohde = {4, 8};
        int[] kohde1 = {4, 6};
        lauta.teeLaiva(alku, Suunta.ALAS, 2);
        Pelaaja pelaaja = new Pelaaja();
        
        pelaaja.ammu(lauta, kohde1);
        System.out.println(lauta);


    }
}