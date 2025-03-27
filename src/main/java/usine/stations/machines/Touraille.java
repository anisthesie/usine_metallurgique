package usine.stations.machines;

import usine.*;

public class Touraille extends Machine {
    //   ......
    //   .eMO..
    //   ..OOs.
    //   ......
    // M( x,   y )   : Première case occupé par la Touraille
    // O( x+1, y )   : deuxième case occupé par la Touraille
    // O( x,   y+1 ) : troisième case occupé par la Touraille
    // O( x+1, y+1 ) : quatrième case occupé par la Touraille
    // e( x-1, y )   : Case où la Touraille prend ses entrées.
    // s( x+2, y+1 ) : Case où la Touraille place les sorties.

    public Touraille(int positionX, int positionY) {
        super(positionX, positionY);
    }


    @Override
    protected void traiterEntree(Usine parent) {

        int xentree = this.getX() - 1;
        int yentree = this.getY();

        if (parent.getLogistique().contiensItem(xentree, yentree)) {
            Produit item = parent.getLogistique().trouverItem(xentree, yentree);

            if (produitEnCours != null) {
                parent.ajouterNotification("La touraille en (" + this.getX() + "," + this.getY() + ") a reçu un produit alors qu'elle est déjà en marche : " + item.getIdentite().getNom() + ".");
                return;
            }

            if (item.getIdentite() != IdentiteProduit.CHARBON) {
                parent.ajouterNotification("La touraille en (" + this.getX() + "," + this.getY() + ") a reçue un produit non traitable : " + item.getIdentite().getNom() + " ! Le produit a été détruit.");
                parent.getLogistique().extraireItem(xentree, yentree);
                return;
            }

            parent.ajouterNotification("La touraille en (" + this.getX() + "," + this.getY() + ") a reçu un produit : " + item.getIdentite().getNom() + " !");
            parent.getLogistique().extraireItem(xentree, yentree);
            produitEnCours = IdentiteProduit.COKE;
        }

    }


    @Override
    protected void travailler(Usine parent) {

        compteur++;

        int xsortie = this.getX() + 2;
        int ysortie = this.getY() + 1;

        if (compteur == 20) {
            try {
                parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.COKE));
            } catch (PlacementIncorrectException e) {
                parent.ajouterNotification(
                        "La touraille en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
            }

            parent.ajouterNotification("La touraille en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.COKE.getNom() + " !");
            produitEnCours = null;
            compteur = 0;
        }

    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case[] cases = {
                parent.getCase(x, y),
                parent.getCase(x + 1, y),
                parent.getCase(x, y + 1),
                parent.getCase(x + 1, y + 1)
        };


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
        return "TOU";
    }
}
