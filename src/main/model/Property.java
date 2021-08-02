package model;

// Represents an property having an id, landlord and lease id
public class Property {
    private int propertyId;                     //property id
    private int nextPropertyId = 1;      // tracks id of next property created
    private String propertyName;                // name of the property
    private String landlordCo;                  // the landlord name
    private boolean isOperating;                // if the property is still in use
    //private String leaseID;                     // lease id

    // EFFECTS: property gets property id number and its landlord name, and is active/operating
    public Property(String name, String landlordName) {
        this.propertyId = nextPropertyId;
        nextPropertyId++;
        this.propertyName = name;
        this.landlordCo = landlordName;
        isOperating = true;
    }

    // EFFECTS: returns property id
    public int getPropertyId() {
        return propertyId;
    }

    // EFFECTS: returns property name
    public String getPropertyName() {
        return propertyName;
    }

    // EFFECTS: returns landlord name
    public String getLandlordCo() {
        return landlordCo;
    }

    // EFFECTS: returns true if the property is operating, false otherwise
    public boolean isOperating() {
        return isOperating;
    }

    // REQUIRES: isOperating()
    // MODIFIES: this
    // EFFECTS: closes the property
    public void close() {
        this.isOperating = false;
    }

    // MODIFIES: this
    // EFFECTS: re-opens the property
    public void reopen() {
        this.isOperating = true;
    }
}
