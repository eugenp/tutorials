import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class UseHashMapToConvertPhoneNumberInWordToNumberUnitTest {

    @Test
    public void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordToNumber_ThenEquivalentNumber() {

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


    @Test
    public void givenNothing_WhenGetWordAsMultiplier_ThenEquivalentNumber() {
        Map<String, Integer> result = UseHashMapToConvertPhoneNumberInWordsToNumber.getWordAsMultiplier();
        assertEquals(2, result.get("double"));
        assertEquals(null, result.get("invalid"));
        assertEquals(null, result.get(""));
        assertEquals(null, result.get("deca"));
    }

    @Test
    public void givenNothing_WhenGetWordAsDigit_ThenEquivalentNumber(){
        Map<String, String> result = UseHashMapToConvertPhoneNumberInWordsToNumber.getWordAsDigit();
        assertEquals("2", result.get("two"));
        assertEquals(null, result.get("invalid"));
        assertEquals(null, result.get(""));
        assertEquals("5", result.get("five"));
        assertEquals(null, result.get("twelve"));

    }
}
