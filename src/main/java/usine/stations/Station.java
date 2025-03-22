package usine.stations;

import usine.PlacementIncorrectException;
import usine.Usine;

public abstract class Station {
    protected int positionX;
    protected int positionY;

    public Station(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Station() {
        this.positionX = -1;
        this.positionY = -1;
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

    public boolean isPlaced() {
        return positionX != -1 && positionY != -1;
    }

    public String getSymbole() {
        return "S";
    }
}
