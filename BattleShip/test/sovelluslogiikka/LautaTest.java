package sovelluslogiikka;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class LautaTest {

    Lauta lauta;
    int koko = 10;
    int erimittaisiaLaivoja = 4;

    public LautaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lauta = new Lauta(koko, erimittaisiaLaivoja);
    }

    @After
    public void tearDown() {
    }

    public boolean teeMontaLaivaa(int maara, int laivanKoko) {
        int[] alku = new int[2];
        int rivi = 0;
        lauta.teeLaiva(alku, Suunta.OIKEALLE, laivanKoko);
        for (int i = 1; i < maara; i++) {
            if ((i * laivanKoko + i - 1) > koko - laivanKoko - 2) {
                rivi++;
            }
            alku[0] = i * laivanKoko + i;
            alku[1] = rivi;
            if (lauta.teeLaiva(alku, Suunta.OIKEALLE, laivanKoko) == false) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void onnistuukoLaivaLaudanUlkopuolelle() {
        int[] alku = {10, 4};
        boolean onnistuuko = lauta.teeLaiva(alku, Suunta.OIKEALLE, 2);
        assertEquals(false, onnistuuko);
    }

    @Test
    public void onnistuukoVertikaalinenLaivaKeskelle() {
        int[] alku = {4, 4};
        boolean onnistuuko = lauta.teeLaiva(alku, Suunta.ALAS, 2);
        assertEquals(true, onnistuuko);
    }

    @Test
    public void onnistuukoVertikaalinenLaivaVasempaanAlaKulmaan() {
        int[] alku = {0, koko - 2};
        boolean onnistuuko = lauta.teeLaiva(alku, Suunta.ALAS, 2);
        assertEquals(true, onnistuuko);
    }

    @Test
    public void onnistuukoHorisontaalinenLaivaVasempaanAlaKulmaan() {
        int[] alku = {2, koko - 1};
        boolean onnistuuko = lauta.teeLaiva(alku, Suunta.OIKEALLE, 2);
        assertEquals(true, onnistuuko);
    }

    @Test
    public void onnistuukoHorisontaalinenLaivaOikeaanYlaKulmaan() {
        int[] alku = {koko - 1, 0};
        boolean onnistuuko = lauta.teeLaiva(alku, Suunta.OIKEALLE, 2);
        assertEquals(false, onnistuuko);
    }

    @Test
    public void onnistuukoHorisontaalinenLaivaOikeaanAlaKulmaan() {
        int[] alku = {koko - 2, koko - 1};
        boolean onnistuuko = lauta.teeLaiva(alku, Suunta.OIKEALLE, 2);
        assertEquals(true, onnistuuko);
    }

    @Test
    public void laskeeXkoordinaatitOikein() {
        int[] alku = {koko - 2, koko - 1};
        int[] odotettu = {8, 9};
        int[] xKoord = lauta.laskeXKoordinaatit(alku, Suunta.OIKEALLE, 2);
        assertArrayEquals(odotettu, xKoord);
    }

    @Test
    public void laskeeYkoordinaatitOikein() {
        int[] alku = {koko - 2, koko - 1};
        int[] odotettu = {9, 9};
        int[] yKoord = lauta.laskeYKoordinaatit(alku, Suunta.OIKEALLE, 2);
        assertArrayEquals(odotettu, yKoord);
    }

    @Test
    public void onnistuukoLaivojenLaittaminenVierekkain() {
        int[] alku = {3, 5};
        int[] alku2 = {4, 4};
        lauta.teeLaiva(alku, Suunta.ALAS, 3);
        boolean onnistuuko = lauta.teeLaiva(alku2, Suunta.OIKEALLE, 3);
        assertEquals(false, onnistuuko);
    }

    @Test
    public void onnistuukoNeljaYhdenmittaistaLaivaa() {
        boolean onnistuuko = teeMontaLaivaa(4, 1);
        assertEquals(true, onnistuuko);
    }

    @Test
    public void onnistuukoViisiYhdenmittaistaLaivaa() {
        assertEquals(false, teeMontaLaivaa(5, 1));
    }

    @Test
    public void onnistuukoKolmeKolmenMittaistaLaivaa() {
        assertEquals(false, teeMontaLaivaa(3, 3));
    }

    @Test
    public void onnistuukoKaksiKolmenMittaistaLaivaa() {
        assertEquals(true, teeMontaLaivaa(2, 3));
    }

    @Test
    public void lukitaankoOikeatRuudut() {
        int[] lukitutX = {3, 4, 5, 3, 4, 5, 3, 4, 5, 3, 4, 5, 3, 4, 5};
        int[] lukitutY = {2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6};
        lauta.teeLaiva(new int[]{4, 3}, Suunta.ALAS, 3);

        assertEquals(true, tarkistaRuudut(lukitutX, lukitutY));
    }

    @Test
    public void lukitaankoOikeatRuudutOikeassaAlakulmassa() {
        int[] lukitutX = {7,7,8,9};
        int[] lukitutY = {9,8,8,8};
        lauta.teeLaiva(new int[]{8, 9}, Suunta.OIKEALLE, 2);

        assertEquals(true, tarkistaRuudut(lukitutX, lukitutY));
    }

    public boolean tarkistaRuudut(int[] lukitutX, int[] lukitutY) {
        boolean lukittu = true;
        for (int i = 0; i < lukitutX.length; i++) {
            if (!lauta.haeRuudut()[lukitutX[i]][lukitutY[i]].onkoLukittu()) {
                lukittu = false;
                System.out.println("ruutu " + lauta.haeRuudut()[lukitutX[i]][lukitutY[i]].haeX() + " , " + lauta.haeRuudut()[lukitutX[i]][lukitutY[i]].haeY()
                        + " ei ole lukittu");
            }
        }
        return lukittu;
    }
}