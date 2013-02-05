package kayttoliittyma;

/**
 *
 * @author pcmakine
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.*;
import sovelluslogiikka.*;

public class Grafiikka {

    JFrame ikkuna;
    JPanel window;
    JPanel lauta;
    JPanel opponentBoard;
    JPanel mySide;
    JPanel opponentSide;
    Lauta pelilauta;
    Object[] possibilities = {"Horizontal ship", "Vertical ship"};
    Suunta suunta;
    JButton[][] buttons;
    JButton[][] opponentButtons;
    JLabel[] vertLabels;
    JLabel[] horLabels;

    public Grafiikka(Lauta pelilauta) {
        this.pelilauta = pelilauta;
        initUI();
    }

    public int askInput(String message) {
        String dialog = "";
        int input = -1;
        do {
            try {
                dialog = (String) JOptionPane.showInputDialog(ikkuna, message, "Alkukoordinaatit", JOptionPane.PLAIN_MESSAGE);
                input = Integer.parseInt(dialog);
            } catch (NumberFormatException e) {
                continue;
            }
            if (input > 10 || input < 1) {
                presentWarning("Antamasi koordinaatti on laudan ulkopuolella."
                        + "\nOle hyvä ja anna uudet koordinaatit.");
            }
        } while (dialog.isEmpty() || input > 10 || input < 1);
        return input;
    }

    public String askDirection(String message) {
        String dialog = "";
        if (message.equals("")) {
            JOptionPane.showMessageDialog(ikkuna, "Enää yhden mittaiset laivat jäljellä, paina ok jatkaaksesi");
        } else {
            dialog = (String) JOptionPane.showInputDialog(ikkuna, message, null, JOptionPane.PLAIN_MESSAGE, null, possibilities,
                    "Horizontal ship");
        }
        return dialog;
    }

    public void checkErrors(int[] alku, Suunta suunta, int pituus) {
        if (!pelilauta.onkoLaudalla(alku, suunta, pituus)) {
            presentWarning("Antamasi laiva menisi laudan ulkopuolelle. \n "
                    + "Ole hyvä ja anna uudet arvot");
        }
        if (!pelilauta.vieressaEiLaivaa(alku, suunta, pituus)) {
            presentWarning("Antamasi laiva menisi olemassaolevan laivan viereen/päälle. \n"
                    + " Ole hyvä ja anna uudet arvot");
        }
    }

    public void presentWarning(String message) {
        JOptionPane.showMessageDialog(ikkuna,
                message,
                "Inane warning",
                JOptionPane.WARNING_MESSAGE);
    }

    public int[] continueWhere() {
        int availableShips = -1;
        int shipLength = -1;
        for (int i = 4; i > 0; i--) {
            if (pelilauta.laivojaMahtuuViela(i) > 0) {
                availableShips = pelilauta.laivojaMahtuuViela(i);
                shipLength = i;
                return new int[]{availableShips, shipLength};
            }
        }
        return new int[]{availableShips, shipLength};
    }

    public void addShips() {
        int[] mark = continueWhere();
        if (mark[0] != -1) {
            try {
                for (int i = mark[1]; i >= 1; i--) {                  //laivoja yhteensä
                    int pituus = i;
                    for (int j = pelilauta.haeErimittaisiaLaivoja()[continueWhere()[1] - 1]; j < (5 - i); j++) {       //kuinka monta tietyn mittaista laivaa
                        createShips(pituus);
                        if (continueWhere()[0] == -1) {
                            break;
                        }
                    }
                }
            } catch (NullPointerException e) {
            }
        } else {
            presentWarning("Sinulla on jo maksimimäärä laivoja. Aika aloittaa peli!"
                    + " Aloita valitsemalla vastustajan laudalta ruutu johon haluata ampua");
        }
    }

    public void createShips(int pituus) {
        int[] alku = new int[2];
        do {
            if (pituus != 1) {
                if (askDirection("Olet luomassa laivaa jonka pituus on " + pituus + " ruutua."
                        + "\nHaluatko tehdä pystysuuntaisen vai vaakasuuntaisen laivan?").equals("Horizontal ship")) {
                    suunta = Suunta.OIKEALLE;
                } else {
                    suunta = Suunta.ALAS;
                }
            } else {
                if (pelilauta.haeErimittaisiaLaivoja()[0] == 0) {
                    askDirection("");
                }
            }
            alku[0] = askInput("Anna alkukoordinaatti x") - 1;
            alku[1] = askInput("Anna alkukoordinaatti y") - 1;
            checkErrors(alku, suunta, pituus);
        } while (!pelilauta.onkoLaudalla(alku, suunta, pituus) || !pelilauta.vieressaEiLaivaa(alku, suunta, pituus));
        pelilauta.teeLaiva(alku, suunta, pituus);
        drawShips();
       // System.out.println(pelilauta);
    }

    public void drawShips() {
        for (int i = 0; i < pelilauta.haeKoko(); i++) {
            for (int j = 0; j < pelilauta.haeKoko(); j++) {
                if (pelilauta.haeRuudut()[i][j].onkoLaiva()) {
                    buttons[j][i].setBackground(Color.blue);
                }
            }
        }
    }

    public void makeAlphabets() {
        for (int i = 0; i < 11; i++) {
            String[] alphabets = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
            if (i == 0) {
                JLabel coords = new JLabel("Coords", JLabel.CENTER);
                JLabel coords1 = new JLabel("Coords", JLabel.CENTER);
                lauta.add(coords);
                opponentBoard.add(coords1);
            } else {
                JLabel coords = new JLabel(alphabets[i - 1], JLabel.CENTER);
                JLabel coords1 = new JLabel(alphabets[i - 1], JLabel.CENTER);
                lauta.add(coords);
                opponentBoard.add(coords1);
            }
        }
    }

    public void makeButtons() {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (j == 1) {
                    JLabel coords = new JLabel(Integer.toString(i), JLabel.CENTER);
                    JLabel coords1 = new JLabel(Integer.toString(i), JLabel.CENTER);
                    lauta.add(coords);
                    opponentBoard.add(coords1);
                }
                JButton button = new JButton();
                JButton button1 = new JButton();
                button.setFocusable(false);
                buttons[i - 1][j - 1] = button;
                opponentButtons[i - 1][j - 1] = button1;
                lauta.add(button);
                opponentBoard.add(button1);
            }
        }
    }

    public final void initUI() {

        ikkuna = new JFrame("Laivanupotus");
        window = new JPanel(new BorderLayout());
        lauta = new JPanel();
        opponentBoard = new JPanel();
        mySide = new JPanel();
        opponentSide = new JPanel();
        buttons = new JButton[10][10];
        opponentButtons = new JButton[10][10];
        mySide.setLayout(new BoxLayout(mySide, BoxLayout.PAGE_AXIS));
        opponentSide.setLayout(new BoxLayout(opponentSide, BoxLayout.PAGE_AXIS));

        JButton addShips = new JButton("Add ships!");
        JLabel me = new JLabel("My board");
        JLabel opponent = new JLabel("Opponent's board");

        lauta.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0)));
        lauta.setLayout(new GridLayout(11, 11, 0, 0));

        opponentBoard.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0)));
        opponentBoard.setLayout(new GridLayout(11, 11, 0, 0));
        makeAlphabets();
        makeButtons();

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                addShips();
            }
        };

        addShips.addActionListener(actionListener);

        ikkuna.setTitle("Laivanupotus");

        ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 500);
        ikkuna.setSize(400, 500);

        ikkuna.setContentPane(window);

        mySide.add(me);
        mySide.add(lauta);
        mySide.add(addShips);

        opponentSide.add(opponent);
        opponentSide.add(opponentBoard);
        window.add(mySide, BorderLayout.WEST);
        window.add(opponentSide, BorderLayout.EAST);

        ikkuna.pack();
        ikkuna.setLocationRelativeTo(null);
        ikkuna.setVisible(true);
    }
}