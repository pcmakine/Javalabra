/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 *
 * @author pcmakine
 */
public class Laiva {

    private int[] xKoord;
    private int[] yKoord;
    private int[][] osumat;         //osuma yksi kohdassa osumat[0][0} (=xkoordinaatti) , osumat[0][1} (=ykoordinaatti)
    private Suunta suunta;
    int osumia;
   // Ruutu ruudut[];
    
    public Laiva(int xKoord[], int yKoord[], Suunta suunta) {
        this.xKoord = xKoord;
        this.yKoord = yKoord;
        this.suunta = suunta;
        osumat = new int[xKoord.length][2];
        osumia = 0;
    }

    public int montakoOsumaa(){
        return osumia;
    }
    public int[] haeXKoordinaatti() {
        return xKoord;
    }
    public int[][] haeOsumat(){
        return osumat;
    }

    public int[] haeYKoordinaatti() {
        return yKoord;
    }

    public Suunta haeSuunta() {
        return suunta;
    }

    public void asetaOsuma(int osumapiste[]) {    //osumapiste annetaan muodossa x, y
        osumat[osumia][0] = osumapiste[0];
        osumat[osumia][1] = osumapiste[1];
        osumia++;
    }

    public int haePituus() {
        if (suunta == Suunta.ALAS) {
            return yKoord.length;
        }
        return xKoord.length;
    }
    
    public boolean sankAlready(){
        if (osumia == xKoord.length) {
            return true;
        }
        return false;
    }


}