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
public class PelaajaTest {

    Pelaaja pelaaja;
    Lauta lauta;

    public PelaajaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lauta = new Lauta(10, 10);
        pelaaja = new Pelaaja(lauta);
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void OsuukoLaivaan() {
        int[] alku = {4, 7};
        int[] kohde = {4, 8};
        int[] kohde1 = {4, 6};
        lauta.teeLaiva(alku, Suunta.ALAS, 2);
        assertEquals(true, pelaaja.ammu(lauta, alku));
        assertEquals(true, pelaaja.ammu(lauta, kohde));
        assertEquals(false, pelaaja.ammu(lauta, kohde1));
        assertEquals(false, pelaaja.ammu(lauta, alku));
    }

    @Test
    public void arvotaankoYhtaPaljonEriSuuntia() {
        Suunta[] suuntia = new Suunta[100];
        int alasSumma = 0;
        int oikealleSumma = 0;

        for (int i = 0; i < 100; i++) {
            suuntia[i] = pelaaja.randomSuunta();
        }
        for (int i = 0; i < 100; i++) {
            if (suuntia[i] == Suunta.ALAS) {
                alasSumma++;
            } else {
                oikealleSumma++;
            }
        }
        System.out.println("Oikealle: " + oikealleSumma + " Alas: " + alasSumma);
        assertEquals(true, (Math.abs(oikealleSumma - alasSumma) < 20) );
    }
}