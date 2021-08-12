package persistence;

import org.json.JSONObject;

// Interface Writable for JSON
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}