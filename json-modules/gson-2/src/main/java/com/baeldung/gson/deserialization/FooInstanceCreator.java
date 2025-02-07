package com.baeldung.gson.deserialization;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class FooInstanceCreator implements InstanceCreator<Foo> {

    @Override
    public Foo createInstance(Type type) {
        return new Foo("sample");
    }

}
