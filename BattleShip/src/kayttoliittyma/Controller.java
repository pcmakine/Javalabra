/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

/**
 *
 * @author pcmakine
 */
import sovelluslogiikka.Lauta;
import sovelluslogiikka.Pelaaja;
import sovelluslogiikka.Suunta;
import sovelluslogiikka.Laiva;

public class Controller {

    private Pelaaja pelaaja;
    private Pelaaja vastustaja;
    private Lauta laudat[] = new Lauta[2];             //laudat[0] pelaajan lauta ja laudat [1] vastustajan
    private Suunta suunta;
    private Vuoro vuoro;
    private boolean gameStarted;
    private boolean gameEnded;
    private Asker asker;
    private Draw drawer;

    public Controller(Pelaaja pelaaja) {
        this.pelaaja = pelaaja;
        this.vuoro = vuoro.PELAAJA;
        this.gameStarted = false;
        this.vastustaja = new Pelaaja(new Lauta(10, 4));
        laudat[0] = pelaaja.haeLauta();
        laudat[1] = vastustaja.haeLauta();
        this.gameEnded = false;
    }

    public void initController(Asker asker, Draw drawer) {
        this.asker = asker;
        this.drawer = drawer;
    }

    public void startGame() {
        if (!gameStarted) {
            if (pelaaja.haeLauta().allShipsDone()) {
                gameStarted = true;
                vastustaja.arvoLaivat();
                drawer.drawBoard(new Lauta[]{pelaaja.haeLauta(), vastustaja.haeLauta()});
            } else {
                asker.presentWarning("Ennenkuin aloitat pelin sinun on tehtävä laudallesi laivoja.\n"
                        + "Käytä lisää laivoja tai lisää satunnaisesti nappeja");
            }
        }
    }

    public void resetPlayer(Pelaaja pelaaja) {
        Lauta uusiLauta = new Lauta(pelaaja.haeLauta().haeKoko(), pelaaja.haeLauta().haeErimittaisiaLaivoja().length);
        Pelaaja uusiPelaaja = new Pelaaja(uusiLauta);
        if (pelaaja == this.pelaaja) {
            this.pelaaja = uusiPelaaja;
            laudat[0] = this.pelaaja.haeLauta();
        } else {
            this.vastustaja = uusiPelaaja;
            laudat[1] = vastustaja.haeLauta();
        }
    }

    public void newGame() {
        resetPlayer(pelaaja);
        resetPlayer(vastustaja);
        drawer.drawBoard(laudat);
        gameStarted = false;
        gameEnded = false;
    }

    public void showOpponentShips() {
        drawer.drawOpponentBoard(laudat[1]);
    }

    public void checkIfGameOver() {
        if (pelaaja.allShipsLost()) {
            asker.presentWarning("Valitettavasti kaikki laivasi upposivat ja hävisit pelin");
            gameEnded = true;
        } else if (vastustaja.allShipsLost()) {
            asker.presentWarning("Sait upotettua kaikki vastustajan laivat. Onneksi olkoon voitit!");
            gameEnded = true;
        }
    }

    public Pelaaja getVuorossaOlevaPelaaja() {
        if (vuoro == vuoro.PELAAJA) {
            return pelaaja;
        } else {
            return vastustaja;
        }
    }

    public void shoot(int x, int y) {
        if (gameStarted && !gameEnded) {
            if (!pelaaja.ammu(vastustaja.haeLauta(), new int[]{x, y})) {
                asker.presentWarning("Olet jo ampunut siihen ruutuun! Valitse toinen ruutu.");
            } else {
                checkIfGameOver();
                if (!gameEnded) {
                    vastustaja.randomShoot(pelaaja.haeLauta());
                    checkIfGameOver();
                }
                drawer.drawBoard(new Lauta[]{pelaaja.haeLauta(), vastustaja.haeLauta()});
            }
        }
    }

    public int[] continueWhere() {
        int availableShips = -1;
        int shipLength = -1;
        for (int i = 4; i > 0; i--) {
            if (getVuorossaOlevaPelaaja().haeLauta().laivojaMahtuuViela(i) > 0) {
                availableShips = getVuorossaOlevaPelaaja().haeLauta().laivojaMahtuuViela(i);
                shipLength = i;
                return new int[]{availableShips, shipLength};
            }
        }
        return new int[]{availableShips, shipLength};
    }

