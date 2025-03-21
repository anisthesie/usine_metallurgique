package usine.directions.axes;

import usine.Produit;

/**
 * Décrit l'axe des 'y'.
 */
public interface Vertical extends Axe {
    @Override
    default double getPositionRelative(Produit produit, int posX, int posY ) {
        return produit.getY() - posY;
    }

    @Override
    default void setPositionRelative( Produit produit, double positionRelative, int posX, int posY ) {
        produit.setY( positionRelative + posY );
    }

    int compare(Produit g, Produit d);
}
