/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

/**
 *
 * @author pcmakine
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Asker {

    JFrame ikkuna;
    private Object[] possibilities = {"Horizontal ship", "Vertical ship"};

    public Asker(JFrame ikkuna) {
        this.ikkuna = ikkuna;
    }

    public int askInput(String message) {
        String dialog = "";
        int input = -1;
        do {
            try {
                dialog = (String) JOptionPane.showInputDialog(ikkuna, message, "Alkukoordinaatit", JOptionPane.PLAIN_MESSAGE);
                input = Integer.parseInt(dialog);
            } catch (NumberFormatException e) {
                continue;
            }
            if (input > 10 || input < 1) {
                presentWarning("Antamasi koordinaatti on laudan ulkopuolella."
                        + "\nOle hyvä ja anna uudet koordinaatit.");
            }
        } while (dialog.isEmpty() || input > 10 || input < 1);
        return input;
    }

    public String askDirection(String message) {
        String dialog = "";
        if (message.equals("")) {
            JOptionPane.showMessageDialog(ikkuna, "Enää yhden mittaiset laivat jäljellä, paina ok jatkaaksesi");
        } else {
            dialog = (String) JOptionPane.showInputDialog(ikkuna, message, null, JOptionPane.PLAIN_MESSAGE, null, possibilities,
                    "Horizontal ship");
        }
        return dialog;
    }

    public void presentWarning(String message) {
        JOptionPane.showMessageDialog(ikkuna,
                message,
                "Nyt tarkkana!",
                JOptionPane.WARNING_MESSAGE);
    }
}