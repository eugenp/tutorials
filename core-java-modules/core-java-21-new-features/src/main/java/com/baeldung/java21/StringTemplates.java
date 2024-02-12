package com.baeldung.java21;

public class StringTemplates {

    public String getStringTemplate() {
        String name = "Baeldung"; 
        String welcomeText = STR."Welcome to \{name}"; 
        return welcomeText;
    }
}
