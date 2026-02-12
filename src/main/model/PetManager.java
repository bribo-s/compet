package model;

import java.util.ArrayList;
import java.util.List;

// Manages multiple pets and tracks which pet is active (at current).
public class PetManager {
    private List<Pet> pets;
    private Pet currentPet;

    // EFFECTS: constructs a pet manager with an empty list of pets and no current pet.
    public PetManager() {
        // TODO: implement
    }

    // REQUIRES: pet is not null.
    // MODIFIES: this
    // EFFECTS: adds given pet to list of pets; if this is the first pet, 
    // set as current pet.
    public void addPet(Pet pet) {
        // TODO: implement
    }

    // REQUIRES: petName is not empty and pet with name exists.
    // MODIFIES: this
    // EFFECTS: sets pet with given name as current pet.
    public void switchPet(String petName) {
        // TODO: implement
    }

    // EFFECTS: returns current pet, or null if no pet is set.
    public Pet getCurrentPet() {
        return null; // stub
    }

    // EFFECTS: returns list of all pets.
    public List<Pet> getAllPets() {
        return null; // stub
    }

    // EFFECTS: returns number of pets managed.
    public int getPetCount() {
        return 0; // stub
    }

    // REQUIRES: petName is not empty.
    // EFFECTS: returns pet with given name, or null if not found.
    public Pet getPetByName(String petName) {
        return null; // stub
    }
}
