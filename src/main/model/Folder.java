package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;

// represent a folder with its name, a list of time records it has and a personal best record inside this folder.
public class Folder implements Writeable {
    private String name;
    private TimeRecord pbRecord = new TimeRecord(1000, "default personal best record");
    private ArrayList<TimeRecord> records;

    // EFFECTS:  build a folder with user input name and an empty list of time records
    public Folder(String name) {
        this.name = name;
        this.records = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS:  the name of the folder is changed into input string
    public void renameFolder(String newName) {
        this.name = newName;
    }

    // MODIFIES: this
    // EFFECTS:  add a time record in this folder and compare it with pb. If the new record is quicker, replace pb.
    public void addTimeRecord(TimeRecord record) {
        records.add(record);
        if (record.getTime() < pbRecord.getTime()) {
            pbRecord = record;
        }
    }

    // REQUIRES: the index exists
    // MODIFIES: this
    // EFFECTS:  delete a time record in this folder
    //           if the index is not valid, output an error message.
    public void deleteTimeRecord(int index) {
        records.remove(records.get(index));
    }

    // EFFECTS:  returns true if two Folders have the same value for each field, false otherwise.
    public boolean isSameFolder(Folder f) {
        return ((this.name == f.name) & ((this.pbRecord).isSameRecord(f.pbRecord))) & (this.isSameRecords(f.records));
    }

    // EFFECTS:  returns true if the two records are two ArrayList with same elements in it, regardless of the order.
    public boolean isSameRecords(ArrayList<TimeRecord> list) {
        return (this.records).containsAll(list) & list.containsAll(this.records);
    }

    // EFFECTS: return how many records does the list have
    public int getRecordsNumber() {
        return records.size();
    }

    // EFFECTS: return the record with input index
    public TimeRecord getIthTimeRecord(int index) {
        return records.get(index);
    }

    public String getName() {
        return name;
    }

    public ArrayList<TimeRecord> getRecords() {
        return records;
    }

    public TimeRecord getPbRecord() {
        return pbRecord;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("pbRecord", pbRecord.toJson());
        json.put("records", recordsToJson());
        return json;
    }

    // EFFECTS:  returns time records in the folder as a JSON array
    private JSONArray recordsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TimeRecord tr : records) {
            jsonArray.put(tr.toJson());
        }

        return jsonArray;
    }
}