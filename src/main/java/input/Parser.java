package input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Parser {

    public static Command getCommand() {
        String inputLine = "";   // will hold the full input line
        String word1 = "";

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            inputLine = reader.readLine();
        } catch (IOException exc) {
            System.out.println("There was an error during reading: "
                    + exc.getMessage());
        }

        StringTokenizer tokenizer = new StringTokenizer(inputLine);

        if (tokenizer.hasMoreTokens())
            word1 = tokenizer.nextToken();      // get first word

        // note: we just ignore the rest of the input line.

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).

        return Command.parseCommand(word1);
    }

    /**
     * Clear the console screen.
     */
    public static void clearScreen(){
        System.out.println(System.lineSeparator().repeat(50));
    }
}
