package model;

import java.util.ArrayList;
import java.util.List;


// Represents a log of all completed focus sessions.
public class SessionLog {
    private List<FocusSession> sessions;

    // EFFECTS: constructs empty session log.
    public SessionLog() {
        // TODO: implement
    }

    // REQUIRES: session is not null.
    // MODIFIES: this
    // EFFECTS: adds given session to log.
    public void addSession(FocusSession session) {
        // TODO: implement
    }
    
    // EFFECTS: returns list of all sessions in log.
    public List<FocusSession> getSessions() {
        return null; // stub
    }

    // EFFECTS: returns number of sessions in log.
    public int getSessionCount() {
        return 0; // stub
    }

    // REQUIRES: petName is not empty.
    // EFFECTS: returns list of sessions completed with given pet.
    public List<FocusSession> getSessionsByPet(String petName) {
        return null; // stub
    }

    // EFFECTS: returns total duration of all sessions in minutes.
    public int getTotalDuration() {
        return 0; // stub
    }
}
