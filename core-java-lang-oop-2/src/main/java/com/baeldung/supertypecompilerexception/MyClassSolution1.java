package com.baeldung.supertypecompilerexception;

public class MyClassSolution1 {

    private int myField1 = 10;
    private int myField2;

    public MyClassSolution1() {
        myField2 = myField1;
    }

    public MyClassSolution1(int i) {
        myField2 = i;
    }
}
