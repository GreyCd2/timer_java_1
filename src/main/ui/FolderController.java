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
//    static AppContext appContext;
    private static final String COMMAND_DELETE = "d";
    private static final String COMMAND_EDIT = "e";
    private static final String COMMAND_CALCULATOR = "c";
    private static final String COMMAND_STORAGE = "s";
    private static final String COMMAND_BACK_TO_MAIN = "b";

//    public FolderController(AppContext appContext) {
    public FolderController() {
        this.timeRecordController = new TimeRecordController();
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
        Page page = new Page("Folder Page", "Please select from following options");
        page.addCommand(new Command(COMMAND_DELETE, "delete a time record"));
        page.addCommand(new Command(COMMAND_EDIT, "edit note for a time record"));
        page.addCommand(new Command(COMMAND_CALCULATOR, "call the average calculator"));
        page.addCommand(new Command(COMMAND_STORAGE, "go back to storage page"));
        page.addCommand(new Command(COMMAND_BACK_TO_MAIN, "back to main starter page"));
        System.out.println(page);

        router(folder);
    }

    static void router(Folder folder) {
        switch (AppContext.readCommand()) {
            case COMMAND_DELETE -> TimeRecordController.deleteRecord(folder);
            case COMMAND_EDIT -> TimeRecordController.editNote(folder);
            case COMMAND_CALCULATOR -> openCalculator(folder);
            case COMMAND_STORAGE -> StorageController.displayPage();
            case COMMAND_BACK_TO_MAIN -> MainController.displayPage();
            default -> invalidFolderCommand(folder);
        }
    }

    /**
     * @param folder
     */
    private static void openCalculator(Folder folder) {
        System.out.println("The calculator is ready.");
        System.out.println("Please enter the number of records to count, or -1 for all records in the folder.");

        int amount = AppContext.readInt();
        if (amount == -1) {
            calculateAll(folder);
        } else {
            calculateDesiredAmount(folder, amount);
        }

        displayPage(folder);
    }

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

    /**
     * Create a new folder with user input name and add it into the storage
     */
    static void createFolder() {
        System.out.println("How would you like to name this new Folder?");
        String folderName = AppContext.readLine();
        AppContext.storage.addFolder(folderName);
        String formatName = String.format("Folder %s has been created.", folderName);
        System.out.println(formatName);

        StorageController.displayPage();
    }

    /**
     * Delete the folder with user input index in the storage
     * Redirect user to the storage page
     */
    static void deleteFolder() {
        System.out.println("Please enter the index of the folder you want to delete:");
        int index = AppContext.readInt();
        String formatIndex;
        if (AppContext.storage.deleteFolder(index)) {
            formatIndex = String.format("Folder %s has been deleted.", index);
        } else {
            formatIndex = "Error: the storage is already empty OR folder with given index does not exist.";
        }
        System.out.println(formatIndex);

        StorageController.displayPage();
    }

    /**
     * Rename the folder with user input index and user input name
     * Redirect user to the storage page
     */
    static void renameFolder() {
        System.out.println("Please enter the index of the folder you want to rename:");
        int index = AppContext.readInt();
        Folder f = AppContext.storage.getFolders().get(index);
        System.out.println("Please enter the new name for this folder:");
        String newName = AppContext.readLine();
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
        int index = AppContext.readInt();
        Folder folder = AppContext.storage.getFolders().get(index);
        System.out.println("Folder: " + folder.getName());
        displayRecords(folder);
        displayPage(folder);
    }

    // MODIFIES: this
    // EFFECTS:  calculate all records' average time in the given folder.
    // MODIFIES: this

    // EFFECTS:  calculate given amount of records' average time in the given folder.
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

        FolderController.displayPage(folder);
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
}
