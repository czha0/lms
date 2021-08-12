package model;

import model.exceptions.NoSufficientFundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// Represents properties with active leases
public class LeaseRecordList implements Writable {
    private String companyName;           // company name
    private List<LeaseRecord> rentRoll;   // a list of Rent Roll for active leases
    private int totalMonthlyPay;          // total monthly rent payment
    private int currentFunds;


    // EFFECTS: This constructor initializes each newly created rent roll
    public LeaseRecordList(String companyName) {
        this.companyName = companyName;
        rentRoll = new ArrayList<>();
        this.currentFunds = 0;
    }

    //REQUIRES: initial balance of funds available
    //EFFECTS: rent roll would add deposit amount into current funds balance
    public void addDeposit(int depositAmount) {
        this.currentFunds = depositAmount;
    }

    // EFFECTS: returns total monthly payment in the rent roll
    public int totalPayment() {
        this.totalMonthlyPay = 0;
        for (LeaseRecord lr: rentRoll) {
            this.totalMonthlyPay = totalMonthlyPay + lr.getRentAmt();
        }
        return totalMonthlyPay;
    }

    // EFFECTS: check if current funds can cover payment and update current fund when paid
    public boolean payMonthlyRent() throws NoSufficientFundException {
        this.totalPayment();
        if (currentFunds >= this.getTotalMonthlyPay()) {
            this.currentFunds = currentFunds - this.getTotalMonthlyPay();
            return true;
        } else {
            throw new NoSufficientFundException();
        }

    }

    // EFFECTS: returns total monthly payment
    public int getTotalMonthlyPay() {
        return totalMonthlyPay;
    }

    // EFFECTS: returns current funds
    public int getCurrentFunds() {
        return currentFunds;
    }

    // EFFECTS: returns name of this LeaseList
    public String getCompanyName() {
        return companyName;
    }

    // EFFECTS: add the new lease record to the last for the rent roll
    public void addLease(LeaseRecord leaseRecord) {
        rentRoll.add(leaseRecord);
    }

    // EFFECTS: returns an unmodifiable list of thingies in this lease record
    public List<LeaseRecord> getLeaseRecords() {
        return Collections.unmodifiableList(rentRoll);
    }

    // EFFECTS: returns an int to represent number of leases in the system
    public int length() {
        return rentRoll.size();
    }

    // EFFECTS: define JSON fields
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("companyName", companyName);
        json.put("records", recordsToJson());
        return json;
    }

    // EFFECTS: returns rentRoll in this leaseRecordList as a JSON array
    private JSONArray recordsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (LeaseRecord lr : rentRoll) {
            jsonArray.put(lr.toJson());
        }

        return jsonArray;
    }

}
