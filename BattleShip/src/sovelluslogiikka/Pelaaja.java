/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 * Pelaaja, joka voi tehdä laivat satunnaisiin paikkoihin. Pelaaja voi myös
 * ampua toisen pelaajan laivoja
 *
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
                    onnistuiko = peliLauta.teeLaiva(randomSquare(), randomSuunta(), i + 1);
                } while (onnistuiko == false);
            }
        }
    }

    public boolean allShipsLost() {
        Laiva[] laivat = peliLauta.haeLaivat();
        for (int j = 0; j < laivat.length; j++) {        //olettaa että kummallakin sama määrä laivoja
            if (!laivat[j].sankAlready()) {
                return false;
            }
        }
        return true;
    }

    public int[] randomSquare() {
        return new int[]{(int) (Math.random() * 10), (int) (Math.random() * 10)};
    }

    public Suunta randomSuunta() {
        if ((Math.random()) < 0.5) {
            return Suunta.ALAS;
        } else {
            return Suunta.OIKEALLE;
        }
    }

    public Lauta haeLauta() {
        return peliLauta;
    }

    public boolean tarkistaOnkoJoAmmuttu(Lauta lauta, int[] kohde) {
        if (lauta.haeRuudut()[kohde[0]][kohde[1]].onkoAmmuttu()) {
            return true;
        }
        return false;
    }

    public boolean ammu(Lauta lauta, int[] kohde) {     //palauttaa true jos ampuminen meni läpi, muuten false. Kahta kertaa ei voi ampua samaan ruutuun
        if (tarkistaOnkoJoAmmuttu(lauta, kohde) || !lauta.onkoLaudalla(kohde, Suunta.ALAS, 1)) {
            return false;
        }
        lauta.haeRuudut()[kohde[0]][kohde[1]].ampuTulee();
        for (int i = 0; i < lauta.haeLaivojenMaara(); i++) {    //kay lapi kaikki laivat
            int[] xKoord = lauta.haeLaivat()[i].haeXKoordinaatti();
            int[] yKoord = lauta.haeLaivat()[i].haeYKoordinaatti();
            for (int j = 0; j < xKoord.length; j++) {
                if (xKoord[j] == kohde[0] && yKoord[j] == kohde[1]) {
                    lauta.haeLaivat()[i].asetaOsuma(kohde);
                }
            }
        }
        return true;
    }

    public void randomShoot(Lauta lauta) {
        boolean successful;
        do {
            int[] target = randomSquare();
            successful = ammu(lauta, target);
            System.out.println("Vastustaja yritti ampua ruutuun " + (target[0] + 1) + ", " + (target[1] + 1));
        } while (!successful);
    }
}