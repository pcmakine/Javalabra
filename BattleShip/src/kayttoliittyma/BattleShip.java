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

    public static void main(String[] args) {
        boolean gameOver = false;
        final Lauta lauta = new Lauta(10, 5);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Grafiikka ikkuna = new Grafiikka(lauta);
            }
        });
    }
}