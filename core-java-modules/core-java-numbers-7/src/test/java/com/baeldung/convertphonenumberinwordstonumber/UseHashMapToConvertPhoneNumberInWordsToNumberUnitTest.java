import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UseHashMapToConvertPhoneNumberInWordsToNumberUnitTest {

    @Test
    void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordsToNumber_ThenEquivalentNumber() {

        assertEquals("5248888",
                UseHashMapToConvertPhoneNumberInWordsToNumber
                        .convertPhoneNumberInWordsToNumber("five two four quadruple eight"));
    }

    @Test
    void givenStringEndingWithConseutiveMultipliers_WhenConvertPhoneNumberInWordsToNumber_ThenThrowException() {

        assertThrows(IllegalArgumentException.class, () -> {
            UseHashMapToConvertPhoneNumberInWordsToNumber
                    .convertPhoneNumberInWordsToNumber("five eight three double triple");
        });
    }

    @Test
    void givenStringWithInvalidWords_WhenConvertPhoneNumberInWordsToNumber_ThenThrowException() {

        assertThrows(IllegalArgumentException.class, () -> {
            UseHashMapToConvertPhoneNumberInWordsToNumber
                    .convertPhoneNumberInWordsToNumber("five eight three two four penta null eight");
        });
    }
}