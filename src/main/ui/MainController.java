package ui;

import model.Storage;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represent the main start page of the Timer app
 *
 * @author Grey
 */
public class MainController {
//    StorageController storageController;
    static final String JSON_STORE = "./data/storage.json";
    public static final String COMMAND_QUIT = "q";
    public static final String COMMAND_SAVE = "s";
    public static final String COMMAND_LOAD = "l";
    public static final String COMMAND_TIMER = "t";
    public static final String COMMAND_STORAGE = "g";
    static JsonWriter jsonWriter;
    static JsonReader jsonReader;
    static AppContext appContext = AppContext.getInstance();

    public static void main(String[] args) {
        new MainController();
    }

    // EFFECTS: shows the starter page and runs the pb timer application
    public MainController() {
        System.out.println("Hi! Welcome to PB Timer.");

        appContext.input = new Scanner(System.in);
        appContext.storage = Storage.getInstance();
        appContext.input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        new StorageController(appContext);

        router();
    }

    static void router() {
        startPage();
        String command = appContext.input.next();
        command = command.toLowerCase();

        switch (command) {
            case COMMAND_QUIT -> quit();
            case COMMAND_SAVE -> saveStorage();
            case COMMAND_LOAD -> loadStorage();
            case COMMAND_TIMER -> Timer.displayPage();
            case COMMAND_STORAGE -> StorageController.displayPage();
            default -> invalidAppCommand();
        }
    }

    private static void quit() {
        System.out.println("Thank you for using PB Timer.");
        System.out.println("See you next time! :)");
        System.exit(0);
    }

    private static void invalidAppCommand() {
        System.out.println("Invalid selection.");
        System.out.println("Please input an character that is provided.");
    }

    private static void startPage() {
        Page page = new Page("Main Page", "Please select which page you want to go");
        page.addCommand(new Command(COMMAND_TIMER, "start your timer here!"));
        page.addCommand(new Command(COMMAND_STORAGE, "go to storage to see your past records!"));
        page.addCommand(new Command(COMMAND_LOAD, "load storage from file and go to storage!"));
        page.addCommand(new Command(COMMAND_SAVE, "save your changes to file and quit!"));
        page.addCommand(new Command(COMMAND_QUIT, "ignore all the changes and quit!"));
        System.out.println(page.toString());
    }

    private static void saveStorage() {
        try {
            jsonWriter.open();
            jsonWriter.write(appContext.storage);
            jsonWriter.close();
            System.out.println("Your changes to storage have been saved to " + JSON_STORE + ".");
            System.out.println("See you next time! :) ");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to file: " + JSON_STORE + ".");
        }
    }

    // EFFECTS:  process the starter page for user command
    private static void loadStorage() {
        try {
            appContext.storage = jsonReader.read();
            System.out.println("Storage from file:" + JSON_STORE + " has been loaded.");
            StorageController.displayPage();
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE + ".");
        }
    }
}
