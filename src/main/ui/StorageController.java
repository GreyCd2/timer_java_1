package ui;

import model.Folder;
import model.Storage;

public class StorageController {
    MainController mainController;
    FolderController folderController;

    private static final String COMMAND_CREAT = "c";
    private static final String COMMAND_DELETE_FOLDER = "d";
    private static final String COMMAND_RENAME = "r";
    private static final String COMMAND_OPEN = "o";
    private static final String COMMAND_BACK_TO_MAIN = "b";

    //    public StorageApp(PbTimerApp pbTimerApp, FolderApp folderApp) {
    public StorageController(MainController mainController) {
        this.mainController = mainController;
        this.folderController = new FolderController(mainController, this);
    }

    void storageRouter() {
        System.out.println("Storage:");
        displayFolders(mainController.storage);

        System.out.println("c -> create a new folder");
        System.out.println("d -> delete a folder");
        System.out.println("r -> rename a folder");
        System.out.println("o -> open a folder");
        System.out.println("b -> back to starter page.");

        String selection = mainController.input.next();
        selection = selection.toLowerCase();
        switch (selection) {
            case COMMAND_CREAT -> folderController.createFolder();
            case COMMAND_DELETE_FOLDER -> folderController.deleteFolder();
            case COMMAND_RENAME -> folderController.renameFolder();
            case COMMAND_OPEN -> folderController.openFolder();
            case COMMAND_BACK_TO_MAIN -> mainController.appRouter();
            default -> invalidStorageCommand();
        }
    }

    private void invalidStorageCommand() {
        System.out.println("Invalid input. Please enter a character that is provided.");
        storageRouter();
    }


    public void displayFolders(Storage s) {
        for (Folder f : (s.getFolders())) {
            System.out.println((s.getFolders()).indexOf(f) + ". Folder: " + f.getName());
        }
        System.out.println("All folders you have are shown above.");
    }
}
