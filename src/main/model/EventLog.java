package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Represents log of all application events.
// Events added during application session and can be printed on exit.
public class EventLog implements Iterable<Event> {
    private static EventLog instance;
    private List<Event> events;

    // EFFECTS: constructs empty event log.
    private EventLog() {
        this.events = new ArrayList<>();
    }

    // EFFECTS: returns instance of event log, creating it if needed.
    public static EventLog getInstance() {
        if (instance == null) {
            instance = new EventLog();
        }
        return instance;
    }

    // REQUIRES: event is not null.
    // MODIFIES: this
    // EFFECTS: adds given event to log.
    public void logEvent(Event event) {
        events.add(event);
    }

    // MODIFIES: this
    // EFFECTS: clears all events and logs cleared event.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns iterator over all logged events.
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
