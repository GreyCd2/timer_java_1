package ui;

import model.Folder;
import model.TimeRecord;

/**
 * Represent the timer layer of the Timer app
 */
public class TimeRecordController {
    public static final String COMMAND_KEEP = "k";
    public static final String COMMAND_NOTE = "n";
    public static final String COMMAND_IGNORE = "i";
    public static final String COMMAND_START = "s";
    public static final String COMMAND_STOP = "l";
    private static Page page = new Page("Time Record Page", "Please select from following options");

    public TimeRecordController() {
        page.addCommand(new Command(COMMAND_KEEP, "keep it without having any note"));
        page.addCommand(new Command(COMMAND_NOTE, "keep it with a note"));
        page.addCommand(new Command(COMMAND_IGNORE, "ignore it and go back to main starter page"));
    }

    /**
     * Display guide page and execute user input command.
     *
     * @param time
     */
    public static void router(double time) {
        System.out.println(page);

        switch (AppContext.readCommand()) {
            case COMMAND_KEEP -> addTimeRecordTo(new TimeRecord(time, ""));
            case COMMAND_NOTE -> keepWithNote(time);
            case COMMAND_IGNORE -> MainController.router();
            default -> invalidTimeRecordCommand(time);
        }
    }

    /**
     * Add the time record to the folder with user input index.
     * Direct to main router.
     * @param timeRecord
     */
    static void addTimeRecordTo(TimeRecord timeRecord) {
        StorageController.displayFolders(AppContext.storage);
        System.out.println("Please enter the index of the folder you would like to add this time record into:");

        int index = AppContext.readInt();
        if (index > 0 && index <= AppContext.storage.getFolders().size()) {
            AppContext.storage.getFolders().get(index - 1).addTimeRecord(timeRecord);
        } else {
            AppContext.errorMessage("Error: invalid index.");
            System.out.println("Please enter an index that exists.");

            addTimeRecordTo(timeRecord);
        }

        System.out.println("Your time record has been successfully added.\nDirecting you back to main page...");
        MainController.router();
    }

    /**
     * Save the recorded time as a time record with user input note.
     * @param time
     */
    private static void keepWithNote(double time) {
        System.out.println("Please enter any note you would like to add for this time record:");
        String note = AppContext.readLine();
        TimeRecord timeRecord = new TimeRecord(time, note);
        addTimeRecordTo(timeRecord);
    }

    /**
     * Delete the time record with user input index.
     * Direct to current folder router.
     * @param folder
     */
    static void deleteRecord(Folder folder) {
        System.out.println("Please enter the index of the record you want to delete:");
        int index = AppContext.readInt();
        if (folder.deleteTimeRecord(index)) {
            System.out.println("Time record " + index + " has been deleted.");
        } else {
            AppContext.errorMessage("Error: the folder is empty OR time record with given index does not exist.");
        }

        FolderController.router(folder);
    }

    /**
     * Change the note of the time record with user input index into the new user input note.
     * @param folder
     */
    static void editNote(Folder folder) {
        System.out.println("Please enter the index of the record you want to edit:");
        int index = AppContext.readInt();

        System.out.println("Please enter the new note:");
        String newNote = AppContext.readLine();

        if (index > 0 && index <= folder.getSize()) {
            folder.getRecords().get(index - 1).setNote(newNote);
            System.out.println("Note for record " + index + " has been edited.");
        } else {
            AppContext.errorMessage("Error: invalid index.");
            System.out.println("Please input");
        }

        FolderController.router(folder);
    }

    /**
     * Display error message and redirect to storage router.
     */
    private static void invalidTimeRecordCommand(double time) {
        AppContext.errorMessage("Error: invalid input.");
        System.out.println("Please input a character that is provided.");

        router(time);
    }
}
