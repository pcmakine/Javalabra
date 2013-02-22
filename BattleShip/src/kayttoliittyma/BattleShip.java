/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import sovelluslogiikka.Controller;
import sovelluslogiikka.*;
import highScore.*;
import javax.swing.SwingUtilities;
import sovelluslogiikka.Board;

/**
 *
 * @author pcmakine
 */
public class BattleShip {

    public static void main(String[] args) {
        Board lauta = new Board(10, 4);
        final Player pelaaja = new Player(lauta);
        final HighScore highscore = new HighScore();
        
       // highscore.reset();

        System.out.println(highscore.getHighscoreString());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Controller controller = new Controller(pelaaja, highscore);
                MainWindow window = new MainWindow(controller);

            }
        });
    }
}