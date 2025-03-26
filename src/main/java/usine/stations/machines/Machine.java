package usine.stations.machines;

import usine.IdentiteProduit;
import usine.Usine;
import usine.stations.Station;

public abstract class Machine extends Station {

    IdentiteProduit produitEnCours = null;
    int compteur = 0;

    public Machine(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public Machine() {
        super();
    }

    protected abstract void travailler(Usine parent);

    protected abstract void traiterEntree(Usine parent);

    @Override
    public void tic(Usine parent) {

        traiterEntree(parent);

        if (produitEnCours != null)
            travailler(parent);
    }
}
