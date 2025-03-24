package usine.stations;

import usine.Case;
import usine.PlacementIncorrectException;
import usine.Usine;
import usine.geometrie.Position;

public abstract class Station {
    protected Position position;

    public Station(int positionX, int positionY) {
        this.position = new Position(positionX, positionY);
    }

    public Station() {
        this.position = new Position(-1, -1);
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
        return position.getX() != -1 && position.getY() != -1;
    }

    public boolean areCasesValid(Case... cases) {
        for (Case c : cases)
            if (c == null || c.isOccupe())
                return false;

        return true;
    }

    public String getSymbole() {
        return "S";
    }
}
