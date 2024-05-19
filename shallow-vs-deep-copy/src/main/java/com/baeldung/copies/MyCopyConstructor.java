package com.baeldung.copies;

public class MyCopyConstructor {

    private String test;

    private Foo foo;

    public MyCopyConstructor(MyCopyConstructor myCopyConstructor) {
        this.test = myCopyConstructor.getTest();
        this.foo = new Foo(myCopyConstructor.getFoo()
          .getI());
    }

    public MyCopyConstructor(String test, Foo foo) {
        this.test = test;
        this.foo = foo;
    }

    public String getTest() {
        return test;
    }

    public Foo getFoo() {
        return foo;
    }
}
