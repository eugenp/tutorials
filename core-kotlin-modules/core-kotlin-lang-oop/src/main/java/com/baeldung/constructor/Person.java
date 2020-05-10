package com.baeldung.constructor;

class PersonJava {
    final String name;
    final String surname;
    final Integer age;

    public PersonJava(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.age = null;
    }

    public PersonJava(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}
