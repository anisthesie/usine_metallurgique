package usine;

import usine.stations.Station;


/**
 * Représente une case de la grille.
 * Contient une station ou un tapis roulant.
 */
public class Case {

    private Station station;
    private TapisRoulant tapis;

    private int x;
    private int y;


    public Case(int x, int y) {
        this(x, y, TapisRoulant.VIDE);
    }

    public Case(int x, int y, TapisRoulant tapis) {
        this.x = x;
        this.y = y;
        this.station = null;
        this.tapis = tapis;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Vérifie si la case est occupée par une station ou un tapis roulant
     *
     * @return true si la case est occupée
     */
    public boolean isOccupe() {
        return station != null || tapis != TapisRoulant.VIDE || tapis == TapisRoulant.OCCUPE;
    }

    /**
     * Vérifie si la case contient une station.
     *
     * @return true si la case contient une station
     */
    public boolean hasStation() {
        return station != null;
    }

    /**
     * Affiche le haut de la case.
     * Synchronisé avec <code>TapisRoulant.afficheHaut()</code>
     *
     * @return l'affichage du haut de la case
     */
    public String afficheHaut() {
        return tapis.afficheHaut();
    }

    /**
     * Affiche le milieu de la case.
     * Si la case contient une station, affiche le symbole de la station.
     * Sinon, affiche <code>TapisRoulant.afficheMilieu()</code>
     *
     * @return l'affichage du milieu de la case
     */
    public String afficheMilieu() {
        if (hasStation()) return station.getSymbole();

        return tapis.afficheMilieu();
    }

    /**
     * Affiche le bas de la case.
     * Synchronisé avec <code>TapisRoulant.afficheBas()</code>
     *
     * @return l'affichage du bas de la case
     */
    public String afficheBas() {
        return tapis.afficheBas();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TapisRoulant getTapis() {
        return tapis;
    }

    public void setTapis(TapisRoulant tapis) {
        this.tapis = tapis;
    }

}
