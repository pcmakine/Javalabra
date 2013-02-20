/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import sovelluslogiikka.Controller;
import javax.swing.JPanel;
import kayttoliittyma.Draw;

/**
 *
 * @author pcmakine
 */
public class ButtonBoard extends JPanel {

    private Controller controller;
    private JButton[][] squares;

    public ButtonBoard(Controller controller, String playerType, Draw drawer) {
        this.controller = controller;
        squares = new JButton[10][10];
        makeLayout();
        createAndAddComponents(playerType);
        setUpDraw(playerType, drawer);
    }

    private void setUpDraw(String playerType, Draw drawer) {
        if (playerType.equals("player")) {
            drawer.setPlayerButtons(squares);
        }
        else{
            drawer.setOpponentButtons(squares);
        }
    }

    private void createAndAddComponents(String playerType) {
        makeAlphabets();
        makeButtons(playerType);
    }

    private void makeLayout() {
        this.setLayout(new GridLayout(11, 11, 0, 0));
        this.setPreferredSize(new Dimension(300, 350));
         this.setMinimumSize(new Dimension(300, 350));
    }

    public void makeAlphabets() {
        for (int i = 0; i < 11; i++) {
            String[] alphabets = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
            if (i == 0) {
                JLabel coords = new JLabel("", JLabel.CENTER);
                this.add(coords);
            } else {
                JLabel coords = new JLabel(alphabets[i - 1], JLabel.CENTER);
                this.add(coords);
            }
        }
    }

    public JLabel makeLabel(int i) {
        return new JLabel(Integer.toString(i), JLabel.CENTER);
    }

    private void makeButtons(String playerType) {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (j == 1) {
                    this.add(makeLabel(i));
                }
                JButton button = new JButton();
                button.setActionCommand("" + (i - 1) + "" + (j - 1));
                squares[i - 1][j - 1] = button;
                if (playerType.equals("player")) {
                    makeAddShipListeners(button);
                } else {
                    makeShootListeners(button);
                }
                this.add(button);
            }
        }
    }

    private void makeAddShipListeners(JButton button) {
        ActionListener addShip = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String target = actionEvent.getActionCommand();
                int targetX = Character.getNumericValue(target.charAt(1));
                int targetY = Character.getNumericValue(target.charAt(0));
                controller.addShip(targetX, targetY);
            }
        };
        button.addActionListener(addShip);
    }

    public void makeShootListeners(JButton opponentButton) {
        ActionListener shootListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String target = actionEvent.getActionCommand();
                int targetX = Character.getNumericValue(target.charAt(1));
                int targetY = Character.getNumericValue(target.charAt(0));
                controller.shoot(targetX, targetY);
            }
        };
        opponentButton.addActionListener(shootListener);
    }
}