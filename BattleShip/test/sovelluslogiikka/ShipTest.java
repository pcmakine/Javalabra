/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pcmakine
 */
public class ShipTest {

    private int koko = 10;
    Board lauta;
    Player player;

    public ShipTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lauta = new Board(koko, 10);
        player = new Player(lauta);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void sankAlreadyWorks() {
        int[] beginning = {koko - 2, koko - 1};
        lauta.createShip(beginning, Direction.RIGHT, 2);
        player.shoot(player.getBoard(), beginning);
        boolean notYet = lauta.getShips()[0].sankAlready();
        player.shoot(player.getBoard(), new int[]{beginning[0] + 1, beginning[1]});
        boolean shotDown = lauta.getShips()[0].sankAlready();
        assertEquals(notYet, false);
        assertEquals(shotDown, true);
    }

    @Test
    public void horizontalShipRightxCoordinates() {
        int[] beginning = {koko - 2, koko - 1};
        int[] expected = {8, 9};
        int[] xcoords = new int[2];
        Ship laiva;
        lauta.createShip(beginning, Direction.RIGHT, 2);
        laiva = lauta.getShips()[0];
        xcoords = laiva.getxCoords();
        assertArrayEquals(expected, xcoords);
    }

    @Test
    public void horizontalShipRightyCoordinates() {
        int[] alku = {koko - 2, koko - 1};
        int[] odotettu = {9, 9};
        int[] ykoord = new int[2];
        lauta.createShip(alku, Direction.RIGHT, 2);
        Ship ship = lauta.getShips()[0];
        ykoord = ship.getyCoords();
        assertArrayEquals(odotettu, ykoord);
    }
}