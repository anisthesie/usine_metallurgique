package usine.stations;

import usine.Case;
import usine.PlacementIncorrectException;
import usine.TapisRoulant;
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

        //if(parent.getLogistique().contiensItem(x - 1, y))

    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case[] cases = {parent.getCase(x, y)};

        if (!this.areCasesValid(cases))
            throw new PlacementIncorrectException("Impossible de placer l'élement dans la case (" + Geometrie.cartesienVersLineaire(x, y, parent.getTailleX()) + ")");


        parent.ajouterStation(this);

        this.setX(x);
        this.setY(y);

        for (Case c : cases) {
            c.setStation(this);
            c.setSymbole(getSymbole());
            parent.getLogistique().setTapis(c.getX(), c.getY(), TapisRoulant.OCCUPE);
        }
    }

    @Override
    public String getSymbole() {
        return "VEN";
    }
}
