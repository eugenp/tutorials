import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class StringComparisonDemoTest {

    @Test
    @DisplayName("Test that i1 and i2 are equal")
    void testThati1Equali2() {
        assertTrue(StringComparisonDemo.i1.equals(StringComparisonDemo.i2));
    }

    @Test
    @DisplayName("Test i1 and i3 are not equal")
    void testThati1NotEquali3() {
        assertFalse(StringComparisonDemo.i1.equals(StringComparisonDemo.i3));
    }

    @Test
    @DisplayName("Test that contentEquals method compares i1 and 13 correctly ")
    void testThati1Equali3ContentEquals() {
        assertTrue(StringComparisonDemo.i1.contentEquals(StringComparisonDemo.i3));
    }

}