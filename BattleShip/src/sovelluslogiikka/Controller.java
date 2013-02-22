/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

/**
 *
 * @author pcmakine
 */
import kayttoliittyma.Asker;
import kayttoliittyma.Draw;
import kayttoliittyma.Turn;
import kayttoliittyma.windowComponents.sidePaneComponents.StatsArea;
import highScore.HighScore;

/**
 * Toimii linkkinä graafisen käyttöliittymän ja sovelluslogiikan välillä, sekä
 * tutkii onko peli yhä käynnissä vai onko jompikumpi pelaaja voittanut.
 *
 * @author Pete
 */
public class Controller {

    /**
     * Pelaaja olio, mallintaa toista pelaajaa
     */
    private Player player;
    /**
     * Mallintaa tietokone vastustajaa
     */
    private Ai computerOpponent;
    /**
     * Taulukko pelaajien laudoista. boards[0] on pelaajan lauta ja boards[1] on
     * vastustajan lauta
     */
    private Board boards[] = new Board[2];
    /**
     * Kertoo kenen vuoro on. Tarvitaan lähinnä jos peliin saadaan kehitettyä
     * jossain vaiheessa kaksinpeli
     */
    private Turn vuoro;
    /**
     * True jos peli on aloitettu, muuten false
     */
    private boolean gameStarted;
    /**
     * True jos peli on loppunut, muuten false
     */
    private boolean gameEnded;
    /**
     * Käyttäjän guissa valitsema laivan pituus
     */
    private int length;
    /**
     * Käyttäjän guissa valitsema laivan suunta
     */
    private Direction direction;
    /**
     * Olio joka hoitaa kyselyt sekä viestien esittämisen käyttäjälle
     */
    private Asker asker;
    /**
     * Piirto -olio joka piirtää laudan aina vuoron jälkeen. Käytännössä vaihtaa
     * pelilautana toimivien nappuloiden taustaväriä tai asettaa niihin tekstiä
     */
    private Draw drawer;
    /**
     * Paneeli ohjelman pääikkunassa, joka näyttää kuinka monta laivaa kullakin
     * pelaajalla on jäljellä
     */
    private StatsArea stats;
    /**
     * Pitää huolta parhaiden tulosten listaamisesta
     */
    private HighScore highscore;
    /**
     * Vuoron järjestysnumero, jota ollaan pelaamassa
     */
    private int turns;

    /**
     * Luo uuden Controller olion sekä luo parametrina saadulle pelaajalle
     * vastustajan. Asettaa myös pelaajien laudat taulukkoon myöhempää käyttöä
     * varten.
     *
     * @param pelaaja Pelaaja, joka mallintaa tällä hetkellä ihmispelaajaa.
     */
    public Controller(Player pelaaja, HighScore highscore) {
        this.player = pelaaja;
        this.vuoro = vuoro.PLAYER;
        this.gameStarted = false;
        this.highscore = highscore;
        this.computerOpponent = new Ai(new Board(10, 4), pelaaja.getBoard());
        boards[0] = pelaaja.getBoard();
        boards[1] = computerOpponent.getBoard();
        this.gameEnded = false;
        this.length = 0;
        this.turns = 1;
    }

    /**
     * Antaa Controller oliolle olion joka osaa viestiä käyttäjän kanssa sekä
     * piirrustusolion
     *
     * @param asker Olio joka esittää käyttäjälle ilmoituksia
     * @param drawer Luokka joka osaa muuttaa pelaajien lautojen ruutujen
     * taustaväriä.
     */
    public void initController(Asker asker, Draw drawer, StatsArea stats) {
        this.asker = asker;
        this.drawer = drawer;
        this.stats = stats;
    }

    /**
     * Kysyy pelaajan nimen, joka sitten näytetään ikkunan tilannetieto
     * kohdassa. Nimeä käytetään lisäksi pelaajan voittaessa highscore tiedoston
     * kirjoit- tamiseen.
     */
    public void setPlayerNames() {
        stats.setPlayerName(asker.askInput("Anna nimesi"));
    }

    /**
     * Tyhjentää highscore tiedoston
     */
    public void resetHighscore() {
        highscore.reset();
    }

    /**
     * Aloittaa pelin jos sitä ei ole vielä aloitettu. Suoritetaan käyttäjän
     * painettua aloita peli -nappulaa. Arpoo myös laivat satunnaisesti
     * vastustajan laudalle käyttäen pelaaja -luokan arvoLaivat metodia.
     */
    public void startGame() {
        if (!gameStarted) {
            if (player.allShipsDone()) {
                gameStarted = true;
                computerOpponent.randomShips();
                setPlayerNames();
                updateStats();
            } else {
                asker.presentWarning("Ennenkuin aloitat pelin sinun on tehtävä laudallesi laivoja.\n"
                        + "Käytä lisää laivoja tai lisää satunnaisesti nappeja");
            }
        }
    }

