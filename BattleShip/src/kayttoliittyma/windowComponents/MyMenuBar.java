/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import sovelluslogiikka.Controller;

/**
 * Mallintaa pääikkunassa olevaa menubaria
 *
 * @author pcmakine
 */
public class MyMenuBar extends JMenuBar {

    /**
     * Menu johon menun eri valinnat lisätään
     */
    private JMenu menu;
    /**
     * controller olio jonka metodeita kutsutaan kun menusta tehdään valintoja
     */
    private Controller controller;
    /**
     * valinta menussa josta nähdään huipputulokset
     */
    private JMenuItem highscore;
    /**
     * valinta menussa josta aloitetaan uusi peli
     */
    private JMenuItem newGame;
    /**
     * valinta menussa josta nollataan huipputulokset
     */
    private JMenuItem resetHighscore;

    /**
     * Tekee uuden mymenubar tyyppisen olion ja kutsuu metodeja jotka tekevät ja
     * lisäävät sen komponentit
     *
     * @param controller controller olio jonka metodeita kutsutaan kun menusta
     * tehdään valintoja
     */
    public MyMenuBar(Controller controller) {
        this.controller = controller;
        createComponents();
        addComponentsTogether();
    }

    /**
     * luo menubarin komponentit
     */
    private void createComponents() {
        this.menu = new JMenu("Menu");
        this.highscore = new JMenuItem("Huipputulokset");
        this.newGame = new JMenuItem("Uusi peli");
        this.resetHighscore = new JMenuItem("Nollaa huipputulokset");
        makeHighscoreListener();
        makeNewGameListener();
        makeResetHighscoreListener();

    }

    /**
     * Tekee kuuntelijan menun nollaa huipputulokset valinnalle
     */
    private void makeResetHighscoreListener() {
        ActionListener resetHighscoreListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.resetHighscore();
            }
        };
        resetHighscore.addActionListener(resetHighscoreListener);
    }

    /**
     * Tekee kuuntelijan menun huipputulokset valinnalle
     */
    private void makeHighscoreListener() {
        ActionListener highscoreListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.showHighScore("Parhaat tulokset");
            }
        };
        highscore.addActionListener(highscoreListener);
    }

    /**
     * Tekee kuuntelijan menun uusi peli valinnalle
     */
    private void makeNewGameListener() {
        ActionListener newGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.newGame();
            }
        };
        newGame.addActionListener(newGameListener);
    }

    /**
     * Lisää kaikki komponentit yhteen
     */
    public void addComponentsTogether() {
        menu.add(highscore);
        menu.add(newGame);
        menu.add(resetHighscore);
        this.add(menu);
    }
}