package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TestEvent {
    private Event event;

    @BeforeEach
    void runBefore() {
        event = new Event("test description");
    }

    @Test
    void testConstructor() {
        assertNotNull(event.getDate());
        assertEquals("test description", event.getDescription());
    }

    @Test
    void testGetDate() {
        Date before = new Date(System.currentTimeMillis() - 1000);
        Date after = new Date(System.currentTimeMillis() + 1000);
        assertTrue(event.getDate().after(before));
        assertTrue(event.getDate().before(after));
    }

    @Test
    void testGetDescription() {
        assertEquals("test description", event.getDescription());
    }

    @Test
    void testToString() {
        String result = event.toString();
        assertTrue(result.contains("test description"));
        assertTrue(result.contains(event.getDate().toString()));
    }

    @Test
    void testDifferentDescriptions() {
        Event e1 = new Event("session started");
        Event e2 = new Event("session ended");
        assertEquals("session started", e1.getDescription());
        assertEquals("session ended", e2.getDescription());
    }
}
