package com.baeldung.dependencyinjectiontypes;

public class AnyClassConstructorDI {

    private AnotherClass anotherClass;

    public AnyClassConstructorDI(AnotherClass anotherClass) {
        this.anotherClass = anotherClass;
    }
}
