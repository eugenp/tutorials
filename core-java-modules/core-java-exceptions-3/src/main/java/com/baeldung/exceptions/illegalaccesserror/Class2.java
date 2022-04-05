package com.baeldung.exceptions.illegalaccesserror;

public class Class2 {

    public void foo() {
        Class1 c1 = new Class1();
        c1.bar();
    }
}
    