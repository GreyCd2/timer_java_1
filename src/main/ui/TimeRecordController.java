package ui;

import model.Folder;
import model.TimeRecord;

/**
 * Represent the timer layer of the Timer app
 */
public class TimeRecordController {
    static AppContext appContext;
    public static final String COMMAND_KEEP = "k";
    public static final String COMMAND_IGNORE = "i";
    public static final String COMMAND_START = "s";
    public static final String COMMAND_STOP = "l";

    public TimeRecordController(AppContext appContext) {
        this.appContext = appContext;
    }

    static void displayPage(double time) {
        System.out.println("What would you like to do with this time record?");
        System.out.println("k -> keep it");
        System.out.println("i -> ignore it and go back to main starter page");

        router(time);
    }

    private static void router(double time) {
        String selection = appContext.input.next();
        selection = selection.toLowerCase();
        if (selection.equals(COMMAND_KEEP)) {
            System.out.println("Please enter any note you would like to add for this time record:");
            String note = appContext.input.next() + appContext.input.nextLine();
            TimeRecord timeRecord = new TimeRecord(time, note);
            addTimeRecordTo(timeRecord);
        } else if (selection.equals(COMMAND_IGNORE)) {
            MainController.router();
        } else {
            System.out.println("Invalid input. Please enter a character that is provided.");
            displayPage(time);
        }
    }
    // MODIFIES: this
    // EFFECTS:  add the time record into a folder with given index.

    static void addTimeRecordTo(TimeRecord tr) {
        System.out.println("Please enter the index of the folder you would like to add this time record into:");
        StorageController.displayFolders(appContext.storage);
        int index = appContext.input.nextInt();
        Folder f = appContext.storage.getFolders().get(index);
        f.addTimeRecord(tr);
        System.out.println("Your time record has been successfully added.");
        System.out.println("Directing you back to storage page...");
        StorageController.displayPage();
    }

    static void deleteRecord(Folder f) {
        System.out.println("Please enter the index of the record you want to delete:");
        int index = appContext.input.nextInt();
        f.deleteTimeRecord(index);
        System.out.println("Time record " + index + " has been deleted.");

        FolderController.displayRecords(f);
    }

    // MODIFIES: this
    // EFFECTS:  replace the note of the record with given index with a new input note
    static void editNote(Folder f) {
        System.out.println("Please enter the index of the record you want to edit:");
        int index = appContext.input.nextInt();
        TimeRecord editRecord = f.getRecords().get(index);
        String newNote = getNewNote();
        editRecord.setNote(newNote);
        System.out.println("Note for record " + index + " has been edited.");

        FolderController.displayPage(f);
    }

    private static String getNewNote() {
        System.out.println("Please enter the new note:");
        return (appContext.input.next() + appContext.input.nextLine());
    }
}
