package com.baeldung.regex.z_regexp;

public class ZRegularExpression {

public boolean testCreditCardNumberValidation(String CreditCardNumber) {
    String pattern = "\\d{16}\\z";
    if (CreditCardNumber.matches(pattern)) {
        return true;
    } else {
        return false;
    }
}

public boolean testLogParsing(String LogLine) {
    String pattern = ".*message\\z";
    if (LogLine.matches(pattern)) {
        return true;
    } else {
        return false;
    }

}

public boolean testEndOfMessageRegex(String MyMessage) {
    String pattern = ".*Baeldung\\s*\\Z";
    if (MyMessage.matches(pattern)) {
        return true;
    } else {
        return false;
    }
}

public boolean testFileExtensionMatching(String FileName) {
    String pattern = ".*\\.jpeg\\Z";
    if (FileName.matches(pattern)) {
        return true;
    } else {
        return false;
    }
}
}
