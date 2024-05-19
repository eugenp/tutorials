package com.baeldung.copies;

public class MyClass {

    private int i;

    private Foo foo;

    public MyClass(Foo foo) {
        this.foo = foo;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }
}
