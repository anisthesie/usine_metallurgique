package usine.stations.machines;

import usine.*;

//   ......
//   .eO...
//   ..MOs.
//   .fO...
//   ......
// M( x,   y )   : Première case occupé par la Founaise
// O( x,   y-1 ) : deuxième case occupé par la Fournaise
// O( x+1, y )   : troisième case occupé par la Fournaise
// O( x,   y+1 ) : quatrième case occupé par la Fournaise
// e( x-1, y-1 ) : Case où la Fournaise prend ses entrées pour la boite 1.
// f( x-1, y+1 ) : Case où la Fournaise prend ses entrées pour la boite 2.
// s( x+2, y )   : Case où la Fournaise place les sorties.
public class Fournaise extends Machine {

    // L'étain est-il présent ?
    private boolean etain = false;
    // Composant à traiter
    private IdentiteProduit composant = null;

    public Fournaise(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    public void tic(Usine parent) {

        traiterEntree(parent);

        boolean litharge = composant == IdentiteProduit.ACANTHITE || composant == IdentiteProduit.POUDRE_ACANTHITE;
        boolean bronze = composant == IdentiteProduit.LINGOT_CUIVRE && etain;

        if (litharge || bronze)
            travailler(parent);


    }

    @Override
    protected void traiterEntree(Usine parent) {
        int xentree1 = this.getX() - 1;
        int yentree1 = this.getY() - 1;

        int xentree2 = this.getX() - 1;
        int yentree2 = this.getY() + 1;


        traiterUneEntree(parent, xentree1, yentree1);
        traiterUneEntree(parent, xentree2, yentree2);
    }

    /**
     * Traite une entrée de la fournaise de grillage.
     * Même processus pour les deux entrées
     *
     * @param parent Usine parente
     * @param x      Position x de l'entrée
     * @param y      Position y de l'entrée
     */
    private void traiterUneEntree(Usine parent, int x, int y) {

        if (parent.getLogistique().contiensItem(x, y)) {

            if (composant != null && etain) {
                parent.ajouterNotification("La fournaise en (" + this.getX() + "," + this.getY() + ") a reçu un produit alors que son inventaire est déjà plein !");
                return;
            }

            Produit item = parent.getLogistique().trouverItem(x, y);

            if (item.getIdentite() == IdentiteProduit.LINGOT_ETAIN) {

                if (etain) {
                    parent.ajouterNotification("La fournaise en (" + this.getX() + "," + this.getY() + ") a reçu un lingot d'étain alors qu'elle en possède déjà un ! Le produit a été détruit.");
                    parent.getLogistique().extraireItem(x, y);
                    return;
                }


                if (composant != null)
                    parent.ajouterNotification("La fournaise en (" + this.getX() + "," + this.getY() + ") a commencé à transformer : " + composant.getNom() + " !");
                else
                    parent.ajouterNotification("La fournaise en (" + this.getX() + "," + this.getY() + ") a reçu un lingot d'étain.");

                etain = true;
                parent.getLogistique().extraireItem(x, y);
                return;
            }
            if (item.getIdentite() == IdentiteProduit.ACANTHITE ||
                    item.getIdentite() == IdentiteProduit.POUDRE_ACANTHITE ||
                    item.getIdentite() == IdentiteProduit.LINGOT_CUIVRE) {

                if (item.getIdentite() == composant) {
                    parent.ajouterNotification("La fournaise en (" + this.getX() + "," + this.getY() + ") a reçu un produit déjà en inventaire : " + item.getIdentite().getNom() + " ! Le produit a été détruit.");
                    parent.getLogistique().extraireItem(x, y);
                    return;
                }

                if (etain)
                    parent.ajouterNotification("La fournaise en (" + this.getX() + "," + this.getY() + ") a commencé à transformer : " + item.getIdentite().getNom() + " !");
                else
                    parent.ajouterNotification("La fournaise en (" + this.getX() + "," + this.getY() + ") a reçu un produit : " + item.getIdentite().getNom() + ".");

                composant = item.getIdentite();
                parent.getLogistique().extraireItem(x, y);
                return;
            }

            parent.ajouterNotification("La fournaise en (" + this.getX() + "," + this.getY() + ") a reçu un produit intraitable : " + item.getIdentite().getNom() + " ! Le produit a été détruit.");
            parent.getLogistique().extraireItem(x, y);
        }
    }

    @Override
    protected void travailler(Usine parent) {

        compteur++;

        int xsortie = this.getX() + 2;
        int ysortie = this.getY();

        if (compteur == 4) {

            IdentiteProduit resultat = IdentiteProduit.LINGOT_BRONZE;

            if (composant == IdentiteProduit.ACANTHITE || composant == IdentiteProduit.POUDRE_ACANTHITE)
                resultat = IdentiteProduit.LITHARGE;


            try {
                parent.getLogistique().placerItem(xsortie, ysortie, new Produit(resultat));
            } catch (PlacementIncorrectException e) {
                parent.ajouterNotification(
                        "La fournaise en (" + this.getX() + "," + this.getY() + ") : " + e.getMessage());
            }

            parent.ajouterNotification("La fournaise en (" + this.getX() + "," + this.getY() + ") a produit : " + resultat.getNom() + " !");
            composant = null;
            if (etain)
                etain = false;
            compteur = 0;
        }


    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {

        Case[] cases = {parent.getCase(x, y), parent.getCase(x, y - 1), parent.getCase(x + 1, y), parent.getCase(x, y + 1)};


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
        return "FOU";
    }
}
