package com.baeldung.regex.z_regexp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZRegularExpressionUnitTest {
    @Test
    public void givenCreditCardNumber_thenReturnIfMatched() {
        String creditCardNumber = "1234567890123456";
        String pattern = "\\d{16}\\z";
        Assertions.assertTrue(creditCardNumber.matches(pattern));
    }

    @Test
    public void givenLogOutput_thenReturnIfMatched() {
        String logLine = "2022-05-01 14:30:00,123 INFO Some log message";
        String pattern = ".*message\\z";
        Assertions.assertTrue(logLine.matches(pattern));
    }

    @Test
    public void givenEmailMessage_thenReturnIfMatched() {
        String myMessage = "Hello HR, I hope i can write to Baeldung\n";
        String pattern = ".*Baeldung\\s*\\Z";
        Assertions.assertTrue(myMessage.matches(pattern));
    }

    @Test
    public void givenFileExtension_thenReturnIfMatched() {
        String fileName = "image.jpeg";
        String pattern = ".*\\.jpeg\\Z";
        Assertions.assertTrue(fileName.matches(pattern));
    }

}
