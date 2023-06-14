package ui;

import model.Folder;
import model.TimeRecord;

/**
 * Represent the timer layer of the Timer app
 */
public class TimeRecordController {
//    static AppContext appContext;
    public static final String COMMAND_KEEP = "k";
    public static final String COMMAND_NOTE = "n";
    public static final String COMMAND_IGNORE = "i";
    public static final String COMMAND_START = "s";
    public static final String COMMAND_STOP = "l";

//    public TimeRecordController(AppContext appContext) {
    public TimeRecordController() {
//        this.appContext = appContext;
    }

    static void displayPage(double time) {
        Page page = new Page("Time Record Page", "What would you like to do with this time record?");
        page.addCommand(new Command(COMMAND_KEEP, "keep it without having any note"));
        page.addCommand(new Command(COMMAND_NOTE, "keep it with a note"));
        page.addCommand(new Command(COMMAND_IGNORE, "ignore it and go back to main starter page"));
        System.out.println(page);
        router(time);
    }

    private static void router(double time) {
        switch (AppContext.readCommand()) {
            case COMMAND_KEEP -> addTimeRecordTo(new TimeRecord(time, ""));
            case COMMAND_NOTE -> keepWithNote(time);
            case COMMAND_IGNORE -> MainController.displayPage();
            default -> invalidTimeRecordCommand(time);
        }
    }

    static void addTimeRecordTo(TimeRecord tr) {
        System.out.println("Please enter the index of the folder you would like to add this time record into:");
        StorageController.displayFolders(AppContext.storage);
        int index = AppContext.readInt();
        Folder f = AppContext.storage.getFolders().get(index);
        f.addTimeRecord(tr);
        System.out.println("Your time record has been successfully added.");
        System.out.println("Directing you back to storage page...");
        StorageController.displayPage();
    }

    private static void keepWithNote(double time) {
        System.out.println("Please enter any note you would like to add for this time record:");
        String note = AppContext.readLine();
        TimeRecord timeRecord = new TimeRecord(time, note);
        addTimeRecordTo(timeRecord);
    }

    static void deleteRecord(Folder f) {
        System.out.println("Please enter the index of the record you want to delete:");
        int index = AppContext.readInt();
        if (f.deleteTimeRecord(index)) {
            System.out.println("Time record " + index + " has been deleted.");
        } else {
            System.out.println("Error: the folder is already empty OR time record with given index does not exist.");
        }

        FolderController.displayRecords(f);
    }

    static void editNote(Folder f) {
        System.out.println("Please enter the index of the record you want to edit:");
        int index = AppContext.readInt();
        TimeRecord editRecord = f.getRecords().get(index);
        String newNote = getNewNote();
        editRecord.setNote(newNote);
        System.out.println("Note for record " + index + " has been edited.");

        FolderController.displayPage(f);
    }

    private static String getNewNote() {
        System.out.println("Please enter the new note:");
        return AppContext.readLine();
    }

    private static void invalidTimeRecordCommand(double time) {
        System.out.println("Invalid input. Please enter a character that is provided.");
        displayPage(time);
    }
}
