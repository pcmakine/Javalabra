/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 * Mallintaa pelilautaa ja pitää kirjaa sen Ruutu tyyppisistä ruutuolioista
 *
 * @author pcmakine
 *
 */
import java.util.*;

public class Board {

    /**
     * Laudan ruutuja kuvaavat ruutu-oliot kaksiulotteisessa taulukossa
     */
    private Square[][] squares;
    /**
     * Laudan sivun pituus
     */
    private int size;
    /**
     * taulukko laiva tyyppisistä olioista jotka on luotu laudalle
     */
    private Ship[] ships;
    /**
     * Tällä hetkellä laudalle luotujen laivojen kokonaismäärä
     */
    private int numberofShips;
    /**
     * 
     */
    /**
     * Eri kokoisten tehtyjen laivojen määrä. Indeksissä n-1 n-kokoisten tehtyjen laivojen määrä
     */
    private int[] shipsDifferentSize;   
    
    /**
     * Eri kokoisten laivojen enimmäismäärä laudalla. Indeksissä n-1 n-kokoisten laivojen maksimimäärä
     */
    private int[] shipsDifferentSizeMax = {4, 3, 2, 1};

    /**
     * Tekee uuden Lauta olion. Saa parametrinaan laudan koon ja erimittaisten
     * laivojen määrän. Kutsuu teeruudut metodia joka tekee Ruutu taulukkoon
     * ruutuja mallintavat ruutu oliot.
     *
     * @param koko Laudan sivun pituus. Lauta on aina neliön muotoinen
     * @param erimittaisia Erimittaisten laivojen määrä
     */
    public Board(int koko, int erimittaisia) {
        this.squares = new Square[koko][koko];
        this.size = koko;
        createSquares();
        this.numberofShips = 0;
        ships = new Ship[10];
        shipsDifferentSize = new int[erimittaisia];
    }

    /**
     * Tekee Ruutu olioita ja asettaa ne taulukkoon myöhempää käyttöä varten.
     */
    private void createSquares() {
        for (int i = 0; i < size; i++) {            //rivi
            for (int j = 0; j < size; j++) {         //sarake
                squares[j][i] = new Square(j, i);
            }
        }
    }

    /**
     * Palauttaa parametrinä annetun ruutu -olion ympäröivät ruudut sekä myös
     * parametrinä annetun ruutu -olion.
     * @param square Ruutu -olio jonka ympäröivät ruudut halutaan selvittää
     * @return Annetun ruudun ympäriltä löytyvät ruudut arraylistinä
     */
    public ArrayList getAdjacentSquares(Square square) {
        ArrayList<Square> adjacent = new ArrayList<>();
        for (int j = 0; j < 3; j++) {                   //käydään läpi ruudut x-1, x ja x+1              
            for (int k = 0; k < 3; k++) {               //käydään läpi ruudut y-1, y ja y+1
                if (square.getX() == 0 && j == 0) {              //jos ollaan vasemmassa reunassa ei yritetä lukita oleamatonta ruutua
                    j++;
                }
                if (square.getY() == 0 && k == 0) {             //jos ollaan yläreunassa ei yritetä lukita olematonta ruutua
                    k++;
                }
                if ((square.getX() - 1 + j) < size && (square.getY() - 1 + k) < size) {             //jos ei mennä laivan ala- tai oikeasta reunasta yli    
                    adjacent.add(squares[square.getX() - 1 + j][square.getY() - 1 + k]);
                }
            }
        }
        return adjacent;
    }

