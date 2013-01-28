/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 *
 * @author pcmakine
 */
public class Pelaaja {

    public Pelaaja() {
    }

    public boolean tarkistaOnkoJoAmmuttu(Lauta lauta, int[] kohde) {
        if(lauta.haeRuudut()[kohde[0]][kohde[1]].onkoAmmuttu()) {
            System.out.println("ammuttu jo");
            return true;
        }
        return false;
    }

    public boolean ammu(Lauta lauta, int[] kohde) {     //palauttaa true jos osui, muuten false
        if(tarkistaOnkoJoAmmuttu(lauta, kohde)){
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