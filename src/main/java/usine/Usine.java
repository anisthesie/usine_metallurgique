package usine;

import input.Parser;
import usine.geometrie.Geometrie;
import usine.geometrie.Position;
import usine.stations.Station;

import java.util.ArrayList;
import java.util.List;

public class Usine {

    public static boolean AFFICHER_INDEX;


    protected int tailleX;
    protected int tailleY;

    private List<String> notifications;

    // contient les tapis Roulants.
    protected Logistique logistique;
    // contient les stations de l'usine.
    protected Case[][] cases;
    // contient les stations
    protected List<Station> stations;

    public Usine(int tailleX, int tailleY) {
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        stations = new ArrayList<>();
        this.cases = new Case[tailleY][tailleX];
        this.notifications = new ArrayList<>();
        initCases();
        logistique = new Logistique(tailleX, tailleY, this);
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

    public void tic() {

        logistique.tic();
        for (Station station : stations) {
            station.tic(this);
        }
    }

    public void afficher() {

        Parser.clearScreen();
        System.out.println(logistique.toString());

        if (!notifications.isEmpty())
            System.out.println("Notifications: ");
        for (String notification : notifications)
            System.out.println("\t" + notification);
        notifications.clear();

        System.out.println();


    }

    public void ajouterStation(Station station) {
        stations.add(station);
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

    public int getVente(IdentiteProduit idProduit) {
        return 0;
    }

    private void initCases() {
        for (int y = 0; y < tailleY; ++y) {
            for (int x = 0; x < tailleX; ++x) {
                cases[y][x] = new Case(x, y, this);
            }
        }
    }

    public Case getCase(int x, int y) {
        if (x < 0 || x >= tailleX || y < 0 || y >= tailleY)
            return null;
        return cases[y][x];
    }

    public Case[][] getCases() {
        return cases;
    }

    public void ajouterNotification(String notification) {
        notifications.add(notification);
    }
}
