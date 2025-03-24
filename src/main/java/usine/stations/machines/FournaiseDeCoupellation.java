package usine.stations.machines;

import usine.Case;
import usine.PlacementIncorrectException;
import usine.TapisRoulant;
import usine.Usine;
import usine.geometrie.Geometrie;

public class FournaiseDeCoupellation extends Machine {
    //   .....
    //   .eO..
    //   ..Ms.
    //   .....
    // M( x,   y )   : Première case occupé par la Founaise
    // O( x,   y-1 ) : deuxième case occupé par la Fournaise
    // e( x-1, y-1 ) : Case où la Fournaise prend ses entrées.
    // s( x+1, y )   : Case où la Fournaise place les sorties.

    public FournaiseDeCoupellation(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public FournaiseDeCoupellation() {
        super();
    }

    @Override
    public void tic(Usine parent) {

    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case[] cases = {
                parent.getCase(x, y),
                parent.getCase(x, y - 1),
        };

        Case[] entrees = {
                parent.getCase(x - 1, y - 1) };

        Case sortie = parent.getCase(x + 1, y);

        if (!this.areCasesValid(cases) || !this.areCasesValid(entrees))
            throw new PlacementIncorrectException("Impossible de placer l'élement dans la case (" + Geometrie.cartesienVersLineaire(x, y, parent.getTailleX()) + ")");

        parent.ajouterStation(this);
        this.position.setX(x);
        this.position.setY(y);

        for(Case entree : entrees) {
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
        return "FDC";
    }
}
