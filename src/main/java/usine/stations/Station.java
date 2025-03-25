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

    public abstract void placer(int x, int y, Usine parent)
            throws PlacementIncorrectException;


    public boolean areCasesValid(Case... cases) {
        for (Case c : cases)
            if (c == null || c.isOccupe())
                return false;

        return true;
    }

    public boolean isPlaced() {
        return x != -1 && y != -1;
    }

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
