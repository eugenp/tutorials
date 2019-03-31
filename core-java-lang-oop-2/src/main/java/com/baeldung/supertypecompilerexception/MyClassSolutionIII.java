package com.baeldung.supertypecompilerexception;

public class MyClassSolutionIII {

    private static final int SOME_CONSTANT = 10;
    private int myField2;

    public MyClassSolutionIII() {
        this(SOME_CONSTANT);
    }

    public MyClassSolutionIII(int i) {
        myField2 = i;
    }
}
