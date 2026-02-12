package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestPetManager {
    private PetManager manager;
    private Pet pet1;
    private Pet pet2;
    private Pet pet3;
    
    @BeforeEach
    void runBefore() {
        manager = new PetManager();
        pet1 = new Pet("Fluffy", "Cat", "Modern Living Room");
        pet2 = new Pet("Bonnie", "Bunny", "Starry Bedroom");
        pet3 = new Pet("Spike", "Dog", "Sunset Living Room");
    }
    
    @Test
    void testConstructor() {
        assertEquals(0, manager.getPetCount());
        assertNull(manager.getCurrentPet());
        assertTrue(manager.getAllPets().isEmpty());
    }
    
    @Test
    void testAddPetFirst() {
        manager.addPet(pet1);
        assertEquals(1, manager.getPetCount());
        assertEquals(pet1, manager.getCurrentPet());
    }
    
    @Test
    void testAddMultiplePets() {
        manager.addPet(pet1);
        manager.addPet(pet2);
        manager.addPet(pet3);
        assertEquals(3, manager.getPetCount());
        assertEquals(pet1, manager.getCurrentPet());
    }
    
    @Test
    void testGetAllPets() {
        manager.addPet(pet1);
        manager.addPet(pet2);
        List<Pet> pets = manager.getAllPets();
        assertEquals(2, pets.size());
        assertTrue(pets.contains(pet1));
        assertTrue(pets.contains(pet2));
    }
    
    @Test
    void testSwitchPet() {
        manager.addPet(pet1);
        manager.addPet(pet2);
        manager.switchPet("Teddy");
        assertEquals(pet2, manager.getCurrentPet());
    }
    
    @Test
    void testSwitchPetMultipleTimes() {
        manager.addPet(pet1);
        manager.addPet(pet2);
        manager.addPet(pet3);
        manager.switchPet("Teddy");
        assertEquals(pet2, manager.getCurrentPet());
        manager.switchPet("Tweety");
        assertEquals(pet3, manager.getCurrentPet());
        manager.switchPet("Fluffy");
        assertEquals(pet1, manager.getCurrentPet());
    }

    @Test
    void testGetPetByNameExists() {
        manager.addPet(pet1);
        manager.addPet(pet2);
        Pet found = manager.getPetByName("Fluffy");
        assertEquals(pet1, found);
    }

    @Test
    void testGetPetByNameNotExists() {
        manager.addPet(pet1);
        Pet found = manager.getPetByName("Unknown");
        assertNull(found);
    }

    @Test
    void testGetPetCountEmpty() {
        assertEquals(0, manager.getPetCount());
    }

    @Test
    void testGetPetCountMultiple() {
        manager.addPet(pet1);
        manager.addPet(pet2);
        assertEquals(2, manager.getPetCount());
    }
}
