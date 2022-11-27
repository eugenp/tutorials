package com.baeldung.copying.java;

import java.util.List;

public class Person {
    private List<String> names;
    private String surname;

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
