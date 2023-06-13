package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeRecordTest {
    private TimeRecord testTimeRecord1;
    private TimeRecord testTimeRecord2;
    private TimeRecord testTimeRecord3;
    private TimeRecord testTimeRecord4;

    @BeforeEach
    void setup() {
        testTimeRecord1 = new TimeRecord(12345, "test note");
        testTimeRecord2 = new TimeRecord(1, "hello");
        testTimeRecord3 = new TimeRecord(1, "test note");
        testTimeRecord4 = new TimeRecord(12345, "test note");
    }

    @Test
    void testConstructor() {
        assertEquals(12345, testTimeRecord1.getTime());
        assertEquals(1, testTimeRecord2.getTime());
        assertEquals("test note", testTimeRecord1.getNote());
        assertEquals("hello", testTimeRecord2.getNote());
    }

    @Test
    void testEditNote() {
        testTimeRecord1.setNote("TESTNOTE");
        assertEquals("TESTNOTE", testTimeRecord1.getNote());
    }

    @Test
    void testIsSameRecord() {
        assertFalse(testTimeRecord1.equals(testTimeRecord2));
        assertFalse(testTimeRecord1.equals(testTimeRecord3));
        assertFalse(testTimeRecord2.equals(testTimeRecord3));
        assertTrue(testTimeRecord1.equals(testTimeRecord4));
    }
}