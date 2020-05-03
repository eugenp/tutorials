package com.baeldung.factorymethod;

public class SingletonBarFactory {

    private static final Bar INSTANCE = new Bar("unnamed");
    
    public static Bar createInstance(String name) {
        INSTANCE.setName(name);
        return INSTANCE;
    }
}
