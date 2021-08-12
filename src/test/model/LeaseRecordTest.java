package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LeaseRecordTest {
    private LeaseRecord testLeaseRecord;
    private String testStartDate = "01/01/2020";
    private String testEndDate = "12/31/2021";

    @BeforeEach
    public void setUp() {
        testLeaseRecord = new LeaseRecord(1,1,Category.COMMERCIAL,testStartDate,testEndDate,1000);
    }

    @Test
    void testConstructor() {
        assertEquals(1,testLeaseRecord.getLeasePropId());
        assertEquals(testStartDate,testLeaseRecord.getStartDate());
        assertEquals(testEndDate,testLeaseRecord.getEndDate());
        assertTrue(testLeaseRecord.isCurrent());
    }

    @Test
    void generalTests() {
        assertEquals(1000,testLeaseRecord.getRentAmt());
        assertEquals(testStartDate,testLeaseRecord.getStartDate());
        assertEquals(testEndDate,testLeaseRecord.getEndDate());
        assertEquals(1,testLeaseRecord.getLeasePropId());
    }

    @Test
    void testPropertyOperatingStatus() {
        testLeaseRecord.setLeaseInactive();
        assertFalse(testLeaseRecord.isCurrent());

        testLeaseRecord.setLeaseActive();
        assertTrue(testLeaseRecord.isCurrent());
    }

    @Test
    void testToString() {
        assertEquals("Lease ID 1: Property ID 1 is a COMMERCIAL lease starts from" +
                " 01/01/2020 to 12/31/2021 with monthly rate at $1000",testLeaseRecord.toString());
    }

}