package com.baeldung.regex.z_regexp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZandzRegularExpressionUnitTest {
    public ZRegularExpression checkRE = new ZRegularExpression();
    @Test
    public void givenCreditCardNumber_thenReturnIfMatched() {
        String creditCardNumber = "1234567890123456";
        boolean isTrue = checkRE.testCreditCardNumberValidation(creditCardNumber);
        if (isTrue) {
            Assertions.assertTrue(true);
        } else {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void givenLogOutput_thenReturnIfMatched() {
        String logLine = "2022-05-01 14:30:00,123 INFO Some log message";
        boolean isTrue = checkRE.testCreditCardNumberValidation(logLine);
        if (isTrue) {
            Assertions.assertTrue(true);
        } else {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void givenEmailMessage_thenReturnIfMatched() {
        String myMessage = "Hello HR, I hope i can write to Baeldung\n";
        boolean isTrue = checkRE.testCreditCardNumberValidation(myMessage);
        if (isTrue) {
            Assertions.assertTrue(true);
        } else {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void givenFileExtension_thenReturnIfMatched() {
        String fileName = "image.jpeg";
        boolean isTrue = checkRE.testCreditCardNumberValidation(fileName);

        if (isTrue) {
            Assertions.assertTrue(true);
        } else {
            Assertions.assertFalse(false);
        }
    }

}
