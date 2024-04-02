package com.baeldung.statementsbeforesuper;

public class Parent {
    public Parent(int id) {
        System.out.println("Parametrized Parent constructor");
    }

    public Parent() {
        System.out.println("Parent constructor");
    }
}