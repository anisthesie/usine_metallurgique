package usine.stations.machines;

import usine.IdentiteProduit;
import usine.Usine;
import usine.stations.Station;

public abstract class Machine extends Station {

    // Produit en cours de traitement, null si aucun.
    IdentiteProduit produitEnCours = null;
    // Compteur de tours passés depuis le début du traitement.
    int compteur = 0;

    public Machine(int positionX, int positionY) {
        super(positionX, positionY);
    }

    public Machine() {
        super();
    }

    @Override
    public void tic(Usine parent) {

        traiterEntree(parent);

        if (produitEnCours != null) travailler(parent);
    }

    /**
     * Travaille sur le produit en cours.
     *
     * @param parent l'usine parente
     */
    protected abstract void travailler(Usine parent);

    /**
     * Traite l'entrée de la machine.
     *
     * @param parent l'usine parente
     */
    protected abstract void traiterEntree(Usine parent);


}
