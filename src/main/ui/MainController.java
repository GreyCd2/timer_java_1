package ui;

import model.Storage;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class MainController {
    StorageController storageController;
    static final String JSON_STORE = "./data/storage.json";
    public static final String COMMAND_QUIT = "q";
    public static final String COMMAND_SAVE = "s";
    public static final String COMMAND_LOAD = "l";
    public static final String COMMAND_TIMER = "t";
    public static final String COMMAND_STORAGE = "g";
    Storage storage;
    Scanner input;
    JsonWriter jsonWriter;
    JsonReader jsonReader;

    public static void main(String[] args) {
        new MainController();
    }

    // EFFECTS: shows the starter page and runs the pb timer application
    public MainController() {
        System.out.println("Hi! Welcome to PB Timer.");

        init();
        appRouter();
    }

    // MODIFIES: this
    // EFFECTS:  processes user input
    private void init() {
        input = new Scanner(System.in);
        storage = new Storage();
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        storageController = new StorageController(this);
    }

    void appRouter() {
        startPage();
        String command = input.next();
        command = command.toLowerCase();

        switch (command) {
            case COMMAND_QUIT -> quit();
            case COMMAND_SAVE -> saveStorage();
            case COMMAND_LOAD -> loadStorage();
            case COMMAND_TIMER -> storageController.folderController.timerController.timerRouter();
            case COMMAND_STORAGE -> storageController.storageRouter();
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

    private void startPage() {
        String page = """
                \u001b[4;32m============== Main Page ==============\u001b[0m
                \u001b[4;32mPlease select which page you want to go\u001b[0m
                \u001b[4;32m[t]\u001b[0m -> start your timer here!
                \u001b[4;32m[g]\u001b[0m -> go to storage to see your past records!
                \u001b[4;32m[l]\u001b[0m -> load storage from file and go to storage!
                \u001b[4;32m[s]\u001b[0m -> save your changes to file and quit!
                \u001b[4;32m[q]\u001b[0m -> ignore all the changes and quit!
                """;
        System.out.println(page);
    }

    private void saveStorage() {
        try {
            jsonWriter.open();
            jsonWriter.write(storage);
            jsonWriter.close();
            System.out.println("Your changes to storage have been saved to " + JSON_STORE + ".");
            System.out.println("See you next time! :) ");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to file: " + JSON_STORE + ".");
        }
    }

    // EFFECTS:  process the starter page for user command
    private void loadStorage() {
        try {
            storage = jsonReader.read();
            System.out.println("Storage from file:" + JSON_STORE + " has been loaded.");
            storageController.storageRouter();
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE + ".");
        }
    }
}
