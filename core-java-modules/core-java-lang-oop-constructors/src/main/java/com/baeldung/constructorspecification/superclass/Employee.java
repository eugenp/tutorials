package com.baeldung.constructorspecification.superclass;

class Employee extends Person {

    String name;

    public Employee(int id) {
        super(id);   //ExplicitConstructorInvocation
    }

    public Employee(int id, String name) {
        super(id);
        this.name = name;
    }
}