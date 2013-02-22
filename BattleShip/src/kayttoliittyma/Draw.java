/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.awt.Color;
import sovelluslogiikka.*;
import javax.swing.JButton;
import java.awt.Font;
import kayttoliittyma.windowComponents.sidePaneComponents.StatsPane;

/**
 * Luokka hoitaa laudalla olevien nappien taustavärin vaihtamisen ja niihin
 * kirjoitettavat merkit
 *
 * @author pcmakine
 */
public class Draw {

    /**
     * napin oletusväri joka palautetaan aina kun lauta tyhjennetään
     */
    private Color buttonDefaultColor;
    /**
     * Napit jotka kuvaavat pelaajan ruutu-olioita
     */
    private JButton[][] squares;
    /**
     * Napit jotka kuvaavat vastustajan ruutu-olioita
     */
    private JButton[][] opponentSquares;
    /**
     * Kustomoitu fontti, jotta saadaan nappien sisälle riittävän pientä tekstiä
     */
    Font newButtonFont;
    /**
     * Sivupaneelissa oleva tekstikenttä johon tämä luokka päivittää tilanteen
     * jokaisen vuoron lopussa.
     */
    StatsPane stats;

    /**
     * Luo uuden piirrustus -olion
     */
    public Draw() {
    }

    /**
     * Asettaa arvot fontille ja nappuloiden oletusvärille
     */
    public void initDrawValues(StatsPane stats) {
        buttonDefaultColor = squares[0][0].getBackground();
        newButtonFont = new Font("Tahoma", squares[0][0].getFont().getStyle(), 6);
        this.stats = stats;
    }

    /**
     * Asettaa squares taulukon osoittamaan pelaajan nappuloihin
     *
     * @param buttons pelaajan nappulat (eli "ruudut")
     */
    public void setPlayerButtons(JButton[][] buttons) {
        squares = buttons;
    }

    /**
     * Asettaa opponentsquares taulukon osoittamaan vastustajan nappuloihin
     *
     * @param buttons vastustajan nappulat (eli "ruudut"). Tällä hetkellä siis
     * tietokonepelaajan koska moninpeliä ei ole vielä toteutettu.
     */
    public void setOpponentButtons(JButton[][] buttons) {
        opponentSquares = buttons;
    }

    /**
     * Piirtää vastustajan laudan eli vaihtaa nappien taustavärin vastustajan
     * laudalla sen mukaan mihin on ammuttu ja missä on laivoja
     *
     * @param opponentBoard vastustajan lauta, joka pitää piirtää
     */
    public void drawOpponentBoard(Board[] boards, int turns) {
        drawBoard(boards, turns);   
        for (int i = 0; i < boards[1].getSize(); i++) {
            for (int j = 0; j < boards[1].getSize(); j++) {
                if (boards[1].getSquares()[i][j].isShot()) {
                }
                if (boards[1].getSquares()[i][j].isShip()) {
                    if (boards[1].getSquares()[i][j].isShot()) {
                        opponentSquares[j][i].setBackground(Color.gray);
                    } else {
                        opponentSquares[j][i].setBackground(Color.blue);
                    }
                }
            }
        }
    }

    /**
     * Vaihtaa lautojen nappien väriä sen mukaan missä parametrina annetuilla
     * laudoilla on laivoja ja mihin on ammuttu. Olettaa lautojen olevan saman
     * kokoiset.
     *
     * @param boards boards[0] on pelaajan lauta ja boards[1] vastustajan lauta
     */
    public void drawBoard(Board[] boards, int turns) {                //olettaa että laudat samankokoisia
        clear(boards[0].getSize());                                               //käydään kummatkin laudat läpi       
        for (int i = 0; i < boards[0].getSize(); i++) {             // laudat[0] pelaajan lauta ja laudat[1] vastustajan lauta
            for (int j = 0; j < boards[0].getSize(); j++) {
                setShipSquaresBlue(boards, i, j);
                setShotSquaresX(boards, i, j);
                setShotShipSquaresGray(boards, i, j);
            }
        }
        setSankShipsBlack(boards);
        updateStats(boards, turns);
    }

