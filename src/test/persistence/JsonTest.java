package persistence;
import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    //NOTE: Ideas are from Demo project
    protected void checkLeaseRecord(int propID, Category category, LeaseRecord leaseRecord) {
        assertEquals(propID, leaseRecord.getLeasePropId());
        assertEquals(category, leaseRecord.getCategory());
    }
}
