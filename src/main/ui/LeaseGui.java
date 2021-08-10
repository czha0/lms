package ui;

import model.Category;
import model.LeaseRecord;
import model.LeaseRecordList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LeaseGui implements ActionListener {
    // variables used for GUI
    // idea from https://www.youtube.com/watch?v=Hiv3gwJC5kw
    private static JPanel panel;
    private static JFrame frame;
    private static JLabel leaseIdLabel;
    private static JTextField leaseIdText;
    private static JLabel propertyIdLabel;
    private static JTextField propertyIdText;
    private static JLabel categoryLabel;
    private static JTextField categoryText;
    private static JLabel monthlyPayLabel;
    private static JTextField monthlyPayText;
    private static JLabel startLabel;
    private static JTextField startText;
    private static JLabel endLabel;
    private static JTextField endText;
    private static JButton saveButton;
    private static JButton clearButton;
    private static JButton loadButton;
    private static JButton newButton;
    private static JButton printButton;

    // variables used for JSON save data
    private static String JSON_STORE = "./data/rentRoll.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;
    private String companyName = "ABC Company";     // company name
    private LeaseRecord inputLeaseRecord;
    private LeaseRecordList uiLeaseRecordList;
    int inputLeaseId;
    int inputPropId;
    Category inputCategory;
    int inputRent;
    String inputStartDate;
    String inputEndDate;


    public static void main(String[] args) {
        leaseGui();
    }

    public static void leaseGui() {
        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);

        panel.setLayout(null);

        leaseLabelSetUp();
        leaseLabelDetailSetUp();
        buttonSetUp();

        frame.setVisible(true);
    }

    // EFFECTS: Setup Buttons on the bottom of GUI
    private static void buttonSetUp() {
        saveButton = new JButton("Add Record");
        saveButton.setBounds(10,200,100,25);
        saveButton.addActionListener(new LeaseGui());
        panel.add(saveButton);

        clearButton = new JButton("Clear Data");
        clearButton.setBounds(150,200,100,25);
        clearButton.addActionListener(new LeaseGui());
        panel.add(clearButton);

        loadButton = new JButton("Load Data");
        loadButton.setBounds(250,200,100,25);
        loadButton.addActionListener(new LeaseGui());
        panel.add(loadButton);

        newButton = new JButton("New Rent Roll");
        newButton.setBounds(350,200,150,25);
        newButton.addActionListener(new LeaseGui());
        panel.add(newButton);

        printButton = new JButton("Print Rent Roll");
        printButton.setBounds(350,230,150,25);
        printButton.addActionListener(new LeaseGui());
        panel.add(printButton);
    }

    // EFFECTS: Set up labels for lease record details
    private static void leaseLabelDetailSetUp() {
        // lease monthly payment field set up
        monthlyPayLabel = new JLabel("Monthly Rent");
        monthlyPayLabel.setBounds(10,70,100,25);
        panel.add(monthlyPayLabel);

        monthlyPayText = new JTextField(5);
        monthlyPayText.setBounds(100,70,165,25);
        panel.add(monthlyPayText);

        // lease start date field set up
        startLabel = new JLabel("Lease Start");
        startLabel.setBounds(10,90,100,25);
        panel.add(startLabel);

        startText = new JTextField(5);
        startText.setBounds(100,90,165,25);
        panel.add(startText);

        // lease end date field set up
        endLabel = new JLabel("Lease End");
        endLabel.setBounds(320,90,100,25);
        panel.add(endLabel);

        endText = new JTextField(5);
        endText.setBounds(400,90,165,25);
        panel.add(endText);
    }

    // EFFECTS: Set up labels for lease record
    private static void leaseLabelSetUp() {
        // lease ID field set up
        leaseIdLabel = new JLabel("Lease ID");
        leaseIdLabel.setBounds(10,10,100,25);
        panel.add(leaseIdLabel);

        leaseIdText = new JTextField(5);
        leaseIdText.setBounds(100,10,165,25);
        panel.add(leaseIdText);

        // property ID field set up
        propertyIdLabel = new JLabel("Property ID");
        propertyIdLabel.setBounds(10,30,100,25);
        panel.add(propertyIdLabel);

        propertyIdText = new JTextField(5);
        propertyIdText.setBounds(100,30,165,25);
        panel.add(propertyIdText);

        // lease category field set up
        categoryLabel = new JLabel("Category");
        categoryLabel.setBounds(10,50,100,25);
        panel.add(categoryLabel);

        categoryText = new JTextField(5);
        categoryText.setBounds(100,50,165,25);
        panel.add(categoryText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveData();
            System.out.println("Saved");
            JLabel msgFeedback = new JLabel("");
            msgFeedback.setBounds(10,250,600,600);
            panel.add(msgFeedback);
            msgFeedback.setText("Lease Record Added.");
        } else if (e.getSource() == clearButton) {
            setAllClear();
        } else if (e.getSource() == newButton) {
            setNewCompany();
        } else if (e.getSource() == printButton) {
            printRentRoll();
        } else if (e.getSource() == loadButton) {
            loadLeaseRecords();
        }
    }


    // EFFECTS: Fetch Text and pull the data into variables
    public void getTextFields() {
        this.inputLeaseId = Integer.parseInt(leaseIdText.getText());
        this.inputPropId = Integer.parseInt(propertyIdText.getText());
        this.inputCategory = Category.MIXED;
        this.inputRent = Integer.parseInt(monthlyPayText.getText());
        this.inputStartDate = startText.getText();
        this.inputEndDate = endText.getText();
    }

    // EFFECTS: Set ALL fields to empty
    public void setAllClear() {
        leaseIdText.setText("");
        propertyIdText.setText("");
        categoryText.setText("");
        monthlyPayText.setText("");
        startText.setText("");
        endText.setText("");

    }

    // EFFECTS: saves the leaseRecords (rentRoll) to file
    public void saveData() {
        uiLeaseRecordList = new LeaseRecordList(companyName);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        this.getTextFields();
        inputLeaseRecord = new LeaseRecord(inputLeaseId, inputPropId,inputCategory,inputStartDate,
                inputEndDate,inputRent);
        uiLeaseRecordList.addLease(inputLeaseRecord);

        try {
            jsonWriter.open();
            jsonWriter.write(uiLeaseRecordList);
            jsonWriter.close();
            System.out.println("Saved " + uiLeaseRecordList.getCompanyName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void setNewCompany() {
        uiLeaseRecordList = new LeaseRecordList(companyName);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }


    // MODIFIES: this
    // EFFECTS: loads leaseRecords (rentRoll) from file
    private void loadLeaseRecords() {
        uiLeaseRecordList = new LeaseRecordList(companyName);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        try {
            this.uiLeaseRecordList = jsonReader.read();
            System.out.println("Loaded " + uiLeaseRecordList.getCompanyName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints all the leaseRecords (rentRoll) in rentRoll to the console
    private void printRentRoll() {
        loadLeaseRecords();
        List<LeaseRecord> rentRoll = uiLeaseRecordList.getLeaseRecords();

        for (LeaseRecord lr : rentRoll) {
            System.out.println(lr);
        }
    }
}
