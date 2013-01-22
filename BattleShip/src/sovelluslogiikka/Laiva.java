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
    
    
    public Laiva(int xKoord[], int yKoord[]){
        this.xKoord = xKoord;
        this.yKoord = yKoord;
    }
    
    public int[] haeXKoordinaatti(){
        return xKoord;
    }
    public int[] haeYKoordinaatti(){
        return yKoord;
    }
}
