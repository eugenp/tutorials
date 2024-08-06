package com.baeldung.junit.norunnablemethods;

public class NameUtilTest {

    public String formatName(String name) {
        return (name == null) ? name : name.replace("$", "_");
    }
}
