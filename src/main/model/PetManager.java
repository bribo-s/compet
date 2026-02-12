package model;

import java.util.ArrayList;
import java.util.List;

// Manages multiple pets and tracks which pet is active (at current).
public class PetManager {
    private List<Pet> pets;
    private Pet currentPet;

    // EFFECTS: constructs a pet manager with an empty list of pets and no current pet.
    public PetManager() {
        this.pets = new ArrayList<>();
        this.currentPet = null;
    }

    // REQUIRES: pet is not null.
    // MODIFIES: this
    // EFFECTS: adds given pet to list of pets; if this is the first pet, 
    // set as current pet.
    public void addPet(Pet pet) {
        pets.add(pet);
        if (currentPet == null) {
            currentPet = pet;
        }
    }

    // REQUIRES: petName is not empty and pet with name exists.
    // MODIFIES: this
    // EFFECTS: sets pet with given name as current pet.
    public void switchPet(String petName) {
        for (Pet pet : pets) {
            if (pet.getName().equals(petName)) {
                currentPet = pet;
                break;
            }
        }
    }

    // EFFECTS: returns current pet, or null if no pet is set.
    public Pet getCurrentPet() {
        return currentPet;
    }

    // EFFECTS: returns list of all pets.
    public List<Pet> getAllPets() {
        return pets;
    }

    // EFFECTS: returns number of pets managed.
    public int getPetCount() {
        return pets.size();
    }

    // REQUIRES: petName is not empty.
    // EFFECTS: returns pet with given name, or null if not found.
    public Pet getPetByName(String petName) {
        for (Pet pet : pets) {
            if (pet.getName().equals(petName)) {
                return pet;
            }
        }
        return null;
    }
}
