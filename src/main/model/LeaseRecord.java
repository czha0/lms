package model;

// Represents lease records having an unique id, start date, end date, if the lease id active
public class LeaseRecord {

    private int leaseId;                    // lease id
    private int leasePropId;                // property id for the lease
    private static int nextLeaseId = 1;     // tracks id of next lease documented
    private String startDate;               // lease starting date
    private String endDate;                 // lease starting date
    private boolean isCurrent;              // if the lease is current
    private int monthlyPmt;                 // monthly lease payment

    //REQUIRES: existing property id, start and end date in dd/MM/yyyy format
    //EFFECTS: lease record gets lease id and its details
    public LeaseRecord(int propId, String beginDate, String endDate, int payment) {
        this.leaseId = nextLeaseId++;
        this.leasePropId = propId;
        this.startDate = beginDate;
        this.endDate = endDate;
        this.monthlyPmt = payment;
        isCurrent = true;
    }


    // EFFECTS: returns lease id
    public int getLeaseId() {
        return leaseId;
    }

    // EFFECTS: returns lease property id
    public int getLeasePropId() {
        return leasePropId;
    }

    // EFFECTS: returns lease start date
    public String getStartDate() {
        return startDate;
    }

    // EFFECTS: returns lease end date
    public String getEndDate() {
        return endDate;
    }

    // EFFECTS: returns monthly rent amount for the lease
    public int getRentAmt() {
        return monthlyPmt;
    }

    // EFFECTS: returns true if the lease is active now, false otherwise
    public boolean isCurrent() {
        return isCurrent;
    }

    // REQUIRES: isCurrent()
    // MODIFIES: this
    // EFFECTS: set lease is inactive
    public void setLeaseInactive() {
        this.isCurrent = false;
    }

    // MODIFIES: this
    // EFFECTS: set the lease to active
    public void setLeaseActive() {
        this.isCurrent = true;
    }
}
