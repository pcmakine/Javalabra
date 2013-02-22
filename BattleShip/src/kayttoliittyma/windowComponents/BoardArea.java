/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents;


import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sovelluslogiikka.Controller;
import kayttoliittyma.Draw;
/**
 * Mallintaa aluetta jossa on pelilauta ja pelilaudan yläpuolella label
 * joka kertoo kenen lauta on kyseessä. Laajentaa jpanelia
 * @author pcmakine
 */
public class BoardArea extends JPanel {

    /**
     * Label joka kertoo kenen lauta on labelin alapuolella
     */
    private JLabel boardLabel;
    
    /**
     * ButtonBoard tyyppinen olio. Käytännössä pelilauta joka koostuu buttoneista
     */
    private ButtonBoard board;
    
    /**
     * Pelaajatyyppi, kertoo ollaanko tekemässä pelaajan vai vastustajan lautaa
     */
    private String playerType;
    
    
    /**
     * Tekee uuden boardarea olion ja kutsuu metodeita jotka tekevät 
     * boardarean osat ja lisäävät ne boardareaan
     * @param controller controller tyyppinen olio joka välitetään boardarean 
     * osille
     * @param playerType pelaajatyyppi, joko pelaaja tai vastustaja
     * @param drawer piirto-olio, joka välitetään alueen osille.
     */
    public BoardArea(Controller controller, String playerType, Draw drawer) {
        this.playerType = playerType;
        makeLayout();
        createComponents(drawer, controller);
        addComponentsTogether();
    }

    /**
     * Tekee alueen osat
     * @param drawer piirto-olio, välitetään tehtävälle buttonboard oliolle
     * @param controller controller-olio, välitetään buttonboard oliolle
     */
    private void createComponents(Draw drawer, Controller controller) {
        if (playerType.equals("player")) {
            boardLabel = new JLabel("Oma lauta");
        }
        else{
            boardLabel = new JLabel("Vastustajan lauta");
        }           
           board = new ButtonBoard(controller, playerType, drawer);
    }

    /**
     * Lisää tehdyt componentit alueeseen
     */
    private void addComponentsTogether() {
        this.add(boardLabel);
        this.add(board);
    }

    /**
     * Tekee layouting sekä asettaa koon
     */
    private void makeLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setPreferredSize(new Dimension(500, 400));
    }
}