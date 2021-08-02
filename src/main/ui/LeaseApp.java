package ui;

import model.LeaseRecord;
import model.LeaseRecordList;
import model.Property;
import model.PropertyList;

import java.util.Locale;
import java.util.Scanner;

public class LeaseApp {
    private Scanner input;
    private Property inputProperty;
    private LeaseRecord inputLeaseRecord;
    private PropertyList uiPropertyList;
    private LeaseRecordList uiLeaseRecordList;

    // EFFECTS: runs the Lease Management application
    public LeaseApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // utilized TellerApp's ui idea
    public void runApp() {
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
            addProperty();
        } else if (command.equals("c")) {
            addLeaseRecord();
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
        System.out.println("\ta -> add a property");
        System.out.println("\tc -> add a lease");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: add Property object with user input
    private void addProperty() {
        System.out.println("You are creating a new property... \n");
        System.out.println("Please enter the property name...");
        String inputPropertyName;
        inputPropertyName = input.next();
        System.out.println("Please enter the landlord name...");
        String inputLandlordName;
        inputLandlordName = input.next();
        System.out.println("Processing your new property addition...");
        inputProperty = new Property(inputPropertyName,inputLandlordName);
        uiPropertyList = new PropertyList();
        uiPropertyList.addProperty(inputProperty);
        System.out.println("Following property added:");
        System.out.println("Property: " + inputProperty.getPropertyName());
        System.out.println("Landlord: " + inputProperty.getLandlordCo());
        System.out.println("Task completed.");
    }

    // EFFECTS: add Lease object with user input
    private void addLeaseRecord() {
        System.out.println("You are creating a new lease record... \n");
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
        inputLeaseRecord = new LeaseRecord(inputLeaseId,inputPropID,inputBeginDate,inputEndDate,inputPmt);
        uiLeaseRecordList = new LeaseRecordList();
        uiLeaseRecordList.addLease(inputLeaseRecord);
        confirmLeaseDetails();
    }

    private void confirmLeaseDetails() {
        System.out.println("Following lease added:");
        System.out.println("Lease ID: " + inputLeaseRecord.getLeaseId());
        System.out.println("Property ID: " + inputLeaseRecord.getLeasePropId());
        System.out.println("Lease period: " + inputLeaseRecord.getStartDate() + " to " + inputLeaseRecord.getEndDate());
        System.out.println("Monthly Rent Amount is $" + inputLeaseRecord.getRentAmt());
        System.out.println("Task completed.");
    }
}
