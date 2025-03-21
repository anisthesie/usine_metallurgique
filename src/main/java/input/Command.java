package input;

/**
 * Enumération des commandes possibles pour le jeu.
 */
public enum Command {

    HAUT,
    BAS,
    GAUCHE,
    DROITE,
    PLACER,
    HELP,
    ENTER,
    UNKNOWN_COMMAND;

    /**
     * Méthode pour parser une commande à partir d'une chaîne de caractères.
     * Utilisé dans {@link Parser#getCommand()} pour convertir l'entrée de l'utilisateur en une énumération.
     *
     * @param commandWord la chaîne de caractères à parser
     * @return la commande correspondante, ou UNKNOWN_COMMAND si la commande n'existe pas
     */
    public static Command parseCommand(String commandWord) {
        if (commandWord == null || commandWord.isEmpty())
            return ENTER;

        try {
            return Command.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN_COMMAND;
        }
    }
}
