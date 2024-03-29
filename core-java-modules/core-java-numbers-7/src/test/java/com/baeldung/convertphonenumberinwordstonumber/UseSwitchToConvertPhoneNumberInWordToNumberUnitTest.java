import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UseSwitchToConvertPhoneNumberInWordToNumberUnitTest {

    @Test
    void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordToNumber_ThenEquivalentNumber(){

        assertEquals("5248888",
                UseSwitchToConvertPhoneNumberInWordToNumber
                        .convertPhoneNumberInWordToNumber("five two four quadruple eight"));

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordToNumber
                    .convertPhoneNumberInWordToNumber("five eight three double triple");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordToNumber
                    .convertPhoneNumberInWordToNumber("five eight three two four penta eight");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordToNumber
                    .convertPhoneNumberInWordToNumber("five eight invalid two four null eight");
        });

    }

    @Test
    void givenString_WhenGetWordAsMultiplier_ThenEquivalentNumber() {
        assertEquals(2, UseSwitchToConvertPhoneNumberInWordToNumber
                .getWordAsMultiplier("double"));

        assertEquals(null, UseSwitchToConvertPhoneNumberInWordToNumber
                .getWordAsMultiplier("hexa"));

    }

    @Test
    void givenString_WhenMapIndividualDigits_ThenEquivalentNumber() {
          assertEquals("5",
                  UseSwitchToConvertPhoneNumberInWordToNumber
                          .getWordAsDigit("five"));

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordToNumber
                    .convertPhoneNumberInWordToNumber("penta");
        });
    }
}