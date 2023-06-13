package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FolderTest {
    private Folder testFolder1;
    private Folder testFolder2;
    private Folder testFolder3;
    private TimeRecord testRecord1;
    private TimeRecord testRecord2;

    @BeforeEach
    void setup() {
        testFolder1 = new Folder("test folder");
        testFolder2 = new Folder("test folder");
        testFolder3 = new Folder("different test folder");
        testRecord1 = new TimeRecord(131, "testrecord1");
        testRecord2 = new TimeRecord(1, "testrecord2");
    }

    @Test
    void testConstructor() {
        assertEquals("test folder", testFolder1.getName());
        assertEquals(0, testFolder1.getSize());
    }

    @Test
    void testRenameFolder() {
        testFolder1.setName("TestFolder");
        assertEquals("TestFolder", testFolder1.getName());
    }

    @Test
    void testAddTimeRecord() {
        testFolder1.addTimeRecord(testRecord1);
        assertEquals(1, testFolder1.getSize());
        assertEquals(testRecord1, testFolder1.getRecords().get(0));
        assertEquals(testRecord1, testFolder1.getBestRecord());
        testFolder1.addTimeRecord(testRecord2);
        assertEquals(2, testFolder1.getSize());
        assertEquals(testRecord1, testFolder1.getRecords().get(0));
        assertEquals(testRecord2, testFolder1.getRecords().get(1));
        assertEquals(testRecord2, testFolder1.getBestRecord());
    }

    @Test
    void testDeleteTimeRecord() {
        testFolder1.addTimeRecord(testRecord1);
        testFolder1.addTimeRecord(testRecord2);
        testFolder1.deleteTimeRecord(1);
        assertEquals(1, testFolder1.getSize());
        assertEquals(testRecord1, testFolder1.getRecords().get(0));
        testFolder1.addTimeRecord(testRecord2);
        testFolder1.deleteTimeRecord(0);
        assertEquals(1, testFolder1.getSize());
        assertEquals(testRecord2, testFolder1.getRecords().get(0));
        testFolder1.deleteTimeRecord(0);
        assertEquals(0, testFolder1.getSize());
    }

    @Test
    void testIsSameFolder() {
        assertTrue(testFolder1.equals(testFolder2));
        assertFalse(testFolder2.equals(testFolder3));
        testFolder1.addTimeRecord(testRecord1);
        testFolder3.addTimeRecord(testRecord1);
        assertFalse(testFolder1.equals(testFolder2));
        assertFalse(testFolder1.equals(testFolder3));
        testFolder2.addTimeRecord(testRecord1);
        testFolder2.addTimeRecord(testRecord2);
        testFolder1.addTimeRecord(testRecord2);
        assertTrue(testFolder1.equals(testFolder2));
    }

    @Test
    void testIsSameRecords() {
        assertTrue(testFolder1.getRecords().equals(testFolder2.getRecords()));
        testFolder1.addTimeRecord(testRecord1);
        testFolder2.addTimeRecord(testRecord1);
        assertTrue(testFolder1.getRecords().equals(testFolder2.getRecords()));
        testFolder1.addTimeRecord(testRecord2);
        assertFalse(testFolder1.getRecords().equals(testFolder2.getRecords()));
        testFolder2.addTimeRecord(testRecord2);
        assertTrue(testFolder1.getRecords().equals(testFolder2.getRecords()));
    }

    @Test
    void testGetRecordNumber() {
        assertEquals(0, testFolder1.getSize());
        testFolder1.addTimeRecord(testRecord1);
        assertEquals(1, testFolder1.getSize());
        testFolder1.addTimeRecord(testRecord2);
        assertEquals(2, testFolder1.getSize());
    }

    @Test
    void testGetIthTimeRecord() {
        testFolder1.addTimeRecord(testRecord1);
        testFolder1.addTimeRecord(testRecord2);
        assertEquals(testRecord1, testFolder1.getRecords().get(0));
        assertEquals(testRecord2, testFolder1.getRecords().get(1));
        testFolder2.addTimeRecord(testRecord2);
        testFolder2.addTimeRecord(testRecord1);
        assertEquals(testRecord2, testFolder2.getRecords().get(0));
        assertEquals(testRecord1, testFolder2.getRecords().get(1));
    }
}