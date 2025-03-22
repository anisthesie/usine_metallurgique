package usine;

import input.Parser;
import usine.directions.Direction2D;
import usine.stations.Station;

public class Usine {
    protected int tailleX;
    protected int tailleY;

    // contient les tapis Roulants.
    protected Logistique logistique;
    // contient les stations de l'usine.
    protected Station[][] stations;

    public Usine(int tailleX, int tailleY) {
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        logistique = new Logistique(tailleX, tailleY);
        stations = new Station[tailleY][tailleX];
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

        for (int y = 0; y < tailleY; ++y) {
            for (int x = 0; x < tailleX; ++x) {
                Station courante = stations[y][x];
                if (null != courante) {
                    courante.tic(this);
                }
            }
        }
    }

    public void afficher() {

        Parser.clearScreen();

        for (int x = 0; x < tailleX; x++) {
            for (int y = 0; y < tailleY; y++){
                int indexLineaire = Direction2D.cartesianToLinear(x, y, tailleY);
                Station courante = stations[y][x];
                TapisRoulant tapis = logistique.getTapis(x, y);

                String symbole = "T";

                if(tapis== TapisRoulant.VIDE){
                    symbole = String.valueOf(indexLineaire);
                }
                else if (tapis == TapisRoulant.OCCUPE){
                    symbole = courante.getSymbole();
                }


                int indentationRelative = 3 - symbole.length();

                if(y == 0)
                    System.out.print("|");

                for(int i = 0; i < indentationRelative;i++)
                    System.out.print(" ");
                System.out.print(indexLineaire);
                System.out.print(" | ");

            }
            System.out.println();
        }
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
}
