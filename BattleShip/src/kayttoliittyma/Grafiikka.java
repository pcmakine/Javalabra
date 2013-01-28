/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

/**
 *
 * @author pcmakine
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.Color;

public class Grafiikka extends JFrame {

    public Grafiikka() {
        initUI();
    }

    public final void initUI() {

        JPanel lauta = new JPanel();

        lauta.setBorder(BorderFactory.createLineBorder(new Color(255,0,0)));
        lauta.setLayout(new GridLayout(10, 10, 0, 0));

        String [] buttons = new String[10*10];
        for (int i = 0; i < 10*10; i++) {
            buttons[i] = "";
        }
        for (int i = 0; i < buttons.length; i++) {
                lauta.add(new JButton(buttons[i]));         
        }
        add(lauta);
        setTitle("Laivanupotus");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}