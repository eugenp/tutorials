package com.baeldung.internal.repo.sample;

public class Greeting {
    private String value;
    private Language language;

    public Greeting(String value, Language language) {
        this.value = value;
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
