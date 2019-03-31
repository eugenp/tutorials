package com.baeldung.supertypecompilerexception;

public class MyClassSolutionI {

    private int myField1 = 10;
    private int myField2;

    public MyClassSolutionI() {
        myField2 = myField1;
    }

    public MyClassSolutionI(int i) {
        myField2 = i;
    }
}
