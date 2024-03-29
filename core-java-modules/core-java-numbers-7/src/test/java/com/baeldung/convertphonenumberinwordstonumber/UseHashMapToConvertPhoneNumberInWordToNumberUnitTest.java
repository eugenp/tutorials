import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UseHashMapToConvertPhoneNumberInWordToNumberUnitTest {

    @Test
    void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordToNumber_ThenEquivalentNumber() {

        assertEquals("5248888",
                UseHashMapToConvertPhoneNumberInWordsToNumber
                        .convertPhoneNumberInWordToNumber("five two four quadruple eight"));

        assertThrows(IllegalArgumentException.class, () -> {
            UseHashMapToConvertPhoneNumberInWordsToNumber
                    .convertPhoneNumberInWordToNumber("five eight three double triple");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            UseHashMapToConvertPhoneNumberInWordsToNumber
                    .convertPhoneNumberInWordToNumber("five eight three two four penta eight");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            UseHashMapToConvertPhoneNumberInWordsToNumber
                    .convertPhoneNumberInWordToNumber("five eight invalid two four null eight");
        });
    }
}