package persistence;

import model.PetManager;
import model.SessionLog;

// Represents complete state of application (pet manager and session log).
public class AppState {
    private PetManager petManager;
    private SessionLog sessionLog;

    // REQUIRES: petManager and sessionLog are not null.
    // EFFECTS: constructs application state with given pet manager and session log.
    public AppState(PetManager petManager, SessionLog sessionLog) {
        // TODO: implement
    }

    // EFFECTS: returns pet manager.
    public PetManager getPetManager() {
        return null; // stub
    }

    // EFFECTS: returns session log.
    public SessionLog getSessionLog() {
        return null; // stub
    }
}
