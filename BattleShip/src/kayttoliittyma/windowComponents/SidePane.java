/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma.windowComponents;

import kayttoliittyma.windowComponents.sidePaneComponents.ShipLengthArea;
import kayttoliittyma.windowComponents.sidePaneComponents.ShipDirectionArea;
import kayttoliittyma.windowComponents.sidePaneComponents.StatsArea;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import sovelluslogiikka.Controller;

/**
 * Pääikkunan vasemmassa laidassa oleva JPanelia laajentava sivupaneeli -olio
 *
 * @author pcmakine
 */
public class SidePane extends JPanel {

    /**
     * Layoutmanager jota sivupaneeli käyttää
     */
    private GridLayout sideLayout;
    /**
     * Sivupaneelissa oleva alue jossa on radiobuttonit tehtävän laivan pituuden
     * valitsemiseksi
     */
    private ShipLengthArea shipLengths;
    /**
     * Sivupaneelissa oleva alue jossa on radiobuttonit tehtävän laivan suunnan
     * valitsemiseksi
     */
    private ShipDirectionArea directions;
    /**
     * Sivupaneelissa oleva alue, joka näyttää tilannetietoa
     */
    private StatsArea stats;

    /**
     * Tekee uuden sivupaneelin sekä kutsuu metodeita jotka tekevät sivu-
     * paneelin osat ja lisäävät ne sivupaneeliin. Osat ovat siis alueet
     * tilannetiedolle sekä laivan pituuden ja suunnan valitsemiselle.
     *
     * @param controller Controller luokan ilmentymä joka toimii linkkinä käyt-
     * töliittymän ja sovelluslogiikan välillä. Sivupaneeli vain välittää
     * controllerin eteenpäin sivupaneelin osille.
     */
    public SidePane(Controller controller) {
        makeLayout();
        createComponents(controller);
        addComponentsTogether();
    }

    /**
     * Luo sivupaneelin osat.
     *
     * @param controller Controller luokan ilmentymä joka toimii linkkinä käyt-
     * töliittymän ja sovelluslogiikan välillä.Välitetään parametrina sivupanee-
     * lin interaktiivisille osille.
     */
    private void createComponents(Controller controller) {

        shipLengths = new ShipLengthArea(controller);
        directions = new ShipDirectionArea(controller);
        stats = new StatsArea(this.getBackground());
    }

    public StatsArea getStatsArea() {
        return stats;
    }

    /**
     * Lisää sivupaneelin osat sivupaneeliin
     */
    private void addComponentsTogether() {
        this.add(shipLengths);
        this.add(directions);
        this.add(stats);
    }

    /**
     * Asettaa layoutin ja sivupaneelin ulkonäön
     */
    private void makeLayout() {
        sideLayout = new GridLayout(0, 1);
        this.setLayout(sideLayout);
        sideLayout.setVgap(10);
        this.setPreferredSize(new Dimension(140, 200));
        this.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
    }
}