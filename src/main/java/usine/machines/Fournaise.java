package usine.machines;

import usine.PlacementIncorrectException;
import usine.Usine;

public class Fournaise extends Machine {
    //   ......
    //   .eO...
    //   ..MOs.
    //   .fO...
    //   ......
    // M( x,   y )   : Première case occupé par la Founaise
    // O( x,   y-1 ) : deuxième case occupé par la usine.machines.Fournaise
    // O( x+1, y )   : troisième case occupé par la usine.machines.Fournaise
    // O( x,   y+1 ) : quatrième case occupé par la usine.machines.Fournaise
    // e( x-1, y-1 ) : Case où la usine.machines.Fournaise prend ses entrées pour la boite 1.
    // f( x-1, y+1 ) : Case où la usine.machines.Fournaise prend ses entrées pour la boite 2.
    // s( x+2, y )   : Case où la usine.machines.Fournaise place les sorties.

    public Fournaise(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    public void tic(Usine parent) {

    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {

    }
}
