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

// Represents a reader that reads storage from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS:  constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads storage from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public Storage read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStorage(jsonObject);
    }

    // EFFECTS:  reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return Files.readString(Paths.get(source), StandardCharsets.UTF_8);
        /*StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();*/
    }

    // EFFECTS:  parses storage from JSON object amd returns it
    private Storage parseStorage(JSONObject jsonObject) {
        Storage storage = new Storage();
        JSONArray foldersInJsonArray = jsonObject.getJSONArray("folders");
        addFolders(storage, foldersInJsonArray);
        return storage;
    }

    // MODIFIES: storage
    // EFFECTS:  parse folders from JSON array and adds them to storage
    private void addFolders(Storage storage, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextFolder = (JSONObject) json;
            addFolder(storage, nextFolder);
        }
    }

    // MODIFIES: storage
    // EFFECTS:  parses folder from JSON Object and adds it to storage
    private void addFolder(Storage storage, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Folder f = new Folder(name);
        JSONArray jsonArray = jsonObject.getJSONArray("records");
        addRecords(f, jsonArray);
        storage.addFolder(f);
    }

    // MODIFIES: f
    // EFFECTS:  parses records from JSON Array and adds them to folder
    private void addRecords(Folder f, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextRecord = (JSONObject) json;
            addRecord(f, nextRecord);
        }
    }

    // MODIFIES: f
    // EFFECTS:  parses record from JSON object and adds it to folder
    private void addRecord(Folder f, JSONObject jsonObject) {
        double time = jsonObject.getDouble("time");
        String note = jsonObject.getString("note");
        TimeRecord record = new TimeRecord(time, note);
        f.addTimeRecord(record);
    }
}
