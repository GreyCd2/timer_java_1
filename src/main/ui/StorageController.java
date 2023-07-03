package ui;

import model.Folder;
import model.Storage;

/**
 * Represent the storage layer of the Timer app
 *
 * @author Grey
 */
public class StorageController {

    FolderController folderController;
    private static final String COMMAND_CREATE = "c";
    private static final String COMMAND_DELETE = "d";
    private static final String COMMAND_RENAME = "r";
    private static final String COMMAND_OPEN = "o";
    private static final String COMMAND_BACK_TO_MAIN = "b";
    private static Page page = new Page("Storage Page", "Please select from following options");

    public StorageController() {
        this.folderController = new FolderController();

        page.addCommand(new Command(COMMAND_OPEN, "open a folder"));
        page.addCommand(new Command(COMMAND_CREATE, "create a new folder"));
        page.addCommand(new Command(COMMAND_RENAME, "rename a folder"));
        page.addCommand(new Command(COMMAND_DELETE, "delete a folder"));
        page.addCommand(new Command(COMMAND_BACK_TO_MAIN, "back to starter page."));
    }

    /**
     * Display guide page and all the folders.
     * Collect user input command and direct to corresponding page.
     */
    public static void router() {
        System.out.println(page);
        displayFolders(AppContext.storage);

        switch (AppContext.readCommand()) {
            case COMMAND_OPEN -> FolderController.openFolder();
            case COMMAND_CREATE -> FolderController.createFolder();
            case COMMAND_RENAME -> FolderController.renameFolder();
            case COMMAND_DELETE -> FolderController.deleteFolder();
            case COMMAND_BACK_TO_MAIN -> MainController.router();
            default -> invalidStorageCommand();
        }
    }

    /**
     * Display all the folders in storage.
     *
     * @param storage
     */
    public static void displayFolders(Storage storage) {
        for (Folder folder : (storage.getFolders())) {
            int index = storage.getFolders().indexOf(folder) + 1;
            System.out.println(index + ". Folder: " + folder.getName());
        }
    }

    /**
     * Display error message and redirect to storage router.
     */
    private static void invalidStorageCommand() {
        AppContext.errorMessage("Error: Invalid input.");
        System.out.println("Please input a character that is provided.");

        router();
    }
}
