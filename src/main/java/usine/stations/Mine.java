package usine.stations;

import usine.*;
import usine.geometrie.Geometrie;

public class Mine extends Station {
    protected IdentiteProduit minerai;
    private int compteur = 0;

    // Recettes    | temps
    // ------------+----------
    // Acanthite   | 2 tours
    // Cassiterite | 7 tours
    // Chalcocite  | 3 tours
    // Charbon     | 1 tour.

    //   ....
    //   .Ms.
    //   ....
    // M( x,   y ) : Case occupé par la Mine
    // s( x+1, y ) : Case où la Mine place les sorties.

    public Mine(IdentiteProduit minerai, int positionX, int positionY) {
        super(positionX, positionY);
        this.minerai = minerai;
    }

    public Mine(IdentiteProduit minerai) {
        super();
        this.minerai = minerai;
    }

    public IdentiteProduit getMinerai() {
        return minerai;
    }

    @Override
    public void tic(Usine parent) {
        switch (minerai) {
            case CHARBON:
                if (compteur == 1) {
                    try {
                        parent.getLogistique().placerItem(this.position.getX() + 1, this.position.getY(), new Produit(IdentiteProduit.CHARBON));
                    } catch (PlacementIncorrectException e) {
                        parent.ajouterNotification(
                                "L'usine de charbon en (" + this.position.getX() + "," + this.position.getY() + ") a construit un objet mais n'a pas trouvé de tapis roulant à sa droite.");
                    }
                    compteur = 0;
                }
                break;
            case ACANTHITE:
                if (compteur == 2) {
                    //parent.ajouterProduit(IdentiteProduit.ACANTHITE);
                    compteur = 0;
                }
                break;
            case CASSITERITE:
                if (compteur == 7) {
                    //parent.ajouterProduit(IdentiteProduit.CASSITERITE);
                    compteur = 0;
                }
                break;
            case CHALCOCITE:
                if (compteur == 3) {
                    //parent.ajouterProduit(IdentiteProduit.CHALCOCITE);
                    compteur = 0;
                }
                break;
        }
        compteur++;
    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case[] cases = {parent.getCase(x, y)};

        if (!this.areCasesValid(cases))
            throw new PlacementIncorrectException("Impossible de placer l'élement dans la case (" + Geometrie.cartesienVersLineaire(x, y, parent.getTailleX()) + ")");

        parent.ajouterStation(this);

        this.position.setX(x);
        this.position.setY(y);

        for (Case c : cases) {
            c.setStation(this);
            c.setSymbole(getSymbole());
            parent.getLogistique().setTapis(c.getX(), c.getY(), TapisRoulant.OCCUPE);
        }


    }


    @Override
    public String getSymbole() {

        switch (minerai) {
            case ACANTHITE:
                return "ACT";
            case CASSITERITE:
                return "CST";
            case CHALCOCITE:
                return "CLT";
            case CHARBON:
                return "CHB";
            default:
                return "M";
        }
    }
}
