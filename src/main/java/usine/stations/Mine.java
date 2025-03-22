package usine.stations;

import input.Parser;
import usine.IdentiteProduit;
import usine.PlacementIncorrectException;
import usine.Usine;

public class Mine extends Station {
    protected IdentiteProduit minerai;

    // Recettes    | temps
    // ------------+----------
    // Acanthite   | 2 tours
    // Cassiterite | 7 tours
    // Chalcocite  | 3 tours
    // Charbon     | 1 tour.

    //   ....
    //   .Ms.
    //   ....
    // M( x,   y ) : Case occupé par la Mine
    // s( x+1, y ) : Case où la Mine place les sorties.

    public Mine( IdentiteProduit minerai, int positionX, int positionY ) {
        super( positionX, positionY );
        this.minerai = minerai;
    }

    public Mine( IdentiteProduit minerai ) {
        super();
        this.minerai = minerai;
    }

    public IdentiteProduit getMinerai() {
        return minerai;
    }

    @Override
    public void tic( Usine parent ) {

    }

    @Override
    public void placer( int x, int y, Usine parent ) throws PlacementIncorrectException {
        this.positionX = x;
        this.positionY = y;
        //parent.ajouterStation( this, x, y );
        Parser.clearScreen();
        System.out.println("Mine de " + minerai + " placée en (" + x + ", " + y + ")");
    }
}
