package com.baeldung.constructorspecification.rules;

class Employee extends Person {

    int id;

    public Employee() {
        super();
    }

    public Employee(String name) {
        super(name);
    }

    public Employee(int id) {
        this();
        //super("John"); // syntax error
        this.id = id;
    }

    public static void main(String[] args) {
        new Employee(100);
    }
}