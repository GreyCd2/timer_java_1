package model;

import org.json.JSONObject;
import persistence.Writeable;

// Represent a time record with its time and note
public class TimeRecord implements Writeable {
    private final double time;
    private String note;

    public TimeRecord(double time) {
        this.time = time;
        this.note = "Default.";
    }

    public TimeRecord(double time, String note) {
        this.time = time;
        this.note = note;
    }

    // MODIFIES: this
    // EFFECTS:  replace the original note with new input string
    public void setNote(String newNote) {
        this.note = newNote;
    }

    // EFFECTS:  return true if the input time record has the same time and note from this time record.
    public boolean equals(TimeRecord tr) {
        return (this.time == tr.time) && (this.note == tr.note);
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
