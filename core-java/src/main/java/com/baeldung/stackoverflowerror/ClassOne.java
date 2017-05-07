package com.baeldung.stackoverflowerror;

public class ClassOne {
    private int oneValue;
    private ClassTwo clsTwoInstance = null;
    
    public ClassOne() {
        oneValue = 0;
        clsTwoInstance = new ClassTwo();
    }
}
