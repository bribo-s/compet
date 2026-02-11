package model;

// Represents a virtual pet companion with a name, type, fondness level, and themed room.
public class Pet {
    private String name;
    private String type;
    private int fondnessLevel;
    private String room;

    // REQUIRES: name is not empty, type is not empty, room is not empty.
    // EFFECTS: constructs a pet with a given name, type, and room; fondness level starts at 0.
    public Pet(String name, String type, String room) {
        // TODO: implement
    }

    // REQUIRES: amount > 0.
    // MODIFIES: this
    // EFFECTS: increases fondness level by given amount.
    public void increaseFondness(int amount) {
        // TODO: implement
    }

    // EFFECTS: returns name of this pet.
    public String getName() {
        return null; // stub
    }

    // EFFECTS: returns type of this pet.
    public String getType() {
        return null; // stub
    }

    // EFFECTS: returns current fondness level of this pet.
    public int getFondnessLevel() {
        return 0; // stub
    }

    // EFFECTS: returns themed room associated with this pet.
    public String getRoom() {
        return null; // stub
    }
}
