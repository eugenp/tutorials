package com.baeldung.dependencyinjectiontypes;

public class AnyClassSetterDI {

    private AnotherClass anotherClass;

    public void setAnotherClass(AnotherClass anotherClass) {
        this.anotherClass = anotherClass;
    }
}