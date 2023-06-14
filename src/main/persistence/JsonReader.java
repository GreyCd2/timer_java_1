package persistence;

import model.Folder;
import model.Storage;
import model.TimeRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Represent a reader that reads storage from JSON data stored in file
 * @author Grey
 */
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    /**
     * Read storage from file and return it
     * Throw IOException if an error occurs reading data from file
     * @return
     * @throws IOException
     */
    public Storage read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStorage(jsonObject);
    }

    /**
     * Read source file as string and returns it
     * @param source
     * @return
     * @throws IOException
     */
    private String readFile(String source) throws IOException {
        return Files.readString(Paths.get(source), StandardCharsets.UTF_8);
    }

    /**
     * Parse storage from JSON object amd return it as a storage
     * @param jsonObject
     * @return
     */
    private Storage parseStorage(JSONObject jsonObject) {
        Storage storage = new Storage();
        JSONArray foldersInJsonArray = jsonObject.getJSONArray("folders");
        addFolders(storage, foldersInJsonArray);
        return storage;
    }

    /**
     * Parse folders from JSON array and adds them to storage
     * @param storage
     * @param jsonArray
     */
    private void addFolders(Storage storage, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextFolder = (JSONObject) json;
            addFolder(storage, nextFolder);
        }
    }

    /**
     * Parse folder from JSON Object and adds it to storage
     * @param storage
     * @param jsonObject
     */
    private void addFolder(Storage storage, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Folder f = new Folder(name);
        JSONArray jsonArray = jsonObject.getJSONArray("records");
        addRecords(f, jsonArray);
        storage.addFolder(f);
    }

    /**
     * Parse records from JSON Array and adds them to folder
     * @param f
     * @param jsonArray
     */
    private void addRecords(Folder f, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextRecord = (JSONObject) json;
            addRecord(f, nextRecord);
        }
    }

    /**
     * Parse record from JSON object and adds it to folder
     * @param f
     * @param jsonObject
     */
    private void addRecord(Folder f, JSONObject jsonObject) {
        double time = jsonObject.getDouble("time");
        String note = jsonObject.getString("note");
        TimeRecord record = new TimeRecord(time, note);
        f.addTimeRecord(record);
    }
}
