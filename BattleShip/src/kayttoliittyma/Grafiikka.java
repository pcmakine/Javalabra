package kayttoliittyma;

/**
 *
 * Graafinen käyttöliittymä
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.*;

public class Grafiikka {

    private JFrame ikkuna;
    private JPanel window;
    private JPanel lauta;
    private JPanel opponentBoard;
    private JPanel mySide;
    private JPanel controls;
    private JPanel opponentSide;
    private JButton[][] buttons;
    private JButton[][] opponentButtons;
    private JLabel[] vertLabels;
    private JLabel[] horLabels;
    private Controller controller;
    private Asker asker;
    private Draw drawer;

    public Grafiikka(Controller controller) {
        this.controller = controller;
        initUI();
        controller.initController(asker, drawer);
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

    public JLabel makeLabel(int i) {
        JLabel coords;
        return coords = new JLabel(Integer.toString(i), JLabel.CENTER);
    }

    public void makeListeners(JButton button1) {
        ActionListener shootListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String target = actionEvent.getActionCommand();
                int targetX = Character.getNumericValue(target.charAt(1));
                int targetY = Character.getNumericValue(target.charAt(0));
                controller.shoot(targetX, targetY);
            }
        };
        button1.addActionListener(shootListener);
    }

    public void makeButtons() {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (j == 1) {
                    lauta.add(makeLabel(i));
                    opponentBoard.add(makeLabel(i));
                }
                JButton button = new JButton();
                JButton button1 = new JButton();
                String command = "" + (i - 1) + "" + (j - 1);
                button1.setActionCommand(command);
                buttons[i - 1][j - 1] = button;
                opponentButtons[i - 1][j - 1] = button1;
                makeListeners(button1);
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
        controls = new JPanel();
        opponentSide = new JPanel();
        buttons = new JButton[10][10];
        opponentButtons = new JButton[10][10];
        mySide.setLayout(new BoxLayout(mySide, BoxLayout.PAGE_AXIS));
        opponentSide.setLayout(new BoxLayout(opponentSide, BoxLayout.PAGE_AXIS));

        asker = new Asker(ikkuna);

        //Making the control buttons
        JButton addShips = new JButton("Lisää laivoja!");

        ActionListener addShipsListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addShips();
            }
        };
        addShips.addActionListener(addShipsListener);

        JButton randomShips = new JButton("Lisää satunnaisesti!");
        ActionListener randomShipsListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addRandomShips();
            }
        };
        randomShips.addActionListener(randomShipsListener);

        JButton startGame = new JButton("Aloita peli!");
        ActionListener startGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.startGame();
            }
        };
        startGame.addActionListener(startGameListener);

        JButton newGame = new JButton("Uusi peli");
        ActionListener newGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.newGame();
            }
        };
        newGame.addActionListener(newGameListener);

        JButton drawOpponent = new JButton("Näytä vastustajan laivat");
        ActionListener drawOpponentListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                controller.showOpponentShips();
            }
        };
        drawOpponent.addActionListener(drawOpponentListener);

        JLabel me = new JLabel("Oma lauta");
        JLabel opponent = new JLabel("Vastustajan lauta");

        lauta.setLayout(new GridLayout(11, 11, 0, 0));

        opponentBoard.setLayout(new GridLayout(11, 11, 0, 0));
        makeAlphabets();
        makeButtons();

        drawer = new Draw(buttons, opponentButtons);

        ikkuna.setContentPane(window);
        controls.add(addShips);
        controls.add(randomShips);
        controls.add(startGame);
        controls.add(newGame);
        controls.add(drawOpponent);

        Dimension dimension = new Dimension(300, 350);
        Dimension dimension2 = new Dimension(400, 400);
        Dimension windowSize = new Dimension(800, 400);
        Dimension windowMinimum = new Dimension(800, 500);

        ikkuna.setTitle("Laivanupotus");
        ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ikkuna.setPreferredSize(windowSize);
        ikkuna.setMinimumSize(windowMinimum);

        mySide.setPreferredSize(dimension2);
        opponentSide.setPreferredSize(dimension2);
        lauta.setPreferredSize(dimension);
        opponentBoard.setPreferredSize(dimension);

        mySide.add(me);
        mySide.add(lauta);

        opponentSide.add(opponent);
        opponentSide.add(opponentBoard);
        window.add(mySide, BorderLayout.WEST);
        window.add(opponentSide, BorderLayout.CENTER);
        window.add(controls, BorderLayout.SOUTH);

        ikkuna.pack();
        ikkuna.setLocationRelativeTo(null);
        ikkuna.setVisible(true);
    }
}