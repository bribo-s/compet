package model.persistence;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Pet;
import model.PetManager;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
public class TestPetManagerJson {
    private PetManager manager;
    private Pet pet1;
    private Pet pet2;
    
    @BeforeEach
    void runBefore() {
        manager = new PetManager();
        pet1 = new Pet("Fluffy", "Bunny", "Starry Bedroom");
        pet2 = new Pet("Teddy", "Dog", "Sunset Living Room");
    }
    
    @Test
    void testToJsonEmpty() {
        JSONObject json = manager.toJson();
        JSONArray pets = json.getJSONArray("pets");
        assertEquals(0, pets.length());
        assertFalse(json.has("currentPetName"));
    }
    
    @Test
    void testToJsonWithPets() {
        manager.addPet(pet1);
        manager.addPet(pet2);
        JSONObject json = manager.toJson();
        JSONArray pets = json.getJSONArray("pets");
        assertEquals(2, pets.length());
        assertEquals("Fluffy", json.getString("currentPetName"));
    }
    
    @Test
    void testToJsonAfterSwitch() {
        manager.addPet(pet1);
        manager.addPet(pet2);
        manager.switchPet("Teddy");
        JSONObject json = manager.toJson();
        assertEquals("Teddy", json.getString("currentPetName"));
    }
}
