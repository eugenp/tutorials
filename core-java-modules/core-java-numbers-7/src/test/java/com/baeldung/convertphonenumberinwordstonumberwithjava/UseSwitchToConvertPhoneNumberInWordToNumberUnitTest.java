import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UseSwitchToConvertPhoneNumberInWordToNumberUnitTest {

    @Test
    public void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordToNumber_ThenEquivalentNumber(){

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
    public void givenString_WhenGetWordAsMultiplier_ThenEquivalentNumber() {
        assertEquals(2, UseSwitchToConvertPhoneNumberInWordToNumber
                .getWordAsMultiplier("double"));

        assertEquals(null, UseSwitchToConvertPhoneNumberInWordToNumber
                .getWordAsMultiplier("two"));

        assertEquals(null, UseSwitchToConvertPhoneNumberInWordToNumber
                .getWordAsMultiplier(""));

        assertEquals(null, UseSwitchToConvertPhoneNumberInWordToNumber
                .getWordAsMultiplier("invalid"));

        assertEquals(null, UseSwitchToConvertPhoneNumberInWordToNumber
                .getWordAsMultiplier("null"));

        assertEquals(null, UseSwitchToConvertPhoneNumberInWordToNumber
                .getWordAsMultiplier("hexa"));

    }

    @Test
    public void givenString_WhenMapIndividualDigits_ThenEquivalentNumber() {
          assertEquals("5",
                  UseSwitchToConvertPhoneNumberInWordToNumber
                          .getWordAsDigit("five"));

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordToNumber
                    .convertPhoneNumberInWordToNumber("penta");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordToNumber
                    .convertPhoneNumberInWordToNumber("twelve");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordToNumber
                    .convertPhoneNumberInWordToNumber("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordToNumber
                    .convertPhoneNumberInWordToNumber("invalid");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            UseSwitchToConvertPhoneNumberInWordToNumber
                    .convertPhoneNumberInWordToNumber("null");
        });
    }
}
