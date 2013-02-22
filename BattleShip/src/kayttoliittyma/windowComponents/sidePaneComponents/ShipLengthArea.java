/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents.sidePaneComponents;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import sovelluslogiikka.Controller;

/**
 * Mallintaa aluetta jossa olevilla napeilla käyttäjä voi valita tehtävän laivan
 * pituuden
 *
 * @author pcmakine
 */
public class ShipLengthArea extends JPanel {

    /**
     * Teksti joka tulee suunnanvalintaradiobuttonien yläpuolelle
     */
    private JLabel shipLengthLabel;
    
    /**
     * Taulukko radiobuttoneista, joilla käyttäjä voi valita laivan pituuden
     */
    private JRadioButton[] shipLengths;
    
    /**
     * Ryhmä johon pituudenvalintanapit laitetaan
     */
    private ButtonGroup shipLengthGroup;
    
    /**
     * Controller luokan ilmentymä joka toimii linkkinä käyt- töliittymän ja
     * sovelluslogiikan välillä.
     */
    private Controller controller;

   
     /**
     * Tekee uuden pituudenvalinta-alue olion ja kutsuu metodeita jotka 
     * tekevät alueen osat ja jotka lisäävät osat alueelle.
     *
     * @param controller Controller luokan ilmentymä joka toimii linkkinä käyt-
     * töliittymän ja sovelluslogiikan välillä.
     */
    public ShipLengthArea(Controller controller) {
        this.controller = controller;
        shipLengthGroup = new ButtonGroup();
        shipLengths = new JRadioButton[4];
        makeLayout();
        createComponents();
        addComponentsTogether();
    }

    /**
     * Metodi joka tekee pituudenvalinta olion osat, labelin ja radiobuttonit
     * (radiobuttonien tekemiseen käytetään makeshiplengthradiobuttons metodia)
     */
    public void createComponents() {
        shipLengthLabel = new JLabel("<html><body>Laivan pituus </body></html>", JLabel.CENTER);
        makeShipLengthRadioButtons();
    }

    /**
     * Lisää kaikki pituudenvalinta-alueen osat alueeseen.
     */
    private void addComponentsTogether() {
        this.add(shipLengthLabel);
        this.add(new JLabel());
        for (int i = 0; i < 4; i++) {
            this.add(shipLengths[i]);
        }
    }

    /**
     * Tekee pituudenvalinta -oliolle layoutin ja musta reunuksen
     */
    private void makeLayout() {
        this.setLayout((new GridLayout(0, 2)));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * Tekee radiobuttonit joista käyttäjä voi valita laivan pituuden
     */
    public void makeShipLengthRadioButtons() {
        for (int i = 0; i < 4; i++) {
            JRadioButton length = new JRadioButton("" + (i + 1));
            shipLengths[i] = length;
            length.setActionCommand("" + (i + 1));
            shipLengthGroup.add(length);
            makeShipLengthListeners(length);
        }
    }

    /**
     * Tekee pituuden valinta radiobuttoneille kuuntelijat
     * @param radioButton Buttoni jolle kuuntelija tehdään
     */
    public void makeShipLengthListeners(JRadioButton radioButton) {
        ActionListener shipLengthListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int length = Character.getNumericValue(actionEvent.getActionCommand().charAt(0));
                controller.setLength(length);
            }
        };
        radioButton.addActionListener(shipLengthListener);
    }
}