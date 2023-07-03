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
    static final String JSON_STORE = "./data/storage.json";
    public static final String COMMAND_QUIT = "q";
    public static final String COMMAND_SAVE = "s";
    public static final String COMMAND_TIMER = "t";
    public static final String COMMAND_STORAGE = "g";
    static JsonWriter jsonWriter;
    static JsonReader jsonReader;
    private static Page page = new Page("Main Page", "Please select which page you want to go");

    public static void main(String[] args) {
        MainController.startApp();
    }

    /**
     * Set up input and storage,
     * start writer and reader,
     * load storage and set up opening page.
     */
    public static void startApp() {
        System.out.println("Hi! Welcome to PB Timer.");

        AppContext.input = new Scanner(System.in);
        AppContext.storage = Storage.getInstance();
        AppContext.input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        new StorageController();

        page.addCommand(new Command(COMMAND_TIMER, "start your timer here!"));
        page.addCommand(new Command(COMMAND_STORAGE, "go to storage to see your past records!"));
        page.addCommand(new Command(COMMAND_SAVE, "save your changes to file and quit!"));
        page.addCommand(new Command(COMMAND_QUIT, "ignore all the changes and quit!"));

        loadStorage();
    }

    /**
     * Load file into storage.
     * Throw error message if fail to read.
     */
    private static void loadStorage() {
        try {
            AppContext.storage = jsonReader.read();
            System.out.println("Storage from file:" + JSON_STORE + " has been loaded.");
        } catch (IOException e) {
            AppContext.errorMessage("Error: unable to read from file " + JSON_STORE + ".");
        }
    }

    /**
     * Display guiding page.
     * Collect user response and direct to corresponding page.
     */
    static void router() {
        System.out.println(page);

        switch (AppContext.readCommand()) {
            case COMMAND_TIMER -> Timer.timer();
            case COMMAND_STORAGE -> StorageController.router();
            case COMMAND_SAVE -> saveStorage();
            case COMMAND_QUIT -> quit();
            default -> invalidMainCommand();
        }
    }

    /**
     * Save current storage into file and quit.
     * Throw error message if fail to save and redirect to router
     */
    private static void saveStorage() {
        try {
            jsonWriter.open();
            jsonWriter.write(AppContext.storage);
            jsonWriter.close();
            System.out.println("Your changes to storage have been saved to " + JSON_STORE + ".");
            System.out.println("See you next time! :) ");
            System.exit(0);
        } catch (FileNotFoundException e) {
            AppContext.errorMessage("Error: unable to save to file: " + JSON_STORE + ".");
            router();
        }
    }

    /**
     * Quit without saving.
     */
    private static void quit() {
        System.out.println("Thank you for using PB Timer.\nSee you next time! :)");
        System.exit(0);
    }

    /**
     * Display error message and redirect to main router.
     */
    private static void invalidMainCommand() {
        AppContext.errorMessage("Error: invalid selection.");
        System.out.println("Please input an character that is provided.");

        router();
    }
}
