package com.baeldung.statementsbeforesuper;

class Child extends Parent {
    Child() {
        super(); // Or super(10); Correct placements
        System.out.println("Child constructor");
        additionalInitialization();
        // super(); Compilation error: Constructor call must be the first statement in a constructor
    }

    private void additionalInitialization() {
        System.out.println("Additional initialization in Child");
    }
}
