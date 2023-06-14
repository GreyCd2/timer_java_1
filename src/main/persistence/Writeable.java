package persistence;

import org.json.JSONObject;

public interface Writeable {
    /**
     * Returns this as JSON object
     *
     * @return
     */
    JSONObject toJson();
}
