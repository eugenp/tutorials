package com.baeldung.copying.java;

import java.util.List;

public class Person {
    private final List<String> names;
    private final String surname;

    public Person(List<String> names, String surname) {
        this.names = names;
        this.surname = surname;
    }

    public List<String> getNames() {
        return names;
    }

    public String getSurname() {
        return surname;
    }
}
