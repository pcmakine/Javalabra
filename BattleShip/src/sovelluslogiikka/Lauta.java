/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 * Mallintaa pelilautaa ja pitää kirjaa sen Ruutu tyyppisistä ruutuolioista
 * @author pcmakine
 * 
 */
public class Lauta {

    /**
     * Laudan ruutuja kuvaavat ruutu-oliot kaksiulotteisessa taulukossa
     */
    private Ruutu[][] ruudut;
    private int koko;
    private Laiva[] laivat;
    private int laivoja;
    private int[] eriMittaisiaLaivoja;     //indeksissä n-1 n-kokoisten tehtyjen laivojen määrä
    private int[] eriMittaisiaLaivojaMax = {4, 3, 2, 1};     //indeksissä n-1 n-kokoisten laivojen maksimimäärä

    public Lauta(int koko, int erimittaisia) {
        this.ruudut = new Ruutu[koko][koko];
        this.koko = koko;
        teeRuudut();
        this.laivoja = 0;
        laivat = new Laiva[10];
        eriMittaisiaLaivoja = new int[erimittaisia];
    }

    private void teeRuudut() {
        for (int i = 0; i < koko; i++) {            //rivi
            for (int j = 0; j < koko; j++) {         //sarake
                ruudut[i][j] = new Ruutu(j, i);
            }
        }
    }

    /**
     * Lukitsee saamiensa x ja y koordinaattiparien ruudut, sekä niiden
     * ympäröivät ruudut niin ettei näihin ruutuihin enää voida tehdä uusia
     * laivoja.
     *
     * @param xKoord Taulukko laivan x-koordinaateista
     * @param yKoord Taulukko laivan y-koordinaateista Pisteet ovat siis esim. [
     * yKoord[0], xKoord[0] ], [ yKoord[1], xKoord[1] ] jne.
     */
    public void lukitseRuudut(int xKoord[], int yKoord[]) {
        for (int i = 0; i < yKoord.length; i++) {           //käydään läpi kaikki laivan ruudut
            for (int j = 0; j < 3; j++) {                   //käydään läpi ruudut x-1, x ja x+1              
                for (int k = 0; k < 3; k++) {               //käydään läpi ruudut y-1, y ja y+1
                    if (xKoord[i] == 0 && j == 0) {              //jos ollaan vasemmassa reunassa ei yritetä lukita oleamatonta ruutua
                        j++;
                    }
                    if (yKoord[i] == 0 && k == 0) {             //jos ollaan yläreunassa ei yritetä lukita olematonta ruutua
                        k++;
                    }
                    if ((xKoord[i] - 1 + j) < koko && (yKoord[i] - 1 + k) < koko) {             //jos ei mennä laivan ala- tai oikeasta reunasta yli    
                        ruudut[xKoord[i] - 1 + j][yKoord[i] - 1 + k].lukitseRuutu();   // voidaan ruutu lukita
                    }
                }
            }
        }
    }

    public int laivojaMahtuuViela(int pituus) {
        return (eriMittaisiaLaivojaMax[pituus - 1] - eriMittaisiaLaivoja[pituus - 1]);
    }

    public void asetaLaivatRuutuihin(int[] xKoord, int[] yKoord) {
        for (int i = 0; i < yKoord.length; i++) {
            ruudut[xKoord[i]][yKoord[i]].asetaLaiva();
        }
    }

    public boolean teeLaiva(int alku[], Suunta suunta, int pituus) {        //alku[0] on alun x koordinaatti ja alku[1] on alun y koordinaatti
        int[] xKoord = laskeXKoordinaatit(alku, suunta, pituus);
        int[] yKoord = laskeYKoordinaatit(alku, suunta, pituus);
        if (onkoLaudalla(alku, suunta, pituus) && vieressaEiLaivaa(alku, suunta, pituus) && laivojaMahtuuViela(pituus) > 0) {
            Laiva laiva = new Laiva(xKoord, yKoord, suunta);
            laivat[laivoja] = laiva;
            eriMittaisiaLaivoja[pituus - 1]++;
            laivoja++;
            asetaLaivatRuutuihin(xKoord, yKoord);
            lukitseRuudut(xKoord, yKoord);
            return true;
        }
        return false;
    }

