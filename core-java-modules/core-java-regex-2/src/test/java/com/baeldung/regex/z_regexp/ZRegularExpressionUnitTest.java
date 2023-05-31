package com.baeldung.regex.z_regexp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
public class ZRegularExpressionUnitTest {
    @Test
    public void givenCreditCardNumber_thenReturnIfMatched() {
        String creditCardNumber = "1234567890123456";
        String creditCardNumber2 = "1234567890123456\n";
        String pattern = "\\d{16}\\z";
        Assertions.assertTrue(Pattern.compile(pattern).matcher(creditCardNumber).find());
        Assertions.assertFalse(Pattern.compile(pattern).matcher(creditCardNumber2).find());
    }

    @Test
    public void givenCreditCardNumber_thenReturnIfNotMatched() {
        String creditCardNumber = "1234567890123456\n";
        String pattern = "\\d{16}\\z";
        Assertions.assertFalse(Pattern.compile(pattern).matcher(creditCardNumber).find());
    }

    @Test
    public void givenLogOutput_thenReturnIfMatched() {
        String logLine = "2022-05-01 14:30:00,123 INFO Some log message";
        String pattern = ".*message\\z";
        Assertions.assertTrue(Pattern.compile(pattern).matcher(logLine).find());
    }

    @Test
    public void givenLogOutput_thenReturnIfNotMatched() {
        String logLine = "2022-05-01 14:30:00,123 INFO Some log message\n";
        String pattern = ".*message\\z";
        Assertions.assertFalse(Pattern.compile(pattern).matcher(logLine).find());
    }
    @Test
    public void givenEmailMessage_thenReturnIfMatched() {
        String myMessage = "Hello HR, I hope i can write to Baeldung\n";
        String myMessage2 = "Hello HR, I hope\n i can write to Baeldung";
        String pattern = ".*Baeldung\\Z";
        String pattern2 = ".*hope\\Z";
        Assertions.assertTrue(Pattern.compile(pattern).matcher(myMessage).find());
        Assertions.assertFalse(Pattern.compile(pattern2).matcher(myMessage2).find());
    }

    @Test
    public void givenEmailMessage_thenReturnIfNotMatched() {
        String myMessage = "Hello HR, I hope\n i can write to Baeldung";
        String pattern = ".*hope\\Z";
        Assertions.assertFalse(Pattern.compile(pattern).matcher(myMessage).find());
    }

    @Test
    public void givenFileExtension_thenReturnIfMatched() {
        String fileName = "image.jpeg\n";
        String fileName2 = "image2.jpeg\n.png";
        String pattern = ".*\\.jpeg\\Z";
        Assertions.assertTrue(Pattern.compile(pattern).matcher(fileName).find());
        Assertions.assertFalse(Pattern.compile(pattern).matcher(fileName2).find());
    }

    @Test
    public void givenFileExtension_thenReturnIfNotMatched() {
        String fileName = "image2.jpeg\n.png";
        String pattern = ".*\\.jpeg\\Z";
        Assertions.assertFalse(Pattern.compile(pattern).matcher(fileName).find());
    }
    @Test
    public void givenURL_thenReturnIfMatched() {
        String url = "https://www.example.com/api/endpoint\n";
        String pattern = ".*/endpoint$";
        Assertions.assertTrue(Pattern.compile(pattern).matcher(url).find());
    }

    @Test
    public void givenSentence_thenReturnIfMatched() {
        String sentence = "Hello, how are you?";
        String pattern = ".*[.?!]$";
        Assertions.assertTrue(Pattern.compile(pattern).matcher(sentence).find());
    }

}