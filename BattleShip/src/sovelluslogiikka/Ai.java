/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 * Mallintaa tietokonepelaajaa ja perii pelaaja -olion metodit. Tällä hetkellä
 * käytössä on kaksi mahdollista ampumismetodia.
 *
 *
 *
 * @author Pete
 */
import java.util.*;

public class Ai extends Player {

    /**
     * Ruutu -olio johon ollaan ampumassa
     */
    private Square targetSquare;
    /**
     * Arraylist ruutu -olioista, joissa vielä mahdollisesti saattaa olla
     * upottamaton laiva
     */
    private ArrayList<Square> maybeShip;
    /**
     * Tieto siitä onko viimeksi löydetty laiva saatu jo upotettua. Aluksi true
     * kun laivoja ei vielä ole löydetty
     */
    private boolean shipSank;
    /**
     * Arraylist osumista jotka löydettyyn laivaan on saatu tehtyä
     */
    private ArrayList<Square> hits;
    /**
     * Tieto siitä voidaanko päätellä löydetyn laivan olevan vaakatasossa. True
     * jos voidaan päätellä, muuten false
     */
    private boolean horizontal;
    /**
     * Tieto siitä voidaanko päätellä löydetyn laivan olevan vertikaalisesti.
     * True jos voidaan päätellä, muuten false
     */
    private boolean vertical;

    /**
     * Tekee uuden tietokonepelaajan ja alustaa muuttujien arvot
     *
     * @param board tietokonepelaajan oma pelilauta
     * @param opponentBoard tietokonepelaajan vastustajan lauta. Saa viitteen
     * tähän jotta voi pitää muistissa ruudut joissa saattaa olla vielä laiva.
     * Voitaisiin toteuttaa myös koordinaatteina jolloin vastustajan lautaa ei
     * tarvittaisi.
     */
    public Ai(Board board, Board opponentBoard) {
        super(board);
        maybeShip = new ArrayList<>();
        for (int i = 0; i < opponentBoard.getSize(); i++) {
            for (int j = 0; j < opponentBoard.getSize(); j++) {
                maybeShip.add(opponentBoard.getSquares()[i][j]);
            }
        }
        hits = new ArrayList<>();
        shipSank = true;
    }

    /**
     * Ampuu täysin satunnaisesti vaikka löydettyjen laivojen perusteella
     * tiedettäisiin ettei ruudussa voi sääntöjen mukaan olla laivaa. Käyttää
     * yläluokan shoot -metodia josta johtuen ei kuitenkaan ammu ruutuihin
     * joihin on ampunut aikaisemmin.
     *
     * @param lauta lauta jonka ruutuun halutaan ampua
     */
    public void randomShoot(Board lauta) {
        boolean successful;
        do {
            int[] target = randomSquare();
            successful = shoot(lauta, target);
        } while (!successful);
    }

    /**
     * Yrittää aina löydettyään laivan upottaa sen. Jos osuu enemmän kuin yhden
     * kerran osaa päätellä onko laiva vaakatasossa vai pystysuorassa.
     * Upotettuaan löydetyn laivan jatkaa satunnaista ampumista kuitenkin niin
     * ettei ammu ruutuihin joista voidaan löydettyjen laivojen perusteella
     * päätellä ettei niissä voi olla laivaa. Käyttää apunaan yläluokan shoot
     * metodia joten ei ammu myöskään ruutuihin joihin on aikaisemmin ampunut.
     *
     * Ampuu arraylist tyyppisessä maybeShip oliossa olevia ruutuja joissa saat-
     * taa vielä olla laivoja paitsi jos ollaan jo löydetty jokin laiva jolloin
     * se yritetään upottaa käyttämällä trysink metodia.
     *
     * @param board lauta jonka ruutuun halutaan ampua
     */
    public void smartShoot(Board board) {
        boolean successful;

        do {
            if (!shipSank) {
                targetSquare = trySink(board);
            } else {
                targetSquare = maybeShip.get((int) (Math.random() * maybeShip.size()));
            }
            int[] target = new int[]{targetSquare.getX(), targetSquare.getY()};
            successful = shoot(board, target);
            if (targetSquare.isShip() && successful) {
                hits.add(targetSquare);
                shipSank = false;
                setNoShip(targetSquare, board);
            }
        } while (!successful);
        checkOrientation();
    }

    /**
     * Päättelee onko laiva pysty- vai vaakasuuntainen jos osumia on kaksi tai
     * enemmän
     */
    private void checkOrientation() {
        for (int i = 0; i < hits.size(); i++) {
            for (int j = i + 1; j < hits.size(); j++) {
                if (hits.get(i).getX() == hits.get(j).getX()) {
                    vertical = true;
                }
                if (hits.get(i).getY() == hits.get(j).getY()) {
                    horizontal = true;
                }
            }
        }
    }

    /**
     * Yrittää löytää ruudun johon ampumalla löydetty laiva saataisiin upotettua
     * Jos tiedetään laivan olevan vaakasuuntainen kutsuu sinkHorizontal metodia
     * ja jos laivan tiedetään olevan pystysuuntainen kutsuu sinkVertical
     * metodia. Muutoin kutsuu satunnaisesti jompaakumpaa.
     *
     * @param lauta lauta, jonka ruutua ollaan ampumassa
     * @return palauttaa ruudun johon yritetään ampua
     */
    public Square trySink(Board lauta) {
        if (horizontal) {
            sinkHorizontal(lauta);
        } else {
            if (vertical) {
                sinkVertical(lauta);
            } else if (Math.random() > 0.5) {
                sinkVertical(lauta);
            } else {
                sinkHorizontal(lauta);
            }
        }
        return targetSquare;
    }

