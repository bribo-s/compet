package model.persistence;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.FocusSession;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
public class TestFocusSessionJson {
    private FocusSession session;
    
    @BeforeEach
    void runBefore() {
        session = new FocusSession(25, "Fluffy", "petting");
    }
    
    @Test
    void testToJson() {
        JSONObject json = session.toJson();
        assertEquals(25, json.getInt("durationMinutes"));
        assertEquals("Fluffy", json.getString("petName"));
        assertEquals("petting", json.getString("interactionType"));
        assertEquals(1, json.getInt("fondnessGained"));
    }
}
