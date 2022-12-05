package com.baeldung.copying.java;

import java.util.List;

public class ImmutablePerson {
    private final List<String> names;
    private final String surname;

    public ImmutablePerson(List<String> names, String surname) {
        this.names = names;
        this.surname = surname;
    }

    public List<String> getNames() {
        return List.copyOf(names);
    }

    public String getSurname() {
        return surname;
    }
}
