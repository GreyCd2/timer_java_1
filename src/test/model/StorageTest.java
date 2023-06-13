package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {
    private Storage testStorage;
    private Folder testFolder1;
    private Folder testFolder2;
    private Folder testFolder3;
    private TimeRecord testTimeRecord;

    @BeforeEach
    void setup() {
        testStorage = new Storage();
        testFolder1 = new Folder("testfolder1");
        testFolder2 = new Folder("testfolder2");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testStorage.getFolderNumber());
    }

    @Test
    void testAddNewFolder() {
        testStorage.addFolder("testfolder1");
        assertEquals(1, testStorage.getFolderNumber());
        assertTrue((testStorage.getIthFolder(0)).isSameFolder(testFolder1));
        testStorage.addFolder("testfolder2");
        assertEquals(2, testStorage.getFolderNumber());
        assertTrue((testStorage.getIthFolder(0)).isSameFolder(testFolder1));
        assertTrue((testStorage.getIthFolder(1)).isSameFolder(testFolder2));
    }

    @Test
    void testAddExistingFolder() {
        testFolder3 = new Folder("testfolder3");
        testTimeRecord = new TimeRecord(2, "test");
        testFolder3.addTimeRecord(testTimeRecord);
        testStorage.addFolder(testFolder3);
        assertEquals(1, testStorage.getFolderNumber());
        assertEquals(testFolder3, testStorage.getIthFolder(0));
    }

    @Test
    void testDeleteFolders() {
        testStorage.addFolder("testfolder1");
        testStorage.addFolder("testfolder2");
        testStorage.deleteFolder(1);
        assertEquals(1, testStorage.getFolderNumber());
        assertTrue((testStorage.getIthFolder(0)).isSameFolder(testFolder1));
        testStorage.addFolder("testfolder2");
        testStorage.deleteFolder(0);
        assertEquals(1, testStorage.getFolderNumber());
        assertTrue((testStorage.getIthFolder(0)).isSameFolder(testFolder2));
        testStorage.deleteFolder(0);
        assertEquals(0, testStorage.getFolderNumber());
    }

    @Test
    void testGetFolders() {
        testStorage.addFolder("testfolder1");
        testStorage.addFolder("testfolder2");
        ArrayList<Folder> storage = testStorage.getFolders();
        assertEquals(2, storage.size());
        assertTrue((storage.get(0)).isSameFolder(testFolder1));
        assertTrue((storage.get(1)).isSameFolder(testFolder2));

    }
}
