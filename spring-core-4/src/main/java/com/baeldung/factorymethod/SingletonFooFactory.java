package com.baeldung.factorymethod;

public class SingletonFooFactory {

    private static final Foo INSTANCE = new Foo();
    
    public static Foo createInstance() {
        return INSTANCE;
    }
}
