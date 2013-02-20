/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents;

/**
 *
 * @author pcmakine
 */
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sovelluslogiikka.Controller;
import kayttoliittyma.Draw;

public class BoardArea extends JPanel {

    private Controller controller;
    private JLabel boardLabel;
    private ButtonBoard board;
    private String playerType;
    
    public BoardArea(Controller controller, String playerType, Draw drawer) {
        this.controller = controller;
        this.playerType = playerType;
        makeLayout();
        createComponents(drawer);
        addComponentsTogether();
    }

    private void createComponents(Draw drawer) {
        if (playerType.equals("player")) {
            boardLabel = new JLabel("Oma lauta");
        }
        else{
            boardLabel = new JLabel("Vastustajan lauta");
        }           
           board = new ButtonBoard(controller, playerType, drawer);

    }

    private void addComponentsTogether() {
        this.add(boardLabel);
        this.add(board);
    }

    private void makeLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setPreferredSize(new Dimension(500, 400));
    }
}