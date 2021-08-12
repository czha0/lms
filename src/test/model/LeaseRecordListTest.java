package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;

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
        testLeaseRecordList = new LeaseRecordList("ABC Company");
        testLeaseA = new LeaseRecord(1,1,Category.RETAIL,testStartDate1,testEndDate1,testRentAmt);
        testLeaseB = new LeaseRecord(2,2,Category.OFFICE,testStartDate1,testEndDate1,testRentAmt);
        testLeaseC = new LeaseRecord(3,3,Category.RESIDENTIAL,testStartDate2,testEndDate2,testRentAmt);
        testLeaseD = new LeaseRecord(4,4,Category.MIXED,testStartDate1,testEndDate1,testRentAmt);
        testLeaseE = new LeaseRecord(5,5,Category.COMMERCIAL,testStartDate1,testEndDate1,testRentAmt);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testLeaseRecordList.length());
        assertEquals(0,testLeaseRecordList.getCurrentFunds());
    }

    @Test
    void testAddLease() {
        testLeaseRecordList.addLease(testLeaseA);
        assertEquals(1,testLeaseA.getLeaseId());
        testLeaseRecordList.addLease(testLeaseB);
        assertEquals(2,testLeaseB.getLeaseId());
        testLeaseRecordList.addLease(testLeaseC);
        testLeaseRecordList.addLease(testLeaseD);
    }

    @Test
    void testTotalMonthlyPay() {
        testLeaseRecordList.addLease(testLeaseA);
        testLeaseRecordList.addLease(testLeaseB);
        testLeaseRecordList.addLease(testLeaseC);
        assertEquals(3000, testLeaseRecordList.totalPayment());
    }

    @Test
    void testEnoughFundToPay() {
        testLeaseRecordList.addDeposit(3000);
        assertEquals(3000,testLeaseRecordList.getCurrentFunds());
        testLeaseRecordList.addLease(testLeaseA);
        testLeaseRecordList.addLease(testLeaseB);
        testLeaseRecordList.addLease(testLeaseC);
        assertEquals(3000,testLeaseRecordList.totalPayment());
        try {
            testLeaseRecordList.makePayment();
        } catch (InsufficientResourcesException e) {
            // enough to cover payment, no exception thrown
        }
        assertEquals(0,testLeaseRecordList.getCurrentFunds());
    }

    @Test
    void testNotEnoughFundToPay() {
        testLeaseRecordList.addDeposit(2000);
        assertEquals(2000,testLeaseRecordList.getCurrentFunds());
        testLeaseRecordList.addLease(testLeaseA);
        testLeaseRecordList.addLease(testLeaseB);
        testLeaseRecordList.addLease(testLeaseC);
        testLeaseRecordList.addLease(testLeaseD);
        try {
            testLeaseRecordList.makePayment();
        } catch (InsufficientResourcesException e) {
            fail("No enough funds.");
        }
        assertEquals(2000,testLeaseRecordList.getCurrentFunds());
    }

}
