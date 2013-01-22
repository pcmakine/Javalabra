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

    public Lauta(int koko) {
        this.ruudut = new Ruutu[koko][koko];
        this.koko = koko;
        teeRuudut();
        this.laivoja = 0;
        laivat = new Laiva[koko];
    }

    private void teeRuudut() {
        for (int i = 0; i < koko; i++) {            //rivi
            for (int j = 0; j < koko; j++) {         //sarake
                ruudut[i][j] = new Ruutu();
            }
        }
    }
    
    public boolean teeLaiva(int alku[], String suunta, int pituus) {        //alku[0] on alun y koordinaatti ja alku[1] on alun x koordinaatti
        if (onkoLaudalla(alku, suunta, pituus) == true) {
           Laiva laiva = new Laiva(laskeXKoordinaatit(alku,suunta,pituus), laskeYKoordinaatit(alku, suunta,pituus));
           laivat[laivoja]=laiva;
           laivoja++;
           return true;
            }
        return false;
    }

    public int[] laskeYKoordinaatit(int alku[], String suunta, int pituus) {
        int yKoordinaatit[] = new int[pituus];
        if (suunta.equals("alas")) {
            for (int i = 0; i < pituus; i++) {
                yKoordinaatit[i] = alku[0] + i;
            }
        } else if (suunta.equals("oikealle")) {             //laiva vaakatasossa, y-koordinaatti ei muutu
            for (int i = 0; i < pituus; i++) {
                yKoordinaatit[i] = alku[0];
            }
        }
        return yKoordinaatit;
    }

    public int[] laskeXKoordinaatit(int alku[], String suunta, int pituus) {
        int xKoordinaatit[] = new int[pituus];
        if (suunta.equals("oikealle")) {
            for (int i = 0; i < pituus; i++) {
                xKoordinaatit[i] = alku[1] + i;
            }
        } else if (suunta.equals("alas")) {
            for (int i = 0; i < pituus; i++) {
                xKoordinaatit[i] = alku[1];
            }
        }
        return xKoordinaatit;
    }

    public boolean onkoLaudalla(int alku[], String suunta, int pituus) {          //tarkista meneekö laiva yli pelilaudan
        if (suunta.equals("alas")) {
            if (alku[0] > (koko - pituus) || alku[1] < 0 || alku[0] <0) {
                return false;
            }
        } else if (suunta.equals("oikealle")) {
            if (alku[1] > (koko - pituus ) || alku[1] < 0 || alku[0] <0) {
                return false;
            }
        }
        return true;
    }
    
    public int haeLaivojenMaara(){
        return laivoja;
    }
    
    public Laiva[] haeLaivat(){
        return laivat;
    }
    public int haeKoko(){
        return koko;
    }
}
