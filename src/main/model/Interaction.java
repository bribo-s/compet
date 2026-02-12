package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents an interaction with a pet, providing random interaction types
// that can occur after completing a focus session.
public class Interaction {
    private static final List<String> INTERACTION_TYPES = new ArrayList<>();
    private Random random;

    static {
        INTERACTION_TYPES.add("petting");
        INTERACTION_TYPES.add("feeding");
        INTERACTION_TYPES.add("playing");
        INTERACTION_TYPES.add("cleaning");
    }

    // EFFECTS: constructs an interaction with new random number generator.
    public Interaction() {
        // TODO: implement
    }

    // EFFECTS: returns random interaction type from list of available interactions.
    public String getRandomInteraction() {
        return null; // stub
    }

    // EFFECTS: returns list of all available interaction types.
    public List<String> getAllInteractionTypes() {
        return null; // stub
    }
}
