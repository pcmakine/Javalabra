/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highScore;

/**
 * Luokka arvojen lisäämiseen ja niiden hakemiseen high score tiedostosta
 * Muokattu koodista
 * http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
 *
 * @author pcmakine
 */
import java.io.*;
import java.util.*;

public class HighScore {

    // An arraylist of the type "score" we will use to work with the scores inside the class
    private ArrayList<Score> scores;
    // The name of the file where the highscores will be saved
    private static final String HIGHSCORE_FILE = "scores.dat";
    //Initialising an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public HighScore() {
        //initialising the scores-arraylist
        scores = new ArrayList<Score>();
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
    }

    public void reset() {
        loadScoreFile();
        scores.removeAll(scores);
        updateScoreFile();
    }

    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
    }

    public void addScore(String name, int score) {
        loadScoreFile();
        boolean topten = checkIfScoreTopTen(score);
        scores.add(new Score(name, score));
        updateScoreFile();
    }

    public boolean checkIfScoreTopTen(int score) {
        if (getScores().size() >= 10) {
            if (score < getScores().get(10).getScore()) {       //rmb to check that the arraylist get 10 is not null!!!
                return true;
            } else {
                return false;
            }
        } else {
            return true;                                    //alle kymmenen highscorea
        }

    }

    public String getHighscoreString() {
        String highscoreString = "";
        int max = 10;

        ArrayList<Score> scores;
        scores = getScores();

        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highscoreString += (i + 1) + ". \t\t" + scores.get(i).getName() + "  \t\t" + scores.get(i).getScore() + "\n";
            i++;
        }
        return highscoreString;
    }

    public void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Laad] CNF Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Laad] IO Error: " + e.getMessage());
            }
        }
    }

    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }
}
