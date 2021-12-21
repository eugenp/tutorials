package com.baeldung.constructorspecification.superclass;

/**
 * Created by arash on 16.12.21.
 */

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
