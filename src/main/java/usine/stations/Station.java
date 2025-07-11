package usine.stations;

import usine.Case;
import usine.PlacementIncorrectException;
import usine.Usine;

public abstract class Station {

    private int x;
    private int y;

    public Station(int positionX, int positionY) {
        this.x = positionX;
        this.y = positionY;
    }

    public Station() {
        this.x = -1;
        this.y = -1;
    }

    /**
     * Simule un tour de simulation pour cette station.
     *
     * @param parent Un lien vers l'usine afin que la station puisse interagir avec
     *               les variables d'instance de l'usine tel que {\code logistique}.
     */
    public abstract void tic(Usine parent);

    /**
     * Place la station dans l'usine.
     *
     * @param x      La position x de la station.
     * @param y      La position y de la station.
     * @param parent L'usine dans laquelle la station est placée.
     * @throws PlacementIncorrectException Si la station ne peut pas être placée dans la case.
     */
    public abstract void placer(int x, int y, Usine parent)
            throws PlacementIncorrectException;


    /**
     * Vérifie si les cases passées en paramètre sont valides pour placer la station.
     *
     * @param cases Les cases à vérifier.
     * @return true si les cases sont valides.
     */
    public boolean casesValides(Case... cases) {
        for (Case c : cases)
            if (c == null || c.isOccupe())
                return false;

        return true;
    }

    /**
     * Vérifie si la station est placée dans l'usine.
     *
     * @return true si la station est placée.
     */
    public boolean isPlaced() {
        return x != -1 && y != -1;
    }

    /**
     * Retourne le symbole de la station.
     * Utilisé pour l'affichage.
     *
     * @return Le symbole de la station.
     */
    public String getSymbole() {
        return "S";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
