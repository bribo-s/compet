package model.persistence;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.FocusSession;
import model.SessionLog;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
public class TestSessionLogJson {
    private SessionLog log;
    private FocusSession session1;
    private FocusSession session2;
    
    @BeforeEach
    void runBefore() {
        log = new SessionLog();
        session1 = new FocusSession(25, "Fluffy", "petting");
        session2 = new FocusSession(30, "Teddy", "feeding");
    }
    
    @Test
    void testToJsonEmpty() {
        JSONObject json = log.toJson();
        JSONArray sessions = json.getJSONArray("sessions");
        assertEquals(0, sessions.length());
    }
    
    @Test
    void testToJsonWithSessions() {
        log.addSession(session1);
        log.addSession(session2);
        JSONObject json = log.toJson();
        JSONArray sessions = json.getJSONArray("sessions");
        assertEquals(2, sessions.length());
    }
}
