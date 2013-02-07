/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 * Pelaaja, joka voi tehdä laivat satunnaisiin paikkoihin. Pelaaja voi myös ampua toisen pelaajan laivoja
 * @author pcmakine
 */
import java.lang.Math;

public class Pelaaja {

    private Lauta peliLauta;

    public Pelaaja(Lauta peliLauta) {
        this.peliLauta = peliLauta;
    }

    public void arvoLaivat() {
        boolean onnistuiko;
        for (int i = peliLauta.haeErimittaisiaLaivojaMax().length - 1; i >= 0; i--) {        //käydään läpi kaikki erimittaiset laivat pisimmästä lyhimpään
            int montako = peliLauta.haeErimittaisiaLaivojaMax()[i];
            for (int j = 0; j < montako; j++) {                                             //tehdään niin monta tämän pituista laivaa kuin sääntöjen mukaan saadaan
                do {
                    onnistuiko = peliLauta.teeLaiva(randomAlku(), randomSuunta(), i+1);
                } while (onnistuiko == false);
            }
        }
    }

    public int[] randomAlku() {
        return new int[]{(int) (Math.random() * 9), (int) (Math.random() * 10)};
    }

    public Suunta randomSuunta() {
        if ((Math.random()) < 0.5) {
            return Suunta.ALAS;
        } else {
            return Suunta.OIKEALLE;
        }
    }

    public boolean tarkistaOnkoJoAmmuttu(Lauta lauta, int[] kohde) {
        if (lauta.haeRuudut()[kohde[0]][kohde[1]].onkoAmmuttu()) {
            System.out.println("ammuttu jo");
            return true;
        }
        return false;
    }

    public boolean ammu(Lauta lauta, int[] kohde) {     //palauttaa true jos osui, muuten false
        if (tarkistaOnkoJoAmmuttu(lauta, kohde)) {
            return false;
        }
        lauta.haeRuudut()[kohde[0]][kohde[1]].ampuTulee();
        for (int i = 0; i < lauta.haeLaivojenMaara(); i++) {    //kay lapi kaikki laivat
            int[] xKoord = lauta.haeLaivat()[i].haeXKoordinaatti();
            int[] yKoord = lauta.haeLaivat()[i].haeYKoordinaatti();
            for (int j = 0; j < xKoord.length; j++) {
                if (xKoord[j] == kohde[0] && yKoord[j] == kohde[1]) {
                    lauta.haeLaivat()[i].asetaOsuma(kohde);
                    return true;
                }
            }
        }
        return false;
    }
}