package com.baeldung.convertphonenumberinwordstonumberwithjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UseIfElseToConvertPhoneNumberInWordToNumberUnitTest {

    @Test
    public void givenStringWithWhiteSpaces_WhenConvertPhoneNumberInWordToNumber_ThenEquivalentNumber(){

        String firstResult = UseIfElseToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five eight three two four triple eight");
        assertEquals("58324888", firstResult);

        String secondResult = UseIfElseToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("");
        assertNotEquals(-1, secondResult);

        String thirdResult = UseIfElseToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("five eight three two four penta eight");
        assertEquals("5832488888", thirdResult);

        String fourthResult = UseIfElseToConvertPhoneNumberInWordToNumber.ConvertPhoneNumberInWordToNumber("one two three four five six seven eight nine zero");
        assertEquals("1234567890", fourthResult);
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenMapModifiers_ThenEquivalentNumber(){
        int firstResult = UseIfElseToConvertPhoneNumberInWordToNumber.mapModifiers("double");
        assertEquals(2, firstResult);

        int secondResult = UseIfElseToConvertPhoneNumberInWordToNumber.mapModifiers("two");
        assertNotEquals(2, secondResult);

        int thirdResult = UseIfElseToConvertPhoneNumberInWordToNumber.mapModifiers("");
        assertEquals(-1, thirdResult);
    }

    @Test
    public void givenStringWithWhiteSpaces_WhenMapIndividualDigits_ThenEquivalentNumber(){
        int firstResult = UseIfElseToConvertPhoneNumberInWordToNumber.mapIndividualDigits("five");
        assertEquals(5, firstResult);

        int secondResult = UseIfElseToConvertPhoneNumberInWordToNumber.mapIndividualDigits("penta");
        assertNotEquals(5, secondResult);

        int thirdResult = UseIfElseToConvertPhoneNumberInWordToNumber.mapIndividualDigits("");
        assertEquals(-1, thirdResult);
    }
}