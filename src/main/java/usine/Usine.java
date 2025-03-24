package usine;

import input.Parser;
import usine.geometrie.Geometrie;
import usine.geometrie.Position;
import usine.stations.Station;

import java.util.ArrayList;
import java.util.List;

public class Usine {
    protected int tailleX;
    protected int tailleY;

    protected boolean afficherIndex;

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
        logistique = new Logistique(tailleX, tailleY);
        stations = new ArrayList<>();
        this.cases = new Case[tailleX][tailleY];
        this.notifications = new ArrayList<>();
        initCases();
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
      /*  for (int y = 0; y < tailleY; ++y) {
            for (int x = 0; x < tailleX; ++x) {

                Case courante = cases[x][y];
                String symbole = courante.getSymbole();

                int indentationRelative = 3 - symbole.length();
                if (x == 0) System.out.print("|");

                for (int i = 0; i < indentationRelative; i++)
                    System.out.print(" ");
                System.out.print(symbole);
                System.out.print(" | ");

            }
            System.out.println();
        }

        */


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
        for (int x = 0; x < tailleX; x++) {
            for (int y = 0; y < tailleY; y++) {
                cases[x][y] = new Case(x, y, this);
            }
        }
    }

    public Case getCase(int x, int y) {
        if (x < 0 || x >= tailleX || y < 0 || y >= tailleY)
            return null;
        return cases[x][y];
    }

    public Case getCase(int indexLineaire) {
        Position position = Geometrie.lineaireVersCartesien(indexLineaire, tailleX);
        return getCase(position.getX(), position.getY());
    }

    public Case[][] getCases() {
        return cases;
    }

    public boolean isAfficherIndex() {
        return afficherIndex;
    }

    public void setAfficherIndex(boolean afficherIndex) {
        this.afficherIndex = afficherIndex;
    }

    public void ajouterNotification(String notification) {
        notifications.add(notification);
    }
}