    /**
     * Lukitsee ruudut jotka ovat annettujen koordinaattien ympärillä. 
     * Lukitseminen tarkoittaa että lukittuihin ruutuihin ei enää myöhemmin
     * voida tehdä uusia laivoja. Käyttää apunaan getAdjacentSquares metodia.
     * @param xCoords x -koordinaatit joiden ympäriltä ruudut halutaan lukita
     * @param yCoords y -koordinaatit joiden ympäriltä ruudut halutaan lukita
     */
    public void lockSquares(int xCoords[], int yCoords[]) {
        ArrayList<Square> adjacents = new ArrayList<>();
        for (int i = 0; i < xCoords.length; i++) {
            adjacents = getAdjacentSquares(squares[xCoords[i]][yCoords[i]]);
            for (int j = 0; j < adjacents.size(); j++) {
                adjacents.get(j).setLocked();
            }
        }
    }

  
    /**
     * Tutkii kuinka monta tietynmittaista laivaa voidaan vielä tehdä.
     *
     * @param pituus Tarkasteltava laivan pituus
     * @return tekemättömien pituus mittaisten laivojen määrä
     */
    public int shipsAvailable(int pituus) {
        return (shipsDifferentSizeMax[pituus - 1] - shipsDifferentSize[pituus - 1]);
    }

    /**
     * Asettaa annettujen koordinaattien ruutu olioille tiedon että ruudussa on
     * laiva.
     * @param xKoord Taulukko laivan x -koordinaateista.
     * @param yKoord Taulukko laivan y -koordinaateista.
     */
    public void setShipsonSquares(int[] xKoord, int[] yKoord) {
        for (int i = 0; i < yKoord.length; i++) {
            squares[xKoord[i]][yKoord[i]].setShip();
        }
    }

    /**
     * Tekee yhden laivan, jos mahdollista. Ei tee laivaa:
     * jos se olisi osittain tai kokonaan laudan ulkopuolella 
     * jos laiva olisi osittain tai kokonaan olemassaolevan laivan viereisissä
     * ruuduissa tai
     * jos annetun pittuden laivoja on tehty jo maksimimäärä.
     * Saa parametreina laivan alkupisteen, suunnan (alas tai oikealle), sekä 
     * pituuden. Laivan tekemisen jälkeen lukitsee vielä ruudut joissa laiva on 
     * sekä ympäröivät ruudut, jottei niihin voida
     * tehdä uusia laivoja. Tähän käytetään lockSquares metodia.
     *
     * @param alku laivan alkupiste. Alku[0] on laivan alkupisteen
     * x-koordinaatti ja alku[1] vastaavasti alkupisteen y-koordinaatti
     * @param suunta Kertoo laivan suunnan, alas tai oikealle
     * @param pituus Laivan pituus
     * @return true jos laiva onnistutaan tekemään, muuten false
     */
    public boolean createShip(int alku[], Direction suunta, int pituus) {        //alku[0] on alun x koordinaatti ja alku[1] on alun y koordinaatti
        int[] xKoord = countxCoords(alku, suunta, pituus);
        int[] yKoord = countyCoords(alku, suunta, pituus);
        if (isOnBoard(alku, suunta, pituus) && noAdjacentShips(alku, suunta, pituus) && shipsAvailable(pituus) > 0) {
            Ship laiva = new Ship(xKoord, yKoord);
            ships[numberofShips] = laiva;
            shipsDifferentSize[pituus - 1]++;
            numberofShips++;
            setShipsonSquares(xKoord, yKoord);
            lockSquares(xKoord, yKoord);
            return true;
        }
        return false;
    }

    /**
     * Laskee y -koordinaattien arvot annetulle alkupisteelle, suunnalle ja
     * pituudelle.
     *
     * @param alku Taulukko alkupisteistä. Alku[0] x-koordinaatti, alku[1]
     * y-koordinaatti
     * @param suunta Laivan suunta alkupisteestä. Oikealle tai alas
     * @param pituus Laivan pituus (ruutuina)
     * @return Palauttaa taulukon lasketuista y-koordinaateista
     */
    public int[] countyCoords(int alku[], Direction suunta, int pituus) {
        int yCoords[] = new int[pituus];
        if (suunta == Direction.DOWN) {
            for (int i = 0; i < pituus; i++) {
                yCoords[i] = alku[1] + i;
            }
        } else if (suunta == Direction.RIGHT) {             //laiva vaakatasossa, y-koordinaatti ei muutu
            for (int i = 0; i < pituus; i++) {
                yCoords[i] = alku[1];
            }
        }
        return yCoords;
    }

