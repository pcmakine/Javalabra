package kayttoliittyma;

import sovelluslogiikka.Controller;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.*;
import kayttoliittyma.windowComponents.*;

/**
 * Graafisen käyttöliittymän ikkuna.
 */
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
    
    
    /**
     * Sivupaneeli, josta löytyvät valinnat laivojen pituuden ja orien-
     * taation valitsemiseen sekä tilannetietopaneeli.
     */
    private SidePane sidePane;
    
    /**
     * Olio joka hoitaa syötteiden kysymisen käyttäjältä sekä ilmoitusten
     * esittämisen käyttäjälle.
     */
    private Asker asker;
    
    /**
     * Pääikkunassa oleva menubar
     */
    private MyMenuBar menu;

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
        controller.initController(asker, drawer,sidePane.getStatsArea());

    }

    /**
     * Tekee pääikkunan komponentit ja lisää ne ikkunaan.
     */
    private void createAndAddComponents() {
        sidePane = new SidePane(controller);
        BoardArea mySide = new BoardArea(controller, "player", drawer);
        BoardArea opponentSide = new BoardArea(controller, "opponent", drawer);
        ControlButtonArea controlButtons = new ControlButtonArea(controller);
        menu = new MyMenuBar(controller);
        this.setJMenuBar(menu);
        mainPanel.add(sidePane, BorderLayout.WEST);
        mainPanel.add(mySide, BorderLayout.CENTER);
        mainPanel.add(opponentSide, BorderLayout.EAST);
        mainPanel.add(controlButtons, BorderLayout.SOUTH);
        drawer.initDrawValues(sidePane.getStatsArea());
    }

    /**
     * Pakkaa kaiken ikkunassa ja asettaa ikkunan näkyväksi
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