    /**
     * Olettaa löydetyn laivan olevan vaakatasossa ja valitsee satunnaisesti
     * ruudun jo osutun ruudun vasemmalta tai oikealta puolelta. Käyttää apunaan
     * randomIncrement metodia, joka tekee satunnaisen valinnan jo osutun kohdan
     * vasemmasta tai oikeasta puolesta.
     *
     * @param lauta lauta jonka ruutua ollaan ampumassa
     * @return palauttaa ruudun johon halutaan ampua
     */
    private Square sinkHorizontal(Board lauta) {
        int randomIncrement;
        do {
            int randomIndex = (int) (Math.random() * hits.size());
            randomIncrement = randomIncrements(lauta, hits.get(randomIndex).getX());
            if (randomIncrement != -10) {
                targetSquare = lauta.getSquares()[hits.get(randomIndex).getX() + randomIncrement][hits.get(randomIndex).getY()];
            }
        } while (randomIncrement == -10);
        return targetSquare;
    }

    /**
     * Olettaa löydetyn laivan olevan pystysuorassa ja valitsee satunnaisesti
     * ruudun jo osutun ruudun ylä- tai alapuolelta. Käyttää apunaan
     * randomIncrement metodia, joka tekee satunnaisen valinnan jo osutun kohdan
     * yläpuolelat tai alapuolelta.
     *
     * @param board lauta jonka ruutua ollaan ampumassa
     * @return palauttaa ruudun johon halutaan ampua
     */
    private Square sinkVertical(Board board) {
        int randomIncrement;
        do {
            int randomIndex = (int) (Math.random() * hits.size());
            randomIncrement = randomIncrements(board, hits.get(randomIndex).getY());
            if (randomIncrement != -10) {
                targetSquare = board.getSquares()[hits.get(randomIndex).getX()][hits.get(randomIndex).getY() + randomIncrement];
            }
        } while (randomIncrement == -10);
        return targetSquare;
    }

    /**
     * apumetodi jota sinkVertical ja sinkHorizontal käyttävät. Arpoo
     * satunnaisen luvun -1 tai 1. Tämä luku on muutos edellisen ampumapisteen
     * x- tai y- koordinaattiin riippuen kutsuvasta metodista.
     *
     * @param board lauta jonka ruutua ollaan ampumassa
     * @param previousHit x- tai y-koordinaatti johon ollaan edellisellä
     * kierroksella ammuttu ja jota ollaan nyt muuttamassa.
     * @return palauttaa 1 tai -1 satunnaisesti. Jos kuitenkin uusi osumapiste
     * olisi laudan ulkopuolella palautetaan -10.
     */
    private int randomIncrements(Board board, int previousHit) {
        int randomIncrement = (int) (-1 * 2 + Math.random() * 4);
        if (previousHit + randomIncrement > board.getSize() - 1 || previousHit + randomIncrement < 0 || randomIncrement == 0) {
            return -10;
        }
        return randomIncrement;
    }

    /**
     * Apumetodi joka tutkii upposiko ammuttu laiva. Jos upposi putsataan
     * muistissa olleet osumat ja asetetaan shipSank = true sekä palautetaan
     * laiva joka upposi.
     *
     * @param hit Ruutu johon ammuttiin
     * @param lauta Lauta jonka ruutuun ammuttiin
     * @return palauttaa laivan joka upposi jos laiva saatiin upotettua. Muussa
     * tapauksessa palauttaa null
     */
    private Ship targetedShipSank(Square hit, Board lauta) {
        Ship[] ships = lauta.getShips();
        for (int i = 0; i < ships.length; i++) {
            int[] xCoords = lauta.getShips()[i].getxCoords();
            int[] yCoords = lauta.getShips()[i].getyCoords();
            for (int j = 0; j < xCoords.length; j++) {
                if (xCoords[j] == hit.getX() && yCoords[j] == hit.getY() && ships[i].sankAlready()) {
                    shipSank = true;
                    hits.clear();
                    return ships[i];
                }
            }
        }
        return null;
    }

    /**
     * Huolehtii sitä että arraylist tyyppisessä maybeShip muuttujassa on
     * ainoastaan ruudut joissa ei voida päätellä olevan laivaa. Jos laiva
     * saatiin tällä kierroksella upotettua poistaa arraylististä laivan
     * ympärillä olleet ruudut, sekä laivan ruudut sillä niissähän ei laivaa voi
     * pelin sääntöjen mukaan olla. Käyttää ruutujen laskemiseen laudan
     * getAdjacentSquares metodia.
     *
     * @param hit ruutu jota ammuttiin
     * @param board lauta jonka ruutua ammuttiin
     */
    public void setNoShip(Square hit, Board board) {
        Ship drown = targetedShipSank(hit, board);
        if (drown != null) {
            vertical = false;
            horizontal = false;
            int[] xCoords = drown.getxCoords();
            int[] yCoords = drown.getyCoords();
            for (int j = 0; j < xCoords.length; j++) {
                ArrayList<Square> adjacents = board.getAdjacentSquares(board.getSquares()[xCoords[j]][yCoords[j]]);
                for (int i = 0; i < adjacents.size(); i++) {
                    if (maybeShip.indexOf(adjacents.get(i)) != -1) {
                        maybeShip.remove(maybeShip.indexOf(adjacents.get(i)));
                    }
                }
            }
        }
    }
}