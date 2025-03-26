import input.Command;
import input.Parser;
import usine.IdentiteProduit;
import usine.PlacementIncorrectException;
import usine.TapisRoulant;
import usine.Usine;
import usine.geometrie.Geometrie;
import usine.geometrie.directions.Direction2D;
import usine.stations.Mine;
import usine.stations.Station;
import usine.stations.Vendeur;
import usine.stations.machines.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Usine usine = new Usine(20, 5);
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

                        // Code pour placer une chaine de tapis
                        /*
                        Usine.AFFICHER_INDEX = true;
                        Parser.clearScreen();
                        usine.afficher();

                        int depart = Parser.getCaseNumber(usine, "Entrez le numéro de la case de départ : ");
                        int arrivee = Parser.getCaseNumber(usine, "Entrez le numéro de la case d'arrivée : ");

                        boolean erreur = false;

                        int[] departXY = Geometrie.lineaireVersCartesien(depart, usine.getTailleX());
                        int[] arriveeXY = Geometrie.lineaireVersCartesien(arrivee, usine.getTailleX());

                        int x1 = departXY[0];
                        int y1 = departXY[1];

                        int x2 = arriveeXY[0];
                        int y2 = arriveeXY[1];

                        try {
                            if (x1 != x2)
                                usine.setTapisHorizontal(y1, Math.min(x1, x2), Math.max(x1, x2));

                            if (y1 != y2)
                                usine.setTapisVertical(x2, Math.min(y1, y2), Math.max(y1, y2));
                        } catch (PlacementIncorrectException e) {
                            erreur = true;
                        }

                        while (arrivee == depart || erreur) {
                            System.out.println("La case de départ et d'arrivée ne peuvent pas être les mêmes.");
                            arrivee = Parser.getCaseNumber(usine, "Entrez le numéro de la case d'arrivée : ");
                        }

                        Usine.AFFICHER_INDEX = false;
                        */
                        // Fin


                        // Debut code pour placer les tapis roulants en une seule unité
                        List<Command> commandesAcceptes = new ArrayList<>(Arrays.asList(Command.HAUT, Command.BAS, Command.GAUCHE, Command.DROITE));

                        System.out.println();
                        System.out.println("Taper la direction du premier segment");
                        Command commandeSegment1 = Parser.getCommand(commandesAcceptes);

                        commandesAcceptes.remove(commandeSegment1);

                        System.out.println("Taper la direction du deuxième segment");
                        Command commandeSegment2 = Parser.getCommand(commandesAcceptes);

                        Direction2D segment1 = Parser.commandToDirection(commandeSegment1);
                        Direction2D segment2 = Parser.commandToDirection(commandeSegment2);
                        tapis = TapisRoulant.trouver(segment1, segment2);

                        Usine.AFFICHER_INDEX = true;

                        Parser.clearScreen();
                        usine.afficher();
                        System.out.println();


                        int numCase = Parser.getCaseNumber(usine);

                        Usine.AFFICHER_INDEX = false;

                        int[] position = Geometrie.lineaireVersCartesien(numCase, usine.getTailleX());
                        int x = position[0];
                        int y = position[1];

                        usine.getLogistique().setTapis(x, y, tapis);
                        // Fin code pour placer les tapis roulants en une seule unité

                        break;
                }

                if (premier_choix == 1 || premier_choix == 2) {
                    Usine.AFFICHER_INDEX = true;

                    Parser.clearScreen();
                    usine.afficher();
                    System.out.println();


                    int numCase = Parser.getCaseNumber(usine);

                    Usine.AFFICHER_INDEX = false;

                    int[] position = Geometrie.lineaireVersCartesien(numCase, usine.getTailleX());
                    int x = position[0];
                    int y = position[1];


                    station = createStation(premier_choix, second_choix);

                    boolean success = false;

                    while (!success) {
                        try {
                            station.placer(x, y, usine);
                            success = true;
                        } catch (PlacementIncorrectException e) {
                            System.out.println(e.getMessage());
                            numCase = Parser.getCaseNumber(usine);
                            position = Geometrie.lineaireVersCartesien(numCase, usine.getTailleX());
                            x = position[0];
                            y = position[1];
                        }
                    }


                }
            }

            usine.tic();
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

}
