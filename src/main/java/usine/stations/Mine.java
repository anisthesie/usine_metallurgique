package usine.stations;

import usine.Case;
import usine.IdentiteProduit;
import usine.PlacementIncorrectException;
import usine.Usine;
import usine.geometrie.Geometrie;

public class Mine extends Station {
    protected IdentiteProduit minerai;

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

    }

    @Override
    public void placer(int x, int y, Usine parent) throws PlacementIncorrectException {
        Case case1 = parent.getCase(x, y);
        Case case2 = parent.getCase(x + 1, y);

        if (case1 == null || case2 == null || case1.isOccupe() || case2.isOccupe())
            throw new PlacementIncorrectException("Impossible de placer l'élement dans la case (" + Geometrie.cartesienVersLineaire(x, y, parent.getTailleX()) + ")");

        parent.ajouterStation(this);

        this.position.setX(x);
        this.position.setY(y);

        case1.setStation(this);
        case2.setStation(this);

        case1.setSymbole(getSymbole());
        case2.setSymbole("-->");

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
