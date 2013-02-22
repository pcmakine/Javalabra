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
 * Mallintaa aluetta jolla tilannetieto näytetään käyttäjälle
 *
 * @author Pete
 */
public class StatsArea extends JTextArea {

    /**
     * Pelaajan nimi
     */
    private String playerName;
    /**
     * Vastustajan nimi. Tällä hetkellä kun kaksinpeliä ei viel ole tämä on aina
     * tietokone.
     */
    private String opponentName;

    /**
     * Tekee uuden tilannetietoalueen ja asettaa alkuarvot
     *
     * @param background Tilannetieto alueen taustaväri
     */
    public StatsArea(Color background) {
        makeLayout(background);
        playerName = "Pelaaja1";
        opponentName = "Tietokone";
        setScore(0, 0, 0);
    }

    /**
     * Asettaa alueen taustavärin sekä tekee alueelle reunuksen ja aset- taa
     * alueen niin ettei käyttäjä voi muokata tekstiä.
     *
     * @param background Alueen taustaväri
     */
    private void makeLayout(Color background) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setEditable(false);
        this.setBackground(background);
    }

    /**
     * Kirjoittaa tilannetietoalueelle pelaajien nimet, laivojen määrät, sekä
     * kierroksen numeron.
     *
     * @param playerShips Pelaajan laivojen määrä
     * @param opponentShips Vastustajan laivojen määrä
     * @param turn vuoronumero
     */
    public void setScore(int playerShips, int opponentShips, int turn) {
        this.setText("Tilannetieto:\n"
                + "Vuoro: " + turn
                + "\nLaivoja jäljellä\n" + playerName + ": " + playerShips
                + "\n" + opponentName + ": " + opponentShips);
    }

    /**
     * Asettaa pelaajan nimeksi parametrinaan saamansa nimen. Jos nimi on
     * tyhjä asetetaan nimeksi "Pelaaja 1"
     *
     * @param name uusi nimi pelaajalle
     */
    public void setPlayerName(String name) {
        if (name == null || name.equals("")) {
            playerName = "Pelaaja 1";
        } else {
            playerName = name;
        }
    }

    /**
     * Asettaa vastustajan nimeksi parametrinaan saamansa nimen. Jos nimi
     * on tyhjä asetetaan nimeksi "Pelaaja 2"
     *
     * @param name uusi nimi vastustajalle
     */
    public void setOpponentName(String name) {
        if (name == null || name.equals("")) {
            opponentName = "Pelaaja 2";
        } else {
            opponentName = name;
        }
    }

    public String getPlayerName() {
        return playerName;
    }
}