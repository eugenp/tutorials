package com.baeldung.jackson.jsonmerge;

public class ProgrammerNotAnnotated {

    private String name;
    private String favouriteLanguage;
    private Keyboard keyboard;

    public ProgrammerNotAnnotated(String name, String favouriteLanguage, Keyboard keyboard) {
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

    public void setFavouriteLanguage(String favouriteLanguage) {
        this.favouriteLanguage = favouriteLanguage;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public String toString() {
        return "Programmer{" + "name='" + name + '\'' + ", favouriteLanguage='" + favouriteLanguage + '\'' + ", keyboard=" + keyboard + '}';
    }
}
