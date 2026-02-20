package model.persistence;

import model.*;
import persistence.AppState;
import persistence.JsonReader;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
public class TestJsonReader {
    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }
    
    @Test
    void testReaderEmptyState() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyState.json");
        try {
            AppState state = reader.read();
            assertEquals(0, state.getPetManager().getPetCount());
            assertEquals(0, state.getSessionLog().getSessionCount());
            assertNull(state.getPetManager().getCurrentPet());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    
    @Test
    void testReaderGeneralState() {
        JsonReader reader = new JsonReader("./data/testCompetApp.json");
        try {
            AppState state = reader.read();
            PetManager pm = state.getPetManager();
            SessionLog sl = state.getSessionLog();
            assertEquals(2, pm.getPetCount());
            assertEquals(2, sl.getSessionCount());
            checkFluffyPet(pm);
            checkTeddyPet(pm);
            assertEquals("Fluffy", pm.getCurrentPet().getName());
            checkSessions(sl);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    
    private void checkFluffyPet(PetManager pm) {
        Pet fluffy = pm.getPetByName("Fluffy");
        assertNotNull(fluffy);
        assertEquals("Fluffy", fluffy.getName());
        assertEquals("Bunny", fluffy.getType());
        assertEquals("Starry Bedroom", fluffy.getRoom());
        assertEquals(3, fluffy.getFondnessLevel());
    }
    
    private void checkTeddyPet(PetManager pm) {
        Pet teddy = pm.getPetByName("Teddy");
        assertNotNull(teddy);
        assertEquals("Teddy", teddy.getName());
        assertEquals("Dog", teddy.getType());
        assertEquals(5, teddy.getFondnessLevel());
    }
    
    private void checkSessions(SessionLog sl) {
        List<FocusSession> sessions = sl.getSessions();
        assertEquals(25, sessions.get(0).getDurationMinutes());
        assertEquals("Fluffy", sessions.get(0).getPetName());
        assertEquals("petting", sessions.get(0).getInteractionType());
    }
}
