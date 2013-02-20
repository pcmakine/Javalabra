/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents.sidePaneComponents;

import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;

/**
 *
 * @author Pete
 */
public class StatsPane extends JTextArea {

    private String playerName;
    private String opponentName;
    private Color background;

    public StatsPane(Color background) {

        this.background = background;
        makeLayout();
        playerName = "Pelaaja1";
        opponentName = "Tietokone";
        setScore(0, 0, 0);
    }

    private void makeLayout() {
        this.setMaximumSize(new Dimension(50, 50));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setEditable(false);
        this.setBackground(background);
    }

    public void setScore(int playerShips, int opponentShips, int turn) {
        this.setText("Tilannetieto:\n"
                + "Vuoro: " + turn
                + "\nLaivoja jäljellä\n" + playerName + ": " + playerShips
                + "\n" + opponentName + ": " + opponentShips);
    }

    public void setPlayerName(String name) {
        if (name == null || name.equals("")) {
            playerName = "Pelaaja 1";
        } else {
            playerName = name;
        }

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setOpponentName(String name) {
        if (name == null || name.equals("")) {
            opponentName = "Pelaaja 2";
        } else {
            opponentName = name;
        }
    }
}