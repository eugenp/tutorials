package com.baeldung.supertypecompilerexception;

public class MyClassSolution3 {

    private static final int SOME_CONSTANT = 10;
    private int myField2;

    public MyClassSolution3() {
        this(SOME_CONSTANT);
    }

    public MyClassSolution3(int i) {
        myField2 = i;
    }
}
