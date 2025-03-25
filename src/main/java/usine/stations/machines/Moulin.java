package usine.stations.machines;

import usine.Case;
import usine.PlacementIncorrectException;
import usine.TapisRoulant;
import usine.Usine;
import usine.geometrie.Geometrie;

public class Moulin extends Machine {
    //   ......
    //   .eMOs.
    //   ......
    // M( x,   y ) : Première case occupé par le Moulin
    // O( x+1, y ) : deuxième case occupé par le Moulin
    // e( x-1, y ) : Case où le Moulin prend ses entrées.
    // s( x+2, y ) : Case où le Moulin place les sorties.

    /**
     * Construit un Moulin
     *
     * @param positionX Position en X de la première case du Moulin (M).
     * @param positionY Position en Y de la première case du Moulin (M).
     */
    public Moulin(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public Moulin() {
        super();
    }

    @Override
    public void tic(Usine parent) {

    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case[] cases = {
                parent.getCase(x, y),
                parent.getCase(x + 1, y)
        };

        Case[] entrees = {parent.getCase(x - 1, y)};

        Case sortie = parent.getCase(x + 2, y);

        if (!this.areCasesValid(cases) || !this.areCasesValid(entrees))
            throw new PlacementIncorrectException("Impossible de placer l'élement dans la case (" + Geometrie.cartesienVersLineaire(x, y, parent.getTailleX()) + ")");

        parent.ajouterStation(this);
        this.setX(x);
        this.setY(y);

        for (Case entree : entrees) {
            entree.setStation(this);
            entree.setSymbole("B");
        }
        for (Case c : cases) {
            parent.getLogistique().setTapis(c.getX(), c.getY(), TapisRoulant.OCCUPE);

            c.setStation(this);
            c.setSymbole(getSymbole());
        }


    }

    @Override
    public String getSymbole() {
        return "MOU";
    }
}
