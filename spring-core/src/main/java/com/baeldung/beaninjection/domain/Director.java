package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;

public class Director {

    private String name;
    private String surname;

    @Autowired
    public Director(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Director{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
