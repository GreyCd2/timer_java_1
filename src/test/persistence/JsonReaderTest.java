package persistence;

import model.Folder;
import model.Storage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Storage storage = reader.read();
            fail("IOException exepected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStorage() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStorage.json");
        try {
            Storage storage = reader.read();
            assertEquals(0, storage.getFolders().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStorage() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStorage.json");
        try {
            Storage storage = reader.read();
            assertEquals(2, storage.getFolders().size());
            Folder folder1 = storage.getFolders().get(0);
            Folder folder2 = storage.getFolders().get(1);
            assertEquals(0, folder1.getSize());
            assertEquals(1, folder2.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
