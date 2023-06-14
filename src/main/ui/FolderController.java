package ui;

import model.Folder;
import model.TimeRecord;

import java.util.ArrayList;

/**
 * Represent the folder layer of the Timer app
 *
 * @author Grey
 */
public class FolderController {
    TimeRecordController timeRecordController;
    static AppContext appContext;
    private static final String COMMAND_DELETE = "d";
    private static final String COMMAND_EDIT = "e";
    private static final String COMMAND_CALCULATOR = "c";
    private static final String COMMAND_STORAGE = "s";
    private static final String COMMAND_BACK_TO_MAIN = "m";

    public FolderController(AppContext appContext) {
        this.appContext = appContext;

        this.timeRecordController = new TimeRecordController(appContext);
    }

    /**
     * User can choose to:
     * 1. Delete a time record
     * 2. Edit note for a time record
     * 3. Use the average calculator
     * 4. Go back to storage page
     * 5. Go back to main page
     *
     * @param folder
     */
    static void displayPage(Folder folder) {
        String page = """
                \u001b[4;32m========= Folder Page =========\u001b[0m
                \u001b[4;32m[d]\u001b[0m -> delete a time record
                \u001b[4;32m[e]\u001b[0m -> edit note for a time record
                \u001b[4;32m[c]\u001b[0m -> call the average calculator
                \u001b[4;32m[s]\u001b[0m -> go back to storage page
                \u001b[4;32m[m]\u001b[0m -> go back to main starter page
                """;
        System.out.println(page);
        router(folder);
    }

    static void router(Folder folder) {
        String selection = appContext.input.next();
        selection = selection.toLowerCase();
        switch (selection) {
            case COMMAND_DELETE -> TimeRecordController.deleteRecord(folder);
            case COMMAND_EDIT -> TimeRecordController.editNote(folder);
            case COMMAND_CALCULATOR -> openCalculator(folder);
            case COMMAND_STORAGE -> StorageController.displayPage();
            case COMMAND_BACK_TO_MAIN -> MainController.router();
            default -> invalidFolderCommand(folder);
        }
    }

    /**
     * If user input is invalid, redirect to choice page again
     *
     * @param f
     */
    private static void invalidFolderCommand(Folder f) {
        System.out.println("\u001b[4;32mInvalid input. Please enter a character that is provided.\u001b[0m");
        displayPage(f);
    }

    /**
     * Create a new folder with user input name and add it into the storage
     */
    static void createFolder() {
        System.out.println("\u001b[4;32mHow would you like to name this new Folder?\u001b[0m");
        String folderName = appContext.input.next() + appContext.input.nextLine();
        appContext.storage.addFolder(folderName);
        String formatName = String.format("\u001b[4;32mFolder %s has been created.\u001b[0m", folderName);
        System.out.println(formatName);

        StorageController.displayPage();
    }

    /**
     * Delete the folder with user input index in the storage
     * Redirect user to the storage page
     */
    static void deleteFolder() {
        System.out.println("\u001b[4;32mPlease enter the index of the folder you want to delete:\u001b[0m");
        int index = appContext.input.nextInt();
        appContext.storage.deleteFolder(index);
        String formatIndex = String.format("\u001b[4;32mFolder %s has been deleted.\u001b[0m", index);
        System.out.println(formatIndex);

        StorageController.displayPage();
    }

    /**
     * Rename the folder with user input index and user input name
     * Redirect user to the storage page
     */
    static void renameFolder() {
        System.out.println("Please enter the index of the folder you want to rename:");
        int index = appContext.input.nextInt();
        Folder f = appContext.storage.getFolders().get(index);
        System.out.println("Please enter the new name for this folder:");
        String newName = appContext.input.next() + appContext.input.nextLine();
        f.setName(newName);
        System.out.println("Folder " + index + " has been renamed as " + newName + ".");

        StorageController.displayPage();
    }

    /**
     * Open the folder with user input index and display all the records in the chosen folder
     * User can then choose what to do with the folder or records inside the folder
     */
    static void openFolder() {
        System.out.println("Please enter the index of the folder you want to open:");
        int index = appContext.input.nextInt();
        Folder folder = appContext.storage.getFolders().get(index);
        System.out.println("Folder: " + folder.getName());
        displayRecords(folder);
        displayPage(folder);
    }

    /**
     * @param folder
     */
    private static void openCalculator(Folder folder) {
        System.out.println("The calculator is ready.");
        System.out.println("Please enter the number of records to count, or -1 for all records in the folder.");

        int amount = appContext.input.nextInt();
        if (amount == -1) {
            calculateAll(folder);
        } else {
            calculateDesiredAmount(folder, amount);
        }

        displayPage(folder);
    }

    // MODIFIES: this
    // EFFECTS:  calculate all records' average time in the given folder.
    public static void calculateAll(Folder folder) {
        double sum = 0;
        int count = 0;

        for (TimeRecord tr : (folder.getRecords())) {
            sum = sum + tr.getTime();
            count++;
        }

        double result = sum / count;
        System.out.println("Your average time is: " + result);
    }

    // MODIFIES: this
    // EFFECTS:  calculate given amount of records' average time in the given folder.
    public static void calculateDesiredAmount(Folder folder, int amount) {
        double sum = 0;
        ArrayList<TimeRecord> records = folder.getRecords();

        for (TimeRecord timeRecord : records) {
            if (records.indexOf(timeRecord) < amount) {
                sum = sum + timeRecord.getTime();
            }
        }

        double result = sum / amount;
        System.out.println("Your average time is: " + result);
    }

    public static void displayRecords(Folder folder) {
        if (folder.getBestRecord() != null) {
            System.out.println("PB Record: " +
                    (folder.getBestRecord()).getTime() + ": " +
                    (folder.getBestRecord()).getNote());
        }

        for (TimeRecord timeRecord : (folder.getRecords())) {
            System.out.println((folder.getRecords()).indexOf(timeRecord) +
                    ". " + timeRecord.getTime() + ": " + timeRecord.getNote());
        }

        System.out.println("All records you have in this folder are shown above.");
    }
}
