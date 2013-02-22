/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents;

/**
 *
 * @author Pete
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import sovelluslogiikka.Controller;

public class ControlButtonPane extends JPanel {

    private Controller controller;
    private JButton randomShips;
    private JButton startGame;
//    private JButton newGame;
    private JButton drawOpponent;

    public ControlButtonPane(Controller controller) {
        this.controller = controller;
        createComponents();
        addComponentsTogether();
    }

    private void createComponents() {
        randomShipsButton();
        startGameButton();
//        newGameButton();
        drawOpponentButton();
    }

    private void addComponentsTogether() {
        this.add(randomShips);
        this.add(startGame);
//        this.add(newGame);
        this.add(drawOpponent);
    }

    private void randomShipsButton() {
        randomShips = new JButton("Lisää satunnaisesti!");
        ActionListener randomShipsListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addRandomShips();
            }
        };
        randomShips.addActionListener(randomShipsListener);
    }

    private void startGameButton() {
        startGame = new JButton("Aloita peli!");
        ActionListener startGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.startGame();
            }
        };
        startGame.addActionListener(startGameListener);
    }

//    private void newGameButton() {
//        newGame = new JButton("Uusi peli");
//        ActionListener newGameListener = new ActionListener() {
//            public void actionPerformed(ActionEvent actionEvent) {
//                controller.newGame();
//            }
//        };
//        newGame.addActionListener(newGameListener);
//    }

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