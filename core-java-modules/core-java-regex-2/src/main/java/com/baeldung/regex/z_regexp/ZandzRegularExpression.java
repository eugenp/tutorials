package com.baeldung.regex.z_regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZandzRegularExpression {

public void testCreditCardNumberValidation() {
    String creditCardNumber = "1234567890123456";
    String pattern = "\\d{16}\\z";
    if (creditCardNumber.matches(pattern)) {
        System.out.println("Match found!");
    } else {
        System.out.println("Match not found.");
    }
}

public void testLogParsing() {
    String logLine1 = "2022-05-01 14:30:00,123 INFO Some log message";
    String logLine2 = "2022-05-01 14:30:00,456 DEBUG Some debug message";
    String pattern = ".*message\\z";
    if (logLine1.matches(pattern)) {
        System.out.println("Match found!");
    } else {
        System.out.println("Match not found.");
    }
    if (logLine2.matches(pattern)) {
        System.out.println("Match found!");
    } else {
        System.out.println("Match not found.");
    }
}

public void testEndOfMessageRegex() {
    String myMessage = "Hello HR, I hope i can write to Baeldung\n";
    String pattern = ".*Baeldung\\s*\\Z";
    if (myMessage.matches(pattern)) {
        System.out.println("Match found!");
    } else {
        System.out.println("Match not found.");
    }
}

public void testFileExtensionMatching() {
    String fileName1 = "image.jpeg";
    String fileName2 = "document.pdf";
    String pattern = ".*\\.jpeg\\Z";
    String pattern2 = ".*\\.pdf\\Z";
    if (fileName1.matches(pattern)) {
        System.out.println("Match found!");
    } else {
        System.out.println("Match not found.");
    }
    if (fileName2.matches(pattern2)) {
        System.out.println("Match found!");
    } else {
        System.out.println("Match not found.");
    }
}
}
