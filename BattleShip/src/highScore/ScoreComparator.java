/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highScore;

/**
 * Comparator tyyppinen luokka, jota käytetään huipputulosten helppoon
 * järjestämiseen javan collections luokan avulla.
 * @author pcmakine
 */
import java.util.Comparator;


public class ScoreComparator implements Comparator<Score> {
    
    /**
     * Vertaa kahta tulosta keskenään. Kertoo kumpi tuloksista oli parempi
     * @param score1 Ensimmäinen vertailtavista tuloksista
     * @param score2 Toinen vertailtavista tuloksista
     * @return Palauttaa -1 jos score1 oli parempi, 1 jos score2 oli parempi ja
     * 0 jos molemmat olivat yhtä hyviä
     */
        public int compare(Score score1, Score score2) {

            int sc1 = score1.getScore();
            int sc2 = score2.getScore();

            if (sc1 < sc2){
                return -1;
            }else if (sc1 > sc2){
                return 1;
            }else{
                return 0;
            }
        }
}