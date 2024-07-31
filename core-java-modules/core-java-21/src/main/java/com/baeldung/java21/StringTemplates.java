package com.baeldung.java21;

public class StringTemplates {

    public String getStringTemplate() {
        String name = "Baeldung"; 
        return STR."Welcome to \{name}"; 
    }
}