    public int[] laskeYKoordinaatit(int alku[], Suunta suunta, int pituus) {
        int yKoordinaatit[] = new int[pituus];
        if (suunta == Suunta.ALAS) {
            for (int i = 0; i < pituus; i++) {
                yKoordinaatit[i] = alku[1] + i;
            }
        } else if (suunta == Suunta.OIKEALLE) {             //laiva vaakatasossa, y-koordinaatti ei muutu
            for (int i = 0; i < pituus; i++) {
                yKoordinaatit[i] = alku[1];
            }
        }
        return yKoordinaatit;
    }

    public int[] laskeXKoordinaatit(int alku[], Suunta suunta, int pituus) {
        int xKoordinaatit[] = new int[pituus];
        if (suunta == Suunta.OIKEALLE) {
            for (int i = 0; i < pituus; i++) {
                xKoordinaatit[i] = alku[0] + i;
            }
        } else if (suunta == Suunta.ALAS) {
            for (int i = 0; i < pituus; i++) {
                xKoordinaatit[i] = alku[0];
            }
        }
        return xKoordinaatit;
    }

    /**
     * Tarkistaa menisikö laiva jota yritetään tehdä laudan ulkopuolelle
     *
     * @param alku Taulukko jossa alku[0] on laivan alkupisteen x-koordinaatti
     * ja alku[1] alkupisteen y-koordinaatti
     * @param suunta Suunta johon laiva menee alkupisteestä. Joko oikealle tai
     * alas
     * @param pituus Kertoo laivan pituuden
     * @return Palauttaa true jos laiva on laudalla ja muuten false
     *
     */
    public boolean onkoLaudalla(int alku[], Suunta suunta, int pituus) {          //tarkista meneekö laiva yli pelilaudan
        if (suunta == Suunta.ALAS) {
            if (alku[1] > (koko - pituus) || alku[0] < 0 || alku[1] < 0) {
                return false;
            }
        } else if (suunta == Suunta.OIKEALLE) {
            if (alku[0] > (koko - pituus) || alku[0] < 0 || alku[1] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param alku Taulukko jossa alku[0] on laivan alkupisteen x-koordinaatti
     * ja alku[1] alkupisteen y-koordinaatti
     * @param suunta Suunta johon laiva menee alkupisteestä. Joko oikealle tai
     * alas
     * @param pituus Kertoo laivan pituuden
     * @return Palauttaa true jos ruutujen joihin laivaa yritetään tehdä
     * vieressä ei ole laivaa, muuten false
     */
    public boolean vieressaEiLaivaa(int alku[], Suunta suunta, int pituus) {        //laivaa ei voi tehdä jos viereisissä ruuduissa on laiva
        int[] xKoord = laskeXKoordinaatit(alku, suunta, pituus);
        int[] yKoord = laskeYKoordinaatit(alku, suunta, pituus);
        for (int i = 0; i < xKoord.length; i++) {
            for (int j = 0; j < yKoord.length; j++) {
                try {
                    if (ruudut[xKoord[i]][yKoord[j]].onkoLukittu()) {
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return true;
    }

    public int haeLaivojenMaara() {
        return laivoja;
    }

    public Laiva[] haeLaivat() {
        return laivat;
    }

    public int haeKoko() {
        return koko;
    }

    public Ruutu[][] haeRuudut() {
        return ruudut;
    }

    public int[] haeErimittaisiaLaivoja() {
        return eriMittaisiaLaivoja;
    }

    public int[] haeErimittaisiaLaivojaMax() {
        return eriMittaisiaLaivojaMax;
    }

    @Override
    public String toString() {
        String tulostettava = "  0 1 2 3 4 5 6 7 8 9\n";
        for (int i = 0; i < this.koko; i++) {
            tulostettava = tulostettava + i + " ";
            for (int j = 0; j < this.koko; j++) {
                tulostettava = tulostettava + ruudut[j][i] + " ";
            }
            tulostettava = tulostettava + "\n";
        }
        return tulostettava;
    }
}