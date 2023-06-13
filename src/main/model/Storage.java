package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;

// represent the storage of the PbTimer App with a list of folders in it.
public class Storage implements Writeable {
    private ArrayList<Folder> storage;

    // EFFECTS: make a new storage with no folders inside
    public Storage() {
        storage = new ArrayList<>();
    }

    // REQUIRES: the new Folder's name does not already exist
    // MODIFIES: this
    // EFFECTS:  add a new empty folder with user input name
    public void addFolder(String newFolderName) {
        Folder folder = new Folder(newFolderName);
        storage.add(folder);
        EventLog.getInstance().logEvent(new Event("Folder " + newFolderName + " added."));
    }

    // MODIFIES: this
    // EFFECTS:  add a folder into the storage
    public void addFolder(Folder f) {
        storage.add(f);
        EventLog.getInstance().logEvent(new Event("Folder " + f.getName() + " added."));
    }

    // REQUIRES: the index exists
    // MODIFIES: this
    // EFFECTS:  delete the folder with user input index.
    //           if the index is not valid, output an error message.
    public void deleteFolder(int index) {
        Folder f = storage.get(index);
        storage.remove(f);
        EventLog.getInstance().logEvent(new Event("Folder " + f.getName() + " deleted."));
    }

    // EFFECTS:  return how many folders are there in the storage
    public int getFolderNumber() {
        return storage.size();
    }

    // EFFECTS:  return the folder with given index in the storage
    public Folder getIthFolder(int index) {
        return storage.get(index);
    }

    public ArrayList<Folder> getFolders() {
        return storage;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("folders", foldersToJson());
        return json;
    }

    // EFFECTS:  returns folders in the storage as a JSON array
    private JSONArray foldersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Folder f : storage) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}