    /**
     * Päivittää tilannetiedon monta laivaa kummallakin pelaajalla on
     *
     * @param boards pelaajien laudat joilla laivat ovat. Boards[0] pelaajan ja
     * boards[1] vastustajan lauta
     */
    public void updateStats(Board[] boards, int turns) {
        int[] amount = new int[2];
        for (int i = 0; i < 2; i++) {
            Ship[] ships;
            if (boards[i] == null || boards[i].getShips() == null) {
                amount[i] = 0;
                break;
            }
            ships = boards[i].getShips();

            for (int j = 0; j < ships.length; j++) {
                try {
                    if (!ships[j].sankAlready()) {
                        amount[i]++;
                    }
                } catch (NullPointerException e) {
                }
            }
        }
        stats.setScore(amount[0], amount[1], turns);
    }

    /**
     * Vaihtaa lautojen nappien taustavärin oletusväriksi
     *
     * @param size lautojen sivun pituus
     */
    public void clear(int size) {                    //olettaa että laudat samankokoisia
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[j][i].setBackground(buttonDefaultColor);
                squares[j][i].setText("");
                opponentSquares[j][i].setBackground(buttonDefaultColor);
                opponentSquares[j][i].setText("");
            }
        }
    }

    /**
     * Vaihtaa yhden pelaajan laudan napin taustavärin siniseksi
     *
     * @param boards boards[0] on pelaajan lauta
     * @param i napin y -koordinaatti
     * @param j napin x -koordinaatti
     */
    public void setShipSquaresBlue(Board[] boards, int i, int j) {
        if (boards[0].getSquares()[i][j].isShip()) {       //jos ollaan käsittelemässä pelaajan lautaa vaihdetaan sen laudan buttonien väriä
            squares[j][i].setBackground(Color.blue);
        }
    }

    /**
     * Vaihtaa yhden pelaajan ja vastustajan laudan napin taustavärin 
     * harmaaksi
     * @param boards boards[0] pelaajan lauta, boards[1] vastustajan lauta
     * @param i napin y-koordinaatti
     * @param j napin x-koordinaatti
     */
    public void setShotShipSquaresGray(Board[] laudat, int i, int j) {
        for (int k = 0; k < 2; k++) {
            if (laudat[k].getSquares()[i][j].isShot() && laudat[k].getSquares()[i][j].isShip()) {
                if (k == 0) {
                    squares[j][i].setBackground(Color.gray);
                } else {
                    opponentSquares[j][i].setBackground(Color.gray);
                }
            }
        }
    }

    public void setSankShipsBlack(Board[] laudat) {
        for (int k = 0; k < 2; k++) {
            Ship[] laivat = laudat[k].getShips();
            for (int l = 0; l < laivat.length; l++) {
                if (laivat[l] == null) {
                    break;
                }
                if (laivat[l].sankAlready()) {
                    int[] xCoords = laivat[l].getxCoords();
                    int[] yCoords = laivat[l].getyCoords();
                    for (int m = 0; m < xCoords.length; m++) {
                        if (k == 0) {
                            squares[yCoords[m]][xCoords[m]].setBackground(Color.black);
                        } else {
                            opponentSquares[yCoords[m]][xCoords[m]].setBackground(Color.black);
                        }
                    }
                }
            }
        }
    }

    public void setShotSquaresX(Board[] laudat, int i, int j) {
        for (int k = 0; k < 2; k++) {
            if (laudat[k].getSquares()[i][j].isShot()) {
                if (k == 0) {
                    squares[j][i].setFont(newButtonFont);
                    squares[j][i].setText("X");
                } else {
                    opponentSquares[j][i].setFont(newButtonFont);
                    opponentSquares[j][i].setText("X");
                }
            }
        }
    }
}