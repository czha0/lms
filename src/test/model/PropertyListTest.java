package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PropertyListTest {

    // Variables for the  test
    private PropertyList testPropertyList;
    private Property testPropertyA;
    private Property testPropertyB;
    private Property testPropertyC;

    @BeforeEach
    public void setUp() {
        testPropertyList = new PropertyList();
        testPropertyA = new Property("PropertyA","LandlordA");
        testPropertyB = new Property("PropertyB","LandlordB");
        testPropertyC = new Property("PropertyC","LandlordC");
    }

    @Test
    void testConstructor() {
        testPropertyList.addProperty(testPropertyA);
        testPropertyList.addProperty(testPropertyB);
        testPropertyList.addProperty(testPropertyC);

        assertEquals(testPropertyC,testPropertyList.getLastProperty());
        assertEquals(3,testPropertyList.length());
    }

}
