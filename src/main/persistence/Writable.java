package persistence;

import org.json.JSONObject;

// Represents data that can be written to JSON format.
public interface Writable {
    // EFFECTS: returns this as JSON object.
    JSONObject toJson();
}
