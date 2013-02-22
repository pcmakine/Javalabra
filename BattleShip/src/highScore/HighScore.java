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

    /**
     * Score tyyppinen arraylist, jota käytetään pistetietojen tilapäiseen va-
     * rastointiin
     */
    private ArrayList<Score> scores;
    
    /**
     * Tiedoston nimi johon huipputulokset tallennetaan
     */
    private static final String HIGHSCORE_FILE = "scores.dat";
    
    /**
     * Alustettu outputStreami tiedostonkäsittelyä varten
     */
    ObjectOutputStream outputStream = null;
    
    /**
     * Alustettu inputStreami tiedostonkäsittelyä varten
     */
    ObjectInputStream inputStream = null;

    /**
     * Tekee highscore olion ja alustaa scores arraylistin
     */
    public HighScore() {
        scores = new ArrayList<Score>();
    }

    /**
     * Tekee uuden Comparator luokan ilmentymän ja järjestää pisteet käyttäen
     * Collections luokan sort metodia
     */
    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
    }

    /**
     * Poistaa kaikki huippupistetiedot
     */
    public void reset() {
        loadScoreFile();
        scores.removeAll(scores);
        updateScoreFile();
    }

    /**
     * Palauttaa arraylistin huippupisteistä
     * @return arraylist scores, johon pisteet on ensin luettu tiedostosta
     */
    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
    }

    /**
     * Lisää parametrina annetun nimen ja tuloksen huippupisteet listaan.
     * 
     * @param name Pelaajan nimi
     * @param score Pelaajan pisteet, eli käytettyjen siirtojen määrä. Mitä väh-
     * emmän sitä parempi
     */
    public void addScore(String name, int score) {
        loadScoreFile();
        scores.add(new Score(name, score));
        updateScoreFile();
    }

    /**
     * Tarkastaa onko parametrinä annettu tulos kymmenen parhaan joukossa.
     * @param score Tulos jonka kuulumine top10 halutaan tarkistaa.
     * @return palauttaa true jos tulos kuuluu kymmenen parhaan joukkoon, muuten
     * false
     */
    public boolean checkIfScoreTopTen(int score) {
        if (getScores() != null && getScores().size() >= 10) {
            if (score < getScores().get(10).getScore()) {       //rmb to check that the arraylist get 10 is not null!!!
                return true;
            } else {
                return false;
            }
        } else {
            return true;                                    //alle kymmenen highscorea
        }
    }

    /**
     * Palauttaa merkkijonoesityksen 10 ensimmäisestä huipputuloksesta
     * @return String tyyppinen olio 10 ensimmäisestä huipputuloksesta
     */
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
            highscoreString += (i + 1) + ". \t\t" + scores.get(i).getName() + " \t\t" + scores.get(i).getScore() + "\n";
            i++;
        }
        return highscoreString;
    }

    /**
     * Lataa huipputulokset tiedostosta scores arraylistiin
     */
    public void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            //System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
           // System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
           // System.out.println("[Laad] CNF Error: " + e.getMessage());
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

    /**
     * Päivittää scores arraylistissä olevat tulokset huipputulostiedostoon
     */
    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            //System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            //System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
               // System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }
}