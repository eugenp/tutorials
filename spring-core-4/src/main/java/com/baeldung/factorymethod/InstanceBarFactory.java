package com.baeldung.factorymethod;

public class InstanceBarFactory {

    public Bar createInstance(String name) {
        return new Bar(name);
    }
}
