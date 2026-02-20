package model.persistence;

import model.*;
import persistence.AppState;
import persistence.JsonReader;
import persistence.JsonWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
public class TestJsonWriter {
    private PetManager petManager;
    private SessionLog sessionLog;
    
    @BeforeEach
    void runBefore() {
        petManager = new PetManager();
        sessionLog = new SessionLog();
    }
    
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }
    
    @Test
    void testWriterEmptyState() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyState.json");
            writer.open();
            writer.write(petManager, sessionLog);
            writer.close();
            
            JsonReader reader = new JsonReader("./data/testWriterEmptyState.json");
            AppState state = reader.read();
            assertEquals(0, state.getPetManager().getPetCount());
            assertEquals(0, state.getSessionLog().getSessionCount());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    
    @Test
    void testWriterGeneralState() {
        try {
            setupPetsAndSessions();
            writeToFile();
            verifyWrittenData();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    
    private void setupPetsAndSessions() {
        Pet pet1 = new Pet("Fluffy", "Bunny", "Starry Bedroom");
        pet1.increaseFondness(3);
        Pet pet2 = new Pet("Teddy", "Dog", "Sunset Living Room");
        pet2.increaseFondness(5);
        petManager.addPet(pet1);
        petManager.addPet(pet2);
        FocusSession session1 = new FocusSession(25, "Fluffy", "petting");
        FocusSession session2 = new FocusSession(30, "Teddy", "feeding");
        sessionLog.addSession(session1);
        sessionLog.addSession(session2);
    }
    
    private void writeToFile() throws IOException {
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralState.json");
        writer.open();
        writer.write(petManager, sessionLog);
        writer.close();
    }
    
    private void verifyWrittenData() throws IOException {
        JsonReader reader = new JsonReader("./data/testWriterGeneralState.json");
        AppState state = reader.read();
        assertEquals(2, state.getPetManager().getPetCount());
        assertEquals(2, state.getSessionLog().getSessionCount());
        Pet loadedPet = state.getPetManager().getPetByName("Fluffy");
        assertEquals("Fluffy", loadedPet.getName());
        assertEquals("Bunny", loadedPet.getType());
        assertEquals(3, loadedPet.getFondnessLevel());
    }
}
