package com.baeldung.supertypecompilerexception;

public class MyClass {

    private int myField1 = 10;
    private int myField2;

    public MyClass() {
        //uncomment this to see the supertype compiler error:
        //this(myField1);
    }

    public MyClass(int i) {
        myField2 = i;
    }
}