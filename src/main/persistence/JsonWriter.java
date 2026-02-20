package persistence;

import model.PetManager;
import model.SessionLog;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of application state to file.
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file.
    public JsonWriter(String destination) {
        // TODO: implement
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing.
    public void open() throws FileNotFoundException {
        // TODO: implement
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of pet manager and session log to file.
    public void write(PetManager petManager, SessionLog sessionLog) {
        // TODO: implement
    }

    // MODIFIES: this
    // EFFECTS: closes writer.
    public void close() {
        // TODO: implement
    }

    // MODIFIES: this
    // EFFECTS: writes string to file.
    private void saveToFile(String json) {
        // TODO: implement
    }
}
