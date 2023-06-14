package ui;

import model.Folder;
import model.TimeRecord;
import java.util.ArrayList;

/**
 * Represent the folder layer of the Timer app
 * @author Grey
 */
public class FolderController {
    MainController mainController;
    TimerController timerController;
    StorageController storageController;
    private static final String COMMAND_DELETE = "d";
    private static final String COMMAND_EDIT = "e";
    private static final String COMMAND_CALCULATOR = "c";
    private static final String COMMAND_STORAGE = "s";
    private static final String COMMAND_BACK_TO_MAIN = "m";

    public FolderController(MainController mainController,
                            StorageController storageController) {
        this.mainController = mainController;
        this.storageController = storageController;

        this.timerController = new TimerController(mainController, storageController, this);
    }

    /**
     * User can choose to:
     * 1. Delete a time record
     * 2. Edit note for a time record
     * 3. Use the average calculator
     * 4. Go back to storage page
     * 5. Go back to main page
     * @param f
     */
    void doWithFolder(Folder f) {
        String page = """
                \u001b[4;32m========= Folder Page =========\u001b[0m
                \u001b[4;32m[d]\u001b[0m -> delete a time record
                \u001b[4;32m[e]\u001b[0m -> edit note for a time record
                \u001b[4;32m[c]\u001b[0m -> call the average calculator
                \u001b[4;32m[s]\u001b[0m -> go back to storage page
                \u001b[4;32m[m]\u001b[0m -> go back to main starter page
                """;
        System.out.println(page);

        String selection = mainController.input.next();
        selection = selection.toLowerCase();
        switch (selection) {
            case COMMAND_DELETE -> timerController.deleteRecord(f);
            case COMMAND_EDIT -> timerController.editNote(f);
            case COMMAND_CALCULATOR -> openCalculator(f);
            case COMMAND_STORAGE -> storageController.storageRouter();
            case COMMAND_BACK_TO_MAIN -> mainController.appRouter();
            default -> invalidFolderCommand(f);
        }
    }

    /**
     * If user input is invalid, redirect to choice page again
     * @param f
     */
    private void invalidFolderCommand(Folder f) {
        System.out.println("Invalid input. Please enter a character that is provided.");
        doWithFolder(f);
    }

    /**
     * Create a new folder with user input name and add it into the storage
     */
    void createFolder() {
        System.out.println("How would you like to name this new Folder?");
        String folderName = mainController.input.next() + mainController.input.nextLine();
        mainController.storage.addFolder(folderName);
        System.out.println("Folder " + folderName + " has been created.");

        storageController.storageRouter();
    }

    /**
     * Delete the folder with user input index in the storage
     * Redirect user to the storage page
     */
    void deleteFolder() {
        System.out.println("Please enter the index of the folder you want to delete:");
        int index = mainController.input.nextInt();
        mainController.storage.deleteFolder(index);
        System.out.println("Folder " + index + " has been deleted.");

        storageController.storageRouter();
    }

    /**
     * Rename the folder with user input index and user input name
     * Redirect user to the storage page
     */
    void renameFolder() {
        System.out.println("Please enter the index of the folder you want to rename:");
        int index = mainController.input.nextInt();
        Folder f = mainController.storage.getFolders().get(index);
        System.out.println("Please enter the new name for this folder:");
        String newName = mainController.input.next() + mainController.input.nextLine();
        f.setName(newName);
        System.out.println("Folder " + index + " has been renamed as " + newName + ".");

        storageController.storageRouter();
    }
    // EFFECTS: open the folder with given index, display all the records in the folder
    //          Then choose what to do with the folder.

    /**
     * Open the folder with user input index and display all the records in the chosen folder
     * User can then choose what to do with the folder or records inside the folder
     */
    void openFolder() {
        System.out.println("Please enter the index of the folder you want to open:");
        int index = mainController.input.nextInt();
        Folder f = mainController.storage.getFolders().get(index);
        System.out.println("Folder: " + f.getName());
        displayRecords(f);
        doWithFolder(f);
    }

    /**
     *
     * @param f
     */
    private void openCalculator(Folder f) {
        System.out.println("The calculator is ready.");
        System.out.println("Please enter the number of records to count, or -1 for all records in the folder.");

        int amount = mainController.input.nextInt();
        if (amount == -1) {
            calculateAll(f);
        } else {
            calculateDesiredAmount(f, amount);
        }

        doWithFolder(f);
    }

    // MODIFIES: this
    // EFFECTS:  calculate all records' average time in the given folder.
    public void calculateAll(Folder f) {
        double sum = 0;
        int count = 0;

        for (TimeRecord tr : (f.getRecords())) {
            sum = sum + tr.getTime();
            count++;
        }

        double result = sum / count;
        System.out.println("Your average time is: " + result);
    }

    // MODIFIES: this
    // EFFECTS:  calculate given amount of records' average time in the given folder.
    public void calculateDesiredAmount(Folder f, int amount) {
        double sum = 0;
        ArrayList<TimeRecord> records = f.getRecords();

        for (TimeRecord tr : records) {
            if (records.indexOf(tr) < amount) {
                sum = sum + tr.getTime();
            }
        }

        double result = sum / amount;
        System.out.println("Your average time is: " + result);
    }

    public void displayRecords(Folder f) {
        System.out.println("PB Record: " + (f.getBestRecord()).getTime() + ": " + (f.getBestRecord()).getNote());

        for (TimeRecord r : (f.getRecords())) {
            System.out.println((f.getRecords()).indexOf(r) + ". " + r.getTime() + ": " + r.getNote());
        }

        System.out.println("All records you have in this folder are shown above.");
    }
}
