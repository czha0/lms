package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PropertyTest {
    private Property testProperty;
    private Property testPropertyB;

    @BeforeEach
    public void setUp() {
        testProperty = new Property("PropertyA", "LandlordA");
    }

    @Test
    void testConstructor() {
        assertEquals("PropertyA", testProperty.getPropertyName());
        assertEquals("LandlordA",testProperty.getLandlordCo());
        assertTrue(testProperty.isOperating());
        testPropertyB = new Property("PropertyB","LandlordB");
    }

    @Test
    void testPropertyOperatingStatus() {
        testProperty.close();
        assertFalse(testProperty.isOperating());

        testProperty.reopen();
        assertTrue(testProperty.isOperating());
    }


}
