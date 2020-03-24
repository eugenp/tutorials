package com.baeldung.immutable.default_;

import org.immutables.value.Value;

@Value.Immutable(prehash = true)
public abstract class Person {

    abstract String getName();

    @Value.Default
    Integer getAge() {
        return 42;
    }
}
