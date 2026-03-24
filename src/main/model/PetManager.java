package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
        EventLog.getInstance().logEvent(new Event("Pet added: " + pet.getName() + " (" + pet.getType() + ")"));
    }

    // REQUIRES: petName is not empty and pet with name exists.
    // MODIFIES: this
    // EFFECTS: sets pet with given name as current pet.
    public void switchPet(String petName) {
        for (Pet pet : pets) {
            if (pet.getName().equals(petName)) {
                currentPet = pet;
                EventLog.getInstance().logEvent(new Event("Switched to pet: " + petName));
                return;
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
    
    // EFFECTS: returns this pet manager as a JSON object.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pets", petsToJson());
        if (currentPet != null) {
            json.put("currentPetName", currentPet.getName());
        }
        return json;
    }
    
    // EFFECTS: returns pets in this manager as a JSON array.
    private JSONArray petsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Pet pet : pets) {
            jsonArray.put(pet.toJson());
        }
        return jsonArray;
    }
}
