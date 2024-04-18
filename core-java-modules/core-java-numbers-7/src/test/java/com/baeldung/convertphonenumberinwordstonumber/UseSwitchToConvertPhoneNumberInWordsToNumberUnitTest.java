import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UseSwitchToConvertPhoneNumberInWordsToNumberUnitTest {

    @Test
    void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordsToNumber_ThenEquivalentNumber() {

        assertEquals("5248888",
                UseSwitchToConvertPhoneNumberInWordsToNumber
                        .convertPhoneNumberInWordsToNumber("five two four quadruple eight"));

    }

    @Test
    void givenStringEndingWithConseutiveMultipliers_WhenConvertPhoneNumberInWordsToNumber_ThenThrowException() {

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordsToNumber
                    .convertPhoneNumberInWordsToNumber("five eight three double triple");
        });
    }

    @Test
    void givenStringWithInvalidWords_WhenConvertPhoneNumberInWordsToNumber_ThenThrowException() {

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordsToNumber
                    .convertPhoneNumberInWordsToNumber("five eight three two four penta null eight");
        });
    }


    @Test
    void givenString_WhenGetWordAsMultiplier_ThenEquivalentNumber() {
        assertEquals(2, UseSwitchToConvertPhoneNumberInWordsToNumber
                .getWordAsMultiplier("double"));
    }

    @Test
    void givenInvalidString_WhenGetWordAsMultiplier_ThenReturnNull() {
        assertEquals(null, UseSwitchToConvertPhoneNumberInWordsToNumber
                .getWordAsMultiplier("hexa"));

    }

    @Test
    void givenString_WhenMapIndividualDigits_ThenEquivalentNumber() {
        assertEquals("5",
                UseSwitchToConvertPhoneNumberInWordsToNumber
                        .getWordAsDigit("five"));
    }

    @Test
    void givenInvalidString_WhenMapIndividualDigits_ThenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordsToNumber
                    .convertPhoneNumberInWordsToNumber("penta");
        });
    }
}