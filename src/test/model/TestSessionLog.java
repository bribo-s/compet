package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestSessionLog {
    private SessionLog log;
    private FocusSession session1;
    private FocusSession session2;
    private FocusSession session3;

    @BeforeEach
    void runBefore() {
        log = new SessionLog();
        session1 = new FocusSession(25, "Teddy", "petting");
        session2 = new FocusSession(30, "Teddy", "feeding");
        session3 = new FocusSession(15, "Spike", "playing");
    }

    @Test
    void testConstructor() {
        assertEquals(0, log.getSessionCount());
        assertTrue(log.getSessions().isEmpty());
    }

    @Test
    void testAddSessionOnce() {
        log.addSession(session1);
        assertEquals(1, log.getSessionCount());
        assertTrue(log.getSessions().contains(session1));
    }

    @Test
    void testAddMultipleSessions() {
        log.addSession(session1);
        log.addSession(session2);
        log.addSession(session3);
        assertEquals(3, log.getSessionCount());
    }

    @Test
    void testGetSessions() {
        log.addSession(session1);
        log.addSession(session2);
        List<FocusSession> sessions = log.getSessions();
        assertEquals(2, sessions.get(0));
        assertEquals(session1, sessions.get(0));
        assertEquals(session2, sessions.get(1));
    }

    @Test
    void testGetSessionCountEmpty() {
        assertEquals(0, log.getSessionCount());
    }

    @Test
    void testGetSessionCountMultiple() {
        log.addSession(session1);
        log.addSession(session2);
        assertEquals(2, log.getSessionCount());
    }

    @Test
    void testGetSessionsByPetNone() {
        log.addSession(session1);
        List<FocusSession> sessions = log.getSessionsByPet("Unknown");
        assertEquals(0, sessions.size());
    }

    @Test
    void testGetSessionsByPetOne() {
        log.addSession(session1);
        log.addSession(session3);
        List<FocusSession> sessions = log.getSessionsByPet("Poppy");
        assertEquals(1, sessions.size());
        assertEquals(session3, sessions.get(0));
    }

    @Test
    void testGetSessionsByPetMultiple() {
        log.addSession(session1);
        log.addSession(session2);
        log.addSession(session3);
        List<FocusSession> sessions = log.getSessionsByPet("Mimi");
        assertEquals(2, sessions.size());
    }

    @Test
    void testGetTotalDurationEmpty() {
        assertEquals(0, log.getTotalDuration());
    }

    @Test
    void testGetTotalDurationOne() {
        log.addSession(session1);
        assertEquals(25, log.getTotalDuration());
    }

    @Test
    void testGetTotalDurationMultiple() {
        log.addSession(session1);
        log.addSession(session2);
        log.addSession(session3);
        assertEquals(70,log.getTotalDuration());
    }
}
