/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import sovelluslogiikka.Controller;
/**
 * Luokka mallintaa pääikkunan aluetta joka on pelilautojen alapuolella.
 * Alueessa on napit joilla voidaan lisätä laivoja tai aloittaa peli. Lisäksi
 * toistaiseksi nappi jolla nähdään vastustajan laivat
 *
 * @author Pete
 */
public class ControlButtonArea extends JPanel {

    /**
     * Controller tyyppinen olio jonka metodeja kutsutaan kun nappeja painetaan
     */
    private Controller controller;
    /**
     * Nappi laivojen satunnaiseksi lisäämiseksi
     */
    private JButton randomShips;
    /**
     * Pelin aloittava nappi
     */
    private JButton startGame;
    /**
     * Nappi jota painamalla nähdään vastustajan laivat
     */
    private JButton drawOpponent;

    /**
     * Tekee uuden alueen valintanapeille sekä kutsuu metodeja jotka luovat
     * alueen komponentit ja lisäävät ne alueeseen.
     *
     * @param controller controller tyyppinen olio jonka metodeja kutsutaan
     * kun valintanappeja painetaan.
     */
    public ControlButtonArea(Controller controller) {
        this.controller = controller;
        createComponents();
        addComponentsTogether();
    }

    /**
     * Kutsuu metodeja jotka tekevät alueen eri napit ja niiden kuuntelijat
     */
    private void createComponents() {
        randomShipsButton();
        startGameButton();
        drawOpponentButton();
    }

    /**
     * Lisää napit alueeseen
     */
    private void addComponentsTogether() {
        this.add(randomShips);
        this.add(startGame);
        this.add(drawOpponent);
    }

    /**
     * Tekee napin jolla laivoja voidaan lisätä satunnaisesti ja sille kuuntelijan
     */
    private void randomShipsButton() {
        randomShips = new JButton("Lisää satunnaisesti!");
        ActionListener randomShipsListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addRandomShips();
            }
        };
        randomShips.addActionListener(randomShipsListener);
    }

    /**
     * Tekee pelin aloittamisnapin ja sille kuuntelijan
     */
    private void startGameButton() {
        startGame = new JButton("Aloita peli!");
        ActionListener startGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.startGame();
            }
        };
        startGame.addActionListener(startGameListener);
    }

    /**
     * Tekee napin jolla nähdään vastustajan laivat ja sille kuuntelijan
     */
    private void drawOpponentButton() {
        drawOpponent = new JButton("Näytä vastustajan laivat");
        ActionListener drawOpponentListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.showOpponentShips();
            }
        };
        drawOpponent.addActionListener(drawOpponentListener);
    }
}