import input.Command;
import input.Parser;
import usine.IdentiteProduit;
import usine.PlacementIncorrectException;
import usine.TapisRoulant;
import usine.Usine;
import usine.geometrie.Geometrie;
import usine.geometrie.Position;
import usine.stations.Mine;
import usine.stations.Station;
import usine.stations.Vendeur;
import usine.stations.machines.*;

public class Main {

    public static int DELAY = 1000;

    public static void main(String[] args) {
        Usine usine = new Usine(10, 5);
        boolean marche = true;

        while (marche) {
            usine.afficher();
            System.out.println();
            System.out.println("Pour une liste des commandes, entrez 'help'");
            System.out.print("Taper une commande ou appuyer sur Entrer pour passer un tour : ");

            Command command = Parser.getCommand();
            while (command == Command.UNKNOWN_COMMAND) {
                System.out.println("Commande inconnue. Entrez 'help' pour une liste des commandes.");
                command = Parser.getCommand();
            }

            if (command == Command.PLACER) {

                Parser.clearScreen();
                usine.afficher();
                System.out.println();
                System.out.println("1. Station");
                System.out.println("2. Machine");
                System.out.println("3. Tapis roulant");
                System.out.println("Que voulez-vous placer? : ");

                Station station;
                TapisRoulant tapis = null;
                int premier_choix = Parser.getInt(1, 3);
                int second_choix = 0;

                Parser.clearScreen();
                switch (premier_choix) {
                    case 1:
                        System.out.println();
                        System.out.println("1. Mine d'Acanthite");
                        System.out.println("2. Mine de Cassiterite");
                        System.out.println("3. Mine de Chalcocite");
                        System.out.println("4. Mine de Charbon");
                        System.out.println("5. Vendeur");
                        System.out.println("Entrez le type de station :");
                        second_choix = Parser.getInt(1, 5);
                        break;
                    case 2:
                        System.out.println();
                        System.out.println("1. Fournaise");
                        System.out.println("2. Fournaise de coupellation");
                        System.out.println("3. Fournaise de grillage");
                        System.out.println("4. Moulin");
                        System.out.println("5. Touraille");
                        System.out.println("Entrez le type de machine :");
                        second_choix = Parser.getInt(1, 5);
                        break;
                    case 3:
                        System.out.println();
                        System.out.println("1. Tapis roulant haut-droite");
                        System.out.println("2. Tapis roulant haut-gauche");
                        System.out.println("3. Tapis roulant haut-bas");
                        System.out.println("4. Tapis roulant bas-droite");
                        System.out.println("5. Tapis roulant bas-gauche");
                        System.out.println("6. Tapis roulant bas-haut");
                        System.out.println("7. Tapis roulant gauche-droite");
                        System.out.println("8. Tapis roulant gauche-haut");
                        System.out.println("9. Tapis roulant gauche-bas");
                        System.out.println("10. Tapis roulant droite-gauche");
                        System.out.println("11. Tapis roulant droite-haut");
                        System.out.println("12. Tapis roulant droite-bas");
                        System.out.println("Entrez le type de tapis roulant :");
                        second_choix = Parser.getInt(1, 12);
                        break;
                }

                Parser.clearScreen();
                usine.afficher();
                System.out.println();

                int numCase = Parser.getCaseNumber(usine);


                Position position = Geometrie.lineaireVersCartesien(numCase, usine.getTailleX());
                int x = position.getX();
                int y = position.getY();

                if (premier_choix == 1 || premier_choix == 2) {
                    station = createStation(premier_choix, second_choix);

                    boolean success = false;

                    while(!success) {
                        try {
                            station.placer(x, y, usine);
                            success = true;
                        } catch (PlacementIncorrectException e) {
                            System.out.println(e.getMessage());
                            numCase = Parser.getCaseNumber(usine);
                            position = Geometrie.lineaireVersCartesien(numCase, usine.getTailleX());
                            x = position.getX();
                            y = position.getY();
                        }
                    }


                } else {
                    tapis = TapisRoulant.BAS_DROITE;
                    System.out.println("Tapis roulant placé dans la position : " + numCase);
                    // usine.placerTapisRoulant(tapis, numCase);
                }


            }

            usine.tic();
            delay(DELAY);
        }
    }

    public static Station createStation(int premier_choix, int second_choix) {
        if (premier_choix == 1) {
            switch (second_choix) {
                case 1:
                    return new Mine(IdentiteProduit.ACANTHITE);
                case 2:
                    return new Mine(IdentiteProduit.CASSITERITE);
                case 3:
                    return new Mine(IdentiteProduit.CHALCOCITE);
                case 4:
                    return new Mine(IdentiteProduit.CHARBON);
                case 5:
                    return new Vendeur();
            }
        } else if (premier_choix == 2) {
            switch (second_choix) {
                case 1:
                    return new Fournaise();
                case 2:
                    return new FournaiseDeCoupellation();
                case 3:
                    return new FournaiseDeGrillage();
                case 4:
                    return new Moulin();
                case 5:
                    return new Touraille();
            }
        }
        return null;

    }

    public static void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