    public boolean addShips() {
        if (gameStarted) {
            asker.presentWarning("Peli on jo aloitettu! Et voi enää lisätä laivoja. Klikkaa ruutua vastustajan laudalta ampuaksesi.");
            return false;
        } else {
            int[] mark = continueWhere();
            if (mark[0] != -1) {
                try {
                    for (int i = mark[1]; i >= 1; i--) {                  //laivoja yhteensä
                        int pituus = i;
                        for (int j = getVuorossaOlevaPelaaja().haeLauta().haeErimittaisiaLaivoja()[continueWhere()[1] - 1]; j < (5 - i); j++) {       //kuinka monta tietyn mittaista laivaa
                            createShips(pituus);
                            if (continueWhere()[0] == -1) {
                                break;
                            }
                        }
                    }
                } catch (NullPointerException e) {
                }
                return true;
            } else {
                asker.presentWarning("Sinulla on jo maksimimäärä laivoja. Aika aloittaa peli!"
                        + " Aloita valitsemalla vastustajan laudalta ruutu johon haluata ampua");
                return false;
            }
        }
    }

    public void createShips(int pituus) {
        int[] alku = new int[2];
        do {
            if (pituus != 1) {
                if (asker.askDirection("Olet luomassa laivaa jonka pituus on " + pituus + " ruutua."
                        + "\nHaluatko tehdä pystysuuntaisen vai vaakasuuntaisen laivan?").equals("Horizontal ship")) {
                    suunta = Suunta.OIKEALLE;
                } else {
                    suunta = Suunta.ALAS;
                }
            } else {
                if (getVuorossaOlevaPelaaja().haeLauta().haeErimittaisiaLaivoja()[0] == 0) {
                    asker.presentWarning("Enää yhden mittaiset laivat jäljellä, paina ok jatkaaksesi");
                }
            }
            alku[0] = asker.askInput("Anna alkukoordinaatti x") - 1;
            alku[1] = asker.askInput("Anna alkukoordinaatti y") - 1;
            checkErrors(alku, suunta, pituus);
        } while (!getVuorossaOlevaPelaaja().haeLauta().onkoLaudalla(alku, suunta, pituus) || !getVuorossaOlevaPelaaja().haeLauta().vieressaEiLaivaa(alku, suunta, pituus));
        getVuorossaOlevaPelaaja().haeLauta().teeLaiva(alku, suunta, pituus);
        drawer.drawBoard(new Lauta[]{pelaaja.haeLauta(), vastustaja.haeLauta()});
    }

    public void checkErrors(int[] alku, Suunta suunta, int pituus) {
        if (!getVuorossaOlevaPelaaja().haeLauta().onkoLaudalla(alku, suunta, pituus)) {
            asker.presentWarning("Antamasi laiva menisi laudan ulkopuolelle. \n "
                    + "Ole hyvä ja anna uudet arvot");
        }
        if (!getVuorossaOlevaPelaaja().haeLauta().vieressaEiLaivaa(alku, suunta, pituus)) {
            asker.presentWarning("Antamasi laiva menisi olemassaolevan laivan viereen/päälle. \n"
                    + " Ole hyvä ja anna uudet arvot");
        }
    }

    public void addRandomShips() {
        if (gameStarted) {
            asker.presentWarning("Peli on jo aloitettu! Et voi enää lisätä laivoja. Klikkaa ruutua vastustajan laudalta ampuaksesi.");
        } else {
            int laivojayht = pelaaja.haeLauta().haeErimittaisiaLaivojaMax().length;
            if (continueWhere()[1] != laivojayht && continueWhere()[0] != pelaaja.haeLauta().haeErimittaisiaLaivojaMax()[laivojayht - 1]) {
                resetPlayer(pelaaja);
            }
            pelaaja.arvoLaivat();
            drawer.drawBoard(new Lauta[]{pelaaja.haeLauta(), vastustaja.haeLauta()});
        }
    }
}