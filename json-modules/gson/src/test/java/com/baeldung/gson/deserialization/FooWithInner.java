package com.baeldung.gson.deserialization;

public class FooWithInner {
    public int intValue;
    public String stringValue;
    public InnerFoo innerFoo;

    public FooWithInner(int intValue, String stringValue, String name) {
        super();
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.innerFoo = new InnerFoo(name);
    }

    public class InnerFoo {
        public String name;

        public InnerFoo(String name) {
            super();
            this.name = name;
        }

    }
}
