package model;

import org.json.JSONObject;

// Represents a virtual pet companion with a name, type, fondness level, and themed room.
public class Pet {
    private String name;
    private String type;
    private int fondnessLevel;
    private String room;

    // REQUIRES: name is not empty, type is not empty, room is not empty.
    // EFFECTS: constructs a pet with a given name, type, and room; fondness level starts at 0.
    public Pet(String name, String type, String room) {
        this.name = name;
        this.type = type;
        this.room = room;
        this.fondnessLevel = 0;
    }

    // REQUIRES: amount > 0.
    // MODIFIES: this
    // EFFECTS: increases fondness level by given amount.
    public void increaseFondness(int amount) {
        this.fondnessLevel += amount;
    }

    // EFFECTS: returns name of this pet.
    public String getName() {
        return name;
    }

    // EFFECTS: returns type of this pet.
    public String getType() {
        return type;
    }

    // EFFECTS: returns current fondness level of this pet.
    public int getFondnessLevel() {
        return fondnessLevel;
    }

    // EFFECTS: returns themed room associated with this pet.
    public String getRoom() {
        return room;
    }
        
    // EFFECTS: returns this pet as a JSON object.
    public JSONObject toJson() {
        return null; // stub
    }
}
