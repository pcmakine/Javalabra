/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents.sidePaneComponents;

import java.awt.Color;
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
 * Mallintaa aluetta jossa olevilla napeilla käyttäjä voi valita tehtävän
 * laivan suunnan
 * @author pcmakine
 */
public class ShipDirectionArea extends JPanel {

    /**
     * Teksti joka tulee suunnanvalintaradiobuttonien yläpuolelle
     */
    private JLabel shipDirectionLabel;
    /**
     * Ryhmä suunnanvalintaradiobuttoneille
     */
    private ButtonGroup shipDirectionGroup;
    /**
     * Controller luokan ilmentymä joka toimii linkkinä käyt- töliittymän ja
     * sovelluslogiikan välillä.
     */
    private Controller controller;
    /**
     * Radiobutton josta valitaan laivan suunnaksi vaakataso
     */
    private JRadioButton horizontal;
    /**
     * Radiobutton josta valitaan laivan suunnaksi pystysuunta
     */
    private JRadioButton vertical;

    /**
     * Tekee uuden suunnanvalinta-alue olion ja kutsuu metodeita jotka tekevät
     * alueen osat ja jotka lisäävät osat alueelle.
     *
     * @param controller Controller luokan ilmentymä joka toimii linkkinä käyt-
     * töliittymän ja sovelluslogiikan välillä.
     */
    public ShipDirectionArea(Controller controller) {
        this.controller = controller;
        shipDirectionGroup = new ButtonGroup();
        makeLayout();
        createComponents();
        addComponentsTogether();
    }

    /**
     * Tekee uuden labelin joka kertoo käyttäjälle mikä radiobuttonien tarkoitus
     * on. Lisäksi kutsuu makeDirectionRadioButtons metodia joka tekee itse
     * napit.
     */
    public void createComponents() {
        shipDirectionLabel = new JLabel("<html><body>  Haluatko tehdä vaaka <br> vai pystylaivan?  </body></html>", JLabel.CENTER);
        makeDirectionRadioButtons();
    }

    /**
     * Tekee napit joista laivan suunta valitaan ja kutsuu metodia
     * makedirectionlisteners joka tekee napeille kuuntelijat
     */
    public void makeDirectionRadioButtons() {
        horizontal = new JRadioButton("Vaaka");
        vertical = new JRadioButton("Pysty");
        horizontal.setActionCommand("Horizontal");
        vertical.setActionCommand("Vertical");
        shipDirectionGroup.add(horizontal);
        shipDirectionGroup.add(vertical);
        makeDirectionListeners(horizontal);
        makeDirectionListeners(vertical);
    }

    /**
     * Tekee suunnanvalintanapeille kuuntelijat
     *
     * @param radioButton Nappi jolle kuuntelija tehdään
     */
    public void makeDirectionListeners(JRadioButton radioButton) {
        ActionListener directionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String direction = actionEvent.getActionCommand();
                controller.setDirection(direction);
            }
        };
        radioButton.addActionListener(directionListener);
    }

    /**
     * Lisää napit ja labelin suunnanvalinta-alueeseen
     */
    private void addComponentsTogether() {
        this.add(shipDirectionLabel);
        this.add(horizontal);
        this.add(vertical);
    }

    /**
     * Tekee suunnanvalinta-alueelle layoutin sekä mustan reunuksen
     */
    private void makeLayout() {
        this.setLayout(new GridLayout(0, 1));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
}