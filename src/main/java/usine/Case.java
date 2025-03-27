package usine;

import usine.geometrie.Geometrie;
import usine.stations.Station;

public class Case {

    private Station station;

    private String symbole;
    private int x;
    private int y;

    private TapisRoulant tapis;

    public Case(int x, int y) {
        this(x, y, TapisRoulant.VIDE);
    }

    public Case(int x, int y, TapisRoulant tapis) {
        this.x = x;
        this.y = y;
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
        return station != null || tapis != TapisRoulant.VIDE || tapis == TapisRoulant.OCCUPE;
    }

    public boolean hasStation() {
        return station != null;
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

    public String afficheHaut() {
        return tapis.afficheHaut();
    }

    public String afficheMilieu(int tailleX) {
        if (this.tapis == TapisRoulant.VIDE && Usine.AFFICHER_INDEX) {
            StringBuilder retour = new StringBuilder(String.valueOf(getIndexLineaire(tailleX)));
            int indentationRelative = 3 - retour.length();
            for (int i = 0; i < indentationRelative; i++)
                retour.append(" ");


            return retour.toString();
        }


        if (hasStation())
            return station.getSymbole();


        return tapis.afficheMilieu();
    }

    public int getIndexLineaire(int tailleX) {
        return Geometrie.cartesienVersLineaire(x, y, tailleX);
    }

    public String afficheBas() {
        return tapis.afficheBas();
    }

}
