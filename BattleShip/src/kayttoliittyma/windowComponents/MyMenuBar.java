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
 *
 * @author pcmakine
 */
public class MyMenuBar extends JMenuBar {

    private JMenu menu;
    private Controller controller;
    private JMenuItem highscore;
    private JMenuItem newGame;
    private JMenuItem resetHighscore;

    public MyMenuBar(Controller controller) {
        this.controller = controller;
        createComponents();
        addComponentsTogether();
    }

    public void createComponents() {
        this.menu = new JMenu("Menu");
        this.highscore = new JMenuItem("Pistetaulukko");
        this.newGame = new JMenuItem("Uusi peli");
        this.resetHighscore = new JMenuItem("Tyhjennä tuloslista");
        makeHighscoreListener();
        makeNewGameListener();
        makeResetHighscoreListener();

    }
    
        private void makeResetHighscoreListener() {
        ActionListener resetHighscoreListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.resetHighscore();
            }
        };
        resetHighscore.addActionListener(resetHighscoreListener);
    }


    private void makeHighscoreListener() {
        ActionListener highscoreListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.showHighScore("Parhaat tulokset");
            }
        };
        highscore.addActionListener(highscoreListener);
    }

    private void makeNewGameListener() {
        ActionListener newGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.newGame();
            }
        };
        newGame.addActionListener(newGameListener);
    }

    public void addComponentsTogether() {
        menu.add(highscore);
        menu.add(newGame);
        menu.add(resetHighscore);
        this.add(menu);
    }
}
