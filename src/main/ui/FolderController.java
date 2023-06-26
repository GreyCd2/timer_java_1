package ui;

import model.Folder;
import model.TimeRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.OptionalDouble;


/**
 * Represent the folder layer of the Timer app
 *
 * @author Grey
 */
public class FolderController {
    TimeRecordController timeRecordController;
    private static final String COMMAND_DELETE = "d";
    private static final String COMMAND_EDIT = "e";
    private static final String COMMAND_CALCULATOR = "c";
    private static final String COMMAND_STORAGE = "s";
    private static final String COMMAND_BACK_TO_MAIN = "b";

    public FolderController() {
        this.timeRecordController = new TimeRecordController();
    }

    /**
     * Display opening page for folder controller and direct to router.
     *
     * @param folder
     */
    static void displayPage(Folder folder) {
        Page page = new Page("Folder Page", "Please select from following options");
        page.addCommand(new Command(COMMAND_CALCULATOR, "call the average calculator"));
        page.addCommand(new Command(COMMAND_DELETE, "delete a time record"));
        page.addCommand(new Command(COMMAND_EDIT, "edit note for a time record"));
        page.addCommand(new Command(COMMAND_STORAGE, "go back to storage page"));
        page.addCommand(new Command(COMMAND_BACK_TO_MAIN, "back to main starter page"));
        System.out.println(page);

        router(folder);
    }

    /**
     * Collect user input command and direct to corresponding page.
     *
     * @param folder
     */
    static void router(Folder folder) {
        switch (AppContext.readCommand()) {
            case COMMAND_CALCULATOR -> openCalculator(folder);
            case COMMAND_DELETE -> TimeRecordController.deleteRecord(folder);
            case COMMAND_EDIT -> TimeRecordController.editNote(folder);
            case COMMAND_STORAGE -> StorageController.displayPage();
            case COMMAND_BACK_TO_MAIN -> MainController.displayPage();
            default -> invalidFolderCommand(folder);
        }
    }

    /**
     * The result page for the calculator, which contains the overall average and the average for latest five records.
     *
     * @param folder
     */
    private static void openCalculator(Folder folder) {
        double averageForLatestFive = calculateLatestN(folder, 5);
        double overallAverage = calculateLatestN(folder, -1);

        System.out.println(String.format("Your average for the latest three records is: %.2f seconds.",
                averageForLatestFive));
        System.out.println(String.format("Your average for all records is: %.2f seconds.", overallAverage));

        displayPage(folder);
    }

    /**
     * An average calculator which calculates the average for all records (if n is -1) or latest n records,
     * with the largest and smallest ones removed.
     *
     * @param folder
     * @param n
     * @return
     */
    public static double calculateLatestN(Folder folder, int n) {
        ArrayList<TimeRecord> records = folder.getRecords();
        ArrayList<TimeRecord> clone;
        ArrayList<Double> times = new ArrayList<>();
        if (n == -1) {
            clone = new ArrayList<>(records);
        } else {
            if (records.size() >= n) {
                clone = new ArrayList<>(records.subList(records.size() - n - 1,
                        records.size() - 1));
            } else {
                return 0;
            }
        }
        clone.sort(Comparator.comparingDouble(TimeRecord::getTime));
        clone.remove(0);
        clone.remove(clone.size() - 1);
        clone.forEach((record) -> times.add(record.getTime()));
        OptionalDouble average = times.stream().mapToDouble(a -> a).average();
        return average.isPresent() ? average.getAsDouble() : 0;
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
        if (AppContext.storage.deleteFolder(index - 1)) {
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
        Folder f = AppContext.storage.getFolders().get(index - 1);
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
        Folder folder = AppContext.storage.getFolders().get(index - 1);
        System.out.println("Folder: " + folder.getName());
        displayRecords(folder);
        displayPage(folder);
    }

    /**
     * Display all the records in the folder and the best record, if has.
     *
     * @param folder
     */
    public static void displayRecords(Folder folder) {
        if (folder.getBestRecord() != null) {
            System.out.println("PB Record: " +
                    (folder.getBestRecord()).getTime() + ": " +
                    (folder.getBestRecord()).getNote());
        }

        for (TimeRecord timeRecord : (folder.getRecords())) {
            int index = folder.getRecords().indexOf(timeRecord) + 1;
            System.out.println(index + ". " + timeRecord.getTime() + ": " + timeRecord.getNote());
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
        System.out.println("Error: invalid input.");
        System.out.println("Please input an character that is provided.");
        displayPage(f);
    }
}
