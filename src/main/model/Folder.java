package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represent a folder with its name, a list of time records it has and a personal best record inside this folder
 * @author Grey
 */
public class Folder implements Writeable {
    private String name;
    private TimeRecord bestRecord;
    private ArrayList<TimeRecord> records;

    /**
     * Build a folder with user input name and an empty list of time records
     * @param name
     */
    public Folder(String name) {
        this.name = name;
        this.records = new ArrayList<>();
    }

    /**
     * Add a time record into the folder
     * If this is the first record being added, take it as the best record
     * Else, compare it with the existing best record, and replace best record with it if this one is quicker
     * @param record
     */
    public void addTimeRecord(TimeRecord record) {
        records.add(record);

        // If this is the first record being added
        if (records.size() == 1) {
            bestRecord = record;
        // If this one is quicker than existing best record
        } else if (record.getTime() < bestRecord.getTime()) {
            bestRecord = record;
        }
    }

    /**
     * Delete the time record with given index
     * @param index
     */
    public void deleteTimeRecord(int index) {
        int indexRange = records.size() - 1;
        // If the index is valid
        if ((index > 0) && (index <= indexRange)) {
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
    }

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

    public void setName(String newName) {
        this.name = newName;
    }

    public int getSize() {
        return records.size();
    }

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

    /**
     * Return time records as a JSON array
     * @return
     */
    private JSONArray recordsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TimeRecord temptRecord : records) {
            jsonArray.put(temptRecord.toJson());
        }

        return jsonArray;
    }
}