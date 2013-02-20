/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 * Mallintaa yhtä laudalle tehtyä laivaa. Tietää koordinaattinsa sekä kuinka
 * monta osumaa on saanut ja osumien koordinaatit
 *
 * @author pcmakine
 */
public class Ship {

    /**
     * Laivan x -koordinaatit
     */
    private int[] xCoords;
    /**
     * Laivan y-koordinaatit
     */
    private int[] yCoords;
    /**
     * Osumien koordinaatit. osuma yksi kohdassa osumat[0][0} (=xkoordinaatti),
     * osumat[0][1} (=ykoordinaatti)
     */
    private int[][] hitCoords;
    /**
     * Osumien yhteismäärä
     */
    int hits;

    /**
     * Luo laiva olion ja asettaa muuttujille alkuarvot
     *
     * @param xCoords Laivan x -koordinaatit
     * @param yKoord Laivan y -koordinaatit
     */
    public Ship(int xCoords[], int yCoords[]) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        hitCoords = new int[xCoords.length][2];
        hits = 0;
    }

    public int getHits() {
        return hits;
    }

    public int[] getxCoords() {
        return xCoords;
    }

    public int[][] getHitCoords() {
        return hitCoords;
    }

    public int[] getyCoords() {
        return yCoords;
    }

    /**
     * Asettaa hitCoords taulukkoon parametrina annetun pisteen
     *
     * @param hitPoint piste joka halutaan laittaa osumataulukkoon. hitPoint[0]
     * on osumapisteen x -koordinaatti ja hitPoint[1] osumapisteen y -koordinaatti
     */
    public void setHitPoint(int hitPoint[]) {    //osumapiste annetaan muodossa x, y
        hitCoords[hits][0] = hitPoint[0];
        hitCoords[hits][1] = hitPoint[1];
        hits++;
    }

    public int getLength() {
        return xCoords.length;
    }

    /**
     * Tarkistaa onko laiva uponnut kokonaan
     * @return true jos on uponnut, muuten false
     */
    public boolean sankAlready() {
        if (hits == xCoords.length) {
            return true;
        }
        return false;
    }
}