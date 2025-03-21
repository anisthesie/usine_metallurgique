import input.Command;
import input.Parser;
import usine.Usine;

public class Main {

    public static int DELAY = 1000;

    public static void main(String[] args) {
        Usine usine = new Usine(10, 5);
        boolean marche = true;

        while (marche) {
            usine.afficher();
            System.out.println();
            System.out.println("Pour une liste des commandes, entrez 'help'");
            System.out.print("Entrer une commande ou Entrer pour continuer : ");

            Command command = Parser.getCommand();
            while (command == Command.UNKNOWN_COMMAND) {
                System.out.println("Commande inconnue. Entrez 'help' pour une liste des commandes.");
                command = Parser.getCommand();
            }

            System.out.println(command);

            usine.tic();
            delay(DELAY);
        }
    }

    public static void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
