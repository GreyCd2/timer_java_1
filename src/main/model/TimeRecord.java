package model;

import org.json.JSONObject;
import persistence.Writeable;

/**
 * Represent a time record with its time and note
 * @author Grey
 */
public class TimeRecord implements Writeable {
    private final double time;
    private String note;

    /**
     * Create a time record with input time and default note
     * @param time
     */
    public TimeRecord(double time) {
        this.time = time;
        this.note = "Default.";
    }

    /**
     * Create a time record with input time and user input note
     * @param time
     * @param note
     */
    public TimeRecord(double time, String note) {
        this.time = time;
        this.note = note;
    }

    public boolean equals(TimeRecord tr) {
        return (this.time == tr.time) && (this.note == tr.note);
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }

    public double getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("time", time);
        json.put("note", note);
        return json;
    }
}
