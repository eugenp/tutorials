package com.baeldung.constructorspecification.simple;

class Person {

    String name;

    public Person() {
        this("Arash");   //ExplicitConstructorInvocation
    }

    public Person(String name) {
        this.name = name;
    }
}