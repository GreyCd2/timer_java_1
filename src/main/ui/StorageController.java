package ui;

import model.Folder;
import model.Storage;

/**
 * Represent the storage layer of the Timer app
 *
 * @author Grey
 */
public class StorageController {
    // MainController mainController;
    FolderController folderController;
    static AppContext appContext;
    private static final String COMMAND_CREAT = "c";
    private static final String COMMAND_DELETE_FOLDER = "d";
    private static final String COMMAND_RENAME = "r";
    private static final String COMMAND_OPEN = "o";
    private static final String COMMAND_BACK_TO_MAIN = "b";

    public StorageController(AppContext appContext) {
        this.appContext = appContext;
        this.folderController = new FolderController(appContext);
    }

    static void storageRouter() {
        System.out.println("Storage:");
        displayFolders(appContext.storage);

        System.out.println("c -> create a new folder");
        System.out.println("d -> delete a folder");
        System.out.println("r -> rename a folder");
        System.out.println("o -> open a folder");
        System.out.println("b -> back to starter page.");

        String selection = appContext.input.next();
        selection = selection.toLowerCase();
        switch (selection) {
            case COMMAND_CREAT -> FolderController.createFolder();
            case COMMAND_DELETE_FOLDER -> FolderController.deleteFolder();
            case COMMAND_RENAME -> FolderController.renameFolder();
            case COMMAND_OPEN -> FolderController.openFolder();
            case COMMAND_BACK_TO_MAIN -> MainController.appRouter();
            default -> invalidStorageCommand();
        }
    }

    private static void invalidStorageCommand() {
        System.out.println("Invalid input. Please enter a character that is provided.");
        storageRouter();
    }


    public static void displayFolders(Storage s) {
        for (Folder f : (s.getFolders())) {
            System.out.println((s.getFolders()).indexOf(f) + ". Folder: " + f.getName());
        }
        System.out.println("All folders you have are shown above.");
    }
}
