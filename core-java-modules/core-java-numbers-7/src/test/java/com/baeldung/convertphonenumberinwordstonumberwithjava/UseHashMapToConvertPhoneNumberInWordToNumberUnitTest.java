package com.baeldung.convertphonenumberinwordstonumberwithjava;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UseHashMapToConvertPhoneNumberInWordToNumberUnitTest {

    @Test
    public void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordToNumber_ThenEquivalentNumber(){

        String firstResult = UseHashMapToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five eight three two four triple eight");
        assertEquals("58324888", firstResult);

        String secondResult = UseHashMapToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("");
        assertNotEquals(-1, secondResult);

        String thirdResult = UseHashMapToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five invalid three two four penta eight");
        assertNotEquals("5832488888", thirdResult);

        String fourthResult = UseHashMapToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("one two three four five six seven eight nine zero");
        assertEquals("1234567890", fourthResult);
    }

    @Test
    public void givenHashMap_WhenMapModifiers_ThenEquivalentNumber(){
        Map<String, Integer> modifiers = new HashMap<>();
        Map<String, Integer> result = UseHashMapToConvertPhoneNumberInWordToNumber.mapModifiers(modifiers);
        assertEquals(2, result.get("double"));
        assertEquals(null, result.get("invalid"));
        assertEquals(null, result.get(""));
        assertEquals(5, result.get("penta"));

    }

    @Test
    public void givenHashMap_WhenMapIndividualDigits_ThenEquivalentNumber(){
        Map<String, Integer> digits = new HashMap<>();
        Map<String, Integer> result = UseHashMapToConvertPhoneNumberInWordToNumber.mapIndividualDigits(digits);
        assertEquals(2, result.get("two"));
        assertEquals(null, result.get("invalid"));
        assertEquals(null, result.get(""));
        assertEquals(5, result.get("five"));

    }
}
