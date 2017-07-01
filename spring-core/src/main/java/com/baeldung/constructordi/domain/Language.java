package com.baeldung.constructordi.domain;

public class Language {
    private String value;
    
    public Language(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return String.format("%s", value);
    }
}
