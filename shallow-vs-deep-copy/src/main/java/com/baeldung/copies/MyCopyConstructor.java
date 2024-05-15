package com.baeldung.copies;

public class MyCopyConstructor {

    private String test;

    public MyCopyConstructor(MyCopyConstructor myCopyConstructor) {
        this.test = myCopyConstructor.getTest();
    }

    public MyCopyConstructor(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }
}
