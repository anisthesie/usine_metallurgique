package usine.stations.machines;

import usine.*;
import usine.geometrie.Geometrie;

public class Moulin extends Machine {
    //   ......
    //   .eMOs.
    //   ......
    // M( x,   y ) : Première case occupé par le Moulin
    // O( x+1, y ) : deuxième case occupé par le Moulin
    // e( x-1, y ) : Case où le Moulin prend ses entrées.
    // s( x+2, y ) : Case où le Moulin place les sorties.

    /**
     * Construit un Moulin
     *
     * @param positionX Position en X de la première case du Moulin (M).
     * @param positionY Position en Y de la première case du Moulin (M).
     */
    public Moulin(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public Moulin() {
        super();
    }

    @Override
    protected void traiterEntree(Usine parent) {

        int xentree = this.getX() - 1;
        int yentree = this.getY();

        if (parent.getLogistique().contiensItem(xentree, yentree)) {
            Produit item = parent.getLogistique().trouverItem(xentree, yentree);

            if (produitEnCours != null) {
                parent.ajouterNotification("Le moulin en (" + this.getX() + "," + this.getY() + ") a reçu un produit alors qu'il est déjà en marche : " + item.getIdentite().getNom() + ".");
                return;
            }

            if (item.getIdentite() != IdentiteProduit.ACANTHITE
                    && item.getIdentite() != IdentiteProduit.CASSITERITE
                    && item.getIdentite() != IdentiteProduit.CHALCOCITE) {
                parent.ajouterNotification("Le moulin en (" + this.getX() + "," + this.getY() + ") a reçu un produit non traitable : " + item.getIdentite().getNom() + " ! Le produit a été détruit.");
                parent.getLogistique().extraireItem(xentree, yentree);
                return;
            }

            parent.ajouterNotification("Le moulin en (" + this.getX() + "," + this.getY() + ") a reçu un produit : " + item.getIdentite().getNom() + " !");
            item = parent.getLogistique().extraireItem(xentree, yentree);
            produitEnCours = item.getIdentite();
        }
    }


    @Override
    protected void travailler(Usine parent) {

        compteur++;

        int xsortie = this.getX() + 2;
        int ysortie = this.getY();

        switch (produitEnCours) {
            case ACANTHITE:
                if (compteur == 3) {
                    try {
                        parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.POUDRE_ACANTHITE));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification(
                                "Le moulin en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
                    }

                    parent.ajouterNotification("Le moulin en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.POUDRE_ACANTHITE.getNom() + " !");
                    produitEnCours = null;
                    compteur = 0;
                }
                break;
            case CASSITERITE:
                if (compteur == 7) {
                    try {
                        parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.POUDRE_CASSITERITE));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification(
                                "Le moulin en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
                    }
                    parent.ajouterNotification("Le moulin en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.POUDRE_CASSITERITE.getNom() + " !");
                    produitEnCours = null;
                    compteur = 0;
                }
                break;
            case CHALCOCITE:
                if (compteur == 3) {
                    try {
                        parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.POUDRE_CHALCOCITE));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification(
                                "Le moulin en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
                    }
                    parent.ajouterNotification("Le moulin en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.POUDRE_CHALCOCITE.getNom() + " !");
                    produitEnCours = null;
                    compteur = 0;
                }
                break;
        }

    }


    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case[] cases = {
                parent.getCase(x, y),
                parent.getCase(x + 1, y)
        };


        if (!this.areCasesValid(cases))
            throw new PlacementIncorrectException("Impossible de placer l'élement dans la case (" + Geometrie.cartesienVersLineaire(x, y, parent.getTailleX()) + ")");

        parent.ajouterStation(this);
        this.setX(x);
        this.setY(y);

        for (Case c : cases) {
            parent.getLogistique().setTapis(c.getX(), c.getY(), TapisRoulant.OCCUPE);

            c.setStation(this);
            c.setSymbole(getSymbole());
        }


    }

    @Override
    public String getSymbole() {
        return "MOU";
    }
}