    /**
     * Apumetodi, joka resetoi pelaajan. Luo siis uuden pelaajan ja pelaajalle
     * uuden laudan sekä laittaa parametrina annetun pelaajamuuttujan
     * osoittamaan tähän uuteen olioon.
     *
     * @param pelaaja Pelaaja -olio joka halutaan resetoida
     */
    private void resetPlayer(Player pelaaja) {
        Board uusiLauta = new Board(pelaaja.getBoard().getSize(), pelaaja.getBoard().getShipsDifferentSize().length);
        Player uusiPelaaja = new Player(uusiLauta);
        if (pelaaja == this.player) {
            this.player = uusiPelaaja;
            boards[0] = this.player.getBoard();
        } else {
            Ai uusiAi = new Ai(uusiLauta, this.player.getBoard());
            this.computerOpponent = uusiAi;
            boards[1] = computerOpponent.getBoard();
        }
    }

    /**
     * Aloittaa uuden pelin. Ajetaan käyttäjän painettua uusi peli -nappulaa.
     * Resetoi pelaajat ja tyhjentää graafiset laudat laivoista.
     */
    public void newGame() {
        resetPlayer(player);
        resetPlayer(computerOpponent);
        turns = 1;
        drawer.drawBoard(boards, turns);
        gameStarted = false;
        gameEnded = false;
    }

    /**
     * Näyttää vastustajan laivat kun käyttäjä painaa näytä vastustajan laivat
     * -nappia. Apumetodi kehityksen tueksi.
     */
    public void showOpponentShips() {
        drawer.drawOpponentBoard(boards, turns);
    }

    /**
     * Pelaajan voittaessa laitetaan pelin tulos highscore listaan jos se on
     * kymmenen parhaan joukossa. Tuloksen kuulumista kymmenen parhaan jouk-
     * koon tutkii highscore olion checkifscoretopten metodi
     */
    public void setHighScore() {
        if (highscore.checkIfScoreTopTen(turns)) {
            highscore.addScore(stats.getPlayerName(), turns);
            showHighScore("Onnittelut! Tuloksesi oli kymmenen parhaan joukossa.");
        }       
    }

    /**
     * Tutkii onko highscore listalla pelejä listattuna ja kutsuu asker
     * olion showhighscore metodia oikeanlaisella parametrilla. Tuo
     * metodi näyttää highscore listan tai ilmoituksen jos lista on tyhä
     * @param message
     */
    public void showHighScore(String message) {
        if (highscore.getScores().isEmpty()) {
            asker.showHighScore("Tuloslistalla ei ole vielä yhtään tulosta!", highscore);
        } else {
            asker.showHighScore(message, highscore);
        }
    }

    /**
     * Tarkistaa onko peli ohi sekä ilmoittaa käyttäjälle kumpi voitti mikäli
     * peli on ohi.
     */
    public void checkIfGameOver() {
        if (player.allShipsLost()) {
            asker.presentWarning("Valitettavasti kaikki laivasi upposivat ja hävisit pelin");
            gameEnded = true;
        } else if (computerOpponent.allShipsLost()) {
            asker.presentWarning("Sait upotettua kaikki vastustajan laivat. Onneksi olkoon voitit!");
            gameEnded = true;
            setHighScore();
        }
    }

    /**
     * Apumetodi joka kertoo kumman pelaajan vuoro on. Metodia tarvitaan lähin-
     * nä jos pelistä tulee myöhemmin versio, jossa voi pelata myös ihmisvastus-
     * tajaa vastaan.
     *
     * @return Palauttaa vuorossa olevan pelaajan.
     */
    public Player getVuorossaOlevaPelaaja() {
        if (vuoro == vuoro.PLAYER) {
            return player;
        } else {
            return computerOpponent;
        }
    }

    /**
     * Ampuu parametreina annettuihin koordinaatteihin vastustajan laudalle
     * ellei samaan ruutuun ole jo ammuttu. Kutsuu myös Pelaaja tyyppisen
     * vastustaja olion randomshoot metodia joka vastaa tuleen satunnaisella
     * kohteella.
     *
     * @param x Kohteen x-koordinaatti
     * @param y Kohteen y-koordinaatti
     */
    public void shoot(int x, int y) {
        if (gameStarted && !gameEnded) {
            if (!player.shoot(computerOpponent.getBoard(), new int[]{x, y})) {
                asker.presentWarning("Olet jo ampunut siihen ruutuun! Valitse toinen ruutu.");
            } else {
                checkIfGameOver();
                if (!gameEnded) {
                    computerOpponent.smartShoot(player.getBoard());
                    checkIfGameOver();
                    turns++;
                }
                drawer.drawBoard(new Board[]{player.getBoard(), computerOpponent.getBoard()}, turns);
            }
        }
    }

