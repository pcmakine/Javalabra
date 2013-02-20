/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents.sidePaneComponents;

/**
 *
 * @author pcmakine
 */
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

public class ShipLengthPane extends JPanel {

    private JPanel parent;
    private JLabel shipLengthLabel;
    private JRadioButton[] shipLengths;
    private ButtonGroup shipLengthGroup;
    private Controller controller;

    public ShipLengthPane(Controller controller) {
        this.controller = controller;
        shipLengthGroup = new ButtonGroup();
        shipLengths = new JRadioButton[4];
        makeLayout();
        createComponents();
        addComponentsTogether();
    }

    public void createComponents() {
        shipLengthLabel = new JLabel("<html><body>Laivan pituus </body></html>", JLabel.CENTER);
        makeShipLengthRadioButtons();

    }

    private void addComponentsTogether() {
        this.add(shipLengthLabel);
        this.add(new JLabel());
        for (int i = 0; i < 4; i++) {
            this.add(shipLengths[i]);
        }
    }

    private void makeLayout() {
        this.setLayout((new GridLayout(0, 2)));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void makeShipLengthRadioButtons() {
        for (int i = 0; i < 4; i++) {
            JRadioButton length = new JRadioButton("" + (i + 1));
            shipLengths[i] = length;
            length.setActionCommand("" + (i + 1));
            shipLengthGroup.add(length);
            makeShipLengthListeners(length);
        }
    }

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