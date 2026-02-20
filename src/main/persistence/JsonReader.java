package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

import model.FocusSession;
import model.Pet;
import model.PetManager;
import model.SessionLog;

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
        this.source = source;
    }
    
    // EFFECTS: reads pet manager and session log from file and returns;
    // throws IOException if an error occurs reading data from file.
    public AppState read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        PetManager petManager = parsePetManager(jsonObject);
        SessionLog sessionLog = parseSessionLog(jsonObject);
        return new AppState(petManager, sessionLog);
    }
    
    // EFFECTS: reads source file as string and returns.
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }
    
    // EFFECTS: parses pet manager from JSON object and returns.
    private PetManager parsePetManager(JSONObject jsonObject) {
        PetManager pm = new PetManager();
        addPets(pm, jsonObject);
        if (jsonObject.has("currentPetName")) {
            String currentPetName = jsonObject.getString("currentPetName");
            pm.switchPet(currentPetName);
        }
        return pm;
    }
    
    // MODIFIES: pm
    // EFFECTS: parses pet from JSON object and adds to pet manager.
    private void addPet(PetManager pm, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        String room = jsonObject.getString("room");
        int fondnessLevel = jsonObject.getInt("fondnessLevel");
        
        Pet pet = new Pet(name, type, room);
        for (int i = 0; i < fondnessLevel; i++) {
            pet.increaseFondness(1);
        }
        pm.addPet(pet);
    }
    
    // MODIFIES: pm
    // EFFECTS: parses pets from JSON object and adds to pet manager.
    private void addPets(PetManager pm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pets");
        for (Object json : jsonArray) {
            JSONObject nextPet = (JSONObject) json;
            addPet(pm, nextPet);
        }
    }
    
    // EFFECTS: parses session log from JSON object and returns.
    private SessionLog parseSessionLog(JSONObject jsonObject) {
        SessionLog log = new SessionLog();
        addSessions(log, jsonObject);
        return log;
    }
    
    // MODIFIES: log
    // EFFECTS: parse focus session from JSON object and adds to session log.
    private void addSession(SessionLog log, JSONObject jsonObject) {
        int duration = jsonObject.getInt("durationMinutes");
        String petName = jsonObject.getString("petName");
        String interactionType = jsonObject.getString("interactionType");
        
        FocusSession session = new FocusSession(duration, petName, interactionType);
        log.addSession(session);
    }
    
    // MODIFIES: log
    // EFFECTS: parses sessions from JSON object and adds to session log.
    private void addSessions(SessionLog log, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sessions");
        for (Object json : jsonArray) {
            JSONObject nextSession = (JSONObject) json;
            addSession(log, nextSession);
        }
    }
}
