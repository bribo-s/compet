package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class TestFocusSession {
    private FocusSession session;
    
    @BeforeEach
    void runBefore() {
        session = new FocusSession(25, "Teddy", "petting");
    }

    @Test
    void testConstructor() {
        assertEquals(25, session.getDurationMinutes());
        assertEquals("Teddy", session.getPetName());
        assertEquals("petting", session.getInteractionType());
        assertEquals(1, session.getFondnessGained());
        assertNotNull(session.getCompletedAt());
    }

    @Test
    void testGetDurationMinutes() {
        assertEquals(25, session.getDurationMinutes());
    }

    @Test
    void testGetPetName() {
        assertEquals("Teddy", session.getPetName());
    }

    @Test
    void testGetInteractionType() {
        assertEquals("petting", session.getInteractionType());
    }

    @Test
    void testGetFondnessGained() {
        assertEquals(1, session.getFondnessGained());
    }

    @Test
    void testGetCompletedAt() {
        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        FocusSession newSession = new FocusSession(30, "Fluffy", "feeding");
        LocalDateTime after = LocalDateTime.now().plusSeconds(1);

        assertTrue(newSession.getCompletedAt().isAfter(before));
        assertTrue(newSession.getCompletedAt().isBefore(after));
    }

    @Test
    void testDifferentDurations() {
        FocusSession shortSession = new FocusSession(15, "Bunny", "playing");
        FocusSession longSession = new FocusSession(50, "Kitty", "cleaning");

        assertEquals(15, shortSession.getDurationMinutes());
        assertEquals(50, longSession.getDurationMinutes());
    }
}
