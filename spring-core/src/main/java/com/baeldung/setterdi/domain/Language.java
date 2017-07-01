package com.baeldung.setterdi.domain;

public class Language {
    private String value;
    
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s", value);
    }
}
