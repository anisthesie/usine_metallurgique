package usine.directions;

import usine.Produit;
import usine.axes.Vertical;

/**
 * La direction vers le haut sur la grille (direction négative sur l'usine.axes.Axe des y).
 */
public class Haut extends Negative implements Vertical {
    public Haut() {
        super(0, -1);
    }

    public int compare(Produit g, Produit d ) {
        return Double.compare( d.getY(), g.getY() );
    }
}
