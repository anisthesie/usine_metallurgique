package usine.stations;

import usine.*;

public class Vendeur extends Station {
    //   ....
    //   .eM.
    //   ....
    // M( x,   y ) : Case occupé par le Vendeur
    // e( x-1, y ) : Case où le Vendeur prends ses entrées.

    public Vendeur(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    public void tic(Usine parent) {
        if (parent.getLogistique().contiensItem(this.getX() - 1, this.getY())) {
            Produit produit = parent.getLogistique().extraireItem(this.getX() - 1, this.getY());
            parent.incrementerVente(produit.getIdentite());
            parent.ajouterNotification("Le vendeur en (" + this.getX() + "," + this.getY() + ") a vendu : " + produit.getIdentite().getNom() + " !");
        }
    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case[] cases = {parent.getCase(x, y)};

        if (!this.casesValides(cases))
            throw new PlacementIncorrectException("Impossible de placer l'élement dans la case.");


        parent.ajouterStation(this);

        this.setX(x);
        this.setY(y);

        for (Case c : cases) {
            c.setStation(this);
            parent.getLogistique().setTapis(c.getX(), c.getY(), TapisRoulant.OCCUPE);
        }
    }

    @Override
    public String getSymbole() {
        return "VEN";
    }
}
