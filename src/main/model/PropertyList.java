package model;

import java.util.LinkedList;

// Represents a set of properties
public class PropertyList {

    private LinkedList<Property> propertyList;   // a list of Rent Roll for active leases

    // EFFECTS: This constructor initializes each newly created rent roll
    public PropertyList() {
        propertyList = new LinkedList<>();
    }

    // EFFECTS: add the new property to the last for the rent roll
    public void addProperty(Property property) {
        propertyList.addLast(property);
    }

    // EFFECTS: get the last property added into the record
    public Property getLastProperty() {
        return propertyList.getLast();
    }

    // EFFECTS: returns an int to represent number of properties in the system
    public int length() {
        return propertyList.size();
    }
}
