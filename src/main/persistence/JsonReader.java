package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads lease records from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads lease records from file and returns it;
    // throws IOException if an error occurs reading data from file
    public LeaseRecordList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeaseRecordList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses leaseRecordList from JSON object and returns it
    private LeaseRecordList parseLeaseRecordList(JSONObject jsonObject) {
        String companyName = jsonObject.getString("companyName");
        LeaseRecordList lrl = new LeaseRecordList(companyName);
        addLeaseRecords(lrl, jsonObject);
        return lrl;
    }

    // MODIFIES: lrl
    // EFFECTS: parses leaseRecords from JSON object and adds them to leaseRecordList
    private void addLeaseRecords(LeaseRecordList lrl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("records");
        for (Object json : jsonArray) {
            JSONObject nextLeaseRecord = (JSONObject) json;
            addLeaseRecord(lrl, nextLeaseRecord);
        }
    }

    // MODIFIES: lrl
    // EFFECTS: parses leaseRecord from JSON object and adds it to leaseRecordList
    private void addLeaseRecord(LeaseRecordList lrl, JSONObject jsonObject) {
        int leaseID = jsonObject.getInt("leaseID");
        int leasePropId = jsonObject.getInt("propID");
        Category category = Category.valueOf(jsonObject.getString("category"));
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        int monthlyPmt = jsonObject.getInt("monthlyPay");
        boolean isCurrent = jsonObject.getBoolean("isCurrent");
        LeaseRecord leaseRecord = new LeaseRecord(leaseID,leasePropId,category,startDate,endDate,monthlyPmt);
        lrl.addLease(leaseRecord);
    }
}