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
 * Tekee pääikkunassa laudoissa näkyvät napit ja niille kuuntelijat. Laajetaa
 * jpanelia.
 *
 * @author pcmakine
 */
public class ButtonBoard extends JPanel {

    /**
     * Controller tyyppinen olio, jonka metodeja kuuntelijat kutsuvat käyttäjän
     * klikatessa nappeja
     */
    private Controller controller;
    /**
     * Kaksiulotteinen taulukko buttoneista jotka kuvaavat laudan ruutuja
     */
    private JButton[][] squares;

    /**
     * Tekee uuden buttonboard olion ja kutsuu metodeja jotka tekevät ja
     * lisäävät olion komponenti sekä metodia joka välittää tehdyt buttonit
     * piirto-oliolle joka sitten pelin kuluessa päivittää niiden taustaväriä ja
     * tekstiä.
     *
     * @param controller controller olio
     * @param playerType pelaajatyyppi, pelaaja tai vastustaja
     * @param drawer piirto-olio joka osaa vaihtaa buttonien väriä
     */
    public ButtonBoard(Controller controller, String playerType, Draw drawer) {
        this.controller = controller;
        squares = new JButton[10][10];
        makeLayout();
        makeAlphabets();
        makeButtons(playerType);
        setUpDraw(playerType, drawer);
    }

    /**
     * Välittää lautaan kuuluvat buttonit piirto-oliolle
     *
     * @param playerType pelaajatyyppi, pelaaja tai vastustaja
     * @param drawer piirto-olio jolle buttonit välitetään
     */
    private void setUpDraw(String playerType, Draw drawer) {
        if (playerType.equals("player")) {
            drawer.setPlayerButtons(squares);
        } else {
            drawer.setOpponentButtons(squares);
        }
    }

    /**
     * Tekee laudan layoutin ja asettaa koon
     */
    private void makeLayout() {
        this.setLayout(new GridLayout(11, 11, 0, 0));
        this.setPreferredSize(new Dimension(300, 350));
        this.setMinimumSize(new Dimension(300, 350));
    }

    /**
     * Tekee koordinaatistoon tarvittavat aakkoset ja lisää ne
     * oikeaan kohtaan
     */
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

    /**
     * Tekee laudan buttonit sekä jokaisen rivin eteen sen järjestysnumeron
     * @param playerType pelaajatyyppi, kertoo onko kyseessä pelaaja vai vas-
     * tustaja
     */
    private void makeButtons(String playerType) {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (j == 1) {
                    this.add(new JLabel(Integer.toString(i), JLabel.CENTER));
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

    /**
     * Tekee parametrinaan saamalleen buttonille kuuntelijan. Nappia painet-
     * taessa kutsutaan controller luokan addship metodia. Tätä metodia käyte-
     * tään siis vain silloin kun ollaan luomassa pelaajan lautaa.
     * @param button button jolle kuuntelija tehdään
     */
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

    /**
     * Tekee parametrinaan saamalleen buttonille kuuntelijan. Nappia painet-
     * taessa kutsutaan controller luokan shoot metodia. Tätä metodia käytetään
     * siis vain silloin kun ollaan luomassa vastustajan lautaa.
     * @param opponentButton button jolle kuuntelija tehdään
     */
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