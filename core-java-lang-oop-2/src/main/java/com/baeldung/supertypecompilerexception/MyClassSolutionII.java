package com.baeldung.supertypecompilerexception;

public class MyClassSolutionII {

    private int myField1 = 10;
    private int myField2;

    public MyClassSolutionII() {
        setupMyFields(myField1);
    }

    public MyClassSolutionII(int i) {
        setupMyFields(i);
    }

    private void setupMyFields(int i) {
        myField2 = i;
    }
}
