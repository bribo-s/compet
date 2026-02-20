package model.persistence;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Pet;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
public class TestPetJson {
    private Pet pet;
    
    @BeforeEach
    void runBefore() {
        pet = new Pet("Fluffy", "Bunny", "Starry Bedroom");
        pet.increaseFondness(3);
    }
    
    @Test
    void testToJson() {
        JSONObject json = pet.toJson();
        assertEquals("Fluffy", json.getString("name"));
        assertEquals("Bunny", json.getString("type"));
        assertEquals("Starry Bedroom", json.getString("room"));
        assertEquals(3, json.getInt("fondnessLevel"));
    }
}
