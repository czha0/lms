package ui;

import java.io.FileNotFoundException;

// NOTE:
// Please run GUI from ./ui/LeaseGui

public class Main {
    public static void main(String[] args) {
        try {
            new LeaseApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
//        new LeaseGui();
    }
}