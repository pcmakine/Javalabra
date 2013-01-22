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
        lauta = new Lauta(koko);
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void onnistuukoVertikaalinenLaivaKeskelle() {
        int[] alku = {4, 4};
        boolean onnistuuko;
        String suunta = "alas";
        onnistuuko = lauta.teeLaiva(alku, suunta, 2);
        assertEquals(true, onnistuuko);
    }

    @Test
    public void onnistuukoVertikaalinenLaivaVasempaanAlaKulmaan() {
        int[] alku = {koko - 2, 0};
        boolean onnistuuko;
        String suunta = "alas";
        onnistuuko = lauta.teeLaiva(alku, suunta, 2);
        assertEquals(true, onnistuuko);
    }

    @Test
    public void onnistuukoHorisontaalinenLaivaVasempaanAlaKulmaan() {
        int[] alku = {koko - 2, 1};
        boolean onnistuuko;
        String suunta = "oikealle";
        onnistuuko = lauta.teeLaiva(alku, suunta, 2);
        assertEquals(true, onnistuuko);
    }

    @Test
    public void onnistuukoHorisontaalinenLaivaOikeaanYlaKulmaan() {
        int[] alku = {0, koko - 1};
        boolean onnistuuko;
        String suunta = "oikealle";
        onnistuuko = lauta.teeLaiva(alku, suunta, 2);
        assertEquals(false, onnistuuko);
    }

    @Test
    public void onnistuukoHorisontaalinenLaivaOikeaanAlaKulmaan() {
        int[] alku = {koko - 1, koko - 2};
        boolean onnistuuko;
        String suunta = "oikealle";
        onnistuuko = lauta.teeLaiva(alku, suunta, 2);
        assertEquals(true, onnistuuko);
    }

    @Test
    public void laskeeXkoordinaatitOikein() {
        int[] alku = {koko - 1, koko - 2};
        int[] odotettu = {8,9};
        String suunta = "oikealle";
        int[] xKoord = new int[2];
        xKoord = lauta.laskeXKoordinaatit(alku, suunta, 2);
        assertArrayEquals(odotettu, xKoord);
    }
    
        @Test
    public void laskeeYkoordinaatitOikein() {
        int[] alku = {koko - 1, koko - 2};
        int[] odotettu = {9,9};
        String suunta = "oikealle";
        int[] yKoord = new int[2];
        yKoord = lauta.laskeYKoordinaatit(alku, suunta, 2);
        assertArrayEquals(odotettu, yKoord);
    }
}