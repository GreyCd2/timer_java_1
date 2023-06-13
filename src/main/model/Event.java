package model;


import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Represents an storage event.
 * @author GreyC
 */
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    /**
     * creates an event with the given description and the current date/time stamp.
     * @param description
     */
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    public Date getDate() {
        return dateLogged;
    }

    public String getDescription() {
        return description;
    }

   /* @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(dateLogged, event.dateLogged) && Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateLogged, description);
    }

    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}

