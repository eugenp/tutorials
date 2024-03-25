import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class UseHashMapToConvertPhoneNumberInWordToNumberUnitTest {

    @Test
    public void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordToNumber_ThenEquivalentNumber(){

        String firstResult = UseHashMapToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five two four quadruple eight");
        assertEquals("5248888", firstResult);

        String secondResult = UseHashMapToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five eight three double triple");
        assertEquals("", secondResult);

        String thirdResult = UseHashMapToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five eight three two four penta eight");
        assertEquals("", thirdResult);
        String fourthResult = UseHashMapToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("one two three four five six seven eight nine zero two");
        assertNull(null, fourthResult);

        String fifthResult = UseHashMapToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five invalid three two four penta eight");
        assertEquals("", fifthResult);
    }

    @Test
    public void givenHashMap_WhenMapModifiers_ThenEquivalentNumber(){
        Map<String, Integer > result = UseHashMapToConvertPhoneNumberInWordToNumber.mapModifiers();
        assertEquals(2, result.get("double"));
        assertEquals(null, result.get("invalid"));
        assertEquals(null, result.get(""));
    }

    @Test
    public void givenHashMap_WhenMapIndividualDigits_ThenEquivalentNumber(){
        Map<String, String> result = UseHashMapToConvertPhoneNumberInWordToNumber.mapIndividualDigits();
        assertEquals("2", result.get("two"));
        assertEquals(null, result.get("invalid"));
        assertEquals(null, result.get(""));
        assertEquals("5", result.get("five"));

    }

    @Test
    public void givenStringWithWhiteSpaces_isValidPhoneNumberFormat_ThenBoolean() {
        boolean firstResult = UseHashMapToConvertPhoneNumberInWordToNumber.isValidPhoneNumberFormat("one two");
        assertEquals(true, firstResult);

        boolean secondResult = UseHashMapToConvertPhoneNumberInWordToNumber.isValidPhoneNumberFormat("two double");
        assertEquals(false, secondResult);

        boolean thirdResult = UseHashMapToConvertPhoneNumberInWordToNumber.isValidPhoneNumberFormat("two three hexa");
        assertEquals(false, thirdResult);

        boolean fourthResult = UseHashMapToConvertPhoneNumberInWordToNumber.isValidPhoneNumberFormat("invalid two triple three");
        assertEquals(false, fourthResult);

    }
}
