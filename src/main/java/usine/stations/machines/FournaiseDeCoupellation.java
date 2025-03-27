package usine.stations.machines;

import usine.*;

public class FournaiseDeCoupellation extends Machine {
    //   .....
    //   .eO..
    //   ..Ms.
    //   .....
    // M( x,   y )   : Première case occupé par la Founaise
    // O( x,   y-1 ) : deuxième case occupé par la Fournaise
    // e( x-1, y-1 ) : Case où la Fournaise prend ses entrées.
    // s( x+1, y )   : Case où la Fournaise place les sorties.

    public FournaiseDeCoupellation(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    protected void traiterEntree(Usine parent) {

        int xentree = this.getX() - 1;
        int yentree = this.getY() - 1;

        if (parent.getLogistique().contiensItem(xentree, yentree)) {
            Produit item = parent.getLogistique().trouverItem(xentree, yentree);

            if (produitEnCours != null) {
                parent.ajouterNotification("La fournaise de coupellation en (" + this.getX() + "," + this.getY() + ") a reçu un produit alors qu'elle est déjà en marche : " + item.getIdentite().getNom() + ".");
                return;
            }

            if (item.getIdentite() != IdentiteProduit.OXYDE_ARGENT && item.getIdentite() != IdentiteProduit.OXYDE_ETAIN && item.getIdentite() != IdentiteProduit.OXYDE_CUIVRE) {
                parent.ajouterNotification("Le fournaise de coupellation en (" + this.getX() + "," + this.getY() + ") a reçu un produit non traitable : " + item.getIdentite().getNom() + " ! Le produit a été détruit.");
                parent.getLogistique().extraireItem(xentree, yentree);
                return;
            }

            parent.ajouterNotification("La fournaise de coupellation en (" + this.getX() + "," + this.getY() + ") a reçu un produit : " + item.getIdentite().getNom() + " !");
            item = parent.getLogistique().extraireItem(xentree, yentree);
            produitEnCours = item.getIdentite();
        }
    }


    @Override
    protected void travailler(Usine parent) {

        compteur++;

        int xsortie = this.getX() + 1;
        int ysortie = this.getY();

        switch (produitEnCours) {
            case OXYDE_ARGENT:
                if (compteur == 10) {
                    try {
                        parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.LINGOT_ARGENT));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification("La fournaise de coupellation en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
                    }

                    parent.ajouterNotification("La fournaise de coupellation en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.LINGOT_ARGENT.getNom() + " !");
                    produitEnCours = null;
                    compteur = 0;
                }
                break;
            case OXYDE_ETAIN:
                if (compteur == 2) {
                    try {
                        parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.LINGOT_ETAIN));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification("La fournaise de coupellation en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
                    }

                    parent.ajouterNotification("La fournaise de coupellation en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.LINGOT_ETAIN.getNom() + " !");
                    produitEnCours = null;
                    compteur = 0;
                }
                break;
            case CHALCOCITE:
                if (compteur == 3) {
                    try {
                        parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.LINGOT_CUIVRE));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification("La fournaise de coupellation en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
                    }

                    parent.ajouterNotification("La fournaise de coupellation en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.LINGOT_CUIVRE.getNom() + " !");
                    produitEnCours = null;
                    compteur = 0;
                }
                break;
        }

    }


    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case[] cases = {parent.getCase(x, y), parent.getCase(x, y - 1),};


        if (!this.casesValides(cases))
            throw new PlacementIncorrectException("Impossible de placer l'élement dans la case.");

        parent.ajouterStation(this);
        this.setX(x);
        this.setY(y);


        for (Case c : cases) {
            parent.getLogistique().setTapis(c.getX(), c.getY(), TapisRoulant.OCCUPE);

            c.setStation(this);
        }


    }

    @Override
    public String getSymbole() {
        return "FDC";
    }
}