    /**
     * Laskee x-koordinaattien arvot annetulle alkupisteelle, suunnalle ja
     * pituudelle.
     *
     * @param alku Taulukko alkupisteistä. Alku[0] x-koordinaatti, alku[1]
     * y-koordinaatti
     * @param suunta Laivan suunta alkupisteestä. Oikealle tai alas
     * @param pituus Laivan pituus (ruutuina)
     * @return Palauttaa taulukon lasketuista x-koordinaateista
     */
    public int[] countxCoords(int alku[], Direction suunta, int pituus) {
        int xCoords[] = new int[pituus];
        if (suunta == Direction.RIGHT) {
            for (int i = 0; i < pituus; i++) {
                xCoords[i] = alku[0] + i;
            }
        } else if (suunta == Direction.DOWN) {
            for (int i = 0; i < pituus; i++) {
                xCoords[i] = alku[0];
            }
        }
        return xCoords;
    }

    /**
     * Tarkistaa menisikö laiva jota yritetään tehdä laudan ulkopuolelle
     * @param alku Taulukko jossa alku[0] on laivan alkupisteen x-koordinaatti
     * ja alku[1] alkupisteen y-koordinaatti
     * @param suunta Suunta johon laiva menee alkupisteestä. Joko oikealle tai
     * alas
     * @param pituus Kertoo laivan pituuden
     * @return Palauttaa true jos laiva on laudalla ja muuten false
     *
     */
    public boolean isOnBoard(int alku[], Direction suunta, int pituus) {
        if (suunta == Direction.DOWN) {
            if (alku[1] > (size - pituus) || alku[0] < 0 || alku[1] < 0) {
                return false;
            }
        } else if (suunta == Direction.RIGHT) {
            if (alku[0] > (size - pituus) || alku[0] < 0 || alku[1] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tutkii ovatko ruudut joihin laivaa yritetään tehdä lukittuina. Jos ovat
     * lukittuina, laivaa ei voi tehdä. Laivoja ei siis voi tehdä vierekkäin.
     *
     * @param alku Taulukko jossa alku[0] on laivan alkupisteen x-koordinaatti
     * @param suunta Laivan suunta alkupisteestä. Alas tai oikealle
     * @param pituus Laivan pituus (ruutuina)
     * @return
     */
    public boolean noAdjacentShips(int alku[], Direction suunta, int pituus) {        //laivaa ei voi tehdä jos viereisissä ruuduissa on laiva
        int[] xCoords = countxCoords(alku, suunta, pituus);
        int[] yCoords = countyCoords(alku, suunta, pituus);
        for (int i = 0; i < xCoords.length; i++) {
            for (int j = 0; j < yCoords.length; j++) {
                try {
                    if (squares[xCoords[i]][yCoords[j]].isLocked()) {
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return true;
    }

    public int getNumberofShips() {
        return numberofShips;
    }

    public Ship[] getShips() {
        return ships;
    }

    public int getSize() {
        return size;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public int[] getShipsDifferentSize() {
        return shipsDifferentSize;
    }

    public int[] getShipsDifferentSizeMax() {
        return shipsDifferentSizeMax;
    }

    /**
     * Metodi testaamisen tueksi
     * @return merkkijonoesitys pelilaudasta
     */
    @Override
    public String toString() {
        String tulostettava = "  0 1 2 3 4 5 6 7 8 9\n";
        for (int i = 0; i < this.size; i++) {
            tulostettava = tulostettava + i + " ";
            for (int j = 0; j < this.size; j++) {
                tulostettava = tulostettava + squares[j][i] + " ";
            }
            tulostettava = tulostettava + "\n";
        }
        return tulostettava;
    }
}