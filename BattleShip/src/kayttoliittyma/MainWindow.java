package kayttoliittyma;

/**
 *
 * Graafisen käyttöliittymän ikkuna
 */
import sovelluslogiikka.Controller;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.*;
import kayttoliittyma.windowComponents.*;

public class MainWindow extends JFrame {

    /**
     * Pääpaneeli, joka on koko ikkunan kokoinen
     */
    private JPanel mainPanel;
    /**
     * Kontroller luokka joka toimii linkkinä GUI:n ja sovelluslogiikan välillä
     */
    private Controller controller;
    /**
     * Piirto -olio joka piirtää laudan aina vuoron jälkeen. Käytännössä vaihtaa
     * pelilautana toimivien nappuloiden taustaväriä tai asettaa niihin tekstiä
     */
    private Draw drawer;
    private SidePane sidePane;
    private Asker asker;

    /**
     * Luo uuden pääikkunan ohjelmalle sekä antaa controller oliolle piirrustus
     * ja kyselijäoliot
     *
     * @param controller Controller luokan ilmentymä joka hoitaa
     * sovelluslogiikan metodien kutsumisen
     */
    public MainWindow(Controller controller) {
        this.controller = controller;
        this.setTitle("Laivanupotus");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1100, 570));
        this.setMinimumSize(new Dimension(800, 500));
        drawer = new Draw();
        this.asker = new Asker(this);
        initUI();
        controller.initController(asker, drawer,sidePane.getStatsPane());

    }

//    public void setPlayerNames() {
//        sidePane.getStatsPane().setPlayerName(asker.askInput("Anna nimesi"));
//        sidePane.getStatsPane().setOpponentName("Tietokone");
//    }

    /**
     * Tekee pääikkunan komponentit ja lisää ne ikkunaan.
     */
    private void createAndAddComponents() {
        sidePane = new SidePane(controller);
        BoardArea mySide = new BoardArea(controller, "player", drawer);
        BoardArea opponentSide = new BoardArea(controller, "opponent", drawer);
        ControlButtonPane controlButtons = new ControlButtonPane(controller);
        mainPanel.add(sidePane, BorderLayout.WEST);
        mainPanel.add(mySide, BorderLayout.CENTER);
        mainPanel.add(opponentSide, BorderLayout.EAST);
        mainPanel.add(controlButtons, BorderLayout.SOUTH);
        drawer.initDrawValues(sidePane.getStatsPane());

    }

    /**
     * Pakkaa kaiken ikkunassa yhteen ja alustaa piirrustusolion myöhempää
     * käyttöä varten
     */
    public final void initUI() {
        mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);
        createAndAddComponents();

        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}