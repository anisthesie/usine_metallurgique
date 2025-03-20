package usine.machines;

import usine.PlacementIncorrectException;
import usine.Usine;

public class FournaiseDeGrillage extends Machine {
    //   .......
    //   ...f...
    //   ...O...
    //   .eOMOs.
    //   .......
    // M( x,   y )   : Première case occupé par la Founaise
    // O( x-1, y )   : deuxième case occupé par la usine.machines.Fournaise
    // O( x,   y-1 ) : troisième case occupé par la usine.machines.Fournaise
    // O( x+1, y )   : quatrième case occupé par la usine.machines.Fournaise
    // e( x-2, y )   : Case où la usine.machines.Fournaise prend ses entrées pour la boite 1.
    // f( x,   y-2 ) : Case où la usine.machines.Fournaise prend ses entrées pour la boite 2.
    // s( x+2, y )   : Case où la usine.machines.Fournaise place les sorties.

    public FournaiseDeGrillage( int positionX, int positionY ) {
        super( positionX, positionY );
    }

    @Override
    public void tic( Usine parent ) {

    }

    @Override
    public void placer( int x, int y, Usine parent ) throws PlacementIncorrectException {

    }
}
