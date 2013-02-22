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
public class SquareTest {

    private int koko = 10;
    Board lauta;
    Player player;

    public SquareTest() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    private void makeShips() {
        int[] beginning = {koko - 2, koko - 1};
        int[] beginning1 = {0, 0};
        lauta.createShip(beginning, Direction.RIGHT, 2);
        lauta.createShip(beginning, Direction.RIGHT, 2);
        lauta.createShip(beginning1, Direction.DOWN, 1);
    }

    @Test
    public void shipsSetCorrectly() {
        boolean notShip = lauta.getSquares()[0][0].isShip();
        makeShips();
        boolean ship = lauta.getSquares()[0][0].isShip();
        boolean ship1 = lauta.getSquares()[8][9].isShip();
        assertEquals(false, notShip);
        assertEquals(true, ship);
        assertEquals(true, ship1);
    }

    @Test
    public void lockedSetCorrectly() {
        boolean notLocked = lauta.getSquares()[0][0].isLocked();
        makeShips();


        boolean locked = lauta.getSquares()[0][0].isLocked();
        boolean locked1 = lauta.getSquares()[8][9].isLocked();

        assertEquals(false, notLocked);
        assertEquals(true, locked);
        assertEquals(true, locked1);
    }
}
