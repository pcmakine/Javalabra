/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import sovelluslogiikka.Lauta;

/**
 *
 * @author pcmakine
 */
import sovelluslogiikka.*;
import highScore.*;

public class BattleShip {

    public static void main(String[] args) {
        final Lauta lauta = new Lauta(10, 4);
        final Pelaaja pelaaja = new Pelaaja(lauta);
        HighScore highscore = new HighScore();

        System.out.print(highscore.getHighscoreString());
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Controller controller = new Controller(pelaaja);
                Grafiikka ikkuna = new Grafiikka(controller);

            }
        });
    }
}