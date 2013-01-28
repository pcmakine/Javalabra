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
public class LaivaTest {

    private int koko = 10;
    Lauta lauta;

    public LaivaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lauta = new Lauta(koko, 10);
    }

    @After
    public void tearDown() {
    }
    
    public void teeTestiLaiva(Suunta suunta, int alku[]){
        
    }

    @Test
    public void horisontaalisellaLaivallaOikeatXKoordinaatit() {
        int[] alku = {koko - 1, koko - 2};
        int [] odotettu = {8,9};
        int[] xkoord = new int[2];
        Laiva laiva;
        lauta.teeLaiva(alku, Suunta.OIKEALLE, 2);
        laiva = lauta.haeLaivat()[0];
        xkoord =laiva.haeXKoordinaatti();
        assertArrayEquals(odotettu, xkoord);
    }
        public void horisontaalisellaLaivallaOikeatYKoordinaatit() {
        int[] alku = {koko - 1, koko - 2};
        int [] odotettu = {9,9};
        int[] ykoord = new int[2];
        Laiva laiva;
        lauta.teeLaiva(alku, Suunta.OIKEALLE, 2);
        laiva = lauta.haeLaivat()[0];
        ykoord =laiva.haeXKoordinaatti();
        assertArrayEquals(odotettu, ykoord);
    }
}