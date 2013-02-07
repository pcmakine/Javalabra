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
    JPanel controls;
    JPanel opponentSide;
    Lauta peliLauta;
    Pelaaja pelaaja;
    Object[] possibilities = {"Horizontal ship", "Vertical ship"};
    Suunta suunta;
    JButton[][] buttons;
    JButton[][] opponentButtons;
    JLabel[] vertLabels;
    JLabel[] horLabels;
    Color buttonDefaultColor;

    boolean gameStarted;
    
    public Grafiikka(Lauta pelilauta, Pelaaja pelaaja) {
        this.peliLauta = pelilauta;
        this.pelaaja = pelaaja;
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
        if (!peliLauta.onkoLaudalla(alku, suunta, pituus)) {
            presentWarning("Antamasi laiva menisi laudan ulkopuolelle. \n "
                    + "Ole hyvä ja anna uudet arvot");
        }
        if (!peliLauta.vieressaEiLaivaa(alku, suunta, pituus)) {
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

    public void addRandomShips() {
        if (gameStarted) {
            presentWarning("Peli on jo aloitettu! Et voi enää lisätä laivoja. Klikkaa ruutua vastustajan laudalta ampuaksesi.");
        }
        else{
        int laivoja = peliLauta.haeErimittaisiaLaivojaMax().length;
        if (continueWhere()[1] != laivoja && continueWhere()[0] != peliLauta.haeErimittaisiaLaivojaMax()[laivoja - 1]) {
            Lauta uusiLauta = new Lauta(peliLauta.haeKoko(), peliLauta.haeErimittaisiaLaivoja().length);
            peliLauta = uusiLauta;
            Pelaaja uusiPelaaja = new Pelaaja(peliLauta);
            pelaaja = uusiPelaaja;
        }
        pelaaja.arvoLaivat();
        clearShipDrawings();
        drawShips();
        }
    }

    public int[] continueWhere() {
        int availableShips = -1;
        int shipLength = -1;
        for (int i = 4; i > 0; i--) {
            if (peliLauta.laivojaMahtuuViela(i) > 0) {
                availableShips = peliLauta.laivojaMahtuuViela(i);
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
                    for (int j = peliLauta.haeErimittaisiaLaivoja()[continueWhere()[1] - 1]; j < (5 - i); j++) {       //kuinka monta tietyn mittaista laivaa
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
                if (peliLauta.haeErimittaisiaLaivoja()[0] == 0) {
                    askDirection("");
                }
            }
            alku[0] = askInput("Anna alkukoordinaatti x") - 1;
            alku[1] = askInput("Anna alkukoordinaatti y") - 1;
            checkErrors(alku, suunta, pituus);
        } while (!peliLauta.onkoLaudalla(alku, suunta, pituus) || !peliLauta.vieressaEiLaivaa(alku, suunta, pituus));
        peliLauta.teeLaiva(alku, suunta, pituus);
        drawShips();
    }
    
    public void startGame(){
        gameStarted = true;
    }

    public void drawShips() {
        buttonDefaultColor = buttons[0][0].getBackground();
        for (int i = 0; i < peliLauta.haeKoko(); i++) {
            for (int j = 0; j < peliLauta.haeKoko(); j++) {
                if (peliLauta.haeRuudut()[i][j].onkoLaiva()) {
                    buttons[j][i].setBackground(Color.blue);
                }
            }
        }
    }

    public void clearShipDrawings() {
        for (int i = 0; i < peliLauta.haeKoko(); i++) {
            for (int j = 0; j < peliLauta.haeKoko(); j++) {
                if (!peliLauta.haeRuudut()[i][j].onkoLaiva()) {
                    buttons[j][i].setBackground(buttonDefaultColor);
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
        controls = new JPanel();
        opponentSide = new JPanel();
        buttons = new JButton[10][10];
        opponentButtons = new JButton[10][10];
        mySide.setLayout(new BoxLayout(mySide, BoxLayout.PAGE_AXIS));
        opponentSide.setLayout(new BoxLayout(opponentSide, BoxLayout.PAGE_AXIS));


        //Making the control buttons
        JButton addShips = new JButton("Add ships!");

        ActionListener addShipsListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                addShips();
            }
        };
        addShips.addActionListener(addShipsListener);

        JButton randomShips = new JButton("Add ships on the board randomly!");
        ActionListener randomShipsListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                addRandomShips();
            }
        };
        randomShips.addActionListener(randomShipsListener);

        JButton startGame = new JButton("Start game!");
        ActionListener startGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                startGame();
            }
        };
        startGame.addActionListener(startGameListener);




        JLabel me = new JLabel("My board");
        JLabel opponent = new JLabel("Opponent's board");

        lauta.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0)));
        lauta.setLayout(new GridLayout(11, 11, 0, 0));

        opponentBoard.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0)));
        opponentBoard.setLayout(new GridLayout(11, 11, 0, 0));
        makeAlphabets();
        makeButtons();


        ikkuna.setTitle("Laivanupotus");

        ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ikkuna.setContentPane(window);
        controls.add(addShips);
        controls.add(randomShips);
        controls.add(startGame);

        Dimension dimension = new Dimension(300, 350);
        Dimension dimension2 = new Dimension(400, 400);

        opponentSide.setPreferredSize(dimension2);
        lauta.setPreferredSize(dimension);
        opponentBoard.setPreferredSize(dimension);

        mySide.add(me);
        mySide.add(lauta);
        mySide.add(controls);
        
        opponentSide.add(opponent);
        opponentSide.add(opponentBoard);
        window.add(mySide, BorderLayout.WEST);
        window.add(opponentSide, BorderLayout.EAST);

        ikkuna.pack();
        ikkuna.setLocationRelativeTo(null);
        ikkuna.setVisible(true);
    }
}