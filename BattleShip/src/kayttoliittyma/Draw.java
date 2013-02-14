/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.awt.Color;
import sovelluslogiikka.*;

/**
 *
 * @author pcmakine
 */
import javax.swing.JButton;

public class Draw {

    private Color buttonDefaultColor;
    private JButton[][] squares;
    private JButton[][] opponentSquares;

    public Draw(JButton[][] squares, JButton[][] opponentSquares) {
        this.squares = squares;
        this.opponentSquares = opponentSquares;
        buttonDefaultColor = squares[0][0].getBackground();
    }

        public void drawOpponentBoard(Lauta lauta) {                //olettaa että laudat samankokoisia
        clearOpponent(lauta);                                               //käydään kummatkin laudat läpi       
        for (int i = 0; i < lauta.haeKoko(); i++) {             // laudat[0] pelaajan lauta ja laudat[1] vastustajan lauta
            for (int j = 0; j < lauta.haeKoko(); j++) {
                if (lauta.haeRuudut()[i][j].onkoAmmuttu()) {
                    opponentSquares[j][i].setBackground(Color.red);
                }
                if (lauta.haeRuudut()[i][j].onkoLaiva()) {
                    if (lauta.haeRuudut()[i][j].onkoAmmuttu()) {
                        opponentSquares[j][i].setBackground(Color.gray);
                    } else {
                        opponentSquares[j][i].setBackground(Color.blue);
                    }
                }
            }
        }
    }
    
    public void drawBoard(Lauta[] laudat) {                //olettaa että laudat samankokoisia
        clear(laudat[0]);                                               //käydään kummatkin laudat läpi       
        for (int i = 0; i < laudat[0].haeKoko(); i++) {             // laudat[0] pelaajan lauta ja laudat[1] vastustajan lauta
            for (int j = 0; j < laudat[0].haeKoko(); j++) {
                if (laudat[0].haeRuudut()[i][j].onkoLaiva()) {       //jos ollaan käsittelemässä pelaajan lautaa vaihdetaan sen laudan buttonien väriä
                    squares[j][i].setBackground(Color.blue);
                }
                if (laudat[0].haeRuudut()[i][j].onkoAmmuttu()) {
                    squares[j][i].setBackground(Color.gray);
                }
                if (laudat[1].haeRuudut()[i][j].onkoAmmuttu()) {
                    opponentSquares[j][i].setBackground(Color.red);
                }
                if (laudat[1].haeRuudut()[i][j].onkoLaiva()) {
                    if (laudat[1].haeRuudut()[i][j].onkoAmmuttu()) {
                        opponentSquares[j][i].setBackground(Color.gray);
                    }
                }
            }
        }
    }

    public void clear(Lauta lauta) {                    //olettaa että laudat samankokoisia
        for (int i = 0; i < lauta.haeKoko(); i++) {
            for (int j = 0; j < lauta.haeKoko(); j++) {
                squares[j][i].setBackground(buttonDefaultColor);
                opponentSquares[j][i].setBackground(buttonDefaultColor);
            }
        }
    }
    
        public void clearOpponent(Lauta lauta) {                    //olettaa että laudat samankokoisia
        for (int i = 0; i < lauta.haeKoko(); i++) {
            for (int j = 0; j < lauta.haeKoko(); j++) {
                opponentSquares[j][i].setBackground(buttonDefaultColor);
            }
        }
    }
}


