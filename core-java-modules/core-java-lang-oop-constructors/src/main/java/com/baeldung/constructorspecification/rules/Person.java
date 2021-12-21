package com.baeldung.constructorspecification.rules;

/**
 * Created by arash on 16.12.21.
 */

class Person {

    String name;

    public Person() {
        this("Arash");
    }

    public Person(String name) {
        this.name = name;
    }
}




