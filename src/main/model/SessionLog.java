package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;


// Represents a log of all completed focus sessions.
public class SessionLog {
    private List<FocusSession> sessions;

    // EFFECTS: constructs empty session log.
    public SessionLog() {
        this.sessions = new ArrayList<>();
    }

    // REQUIRES: session is not null.
    // MODIFIES: this
    // EFFECTS: adds given session to log.
    public void addSession(FocusSession session) {
        sessions.add(session);
    }
    
    // EFFECTS: returns list of all sessions in log.
    public List<FocusSession> getSessions() {
        return sessions;
    }

    // EFFECTS: returns number of sessions in log.
    public int getSessionCount() {
        return sessions.size();
    }

    // REQUIRES: petName is not empty.
    // EFFECTS: returns list of sessions completed with given pet.
    public List<FocusSession> getSessionsByPet(String petName) {
        List<FocusSession> petSessions = new ArrayList<>();
        for (FocusSession session : sessions) {
            if (session.getPetName().equals(petName)) {
                petSessions.add(session);
            }
        }
        return petSessions;
    }

    // EFFECTS: returns total duration of all sessions in minutes.
    public int getTotalDuration() {
        int total = 0;
        for (FocusSession session : sessions) {
            total += session.getDurationMinutes();
        }
        return total;
    }

    // EFFECTS: returns this session log as a JSON object.
    public JSONObject toJson() {
        return null; // stub
    }

    // EFFECTS: returns sessions in this log as a JSON array.
    private JSONArray sessionsTOJson() {
        return null; // stub
    }
}
