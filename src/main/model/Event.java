package model;

import java.util.Date;

// Represents logged application event with timestamp and description.
public class Event {
    private Date date;
    private String description;

    // REQUIRES: description is not null.
    // EFFECTS: constructs event with current date and given description.
    public Event(String description) {
        this.date = new Date();
        this.description = description;
    }

    // EFFECTS: returns date event was created.
    public Date getDate() {
        return date;
    }

    // EFFECTS: returns description of event.
    public String getDescription() {
        return description;
    } 

    // EFFECTS: returns string representation of event with date and description.
    @Override
    public String toString() {
        return date.toString() + "\n" + description;
    }
}
