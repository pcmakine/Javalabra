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
import java.lang.Math;

/**
 *
 * @author pcmakine
 */
public class PlayerTest {

    Player player;
    Board board;

    public PlayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        int[] alku = {4, 7};
        board = new Board(10, 10);
        player = new Player(board);
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    public void makeAllShips() {
        player.getBoard().createShip(new int[]{0, 0}, Direction.RIGHT, 4);
        player.getBoard().createShip(new int[]{0, 3}, Direction.RIGHT, 3);
        player.getBoard().createShip(new int[]{4, 3}, Direction.RIGHT, 3);
        player.getBoard().createShip(new int[]{0, 5}, Direction.RIGHT, 2);
        player.getBoard().createShip(new int[]{3, 5}, Direction.RIGHT, 2);
        player.getBoard().createShip(new int[]{6, 5}, Direction.RIGHT, 2);
        player.getBoard().createShip(new int[]{0, 7}, Direction.RIGHT, 1);
        player.getBoard().createShip(new int[]{2, 7}, Direction.RIGHT, 1);
        player.getBoard().createShip(new int[]{4, 7}, Direction.RIGHT, 1);
        player.getBoard().createShip(new int[]{6, 7}, Direction.RIGHT, 1);
    }

    @Test
    public void allShipsDoneWorks() {
        boolean done = player.allShipsDone();
        makeAllShips();
        boolean done1 = player.allShipsDone();
        assertEquals(false, done);
        assertEquals(true, done1);
    }

    @Test
    public void allShipsLostWorks() {
        makeAllShips();
        boolean lost = player.allShipsLost();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                player.shoot(board, new int[]{i, j});
            }
        }
        boolean lost1 = player.allShipsLost();
        assertEquals(false, lost);
        assertEquals(true, lost1);
    }

    @Test
    public void hitPointsSetCorrectly() {
        int[] beginning = {4, 7};
        int[] target = {4, 8};
        int[] target1 = {4, 9};

        int[][] expected = {{4, 7}, {4, 8}, {4, 9}};

        board.createShip(beginning, Direction.DOWN, 3);
        player.shoot(board, beginning);
        player.shoot(board, target);
        player.shoot(board, target1);
        int[][] osumat = board.getShips()[0].getHitCoords();
        assertArrayEquals(expected, osumat);

    }

    @Test
    public void notPossibleToShootSameSquareTwice() {
        int[] beginning = {4, 7};
        int[] target = {4, 8};
        int[] target1 = {4, 9};

        assertEquals(true, player.shoot(board, beginning));
        assertEquals(true, player.shoot(board, target));
        assertEquals(true, player.shoot(board, target1));
        assertEquals(false, player.shoot(board, target1));
    }

    @Test
    public void equalNumberOfDifferentDirections() {
        Direction[] suuntia = new Direction[100];
        int alasSumma = 0;
        int oikealleSumma = 0;

        for (int i = 0; i < 100; i++) {
            suuntia[i] = player.randomDirection();
        }
        for (int i = 0; i < 100; i++) {
            if (suuntia[i] == Direction.DOWN) {
                alasSumma++;
            } else {
                oikealleSumma++;
            }
        }
        assertEquals(true, (Math.abs(oikealleSumma - alasSumma) < 20));
    }
}