package model;

import java.util.LinkedList;

// Represents properties with active leases
public class LeaseRecordList {

    private LinkedList<LeaseRecord> rentRoll;   // a list of Rent Roll for active leases

    // EFFECTS: This constructor initializes each newly created rent roll
    public LeaseRecordList() {
        rentRoll = new LinkedList<>();
    }

    // EFFECTS: add the new lease record to the last for the rent roll
    public void addLease(LeaseRecord leaseRecord) {
        rentRoll.addLast(leaseRecord);
    }

    // EFFECTS: get the last lease record
    public LeaseRecord getLastLease() {
        return rentRoll.getLast();
    }

    // EFFECTS: returns an int to represent number of leases in the system
    public int length() {
        return rentRoll.size();
    }



}
