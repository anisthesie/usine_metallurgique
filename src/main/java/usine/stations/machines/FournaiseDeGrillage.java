package usine.stations.machines;

import usine.*;
import usine.geometrie.Geometrie;

public class FournaiseDeGrillage extends Machine {
    //   .......
    //   ...f...
    //   ...O...
    //   .eOMOs.
    //   .......
    // M( x,   y )   : Première case occupé par la Founaise
    // O( x-1, y )   : deuxième case occupé par la Fournaise
    // O( x,   y-1 ) : troisième case occupé par la Fournaise
    // O( x+1, y )   : quatrième case occupé par la Fournaise
    // e( x-2, y )   : Case où la Fournaise prend ses entrées pour la boite 1.
    // f( x,   y-2 ) : Case où la Fournaise prend ses entrées pour la boite 2.
    // s( x+2, y )   : Case où la Fournaise place les sorties.


    private boolean coke = false;
    private IdentiteProduit composant = null;

    public FournaiseDeGrillage(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public FournaiseDeGrillage() {
        super();
    }


    @Override
    public void tic(Usine parent) {

        traiterEntree(parent);

        if (composant != null && coke) travailler(parent);

    }

    @Override
    protected void traiterEntree(Usine parent) {
        int xentree1 = this.getX() - 2;
        int yentree1 = this.getY();

        int xentree2 = this.getX();
        int yentree2 = this.getY() - 2;


        traiterUneEntree(parent, xentree1, yentree1);
        traiterUneEntree(parent, xentree2, yentree2);
    }

    private void traiterUneEntree(Usine parent, int x, int y) {

        if (parent.getLogistique().contiensItem(x, y)) {

            if (composant != null && coke) {
                parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a reçu un produit alors que son inventaire est déjà plein !");
                return;
            }

            Produit item = parent.getLogistique().trouverItem(x, y);

            if (item.getIdentite() == IdentiteProduit.COKE) {

                if (coke) {
                    parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a reçu du coke alors qu'elle en possède déjà un ! Le produit a été détruit.");
                    parent.getLogistique().extraireItem(x, y);
                    return;
                }


                if (composant != null)
                    parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a commencé à transformer : " + composant.getNom() + " !");
                else
                    parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a reçu du coke.");

                coke = true;
                parent.getLogistique().extraireItem(x, y);
                return;
            }
            if (item.getIdentite() == IdentiteProduit.LITHARGE || item.getIdentite() == IdentiteProduit.CASSITERITE || item.getIdentite() == IdentiteProduit.POUDRE_CASSITERITE || item.getIdentite() == IdentiteProduit.CHALCOCITE || item.getIdentite() == IdentiteProduit.POUDRE_CHALCOCITE) {

                if (item.getIdentite() == composant) {
                    parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a reçu un produit déjà en inventaire : " + item.getIdentite().getNom() + " ! Le produit a été détruit.");
                    parent.getLogistique().extraireItem(x, y);
                    return;
                }

                if (coke)
                    parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a commencée à transformer : " + item.getIdentite().getNom() + " !");
                else
                    parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a reçu un produit : " + item.getIdentite().getNom() + ".");

                composant = item.getIdentite();
                parent.getLogistique().extraireItem(x, y);
                return;
            }

            parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a reçu un produit intraitable : " + item.getIdentite().getNom() + " ! Le produit a été détruit.");
            parent.getLogistique().extraireItem(x, y);
        }
    }

    @Override
    protected void travailler(Usine parent) {

        int xsortie = this.getX() + 2;
        int ysortie = this.getY();

        switch (composant) {
            case LITHARGE:
                if (compteur == 14) {
                    try {
                        parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.OXYDE_ARGENT));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
                    }
                    parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.OXYDE_ARGENT.getNom() + " !");
                    compteur = 0;
                    coke = false;
                    composant = null;
                }
                break;
            case CASSITERITE:
            case POUDRE_CASSITERITE:
                if (compteur == 12) {
                    try {
                        parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.OXYDE_ETAIN));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
                    }

                    parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.OXYDE_ETAIN.getNom() + " !");
                    compteur = 0;
                    coke = false;
                    composant = null;
                }
                break;
            case CHALCOCITE:
            case POUDRE_CHALCOCITE:
                if (compteur == 13) {
                    try {
                        parent.getLogistique().placerItem(xsortie, ysortie, new Produit(IdentiteProduit.OXYDE_CUIVRE));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
                    }

                    parent.ajouterNotification("La fournaise de grillage en (" + this.getX() + "," + this.getY() + ") a produit : " + IdentiteProduit.OXYDE_CUIVRE.getNom() + " !");
                    compteur = 0;
                    coke = false;
                    composant = null;
                }
                break;

        }

        compteur++;
    }


    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case[] cases = {parent.getCase(x, y), parent.getCase(x - 1, y), parent.getCase(x, y - 1), parent.getCase(x + 1, y)};


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
        return "FDG";
    }
}
