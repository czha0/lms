package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE: Ideas are from Demo project

    @Test
    void testWriterInvalidFile() {
        try {
            LeaseRecordList lrl = new LeaseRecordList("ABC Company");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRentRoll() {
        try {
            LeaseRecordList lrl = new LeaseRecordList("ABC Company");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRentRoll.json");
            writer.open();
            writer.write(lrl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRentRoll.json");
            lrl = reader.read();
            assertEquals("ABC Company", lrl.getCompanyName());
            assertEquals(0, lrl.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRentRoll() {
        try {
            LeaseRecordList lrl = new LeaseRecordList("ABC Company");
            lrl.addLease(new LeaseRecord(1, 1,Category.COMMERCIAL,
                    "01/JAN/2020","31/DEC/2022",1000));
            lrl.addLease(new LeaseRecord(2,2,Category.OFFICE,
                    "01/JAN/2020","31/DEC/2022",3000));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRentRoll.json");
            writer.open();
            writer.write(lrl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRentRoll.json");
            lrl = reader.read();
            assertEquals("ABC Company", lrl.getCompanyName());
            List<LeaseRecord> rentRoll = lrl.getLeaseRecords();
            assertEquals(2, rentRoll.size());
            checkLeaseRecord(1, Category.COMMERCIAL, rentRoll.get(0));
            checkLeaseRecord(2, Category.OFFICE, rentRoll.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}