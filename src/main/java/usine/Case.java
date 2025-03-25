package usine;

import usine.geometrie.Geometrie;
import usine.stations.Station;

public class Case {

    private Station station;
    private Usine usine;

    private String symbole;
    private int x;
    private int y;
    private int indexLineaire;

    private TapisRoulant tapis;

    public Case(int x, int y, Usine usine, TapisRoulant tapis) {
        this.x = x;
        this.y = y;
        this.usine = usine;
        this.indexLineaire = Geometrie.cartesienVersLineaire(x, y, usine.getTailleX());
        this.symbole = String.valueOf(indexLineaire);
        this.station = null;
        this.tapis = tapis;
    }

    public String getSymbole() {
        if (!isOccupe() && !Usine.AFFICHER_INDEX)
            return " ";
        return symbole;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public boolean isOccupe() {
        return station != null || usine.getLogistique().getTapis(x, y) != TapisRoulant.VIDE || usine.getLogistique().getTapis(x, y) == TapisRoulant.OCCUPE;
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
