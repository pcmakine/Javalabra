/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 *
 * @author pcmakine
 */
public class Ruutu {

    private boolean onAmmuttu;
    private boolean onkoLukittu;
    private int x;
    private int y;

    public Ruutu(int x, int y) {
        this.onAmmuttu = false;
        this.onkoLukittu = false;
    }

    public void ampuTulee() {
        this.onAmmuttu = true;
    }

    public boolean onkoAmmuttu() {
        return onAmmuttu;
    }

    public void lukitseRuutu() {
        onkoLukittu = true;
    }

    public int haeX() {
        return x;
    }

    public int haeY() {
        return y;
    }

    public boolean onkoLukittu() {
        return onkoLukittu;
    }

    /*   public boolean onkoSama(Ruutu ruutu) {
     return this.x == ruutu.x && this.y == ruutu.y;
     }*/
    public String toString() {
        String tulostettava = "";
        if (onAmmuttu) {
            tulostettava = "X";
        } else if (onkoLukittu) {
            tulostettava = "Z";
        } else {
            tulostettava = "O";
        }
        return tulostettava;
    }
}