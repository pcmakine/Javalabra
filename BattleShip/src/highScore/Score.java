/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highScore;

/**
 * Muokattu koodista: http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
 * @author pcmakine
 */
import java.io.Serializable;

public class Score implements Serializable {

    private int score;
    private String name;

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public Score(String name, int score) {
        this.score = score;
        this.name = name;
    }
    

}
