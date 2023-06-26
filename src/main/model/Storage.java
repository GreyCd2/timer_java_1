package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;

/**
 * Represent the storage of the PbTimer App with a list of folders in it
 *
 * @author Grey
 */
public class Storage implements Writeable {
    private static final Storage INSTANCE = new Storage();
    private ArrayList<Folder> folders;

    /**
     * Create a new storage with a default folder named "Folder 1" inside
     */
    private Storage() {
        folders = new ArrayList<>();
        addFolder("Folder 1");
    }

    public static Storage getInstance() {
        return INSTANCE;
    }

    /**
     * Add a new empty folder with user input name
     *
     * @param folderName
     */
    public void addFolder(String folderName) {
        // The folder name does not already exit.
        for (Folder temptFolder : folders) {
            if (temptFolder.getName() == folderName) {
                System.out.println("Name duplicated.");
                return;
            }
        }
        Folder folder = new Folder(folderName);
        folders.add(folder);
        EventHistory.getInstance().logEvent(new Event("Folder " + folderName + " added."));
    }

    /**
     * Add an existing folder into the storage
     *
     * @param f
     */
    public void addFolder(Folder f) {
        folders.add(f);
        EventHistory.getInstance().logEvent(new Event("Folder " + f.getName() + " added."));
    }

    /**
     * Delete the folder with user input index.
     * If the deleted folder is the last folder in the storage, create a new default folder.
     *
     * @param index
     */
    public boolean deleteFolder(int index) {
        boolean result = false;
        if ((index > 0) && (index <= folders.size())) {
            Folder f = folders.get(index - 1);
            folders.remove(f);
            result = true;
            EventHistory.getInstance().logEvent(new Event("Folder " + f.getName() + " deleted."));
        }

        if (folders.size() == 0) {
            addFolder("Folder 1");
        }

        return result;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("folders", foldersToJson());
        return json;
    }

    /**
     * Return folders in the storage as a JSON array
     *
     * @return
     */
    private JSONArray foldersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Folder folder : folders) {
            jsonArray.put(folder.toJson());
        }

        return jsonArray;
    }
}
