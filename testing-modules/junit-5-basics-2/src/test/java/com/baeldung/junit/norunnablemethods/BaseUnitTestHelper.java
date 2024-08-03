package com.baeldung.junit.norunnablemethods;

public class BaseUnitTestHelper {

    public String formatString(String text) {
        return (text == null) ? text : text.replace("$", "_");
    }
}
