/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;


import java.util.*;
/**
 * Mallintaa pelilautaa ja pitää kirjaa sen Ruutu tyyppisistä ruutuolioista
 *
 * @author pcmakine
 *
 */
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
     * @param size Laudan sivun pituus. Lauta on aina neliön muotoinen
     * @param differentLengths Erimittaisten laivojen määrä
     */
    public Board(int size, int differentLengths) {
        this.squares = new Square[size][size];
        this.size = size;
        createSquares();
        this.numberofShips = 0;
        ships = new Ship[10];
        shipsDifferentSize = new int[differentLengths];
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
     * @param length Tarkasteltava laivan pituus
     * @return tekemättömien pituus mittaisten laivojen määrä
     */
    public int shipsAvailable(int length) {
        return (shipsDifferentSizeMax[length - 1] - shipsDifferentSize[length - 1]);
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
     * @param beginning laivan alkupiste. Alku[0] on laivan alkupisteen
     * x-koordinaatti ja alku[1] vastaavasti alkupisteen y-koordinaatti
     * @param direction Kertoo laivan suunnan, alas tai oikealle
     * @param length Laivan pituus
     * @return true jos laiva onnistutaan tekemään, muuten false
     */
    public boolean createShip(int beginning[], Direction direction, int length) {        //alku[0] on alun x koordinaatti ja alku[1] on alun y koordinaatti
        int[] xKoord = countxCoords(beginning, direction, length);
        int[] yKoord = countyCoords(beginning, direction, length);
        if (isOnBoard(beginning, direction, length) && noAdjacentShips(beginning, direction, length) && shipsAvailable(length) > 0) {
            Ship laiva = new Ship(xKoord, yKoord);
            ships[numberofShips] = laiva;
            shipsDifferentSize[length - 1]++;
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
     * @param beginning Taulukko alkupisteistä. Alku[0] x-koordinaatti, alku[1]
     * y-koordinaatti
     * @param direction Laivan suunta alkupisteestä. Oikealle tai alas
     * @param length Laivan pituus (ruutuina)
     * @return Palauttaa taulukon lasketuista y-koordinaateista
     */
    public int[] countyCoords(int beginning[], Direction direction, int length) {
        int yCoords[] = new int[length];
        if (direction == Direction.DOWN) {
            for (int i = 0; i < length; i++) {
                yCoords[i] = beginning[1] + i;
            }
        } else if (direction == Direction.RIGHT) {             //laiva vaakatasossa, y-koordinaatti ei muutu
            for (int i = 0; i < length; i++) {
                yCoords[i] = beginning[1];
            }
        }
        return yCoords;
    }

    /**
     * Laskee x-koordinaattien arvot annetulle alkupisteelle, suunnalle ja
     * pituudelle.
     *
     * @param beginning Taulukko alkupisteistä. Alku[0] x-koordinaatti, alku[1]
     * y-koordinaatti
     * @param direction Laivan suunta alkupisteestä. Oikealle tai alas
     * @param length Laivan pituus (ruutuina)
     * @return Palauttaa taulukon lasketuista x-koordinaateista
     */
    public int[] countxCoords(int beginning[], Direction direction, int length) {
        int xCoords[] = new int[length];
        if (direction == Direction.RIGHT) {
            for (int i = 0; i < length; i++) {
                xCoords[i] = beginning[0] + i;
            }
        } else if (direction == Direction.DOWN) {
            for (int i = 0; i < length; i++) {
                xCoords[i] = beginning[0];
            }
        }
        return xCoords;
    }

    /**
     * Tarkistaa menisikö laiva jota yritetään tehdä laudan ulkopuolelle
     * @param beginning Taulukko jossa alku[0] on laivan alkupisteen x-koordinaatti
     * ja alku[1] alkupisteen y-koordinaatti
     * @param direction Suunta johon laiva menee alkupisteestä. Joko oikealle tai
     * alas
     * @param length Kertoo laivan pituuden
     * @return Palauttaa true jos laiva on laudalla ja muuten false
     *
     */
    public boolean isOnBoard(int beginning[], Direction direction, int length) {
        if (direction == Direction.DOWN) {
            if (beginning[1] > (size - length) || beginning[0] < 0 || beginning[1] < 0) {
                return false;
            }
        } else if (direction == Direction.RIGHT) {
            if (beginning[0] > (size - length) || beginning[0] < 0 || beginning[1] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tutkii ovatko ruudut joihin laivaa yritetään tehdä lukittuina. Jos ovat
     * lukittuina, laivaa ei voi tehdä. Laivoja ei siis voi tehdä vierekkäin.
     *
     * @param beginning Taulukko jossa alku[0] on laivan alkupisteen x-koordinaatti
     * @param direction Laivan suunta alkupisteestä. Alas tai oikealle
     * @param length Laivan pituus (ruutuina)
     * @return true jos laivan vieressä ei ole laivoja, muuten false
     */
    public boolean noAdjacentShips(int beginning[], Direction direction, int length) {        //laivaa ei voi tehdä jos viereisissä ruuduissa on laiva
        int[] xCoords = countxCoords(beginning, direction, length);
        int[] yCoords = countyCoords(beginning, direction, length);
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
        String toPrint = "  0 1 2 3 4 5 6 7 8 9\n";
        for (int i = 0; i < this.size; i++) {
            toPrint = toPrint + i + " ";
            for (int j = 0; j < this.size; j++) {
                toPrint = toPrint + squares[j][i] + " ";
            }
            toPrint = toPrint + "\n";
        }
        return toPrint;
    }
}