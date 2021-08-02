package ui;

import model.Category;
import model.LeaseRecord;
import model.LeaseRecordList;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LeaseApp {
    private static final String JSON_STORE = "./data/rentRoll.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;
    private LeaseRecord inputLeaseRecord;
    private LeaseRecordList uiLeaseRecordList;

    // EFFECTS: constructs workroom and runs application
    public LeaseApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        uiLeaseRecordList = new LeaseRecordList("ABC Co's Lease Record");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runLeaseApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // utilized TellerApp's ui idea
    public void runLeaseApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        System.out.println("Welcome to Lease Management System");

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\n Thank you for using the system!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addLeaseRecord();
        } else if (command.equals("p")) {
            printLeaseRecords();
        } else if (command.equals("s")) {
            saveLeaseRecords();
        } else if (command.equals("l")) {
            loadLeaseRecords();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the system
    private void init() {
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a lease record");
        System.out.println("\tp -> print rent roll");
        System.out.println("\ts -> save rent roll to file");
        System.out.println("\tl -> load rent roll from file");
        System.out.println("\tq -> quit");
    }


    // EFFECTS: add Lease object with user input
    private void addLeaseRecord() {
        System.out.println("You are creating a new lease record... \n");
        Category category = readCategory();
        System.out.println("Please enter the lease/contract id (only integer)...");
        int inputLeaseId;
        inputLeaseId = Integer.parseInt(input.next());
        System.out.println("Please enter the property id (only integer)...");
        int inputPropID;
        inputPropID = Integer.parseInt(input.next());
        System.out.println("Please enter the lease start time (dd/MM/yyy) ...");
        String inputBeginDate;
        inputBeginDate = input.next();
        System.out.println("Please enter the lease end time (dd/MM/yyy) ...");
        String inputEndDate;
        inputEndDate = input.next();
        System.out.println("Please enter monthly rent");
        int inputPmt;
        inputPmt = Integer.parseInt(input.next());
        System.out.println("Processing your new lease addition...");
        inputLeaseRecord = new LeaseRecord(inputLeaseId,inputPropID,
                category,inputBeginDate,inputEndDate,inputPmt);
        uiLeaseRecordList = new LeaseRecordList("ABC Company");
        uiLeaseRecordList.addLease(inputLeaseRecord);
        confirmLeaseDetails();
    }

    // EFFECTS: prompts user to select category and returns it
    private Category readCategory() {
        System.out.println("Please select a category for the lease");

        int menuLabel = 1;
        for (Category c : Category.values()) {
            System.out.println(menuLabel + ": " + c);
            menuLabel++;
        }

        int menuSelection = input.nextInt();
        return Category.values()[menuSelection - 1];
    }

    // EFFECTS: print out lease details when input data from keyboard
    private void confirmLeaseDetails() {
        System.out.println("Following lease added:");
        System.out.println("Lease type: " + inputLeaseRecord.getCategory());
        System.out.println("Lease ID: " + inputLeaseRecord.getLeaseId());
        System.out.println("Property ID: " + inputLeaseRecord.getLeasePropId());
        System.out.println("Lease period: " + inputLeaseRecord.getStartDate() + " to " + inputLeaseRecord.getEndDate());
        System.out.println("Monthly Rent Amount is $" + inputLeaseRecord.getRentAmt());
        System.out.println("Task completed.");
    }

    // EFFECTS: prints all the leaseRecords (rentRoll) in rentRoll to the console
    private void printLeaseRecords() {
        List<LeaseRecord> rentRoll = uiLeaseRecordList.getLeaseRecords();

        for (LeaseRecord lr : rentRoll) {
            System.out.println(lr);
        }
    }

    // EFFECTS: saves the leaseRecords (rentRoll) to file
    private void saveLeaseRecords() {
        try {
            jsonWriter.open();
            jsonWriter.write(uiLeaseRecordList);
            jsonWriter.close();
            System.out.println("Saved " + uiLeaseRecordList.getCompanyName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads leaseRecords (rentRoll) from file
    private void loadLeaseRecords() {
        try {
            uiLeaseRecordList = jsonReader.read();
            System.out.println("Loaded " + uiLeaseRecordList.getCompanyName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
