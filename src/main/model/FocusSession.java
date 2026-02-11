package model;

import java.time.LocalDateTime;

// Represents a completed focus and study session with details about duration,
// associated pet, and interaction that occured after completion.
public class FocusSession {
    private int durationMinutes;
    private String petName;
    private String interactionType;
    private LocalDateTime completedAt;
    private int fondnessGained;

    // REQUIRES: durationMinutes > 0, petName is not empty, interactionType is empty.
    // EFFECTS: constructs a focus session with given duration, pet name, and the interaction type;
    // sets completion time to the current time and fondness gained to 1.
    public FocusSession(int durationMinutes, String petName, String interactionType) {
        // TODO: implement
    }

    // EFFECTS: returns duration of this session in minutes.
    public int getDurationMinutes() {
        return 0; // stub
    }

    // EFFECTS: returns name of the pet associated with this session.
    public String getPetName() {
        return null; // stub
    }

    // EFFECTS: returns type of interaction that occurred after this session.
    public String getInteractionType() {
        return null; // stub
    }

    // EFFECTS: returns date and time when this session was completed.
    public LocalDateTime getCompletedAt() {
        return null; // stub
    }

    // EFFECTS: returns amount of fondness gained from this session.
    public int getFondnessGained() {
        return 0; // stub
    }
}
