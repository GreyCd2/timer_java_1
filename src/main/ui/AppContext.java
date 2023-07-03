package ui;

import model.Storage;

import java.util.Scanner;

/**
 * Represent the action of read user input.
 *
 * @author Grey
 */
public class AppContext {
    public static Storage storage;
    public static Scanner input;

    private AppContext() {
    }

    /**
     * Read the line of text that user inputs.
     *
     * @return
     */
    public static String readLine() {
        return input.next();
    }

    /**
     * Read the next integer that user inputs.
     *
     * @return
     */
    public static int readInt() {
        try {
            return input.nextInt();
        } catch (Exception e) {
            errorMessage("Error: invalid input.");
            System.out.println("Please input an valid index.");
            return readInt();
        }
    }

    /**
     * Read the next command that user inputs.
     * Recurve until the format is a correct command format.
     *
     * @return
     */
    public static String readCommand() {
        String command = input.next();
        while (command.length() > 1) {
            errorMessage("Error: invalid command.");
            System.out.println("Please input an given character.");
            command = input.next();
        }
        command = command.toLowerCase();
        return command;
    }

    /**
     * Print the error message in red text.
     *
     * @param message
     */
    public static void errorMessage(String message) {
        System.out.println("\u001b[4;31m" + message + "\u001b[0m");
    }
}
