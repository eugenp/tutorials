package com.baeldung.beaninjection.domain;

public class PersonalData {

    private String name;
    private String surname;

    public PersonalData(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", surname='" + surname + '\'';
    }
}
