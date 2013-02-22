/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;


import java.lang.Math;

/**
 * Mallintaa pelaajaa. Tuntee pelilautansa.
 *
 * @author pcmakine
 */
public class Player {

    /**
     * Pelaajan pelilauta -olio
     */
    private Board board;

    /**
     * Luo uuden peelaaja -olion ja asettaa pelaajalle parametrina annettavan
     * laudan
     * @param board pelaajan pelilauta
     */
    public Player(Board board) {
        this.board = board;
    }

    /**
     * Täyttää pelaajan laudan satunnaiseen paikkaan asetetuilla laivoilla.
     * Ei tuhoa laudalla jo olevia laivoja, vaan tekee satunnaisesti vain 
     * laudalta puuttuvat laivat.
     */
    public void randomShips() {
        boolean success;
        for (int i = board.getShipsDifferentSizeMax().length; i > 0; i--) {        //käydään läpi kaikki erimittaiset laivat pisimmästä lyhimpään
            int availableShips = board.shipsAvailable(i);
            for (int j = 0; j < availableShips; j++) {                                             //tehdään niin monta tämän pituista laivaa kuin sääntöjen mukaan saadaan
                do {
                    success = board.createShip(randomSquare(), randomDirection(), i);
                } while (success == false);
            }
        }
    }
    
        /**
     * Tutkii onko laivoja tehty maksimimäärä.
     * @return Jos laivoja on laudalla yhtä paljon kuin laivoja saa maksimissaan olla
     * palautetaan true, muuten false.
     */
    public boolean allShipsDone(){
        int maxShips = 0;
        for (int i = 0; i < board.getShipsDifferentSizeMax().length; i++) {
            maxShips = maxShips + board.getShipsDifferentSizeMax()[i];
        }
        return board.getNumberofShips() == maxShips;     
    }

    /**
     * Tutkii onko kaikki pelaajan laivat upotettu
     * @return Jos kaikki pelaajan laivat on upotettu palauttaa true, muuten
     * false
     */
    public boolean allShipsLost() {
        Ship[] laivat = board.getShips();
        for (int j = 0; j < laivat.length; j++) {
            if (!laivat[j].sankAlready()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Arpoo satunnaisen koordinaatit
     * @return palauttaa arpomansa koordinaatit yksiulotteisessa taulukossa 
     * indekseissä nolla ja yksi
     */
    public int[] randomSquare() {
        return new int[]{(int) (Math.random() * 10), (int) (Math.random() * 10)};
    }

    /**
     * Arpoo satunnaisluvun nollan ja yhden välillä ja palauttaa satunnaisesti
     * suunnan alas tai oikealle
     * @return jos arvottu luku on pienempi kuin 0,5 palautetaan alas, muuten
     * oikealle
     */
    public Direction randomDirection() {
        if ((Math.random()) < 0.5) {
            return Direction.DOWN;
        } else {
            return Direction.RIGHT;
        }
    }

    public Board getBoard() {
        return board;
    }
/**
 * Apumetodi joka tarkistaa onko ruutuun johon yritetään ampua ammuttu jo aikai-
 * semmin
 * @param board lauta johon yritetään ampua
 * @param target koordinaatit joihin yritetään ampua. target[0] on x -koordinaat-
 * ti ja target[1] y -koordinaatti
 * @return palauttaa true jos ruutuun on jo aikaisemmin ammuttu, muuten false
 */
    private boolean checkIfShotAlready(Board board, int[] target) {
        if (board.getSquares()[target[0]][target[1]].isShot()) {
            return true;
        }
        return false;
    }
    
    /**
     * Ampuu parametrina annetun laudan ruutuun joka löytyy koordinaateista
     * jotka myös annetaan parametrina. Jos osutaan laivaan asettaa laivalle 
     * osumat. Asettaa myös ammutulle ruutu -oliolle tiedon että sitä on ammut-
     * tu.
     * @param board lauta jonka ruutuun ollaaan ampumassa
     * @param target kohteen koordinaatit, target[0] on x -koordinaat-
     * ti ja target[1] y -koordinaatti
     * @return palauttaa true jos ampuminen onnistui, eli jos ruutuun ei oltu
     * ammuttu aikaisemmin, tai jos annetut koordinaatit eivät ole laudalla
     */
    public boolean shoot(Board board, int[] target) {
        if (checkIfShotAlready(board, target) || !board.isOnBoard(target, Direction.DOWN, 1)) {
            return false;
        }
        board.getSquares()[target[0]][target[1]].setShot();
        for (int i = 0; i < board.getNumberofShips(); i++) {    //kay läpi kaikki laivat
            Ship ships[] = board.getShips();
            int[] xKoord = ships[i].getxCoords();
            int[] yKoord = ships[i].getyCoords();
            for (int j = 0; j < xKoord.length; j++) {
                if (xKoord[j] == target[0] && yKoord[j] == target[1]) {
                    ships[i].setHitPoint(target);
                }
            }
        }
        return true;
    }

}