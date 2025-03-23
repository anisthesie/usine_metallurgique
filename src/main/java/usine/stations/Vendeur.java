package usine.stations;

import usine.Case;
import usine.PlacementIncorrectException;
import usine.Usine;
import usine.geometrie.Geometrie;

public class Vendeur extends Station {
    //   ....
    //   .eM.
    //   ....
    // M( x,   y ) : Case occupé par le Vendeur
    // e( x-1, y ) : Case où le Vendeur prends ses entrées.

    public Vendeur(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public Vendeur() {
        super();
    }

    @Override
    public void tic(Usine parent) {

    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case case1 = parent.getCase(x - 1, y);
        Case case2 = parent.getCase(x, y);

        if (case1 == null || case2 == null || case1.isOccupe() || case2.isOccupe())
            throw new PlacementIncorrectException("Impossible de placer l'élement dans la case (" + Geometrie.cartesienVersLineaire(x, y, parent.getTailleX()) + ")");


        parent.ajouterStation(this);

        this.position.setX(x);
        this.position.setY(y);

        case1.setStation(this);
        case2.setStation(this);

        case1.setSymbole("-->");
        case2.setSymbole(getSymbole());
    }

    @Override
    public String getSymbole() {
        return "VEN";
    }
}
