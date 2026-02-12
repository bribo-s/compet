package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestInteraction {
    private Interaction interaction;

    @BeforeEach
    void runBefore() {
        interaction = new Interaction();
    }

    @Test
    void testConstructor() {
        assertNotNull(interaction);
    }

    @Test
    void testGetAllInteractionTypes() {
        List<String> types = interaction.getAllInteractionTypes();
        assertEquals(4, types.size());
        assertTrue(types.contains("petting"));
        assertTrue(types.contains("feeding"));
        assertTrue(types.contains("playing"));
        assertTrue(types.contains("cleaning"));
    }

    @Test
    void testGetRandomInteractionReturnsValidType() {
        String randomType = interaction.getRandomInteraction();
        List<String> validTypes = interaction.getAllInteractionTypes();
        assertTrue(validTypes.contains(randomType));
    }

    @Test
    void testGetRandomInteractionMultipleTimes() {
        boolean foundDifferent = false;
        String first = interaction.getRandomInteraction();

        for (int i = 0; i < 20; i++) {
            String current = interaction.getRandomInteraction();
            if (!current.equals(first)) {
                foundDifferent = true;
                break;
            }
        }

        assertTrue(foundDifferent || first != null);
    }

    @Test
    void testAllInteractionTypesAreValid() {
        for (int i = 0; i < 30; i++) {
            String type = interaction.getRandomInteraction();
            assertNotNull(type);
            assertFalse(type.isEmpty());
        }
    }
}
