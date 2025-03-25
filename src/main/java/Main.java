import input.Command;
import input.Parser;
import usine.IdentiteProduit;
import usine.PlacementIncorrectException;
import usine.TapisRoulant;
import usine.Usine;
import usine.geometrie.Geometrie;
import usine.geometrie.Position;
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
                        break;
                }

                Usine.AFFICHER_INDEX = true;

                Parser.clearScreen();
                usine.afficher();
                System.out.println();


                int numCase = Parser.getCaseNumber(usine);

                Usine.AFFICHER_INDEX = false;

                Position position = Geometrie.lineaireVersCartesien(numCase, usine.getTailleX());
                int x = position.getX();
                int y = position.getY();

                if (premier_choix == 1 || premier_choix == 2) {
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
                            x = position.getX();
                            y = position.getY();
                        }
                    }


                } else {
                    usine.getLogistique().setTapis(x, y, tapis);
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
