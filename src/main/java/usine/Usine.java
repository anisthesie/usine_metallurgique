package usine;

import usine.stations.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usine {

    protected int tailleX;
    protected int tailleY;

    // contient les notifications qui seront affichées à l'utilisateur.
    private List<String> notifications;

    // contient les tapis Roulants.
    public Logistique logistique;
    // contient les cases de l'usine.
    protected Case[][] cases;
    // contient les stations
    private List<Station> stationList;
    // lie les produits avec leur nombre de ventes
    protected Map<IdentiteProduit, Integer> ventes;

    public Usine(int tailleX, int tailleY) {
        ventes = new HashMap<>();
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        stationList = new ArrayList<>();
        this.cases = new Case[tailleY][tailleX];
        this.notifications = new ArrayList<>();
        initCases();
        logistique = new Logistique(tailleX, tailleY);
        logistique.setGrille(cases);
    }

    public void tic() {
        logistique.tic();
        for (Station station : stationList) {
            if (station.isPlaced()) station.tic(this);
        }
    }

    public void afficher() {

        // Vider la console avant d'afficher
        System.out.println(System.lineSeparator().repeat(50));

        System.out.println(logistique.toString());

        if (!notifications.isEmpty()) System.out.println("Notifications: ");
        for (String notification : notifications)
            System.out.println("\t" + notification);
        notifications.clear();

        System.out.println();


    }

    /**
     * Récupère le nombre de ventes d'un produit.
     *
     * @param idProduit l'identité du produit.
     * @return le nombre de ventes du produit.
     */
    public int getVente(IdentiteProduit idProduit) {
        if (ventes.containsKey(idProduit)) return ventes.get(idProduit);
        return 0;
    }

    /**
     * Ajoute une station à l'usine.
     *
     * @param station la station à ajouter.
     */
    public void ajouterStation(Station station) {
        stationList.add(station);
    }

    /**
     * Ajoute une notification à afficher.
     *
     * @param notification la notification à ajouter.
     */
    public void ajouterNotification(String notification) {
        notifications.add(notification);
    }


    /**
     * Incrémente le nombre de ventes d'un produit.
     *
     * @param idProduit l'identité du produit.
     */
    public void incrementerVente(IdentiteProduit idProduit) {

        if (ventes.containsKey(idProduit)) {
            int nbVentes = ventes.get(idProduit);
            ventes.remove(idProduit);
            ventes.put(idProduit, nbVentes + 1);
        } else ventes.put(idProduit, 1);

    }

    /**
     * Récupère une case de l'usine.
     *
     * @param x la coordonnée x de la case.
     * @param y la coordonnée y de la case.
     * @return la case correspondante.
     */
    public Case getCase(int x, int y) {
        if (x < 0 || x >= tailleX || y < 0 || y >= tailleY) return null;
        return cases[y][x];
    }


    public void setTapisHorizontal(int y, int x1, int x2) {
        logistique.setTapisHorizontal(y, x1, x2);
    }

    public void setTapisVertical(int x, int y1, int y2) {
        logistique.setTapisVertical(x, y1, y2);
    }

    public void setStation(int x, int y, Station station) {
        station.placer(x, y, this);
    }

    public Produit trouverItem(int x, int y) {
        return logistique.trouverItem(x, y);
    }

    public int getTailleX() {
        return tailleX;
    }

    public int getTailleY() {
        return tailleY;
    }

    public Logistique getLogistique() {
        return logistique;
    }


    private void initCases() {
        for (int y = 0; y < tailleY; ++y) {
            for (int x = 0; x < tailleX; ++x) {
                cases[y][x] = new Case(x, y);
            }
        }
    }

    public Case[][] getCases() {
        return cases;
    }


}
