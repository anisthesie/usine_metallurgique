package input;

import usine.Usine;
import usine.geometrie.Geometrie;
import usine.geometrie.directions.Direction2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

public class Parser {

    public static Command getCommand() {
        return Command.parseCommand(getString());
    }

    public static Direction2D commandToDirection(Command command) {
        switch (command) {
            case HAUT:
                return Direction2D.HAUT;
            case BAS:
                return Direction2D.BAS;
            case GAUCHE:
                return Direction2D.GAUCHE;
            case DROITE:
                return Direction2D.DROITE;
            default:
                return null;
        }
    }

    public static Command getCommand(List<Command> acceptedCommands) {

        afficherCommandes(acceptedCommands);

        Command command = getCommand();
        while (!isValid(command, acceptedCommands)) {
            System.out.println("Commande invalide.");
            afficherCommandes(acceptedCommands);
            command = getCommand();
        }
        return command;
    }

    public static void afficherCommandes(List<Command> acceptedCommands) {
        System.out.print("(");
        for (int i = 0; i < acceptedCommands.size(); i++) {
            System.out.print(acceptedCommands.get(i).toString());
            if (i < acceptedCommands.size() - 1)
                System.out.print(", ");
        }
        System.out.print(") : ");
    }

    public static boolean isValid(Command command, List<Command> acceptedCommands) {
        for (Command acceptedCommand : acceptedCommands) {
            if (command == acceptedCommand)
                return true;
        }
        return false;
    }

    public static int getCaseNumber(Usine usine) {
        System.out.print("Entrez le numéro de case où placer l'élément : ");
        return getInt(0, Geometrie.cartesienVersLineaire(usine.getTailleX() - 1, usine.getTailleY() - 1, usine.getTailleX()));
    }

    public static int getInt(int min, int max) {
        int numCase = min - 1;

        while (numCase < min || numCase > max) {
            try {
                numCase = Parser.getInt();
            } catch (NumberFormatException e) {
                System.out.println("Nombre invalide.");
            }
            if (numCase < min || numCase > max) {
                System.out.println("Entrez un nombre entre " + min + " et " + max);
            }
        }

        return numCase;

    }

    public static int getInt() throws NumberFormatException {
        return Integer.parseInt(getString());
    }

    public static String getString() {
        String inputLine = "";
        String word1 = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            inputLine = reader.readLine();
        } catch (IOException exc) {
            System.out.println("Erreur : " + exc.getMessage());
        }

        StringTokenizer tokenizer = new StringTokenizer(inputLine);

        if (tokenizer.hasMoreTokens()) word1 = tokenizer.nextToken();
        return word1;
    }


    /**
     * Clear the console screen.
     */
    public static void clearScreen() {
        System.out.println(System.lineSeparator().repeat(50));
    }
}
