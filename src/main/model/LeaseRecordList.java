package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// Represents properties with active leases
public class LeaseRecordList implements Writable {
    private String companyName;                        // company name
    private List<LeaseRecord> rentRoll;   // a list of Rent Roll for active leases

    // EFFECTS: This constructor initializes each newly created rent roll
    public LeaseRecordList(String companyName) {
        this.companyName = companyName;
        rentRoll = new ArrayList<>();
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
