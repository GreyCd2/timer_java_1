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
//    static AppContext appContext;
    private static final String COMMAND_CREATE = "c";
    private static final String COMMAND_DELETE = "d";
    private static final String COMMAND_RENAME = "r";
    private static final String COMMAND_OPEN = "o";
    private static final String COMMAND_BACK_TO_MAIN = "b";

//    public StorageController(AppContext appContext) {
    public StorageController() {
//        this.appContext = appContext;
        this.folderController = new FolderController();
    }

    static void displayPage() {
        Page page = new Page("Storage Page", "Please select from following options");
        page.addCommand(new Command(COMMAND_CREATE, "create a new folder"));
        page.addCommand(new Command(COMMAND_DELETE, "delete a folder"));
        page.addCommand(new Command(COMMAND_RENAME, "rename a folder"));
        page.addCommand(new Command(COMMAND_OPEN, "open a folder"));
        page.addCommand(new Command(COMMAND_BACK_TO_MAIN, "back to starter page."));
        System.out.println(page);
        displayFolders(AppContext.storage);

        router();
    }

    private static void router() {
        switch (AppContext.readCommand()) {
            case COMMAND_CREATE -> FolderController.createFolder();
            case COMMAND_DELETE -> FolderController.deleteFolder();
            case COMMAND_RENAME -> FolderController.renameFolder();
            case COMMAND_OPEN -> FolderController.openFolder();
            case COMMAND_BACK_TO_MAIN -> MainController.displayPage();
            default -> invalidStorageCommand();
        }
    }

    public static void displayFolders(Storage storage) {
        for (Folder folder : (storage.getFolders())) {
            System.out.println((storage.getFolders()).indexOf(folder) + ". Folder: " + folder.getName());
        }
        System.out.println("All folders you have are shown above.");
    }

    private static void invalidStorageCommand() {
        System.out.println("Invalid input. Please enter a character that is provided.");
        displayPage();
    }
}
