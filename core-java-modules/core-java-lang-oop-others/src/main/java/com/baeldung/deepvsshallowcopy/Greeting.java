package com.baeldung.deepvsshallowcopy;

import java.io.Serializable;

public class Greeting implements Cloneable, Serializable {

    private String value;
    private Language language;

    public Greeting(String value, Language language) {
        this.value = value;
        this.language = language;
    }

    @Override
    public Greeting clone() {
        try {
            return (Greeting) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
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
