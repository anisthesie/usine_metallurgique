package usine.geometrie.directions;

import usine.Produit;
import usine.geometrie.directions.axes.Vertical;

/**
 * La direction vers le haut sur la grille (direction négative sur l'Axe des y).
 */
public class Haut extends Negative implements Vertical {
    public Haut() {
        super(0, -1);
    }

    public int compare(Produit g, Produit d ) {
        return Double.compare( d.getY(), g.getY() );
    }
}
