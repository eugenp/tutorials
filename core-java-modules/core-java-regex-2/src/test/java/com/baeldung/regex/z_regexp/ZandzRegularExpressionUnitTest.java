package com.baeldung.regex.z_regexp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class ZandzRegularExpressionUnitTest {
    @Test
    public void givenCreditCardNumber_whenPatternFounded_thenReturnIfMatched() {
        String creditCardNumber = "1234567890123456";
        String extraText = "123";
        String pattern = "\\d{16}\\z";
        Assertions.assertTrue(creditCardNumber.matches(pattern));
        Assertions.assertFalse((creditCardNumber + extraText).matches(pattern));
    }

    @Test
    public void givenLogOutput_whenPatternFound_thenReturnIfMatched() {
        String logLine1 = "2022-05-01 14:30:00,123 INFO Some log message";
        String logLine2 = "2022-05-01 14:30:00,456 DEBUG Some debug message";
        String pattern = ".*message\\z";
        Assertions.assertTrue(logLine1.matches(pattern));
        Assertions.assertTrue(logLine2.matches(pattern));
        Assertions.assertFalse((logLine1 + "\n" + logLine2).matches(pattern));
    }

    @Test
    public void givenEmailMessage_whenPatternFound_thenReturnIfMatched() {
        String myMessage = "Hello HR, I hope i can write to Baeldung\n";
        String pattern = ".*Baeldung\\s*\\Z";
        Assertions.assertTrue(myMessage.matches(pattern));
        Assertions.assertFalse(myMessage.matches(pattern));
    }

    @Test
    public void givenFileExtension_whenPatternFound_thenReturnIfMatched() {
        String fileName1 = "image.jpeg";
        String fileName2 = "document.pdf";
        String pattern = ".*\\.jpeg\\Z";
        String pattern2 = ".*\\.pdf\\Z";
        Assertions.assertTrue(fileName1.matches(pattern));
        Assertions.assertTrue(fileName2.matches(pattern2));
        Assertions.assertFalse((fileName1 + ".txt").matches(pattern));
    }

}
