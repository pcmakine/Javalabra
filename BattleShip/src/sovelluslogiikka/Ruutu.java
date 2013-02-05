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
    private boolean laiva;
    private int x;
    private int y;

    public Ruutu(int x, int y) {
        this.onAmmuttu = false;
        this.onkoLukittu = false;
        this.laiva = false;
        this.x = x;
        this.y = y;
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

    public boolean onkoLaiva() {
        return laiva;
    }

    public void asetaLaiva() {
        laiva = true;
    }

    public boolean onkoLukittu() {
        return onkoLukittu;
    }

    public String toString() {
        String tulostettava = "";
        if (onAmmuttu) {
            tulostettava = "X";
        } else {
            tulostettava = "O";
        }
        if (onkoLukittu) {
            tulostettava = "Z";
        } else {
            tulostettava = "O";
        }
        if (laiva) {
            tulostettava = "L";
        } else {
            tulostettava = "O";
        }
        return tulostettava;
    }
}