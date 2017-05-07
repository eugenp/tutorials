package com.baeldung.stackoverflowerror;

public class ClassTwo {
    private int twoValue;
    private ClassOne clsOneInstance = null;
    
    public ClassTwo() {
        twoValue = 10;
        clsOneInstance = new ClassOne();
    }
}
