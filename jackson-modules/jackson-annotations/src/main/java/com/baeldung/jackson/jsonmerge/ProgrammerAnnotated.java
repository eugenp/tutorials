package com.baeldung.jackson.jsonmerge;

import com.fasterxml.jackson.annotation.JsonMerge;

public class ProgrammerAnnotated {

    private String name;
    private String favouriteLanguage;
    @JsonMerge
    private Keyboard keyboard;

    public ProgrammerAnnotated(String name, String favouriteLanguage, Keyboard keyboard) {
        this.name = name;
        this.favouriteLanguage = favouriteLanguage;
        this.keyboard = keyboard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavouriteLanguage() {
        return favouriteLanguage;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    @Override
    public String toString() {
        return "Programmer{" + "name='" + name + '\'' + ", favouriteLanguage='" + favouriteLanguage + '\'' + ", keyboard=" + keyboard + '}';
    }
}
