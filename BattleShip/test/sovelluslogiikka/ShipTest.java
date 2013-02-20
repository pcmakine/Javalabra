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
    }

    @After
    public void tearDown() {
    }
    
    public void teeTestiLaiva(Direction suunta, int alku[]){
        
    }

    @Test
    public void horisontaalisellaLaivallaOikeatXKoordinaatit() {
        int[] alku = {koko - 2, koko - 1};
        int [] odotettu = {8,9};
        int[] xkoord = new int[2];
        Ship laiva;
        lauta.createShip(alku, Direction.RIGHT, 2);
        laiva = lauta.getShips()[0];
        xkoord =laiva.getxCoords();
        assertArrayEquals(odotettu, xkoord);
    }
        public void horisontaalisellaLaivallaOikeatYKoordinaatit() {
        int[] alku = {koko - 2, koko - 1};
        int [] odotettu = {9,9};
        int[] ykoord = new int[2];
        lauta.createShip(alku, Direction.RIGHT, 2);
        Ship ship = lauta.getShips()[0];
        ykoord = ship.getxCoords();
        assertArrayEquals(odotettu, ykoord);
    }
}