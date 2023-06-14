package ui;

import model.Storage;

import java.util.Scanner;

public class AppContext {
//    private static final AppContext INSTANCE = new AppContext();
    public static Storage storage;
    public static Scanner input;

    private AppContext() {
    }

/*    public static AppContext getInstance() {
        return INSTANCE;
    }*/

    public static String readLine() {
//        return input.next() + input.nextLine();
        return input.next();
    }

    public static int readInt() {
        try {
            return input.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please input an valid index.");
            return readInt();
        }
    }

    public static String readCommand() {
        String command = input.next();
        while (command.length() > 1) {
            System.out.println("Invalid command. Please input an given character.");
            command = input.next();
        }
        command = command.toLowerCase();
        return command;
    }
}
