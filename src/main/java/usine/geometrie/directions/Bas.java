package usine.geometrie.directions;

import usine.Produit;
import usine.geometrie.directions.axes.Vertical;

/**
 * La direction vers le bas sur la grille (direction positive sur l'Axe des y).
 */
public class Bas extends Positive implements Vertical {
    public Bas() {
        super(0, 1);
    }

    @Override
    public int compare(Produit g, Produit d) {
        return Double.compare(g.getY(), d.getY());
    }
}
