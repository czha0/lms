package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PropertyTest {
    private Property testProperty;

    @BeforeEach
    public void setUp() {
        testProperty = new Property("Thunderbird Stadium", "UBC Property Trust");
    }

    @Test
    void testConstructor() {
        assertEquals("Thunderbird Stadium", testProperty.getPropertyName());
        assertEquals(1,testProperty.getPropertyId());
        assertEquals("UBC Property Trust",testProperty.getLandlordCo());
        assertTrue(testProperty.isOperating());
    }
}
