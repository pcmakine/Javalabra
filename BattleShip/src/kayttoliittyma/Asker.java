/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

/**
 * Varoittaa käyttäjää dialogi ikkunalla jos jokin menee pieleen
 *
 * @author pcmakine
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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