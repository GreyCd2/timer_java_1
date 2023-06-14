package persistence;

import model.Storage;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Represent a writer that writes current storage into JSON file
 *
 * @author Grey
 */
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /**
     * Open writer
     * Throw FileNotFoundException if destination file cannot be opened for writing
     *
     * @throws FileNotFoundException
     */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    /**
     * Writes JSON representation of storage to file
     *
     * @param storage
     */
    public void write(Storage storage) {
        JSONObject json = storage.toJson();
        saveToFile(json.toString(TAB));
    }

    public void close() {
        writer.close();
    }

    /**
     * Writes string to file
     *
     * @param json
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}
