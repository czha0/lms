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


// Runs Lease App called from Main Class
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
    private JButton saveButton;
    private JButton clearButton;
    private JButton loadButton;
    private JButton newButton;
    private JButton printButton;
    private JButton addButton;

    // variables used for JSON save data
    private static String JSON_STORE = "./data/rentRoll.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String companyName = "ABC Company";
    private LeaseRecord inputLeaseRecord;
    private LeaseRecordList uiLeaseRecordList;
    int inputLeaseId;
    int inputPropId;
    Category inputCategory;
    int inputRent;
    String inputStartDate;
    String inputEndDate;


    // EFFECTS: constructs Leases and runs application
    public LeaseGui() throws FileNotFoundException {
        uiLeaseRecordList = new LeaseRecordList(companyName);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        leaseGui();
    }

    // EFFECTS: constructs GUI with frame, panel, label, texts and buttons
    public void leaseGui() throws FileNotFoundException {
        frame = new JFrame("Lease Management System");
        panel = new JPanel();
        frame.setSize(600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);

        panel.setLayout(null);

        leaseLabelSetUp();
        leaseLabelDetailSetUp();
        buttonSetUp();
        buttonJsonSetUp();

        frame.setVisible(true);
    }

    // EFFECTS: Setup Buttons on the bottom of GUI
    private void buttonSetUp() {
        // add record button
        addButton = new JButton("Add Record");
        addButton.setBounds(10,200,100,25);
        addButton.addActionListener(this::actionPerformed);
        panel.add(addButton);

        // clear input button
        clearButton = new JButton("Clear Input");
        clearButton.setBounds(150,200,100,25);
        clearButton.addActionListener(this::actionPerformed);
        panel.add(clearButton);
    }

    // EFFECTS: Setup Buttons on the bottom of GUI for JSON related functions
    private void buttonJsonSetUp() {
        // save data button
        saveButton = new JButton("Save Data");
        saveButton.setBounds(250, 200, 100, 25);
        saveButton.addActionListener(this::actionPerformed);
        panel.add(saveButton);

        // load data button
        loadButton = new JButton("Load Data");
        loadButton.setBounds(250, 230, 100, 25);
        loadButton.addActionListener(this::actionPerformed);
        panel.add(loadButton);

        // delete all data button
        newButton = new JButton("Delete All Data");
        newButton.setBounds(350, 200, 150, 25);
        newButton.addActionListener(this::actionPerformed);
        panel.add(newButton);

        // print all button
        printButton = new JButton("Print Rent Roll");
        printButton.setBounds(350, 230, 150, 25);
        printButton.addActionListener(this::actionPerformed);
        panel.add(printButton);
    }

    // EFFECTS: Set up labels for lease record details
    private void leaseLabelDetailSetUp() {
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
    private void leaseLabelSetUp() {
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

    // EFFECTS: Set button to act respective methods when clicked upon
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveData();
        } else if (e.getSource() == clearButton) {
            setAllClear();
        } else if (e.getSource() == addButton) {
            addRecord();
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

    // EFFECTS: add current lease record the leaseRecords (rentRoll)
    public void addRecord() {
        this.getTextFields();
        inputLeaseRecord = new LeaseRecord(inputLeaseId, inputPropId,inputCategory,inputStartDate,
                inputEndDate,inputRent);
        uiLeaseRecordList.addLease(inputLeaseRecord);
        setAllClear();
    }

    // EFFECTS: saves the leaseRecords (rentRoll) to file
    public void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(uiLeaseRecordList);
            jsonWriter.close();
            System.out.println("Saved this lease to " + JSON_STORE);
            JOptionPane.showMessageDialog(null,"Saved "
                    + uiLeaseRecordList.getCompanyName() + " to " + JSON_STORE);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            JOptionPane.showMessageDialog(null,"Unable to write to file: " + JSON_STORE);
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
            JOptionPane.showMessageDialog(null,"Loaded "
                    + uiLeaseRecordList.getCompanyName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            JOptionPane.showMessageDialog(null,"Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints all the leaseRecords (rentRoll) in rentRoll to the console
    private void printRentRoll() {
        List<LeaseRecord> rentRoll = uiLeaseRecordList.getLeaseRecords();
        JOptionPane.showMessageDialog(null,"Printing Data on Console");
        System.out.println("Printing Data on Console");
        for (LeaseRecord lr : rentRoll) {
            System.out.println(lr);
        }
    }

//    // EFFECTS: initialize text print
//    public void setScreenPrinter() {
//        // Set the contents of the JTextArea.
//        printArea = new JTextArea();
//        String text = "Showing Console data.";
//        printArea.setText(text);
//        printArea.setLineWrap(true);
//        printArea.setWrapStyleWord(true);
//
//        JScrollPane scrollPane = new JScrollPane(printArea);
//        scrollPane.setPreferredSize(new Dimension(500, 200));
//        printArea.setEditable(false);
//        scrollPane.setVerticalScrollBarPolicy(
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//    }

}
