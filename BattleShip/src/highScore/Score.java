/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highScore;

/**
 * Muokattu koodista: http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
 * Mallintaa yhtä huippupistealkiota. Huippupisteeseen liittyy aina pelaajan
 * nimi ja pisteet. Pisteet ovat käytettyjä vuoroja, joten mitä pienemmät pisteet
 * sitä parempi.
 * @author pcmakine
 */
import java.io.Serializable;

public class Score implements Serializable {

    /**
     * Kertoo käytettyjen vuorojen määrän
     */
    private int score;
    
    /**
     * Huipputuloksen tehneen pelaajan nimi
     */
    private String name;

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    /**
     * Tekee uuden score tyyppisen huipputulos -olion
     * @param name Huipputuloksen tehneen pelaajan nii
     * @param score Käytettyjen vuorojen määrä
     */
    public Score(String name, int score) {
        this.score = score;
        this.name = name;
    }
    

}