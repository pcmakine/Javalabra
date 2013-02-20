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
 *
 * @author pcmakine
 */
public class ShipDirectionPane extends JPanel {

    private JLabel shipDirectionLabel;
    private ButtonGroup shipDirectionGroup;
    private Controller controller;
    private JRadioButton horizontal;
    private JRadioButton vertical;

    public ShipDirectionPane(Controller controller) {
        this.controller = controller;
        shipDirectionGroup = new ButtonGroup();
        makeLayout();
        createComponents();
        addComponentsTogether();
    }

    public void createComponents() {
        shipDirectionLabel = new JLabel("<html><body>  Haluatko tehdä vaaka <br> vai pystylaivan?  </body></html>", JLabel.CENTER);
        makeDirectionRadioButtons();
    }

    private void addComponentsTogether() {
        this.add(shipDirectionLabel);
        this.add(horizontal);
        this.add(vertical);
    }

    private void makeLayout() {
        this.setLayout(new GridLayout(0, 1));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

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
    
   public void makeDirectionListeners(JRadioButton radioButton) {
        ActionListener directionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String direction = actionEvent.getActionCommand();
                controller.setDirection(direction);
            }
        };
        radioButton.addActionListener(directionListener);
    }

}