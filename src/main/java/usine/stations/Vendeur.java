package usine.stations;

import usine.PlacementIncorrectException;
import usine.Usine;

public class Vendeur extends Station {
    //   ....
    //   .eM.
    //   ....
    // M( x,   y ) : Case occupé par le usine.Vendeur
    // e( x-1, y ) : Case où le usine.Vendeur prends ses entrées.

    public Vendeur( int positionX, int positionY ) {
        super( positionX, positionY );
    }

    @Override
    public void tic( Usine parent ) {

    }

    @Override
    public void placer( int x, int y, Usine parent ) throws PlacementIncorrectException {

    }
}
