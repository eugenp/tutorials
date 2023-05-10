package com.baeldung.regex.z_regexp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZandzRegularExpressionUnitTest {
    @Test
    public void givenCreditCardNumber_thenReturnIfMatched() {
        String creditCardNumber = "1234567890123456";
        String pattern = "\\d{16}\\z";
        boolean isTrue = creditCardNumber.matches(pattern);
        Assertions.assertTrue(isTrue);
    }

    @Test
    public void givenLogOutput_thenReturnIfMatched() {
        String logLine = "2022-05-01 14:30:00,123 INFO Some log message";
        String pattern = ".*message\\z";
        boolean isTrue = logLine.matches(pattern);
        Assertions.assertTrue(isTrue);
    }

    @Test
    public void givenEmailMessage_thenReturnIfMatched() {
        String myMessage = "Hello HR, I hope i can write to Baeldung\n";
        String pattern = ".*Baeldung\\s*\\Z";
        boolean isTrue = myMessage.matches(pattern);
        Assertions.assertTrue(isTrue);
    }

    @Test
    public void givenFileExtension_thenReturnIfMatched() {
        String fileName = "image.jpeg";
        String pattern = ".*\\.jpeg\\Z";
        boolean isTrue = fileName.matches(pattern);
        Assertions.assertTrue(isTrue);
    }

}
