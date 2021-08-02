package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    //NOTE: Ideas are from Demo project

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            LeaseRecordList lrl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRentRoll() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRentRoll.json");
        try {
            LeaseRecordList lrl = reader.read();
            assertEquals("ABC Company", lrl.getCompanyName());
            assertEquals(0, lrl.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRentRoll() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRentRoll.json");
        try {
            LeaseRecordList lrl = reader.read();
            assertEquals("ABC Company", lrl.getCompanyName());
            List<LeaseRecord> rentRoll = lrl.getLeaseRecords();
            assertEquals(2, rentRoll.size());
            checkLeaseRecord(1, Category.COMMERCIAL, rentRoll.get(0));
            checkLeaseRecord(2, Category.OFFICE, rentRoll.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}