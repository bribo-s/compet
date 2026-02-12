package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPet {
    private Pet pet;

    @BeforeEach
    void runBefore() {
        pet = new Pet("Teddy", "Dog", "Starry Bedroom");
    }

    @Test
    void testConstructor() {
        assertEquals("Teddy", pet.getName());
        assertEquals("Dog", pet.getType());
        assertEquals("Starry Bedroom", pet.getRoom());
        assertEquals(0, pet.getFondnessLevel());
    }

    @Test
    void testGetName() {
        assertEquals("Teddy", pet.getName());
    }
    
    @Test
    void testGetType() {
        assertEquals("Dog", pet.getType());
    }

    @Test
    void testGetRoom() {
        assertEquals("Starry Bedroom", pet.getRoom());
    }

    @Test
    void testGetFOndnessLevelInitial() {
        assertEquals(0, pet.getFondnessLevel());
    }

    @Test
    void testIncreaseFondnessOnce() {
        pet.increaseFondness(1);
        assertEquals(1, pet.getFondnessLevel());
    }

    @Test
    void testIncreaseFondnessMultipleTimes() {
        pet.increaseFondness(1);
        pet.increaseFondness(2);
        pet.increaseFondness(3);
        assertEquals(6, pet.getFondnessLevel());
    }

    @Test
    void testIncreaseFondnessByLargeAmount() {
        pet.increaseFondness(50);
        assertEquals(50, pet.getFondnessLevel());
    }

    @Test
    void testDifferentPetTypes() {
        Pet dog = new Pet("Spike", "Dog", "Modern Living Room");
        Pet bird = new Pet("Tweety", "Bird", "Lush Bedroom");

        assertEquals("Dog", dog.getType());
        assertEquals("Bird", bird.getType());
        assertEquals("Modern Living Room", dog.getRoom());
        assertEquals("Lush Bedroom", bird.getRoom());
    }
}
