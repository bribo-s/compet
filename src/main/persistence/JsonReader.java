package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads application state from JSON data stored in file.
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file.
    public JsonReader(String source) {
        // TODO: implement
    }

    // EFFECTS: reads pet manager and session log from file and returns;
    // throws IOException if an error occurs reading data from file.
    public AppState read() throws IOException {
        // TODO: implement
        return null; // stub
    }

    // EFFECTS: reads source file as string and returns.
    private String readFile(String source) throws IOException {
        // TODO: implement
        return null; // stub
    }

    // EFFECTS: parses pet manager from JSON object and returns.
    private PetManager parsePetManager(JSONObject jsonObject) {
        // TODO: implement
        return null; // stub
    }

    // MODIFIES: pm
    // EFFECTS: parses pets from JSON object and adds to pet manager.
    private void addPets(PetManager pm, JSONObject jsonObject) {
        // TODO: implement
    }

    // EFFECTS: parses pet from JSON object and returns.
    private Pet parsePet(JSONObject jsonObject) {
        // TODO: implement
        return null; // stub
    }

    // EFFECTS: parses session log from JSON object and returns.
    private SessionLog parseSessionLog(JSONObject jsonObject) {
        // TODO: implement
        return null; // stub
    }

    // MODIFIES: log
    // EFFECTS: parses sessions from JSON object and adds to session log.
    private void addSessions(SessionLog log, JSONObject jsonObject) {
        // TODO: implement
    }
    
    // EFFECTS: parses focus session from JSON object and returns.
    private FocusSession parseSession(JSONObject jsonObject) {
        // TODO: implement
        return null; // stub
    }
}