    /**
     * Lisää laudalle laivan, jos mahdollista. Käyttäjä on valinnut laivan
     * pituuden ja suunnan GUI:sta. Alkupiste on parametreinä saatava x- ja
     * y-koordinaatti.
     *
     * @param originx Alkupisteen x-koordinaatti, jonka käyttäjä valitsee klik-
     * kaamalla haluamaansa ruutua.
     * @param originy Alkupisteen y-koordinaatti, jonka käyttäjä valitsee klik-
     * kaamalla haluamaansa ruutua.
     */
    public void addShip(int originx, int originy) {
        if (gameStarted) {
            asker.presentWarning("Peli on jo aloitettu! Et voi enää lisätä laivoja. Klikkaa ruutua vastustajan laudalta ampuaksesi.");
        } else if (!checkErrors(new int[]{originx, originy}, direction, length)) {
            getVuorossaOlevaPelaaja().getBoard().createShip(new int[]{originx, originy}, direction, length);
            drawer.drawBoard(new Board[]{player.getBoard(), computerOpponent.getBoard()}, turns);
        }
    }

    /**
     * Tarkistaa voidaanko laiva tehdä ja kertoo käyttäjälle jos ei voida ja
     * miksi ei voida. Tarkistaa laivan voiko laivan tehdä käyttämällä pelaajan
     * laudan metodeja.
     *
     * @param beginning Alkukoordinaatit. Beginning[0] on x-koordinaatti ja
     * beginning[1] y- koordinaatti
     * @param direction Laivan suunta
     * @param length Laivan pituus (ruutuina)
     * @return Palauttaa true jos laivan voi tehdä, muuten false
     */
    public boolean checkErrors(int[] beginning, Direction direction, int length) {
        if (this.direction == null || this.length == 0) {
            asker.presentWarning("Ole hyvä ja valitse luotavan laivan pituus ja orientaatio vasemmalla olevista vaihtoehdoista");
            return true;
        } else if (getVuorossaOlevaPelaaja().getBoard().shipsAvailable(length) < 1) {
            asker.presentWarning("Sinulla on jo maksimimäärä " + length + " pituisia laivoja.");
            return true;
        } else if (!getVuorossaOlevaPelaaja().getBoard().isOnBoard(beginning, direction, length)) {
            asker.presentWarning("Antamasi laiva menisi laudan ulkopuolelle. \n "
                    + "Ole hyvä ja anna uudet arvot");
            return true;
        } else if (!getVuorossaOlevaPelaaja().getBoard().noAdjacentShips(beginning, direction, length)) {
            asker.presentWarning("Antamasi laiva menisi olemassaolevan laivan viereen/päälle. \n"
                    + " Ole hyvä ja anna uudet arvot");
            return true;
        }
        return false;
    }

    /**
     * Lisää laudalle tekemättä olevat laivat sattumanvaraisesti jos
     * mahdollista. Jos ei mahdollista ilmoittaa sen käyttäjälle.
     */
    public void addRandomShips() {
        if (gameStarted) {
            asker.presentWarning("Peli on jo aloitettu! Et voi enää lisätä laivoja. Klikkaa ruutua vastustajan laudalta ampuaksesi.");
        } else {
            if (getVuorossaOlevaPelaaja().allShipsDone()) {
                resetPlayer(player);
            }
            player.randomShips();
            drawer.drawBoard(new Board[]{player.getBoard(), computerOpponent.getBoard()}, turns);
        }
    }

    /**
     * Kutsuu draw luokan samannimistä metodia joka päivittää tilanteen tilan-
     * netietopaneeliin.
     */
    public void updateStats() {
        drawer.updateStats(boards, turns);
    }

    /**
     * Asettaa käyttäjän valitseman laivan pituuden muistiin
     *
     * @param length käytääjän valitsema laivan pituus
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Asettaa käyttäjän valitseman suunnan muistiin
     *
     * @param direction suunta jonka käyttäjä on valinnut
     */
    public void setDirection(String direction) {
        if (direction.equalsIgnoreCase("vertical")) {
            this.direction = this.direction.DOWN;
        } else {
            this.direction = this.direction.RIGHT;
        }
        System.out.println(this.direction);
    }
}