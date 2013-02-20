/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents;

import kayttoliittyma.windowComponents.sidePaneComponents.ShipLengthPane;
import kayttoliittyma.windowComponents.sidePaneComponents.ShipDirectionPane;
import kayttoliittyma.windowComponents.sidePaneComponents.StatsPane;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import sovelluslogiikka.Controller;


/**
 *
 * @author pcmakine
 */
public class SidePane extends JPanel {

    private GridLayout sideLayout;
    private ShipLengthPane shipLengths;
    private ShipDirectionPane directions;
    private Controller controller;
    private StatsPane stats;

    public SidePane(Controller controller) {
        this.controller = controller;
        makeLayout();
        createComponents();
        addComponentsTogether();
    }

    private void createComponents() {
        shipLengths = new ShipLengthPane(controller);
        directions = new ShipDirectionPane(controller);
        stats = new StatsPane(this.getBackground());
    }
    
    public StatsPane getStatsPane(){
        return stats;
    }

    private void addComponentsTogether() {
        this.add(shipLengths);
        this.add(directions);
        this.add(stats);
    }

    private void makeLayout() {
        sideLayout = new GridLayout(0, 1);
        this.setLayout(sideLayout);
        sideLayout.setVgap(10);
        this.setPreferredSize(new Dimension(140, 200));
        this.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
    }
}