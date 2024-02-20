package com.baeldung.emailandphonemasking;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailAndPhoneMaskingUnitTest {


    String phoneNumber = "+12344567890";
    String expectedMaskedPhoneNumber = "+*******7890";
    String email = "testemailaddress@example.com";
    String expectedMaskedEmail = "te**************@example.com";

    @Test
    public void givenEmailAddress_whenUsingStringManipulation_thenMaskEmail() {
        int atIndex = email.indexOf('@');
        String repeatedString = IntStream.range(0, atIndex - 2).mapToObj(i -> "*").collect(Collectors.joining());
        String maskedPart = email.substring(0, atIndex - repeatedString.length()) + repeatedString;
        String maskedEmail = maskedPart + email.substring(atIndex);
        assertEquals(expectedMaskedEmail, maskedEmail);
    }

    @Test
    public void givenEmailAddress_whenUsingRegex_thenMaskEmail() {
        int atIndex = email.indexOf('@');
        String regex = "(.{2})(.*)(@.*)";
        String repeatedAsterisks = "*".repeat(atIndex - 2);
        String maskedEmail = email.replaceAll(regex, "$1" + repeatedAsterisks + "$3");
        assertEquals(expectedMaskedEmail, maskedEmail);
    }


    @Test
    public void givenPhoneNumber_whenUsingStringManipulation_thenMaskPhone() {
        String maskedPhoneNumber = phoneNumber.replaceAll("\\d(?=\\d{4})", "*");
        assertEquals(expectedMaskedPhoneNumber, maskedPhoneNumber);
    }

    @Test
    public void givenPhoneNumber_whenUsingRegex_thenMaskPhone() {
        int lastDigitsIndex = phoneNumber.length() - 5;
        String regex = "(\\+)(\\d+)(\\d{4})";
        String repeatedAsterisks = "*".repeat(Math.max(0, lastDigitsIndex));
        String maskedPhoneNumber = phoneNumber.replaceAll(regex, "$1" + repeatedAsterisks + "$3");
        assertEquals(expectedMaskedPhoneNumber, maskedPhoneNumber);
    }


}
