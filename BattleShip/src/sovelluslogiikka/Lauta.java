/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 *
 * @author pcmakine
 */
public class Lauta {

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

    public void lukitseYmparillaOlevatRuudut(int xKoord[], int yKoord[]) {
        for (int i = 0; i < yKoord.length; i++) {
            for (int k = 0; k < 3; k++) {
                try {
                    ruudut[(xKoord[0] - 1) + k][yKoord[i] - 1].lukitseRuutu();
                    if (i == yKoord.length - 1) {
                        ruudut[(xKoord[i] - 1) + k][yKoord[i]].lukitseRuutu();      //kun mennään laivasta yli jatketaan vielä niin että kaikki ympäröivät tulevat lukittua
                        if (i != koko - 1) {
                            ruudut[(xKoord[i] - 1) + k][yKoord[i] + 1].lukitseRuutu();
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
    }

    public boolean laivojaMahtuuViela(int pituus) {
        if (eriMittaisiaLaivoja[pituus - 1] < eriMittaisiaLaivojaMax[pituus - 1]) {
            return true;
        }
        return false;
    }

 /*   public Ruutu[] mitkaRuudut(int[] xKoord, int[] yKoord) {
        Ruutu[] laivanRuudut = new Ruutu[xKoord.length];
        for (int i = 0; i < xKoord.length; i++) {
            laivanRuudut[i] = ruudut[yKoord[i]][xKoord[i]];
        }
        return laivanRuudut;
    }*/

    public boolean teeLaiva(int alku[], Suunta suunta, int pituus) {        //alku[0] on alun x koordinaatti ja alku[1] on alun y koordinaatti
        int[] xKoord = laskeXKoordinaatit(alku, suunta, pituus);
        int[] yKoord = laskeYKoordinaatit(alku, suunta, pituus);
       // Ruutu[] laivanRuudut = mitkaRuudut(xKoord, yKoord);
        if (onkoLaudalla(alku, suunta, pituus) && vieressaEiLaivaa(alku, suunta, pituus) && laivojaMahtuuViela(pituus)) {
            Laiva laiva = new Laiva(xKoord, yKoord, suunta);
            laivat[laivoja] = laiva;
            eriMittaisiaLaivoja[pituus - 1]++;
            laivoja++;
            lukitseYmparillaOlevatRuudut(xKoord, yKoord);
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

    //vaatii testaamista
    public boolean vieressaEiLaivaa(int alku[], Suunta suunta, int pituus) { //laivaa ei voi tehdä jos viereisissä ruuduissa on laiva
        int[] xKoord = laskeXKoordinaatit(alku, suunta, pituus);
        int[] yKoord = laskeYKoordinaatit(alku, suunta, pituus);
        for (int i = 0; i < xKoord.length; i++) {
            for (int j = 0; j < yKoord.length; j++) {
                if (ruudut[xKoord[i]][yKoord[j]].onkoLukittu()) {
                    return false;
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