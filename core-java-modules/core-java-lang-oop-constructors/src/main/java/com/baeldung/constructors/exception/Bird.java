package com.baeldung.constructors.exception;

public class Bird extends Animal {

    // Note that we are throwing parent exception
    public Bird() throws ReflectiveOperationException {
        super();
    }

    public Bird(String id, int age) {
        super(id, age);
    }
}
