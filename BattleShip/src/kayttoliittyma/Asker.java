/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import highScore.HighScore;

/**
 * Varoittaa käyttäjää dialogi ikkunalla jos jokin menee pieleen
 *
 * @author pcmakine
 */
public class Asker {

    /**
     * Ikkuna jolle tehtävä dialogi kuuluu. Ohjelman pääikkuna.
     */
    JFrame mainWindow;

    /**
     * Luo uuden olion ja asettaa parametrina saadun JFramen muistiin
     *
     * @param mainWindow ikkuna johon varoitusdialogit tulevat, käytännössä
     * ohjelman pääikkuna
     */
    public Asker(JFrame mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Näyttää huippupisteet käyttäjälle parametrinaan saamansa merkki-
     * jonon alapuolella. Toistaiseksi ei muotoile merkkijonoa taulukoksi, joten
     * pisteet eivät välttämättä ole allekkain
     * @param message Viesti joka käyttäjälle halutaan näyttää pistetaulukon
     * yläpuolella
     * @param highscore highscore olio jonka metodia gethighscorestring käyte-
     * tään huippupisteiden selvittämiseksi.
     */
    public void showHighScore(String message, HighScore highscore) {
        JOptionPane.showMessageDialog(mainWindow,
                (message + "\n" +  highscore.getHighscoreString()),
                "Pistetilasto",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Kysyy käyttäjän nimeä esittäen parametrina saamansa viestin käyt-
     * täjälle
     * @param message viesti joka esitetään käyttäjälle
     * @return palauttaa käyttäjän syöttämän nimen, jonka pitää olla alle kym-
     * menen merkkiä pitkä.
     */
    public String askInput(String message) {
        String dialog = "";
        try {
            do {
                dialog = (String) JOptionPane.showInputDialog(mainWindow, message, "Anna pelaajan nimi", JOptionPane.PLAIN_MESSAGE);

                if (dialog.length() > 10) {
                    presentWarning("Liian pitkä nimi! Anna alle kymmenen merkin pituinen nimi.");
                }

            } while (dialog.length() > 10 || dialog == null);
        } catch (NullPointerException e) {
        }
        return dialog;
    }

    /**
     * Esittää käyttäjälle parametrina saamansa viestin
     *
     * @param message viesti joka esitetään käyttäjälle
     */
    public void presentWarning(String message) {
        JOptionPane.showMessageDialog(mainWindow,
                message,
                "Nyt tarkkana!",
                JOptionPane.WARNING_MESSAGE);
    }
}