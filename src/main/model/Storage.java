package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;
import java.util.ArrayList;

/**
 * Represent the storage of the PbTimer App with a list of folders in it
 * @author Grey
 */
public class Storage implements Writeable {
    private ArrayList<Folder> folders;

    /**
     * Create a new storage with no folders inside
     */
    public Storage() {
        folders = new ArrayList<>();
    }

    /**
     * Add a new empty folder with user input name
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
     * @param f
     */
    public void addFolder(Folder f) {
        // TODO: WHY EXIST
        folders.add(f);
        EventHistory.getInstance().logEvent(new Event("Folder " + f.getName() + " added."));
    }

    /**
     * Delete the folder with user input index.
     * @param index
     */
    public void deleteFolder(int index) {
        int indexRange = folders.size() - 1;
        if ((index > 0) && (index <= indexRange)) {
            Folder f = folders.get(index);
            folders.remove(f);
            EventHistory.getInstance().logEvent(new Event("Folder " + f.getName() + " deleted."));
        }
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
