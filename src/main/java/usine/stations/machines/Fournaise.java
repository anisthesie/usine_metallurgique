package usine.stations.machines;

import usine.Case;
import usine.PlacementIncorrectException;
import usine.TapisRoulant;
import usine.Usine;
import usine.geometrie.Geometrie;

public class Fournaise extends Machine {
    //   ......
    //   .eO...
    //   ..MOs.
    //   .fO...
    //   ......
    // M( x,   y )   : Première case occupé par la Founaise
    // O( x,   y-1 ) : deuxième case occupé par la Fournaise
    // O( x+1, y )   : troisième case occupé par la Fournaise
    // O( x,   y+1 ) : quatrième case occupé par la Fournaise
    // e( x-1, y-1 ) : Case où la Fournaise prend ses entrées pour la boite 1.
    // f( x-1, y+1 ) : Case où la Fournaise prend ses entrées pour la boite 2.
    // s( x+2, y )   : Case où la Fournaise place les sorties.

    public Fournaise(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public Fournaise() {
        super();
    }

    @Override
    public void tic(Usine parent) {

    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {

        Case[] cases = {parent.getCase(x, y), parent.getCase(x, y - 1), parent.getCase(x + 1, y), parent.getCase(x, y + 1)};


        Case[] entrees = {parent.getCase(x - 1, y - 1),
                parent.getCase(x - 1, y + 1)};

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
        return "FOU";
    }
}
