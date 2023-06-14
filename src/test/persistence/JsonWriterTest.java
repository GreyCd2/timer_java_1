package persistence;

import model.Folder;
import model.Storage;
import model.TimeRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Storage storage = new Storage();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStorage() {
        try {
            Storage storage = new Storage();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStorage.json");
            writer.open();
            writer.write(storage);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStorage.json");
            storage = reader.read();
            assertEquals(0, storage.getFolders().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStorage() {
        try {
            Storage storage = new Storage();
            storage.addFolder("test folder 1");
            Folder testFolder2 = new Folder("test folder 2");
            TimeRecord testRecord = new TimeRecord(123, "test record");
            testFolder2.addTimeRecord(testRecord);
            storage.addFolder(testFolder2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStorage.json");
            writer.open();
            writer.write(storage);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStorage.json");
            storage = reader.read();
            assertEquals(2, storage.getFolders().size());
            Folder folder1 = storage.getFolders().get(0);
            Folder folder2 = storage.getFolders().get(1);
            assertEquals(0, folder1.getSize());
            assertEquals(1, folder2.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
