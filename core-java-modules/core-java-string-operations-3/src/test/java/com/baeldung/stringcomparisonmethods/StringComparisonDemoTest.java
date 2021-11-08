import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class StringComparisonDemoTest {

    @Test
    @DisplayName("Test that i1 and i2 are equal")
    void givenI1AndI2_ifBothAreStringObjects_thenSuccess() {
        assertEquals(StringComparisonDemo.i1, StringComparisonDemo.i2);
    }

    @Test
    @DisplayName("Test i1 and i3 are not equal")
    void givenI1AndI3_ifBothNotStringObject_thenFail() {
        assertNotEquals(StringComparisonDemo.i1, StringComparisonDemo.i3);
    }

    @Test
    @DisplayName("Test that contentEquals method compares i1 and 13 correctly ")
    void givenI1AndI3_ifBothObjectCharactersAreSame_thenSuccess() {
        assertTrue(StringComparisonDemo.i1.contentEquals(StringComparisonDemo.i3));
    }

}