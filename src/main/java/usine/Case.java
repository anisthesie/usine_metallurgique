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

    public Case(int x, int y, Usine usine) {
        this.x = x;
        this.y = y;
        this.usine = usine;
        this.indexLineaire = Geometrie.cartesienVersLineaire(x, y, usine.getTailleX());
        this.symbole = String.valueOf(indexLineaire);
        this.station = null;
    }

    public String getSymbole() {
        if (!isOccupe() && !usine.isAfficherIndex())
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
        return station != null;
    }
}
