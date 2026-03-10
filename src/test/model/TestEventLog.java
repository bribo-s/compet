package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TestEventLog {
    private EventLog log;
    private Event event1;
    private Event event2;

    @BeforeEach
    void runBefore() {
        log = EventLog.getInstance();
        log.clear();
        event1 = new Event("first event");
        event2 = new Event("second event");
    }

    @Test
    void testGetInstanceSingleton() {
        EventLog second = EventLog.getInstance();
        assertSame(log, second);
    }

    @Test
    void testLogEventOne() {
        log.logEvent(event1);
        Iterator<Event> it = log.iterator();
        it.next();
        assertTrue(it.hasNext());
        assertEquals("first event", it.next().getDescription());
    }

    @Test
    void testLogEventMultiple() {
        log.logEvent(event1);
        log.logEvent(event2);
        int count = 0;
        for (Event e : log) {
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    void testClearLeavesOneEvent() {
        log.logEvent(event1);
        log.logEvent(event2);
        log.clear();
        int count = 0;
        String lastDesc = "";
        for (Event e : log) {
            count++;
            lastDesc = e.getDescription();
        }
        assertEquals(1, count);
        assertEquals("Event log cleared.", lastDesc);
    }

    @Test
    void testIteratorHasNext() {
        log.logEvent(event1);
        Iterator<Event> it = log.iterator();
        assertTrue(it.hasNext());
    }
}
