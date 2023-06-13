package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a log of storage events.
 * @author Grey
 */
public class EventHistory implements Iterable<Event> {
    // the only EventLog in the system (Singleton Design Pattern)
    private static EventHistory INSTANCE;
    private Collection<Event> events;

    /**
     *  Creates an event history with no events in it.
     */
    private EventHistory() {
        events = new ArrayList<>();
    }

    public static EventHistory getInstance() {
        // Create instance if the event history does not have it yet.
        if (INSTANCE == null) {
            INSTANCE = new EventHistory();
        }

        return INSTANCE;
    }

    /**
     * Adds an event to the event history.
     * @param e
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    /**
     * Clears the event log and logs the event
     */
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
