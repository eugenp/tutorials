import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UseSwitchToConvertPhoneNumberInWordToNumberUnitTest {

    @Test
    public void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordToNumber_ThenEquivalentNumber(){

        String firstResult = UseSwitchToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five eight three two four triple eight");
        assertEquals("58324888", firstResult);

        String secondResult = UseSwitchToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five eight three double triple");
        assertEquals("", secondResult);

        String thirdResult = UseSwitchToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five eight three two four penta eight");
        assertEquals("", thirdResult);

        String fourthResult = UseSwitchToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("one two three four five six seven eight nine zero two");
        assertNull(null, fourthResult);
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenMapModifiers_ThenEquivalentNumber() {
        String firstResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapModifiers("double");
        assertEquals("2", firstResult);

        String secondResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapModifiers("two");
        assertNotEquals("2", secondResult);

        String thirdResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapModifiers("");
        assertEquals("-1", thirdResult);

        String fourthResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapModifiers("Invalid");
        assertEquals("-1", fourthResult);

        String fifthResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapModifiers("null");
        assertEquals("-1", fifthResult);
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenMapIndividualDigits_ThenEquivalentNumber() {
        String firstResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapIndividualDigits("five");
        assertEquals("5", firstResult);

        String secondResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapIndividualDigits("penta");
        assertEquals("-1", secondResult);

        String thirdResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapIndividualDigits("Invalid");
        assertEquals("-1", thirdResult);

        String fourthResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapIndividualDigits("null");
        assertEquals("-1", fourthResult);

        String fifthResult = UseSwitchToConvertPhoneNumberInWordToNumber.mapIndividualDigits("");
        assertEquals("-1", fifthResult);
    }

    @Test
    public void givenStringWithWhiteSpaces_isValidPhoneNumberFormat_ThenBoolean() {
        boolean firstResult = UseSwitchToConvertPhoneNumberInWordToNumber.isValidPhoneNumberFormat("one two");
        assertEquals(true, firstResult);

        boolean secondResult = UseSwitchToConvertPhoneNumberInWordToNumber.isValidPhoneNumberFormat("two double");
        assertEquals(false, secondResult);

        boolean thirdResult = UseSwitchToConvertPhoneNumberInWordToNumber.isValidPhoneNumberFormat("two three hexa");
        assertEquals(false, thirdResult);

        boolean fourthResult = UseSwitchToConvertPhoneNumberInWordToNumber.isValidPhoneNumberFormat("invalid two triple three");
        assertEquals(false, fourthResult);

    }

}