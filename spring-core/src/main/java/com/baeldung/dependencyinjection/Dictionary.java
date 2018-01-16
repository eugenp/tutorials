package com.baeldung.dependencyinjection;

public class Dictionary {

    private String language;

    public Dictionary() {
        this.language = "English";
    }

    public Dictionary(String language) {
        this.language = language;
    }

    public String hello() {
        return "Hello from dictionary in " + language;
    }
}
