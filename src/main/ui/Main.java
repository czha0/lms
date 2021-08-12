package ui;

import java.io.FileNotFoundException;

// Main class to run the GUI application
public class Main {
    // EFFECTS: runs the GUI application
    public static void main(String[] args) {
        try {
            new LeaseGui();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }
}