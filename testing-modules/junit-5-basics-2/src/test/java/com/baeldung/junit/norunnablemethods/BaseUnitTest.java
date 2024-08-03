package com.baeldung.junit.norunnablemethods;

public abstract class BaseUnitTest {

    public String formatString(String text) {
        return (text == null) ? text : text.replace("$", "_");
    }
}
