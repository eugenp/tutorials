package com.baeldung.injection;

public class UserPreferences {
    private String name;

    public UserPreferences() {
    }

    public UserPreferences(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
