package com.baeldung.supertypecompilerexception;

public class MyClassSolution2 {

    private int myField1 = 10;
    private int myField2;

    public MyClassSolution2() {
        setupMyFields(myField1);
    }

    public MyClassSolution2(int i) {
        setupMyFields(i);
    }

    private void setupMyFields(int i) {
        myField2 = i;
    }
}
