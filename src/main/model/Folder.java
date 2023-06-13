package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.Objects;

// represent a folder with its name, a list of time records it has and a personal best record inside this folder.
public class Folder implements Writeable {
    private String name;
    private TimeRecord bestRecord;
    private ArrayList<TimeRecord> records;

    // EFFECTS:  build a folder with user input name and an empty list of time records
    public Folder(String name) {
        this.name = name;
        this.records = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS:  the name of the folder is changed into input string
    public void setName(String newName) {
        this.name = newName;
    }

    // MODIFIES: this
    // EFFECTS:  add a time record in this folder and compare it with pb. If the new record is quicker, replace pb.
    public void addTimeRecord(TimeRecord record) {
        records.add(record);
        if (records.size() == 1) {
            // the first added time record
            bestRecord = record;
        } else if (record.getTime() < bestRecord.getTime()) {
            bestRecord = record;
        }
    }

    // REQUIRES: the index exists
    // MODIFIES: this
    // EFFECTS:  delete a time record in this folder
    //           if the index is not valid, output an error message.
    public void deleteTimeRecord(int index) {
        TimeRecord timeRecord = records.get(index);
        records.remove(timeRecord);

        // If the deleted record is the previous best record, search through the list for the next best record.
        if (timeRecord.equals(bestRecord)) {
            double min = Double.MAX_VALUE;
            for (TimeRecord tempRecord : records) {
                if (tempRecord.getTime() < min) {
                    min = tempRecord.getTime();
                    bestRecord = tempRecord;
                }
            }
        }
    }

/*    // EFFECTS:  returns true if two Folders have the same value for each field, false otherwise.
    public boolean equals(Folder f) {
        return ((this.name == f.name) & ((this.bestRecord).equals(f.bestRecord))) & (this.isSameRecords(f.records));
    }

    // EFFECTS:  returns true if the two records are two ArrayList with same elements in it, regardless of the order.
    public boolean isSameRecords(ArrayList<TimeRecord> list) {
        return (this.records).containsAll(list) & list.containsAll(this.records);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return Objects.equals(name, folder.name) &&
                Objects.equals(bestRecord, folder.bestRecord) &&
                Objects.equals(records, folder.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bestRecord, records);
    }

    // EFFECTS: return how many records does the list have
    public int getSize() {
        return records.size();
    }

/*    // EFFECTS: return the record with input index
    public TimeRecord getIthTimeRecord(int index) {
        return records.get(index);
    }*/

    public String getName() {
        return name;
    }

    public ArrayList<TimeRecord> getRecords() {
        return records;
    }

    public TimeRecord getBestRecord() {
        return bestRecord;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        if (bestRecord != null) {
            json.put("pbRecord", bestRecord.toJson());
        } else {
            json.put("pbRecord", "");
        }
        json.put("records", recordsToJson());
        return json;
    }

    // EFFECTS:  returns time records in the folder as a JSON array
    private JSONArray recordsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TimeRecord temptRecord : records) {
            jsonArray.put(temptRecord.toJson());
        }

        return jsonArray;
    }
}