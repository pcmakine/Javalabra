/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 * Laudan yksi ruutu. Tietää, onko siinä laivaa ja onko viereisessä ruudussa
 * laivaa (onkolukittu) sekä onko ruutuun ammuttu
 *
 * @author pcmakine
 */
public class Square {
/**
 * Kertoo onko ruutuun ammuttu. True jos on, muuten false
 */
    private boolean shot;
    /**
     * True jos ruutu on lukittu eli viereisessä ruudussa on laiva
     */
    private boolean locked;
    /**
     * True jos ruudussa on laiva
     */
    private boolean ship;
    /**
     * Ruudun x-koordinaatti
     */
    private int x;
    /**
     * Ruudun y-koordinaatti
     */
    private int y;

    /**
     * Tekee uuden ruutu -olion ja asettaa muuttujien alkuarvot
     * @param x ruudun x -koordinaatti
     * @param y ruudun y -koordinaatti
     */
    public Square(int x, int y) {
        this.shot = false;
        this.locked = false;
        this.ship = false;
        this.x = x;
        this.y = y;
    }   
 
    
    public void setShot() {
        this.shot = true;
    }
/**
 * Tarkistaa onko ruutuun ammuttu
 * @return true jos on ammuttu, muuten false
 */
    public boolean isShot() {
        return shot;
    }

    public void setLocked() {
        locked = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Tarkistaa onko ruudussa laivaa
     * @return true jos on laiva, muuten false
     */
    public boolean isShip() {
        return ship;
    }

    public void setShip() {
        ship = true;
    }

    /**
     * Tarkistaa onko ruutu lukittu eli onko viereisessä ruudussa laivaa
     * @return true jos on lukittu, muuten false
     */
    public boolean isLocked() {
        return locked;
    }
}