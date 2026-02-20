package model;

import java.time.LocalDateTime;

import org.json.JSONObject;

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
        this.durationMinutes = durationMinutes;
        this.petName = petName;
        this.interactionType = interactionType;
        this.completedAt = LocalDateTime.now();
        this.fondnessGained = 1;
    }

    // EFFECTS: returns duration of this session in minutes.
    public int getDurationMinutes() {
        return durationMinutes;
    }

    // EFFECTS: returns name of the pet associated with this session.
    public String getPetName() {
        return petName;
    }

    // EFFECTS: returns type of interaction that occurred after this session.
    public String getInteractionType() {
        return interactionType;
    }

    // EFFECTS: returns date and time when this session was completed.
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    // EFFECTS: returns amount of fondness gained from this session.
    public int getFondnessGained() {
        return fondnessGained;
    }
    
    // EFFECTS: returns this focus session as a JSON object.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("durationMinutes", durationMinutes);
        json.put("petName", petName);
        json.put("interactionType", interactionType);
        json.put("fondnessGained", fondnessGained);
        return json;
    }
}
