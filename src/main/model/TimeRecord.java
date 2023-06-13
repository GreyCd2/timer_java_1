package model;

import org.json.JSONObject;
import persistence.Writeable;

// Represent a time record with its time and note
public class TimeRecord implements Writeable {
    private double time;   // the time that has been recorded
    private String note;   // the note that the user add to it

    // REQUIRES: time > 0
    // MODIFIES: this
    // EFFECTS:  construct a new time record with input time and note
    public TimeRecord(double time, String note) {
        this.time = time;
        this.note = note;
    }

    // MODIFIES: this
    // EFFECTS:  replace the original note with new input string
    public void editNote(String newNote) {
        this.note = newNote;
    }

    // EFFECTS:  return true if the input time record has the same time and note from this time record.
    public boolean isSameRecord(TimeRecord tr) {
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
