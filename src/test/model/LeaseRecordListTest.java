package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeaseRecordListTest {

    // Variables for the  test
    private LeaseRecordList testLeaseRecordList;
    private LeaseRecord testLeaseA;
    private LeaseRecord testLeaseB;
    private LeaseRecord testLeaseC;                 //this is with 2nd test date
    private LeaseRecord testLeaseD;
    private LeaseRecord testLeaseE;
    private int testRentAmt = 1000;
    private String testStartDate1 = "01/01/2020";
    private String testEndDate1 = "12/31/2021";
    private String testStartDate2 = "01/01/2019";
    private String testEndDate2 = "12/31/2021";     // two sets of lease date

    @BeforeEach
    public void setUp() {
        testLeaseRecordList = new LeaseRecordList();
        testLeaseA = new LeaseRecord(1,1,testStartDate1,testEndDate1,testRentAmt);
        testLeaseB = new LeaseRecord(2,2,testStartDate1,testEndDate1,testRentAmt);
        testLeaseC = new LeaseRecord(3,3,testStartDate2,testEndDate2,testRentAmt);
        testLeaseD = new LeaseRecord(4,4,testStartDate1,testEndDate1,testRentAmt);
        testLeaseE = new LeaseRecord(5,5,testStartDate1,testEndDate1,testRentAmt);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testLeaseRecordList.length());
    }

    @Test
    void testAddLease() {
        testLeaseRecordList.addLease(testLeaseA);
        assertEquals(1,testLeaseA.getLeaseId());
        testLeaseRecordList.addLease(testLeaseB);
        assertEquals(2,testLeaseB.getLeaseId());
        testLeaseRecordList.addLease(testLeaseC);
        testLeaseRecordList.addLease(testLeaseD);
        assertEquals(testLeaseD, testLeaseRecordList.getLastLease());

        testLeaseRecordList.addLease(testLeaseE);
        assertEquals(testLeaseE, testLeaseRecordList.getLastLease());
    }


}
